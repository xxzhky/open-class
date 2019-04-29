package com.dt.open.hotloaded.plg;

import com.alibaba.fastjson.JSON;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import sun.nio.cs.ext.PCK;

import java.io.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.*;

public class DefaultSpringPluginFactory implements ApplicationContextAware, InitializingBean, SpringPluginFactory {

    private static final String BASE_DIR;
    private Map<String, PluginConfig> configs= new HashMap<>();
    private ApplicationContext applicationContext;
    private Map<String, Advice> adviceCache = new HashMap<>();//当前安装且激活的插件

    static{
        BASE_DIR=System.getProperty("user.home")+"/.plugins/";
    }

    /**
     * 1.获取plugin配置
     * 2.获取ioc中当前所有bean
     * 3.安装条件验证
     * 4.构建切面通知对象
     * 5.添加至aop切面
     * @param pluginId
     */
    @Override
    public void activePlugin(String pluginId) {

        if (configs.containsKey(pluginId)){
            throw new RuntimeException(String.format("已存在指定的插件 id=%s", pluginId));
        }
        PluginConfig config= configs.get(pluginId);
        //获取所有IOC-bean
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean= applicationContext.getBean(name);
            if (bean==this){
                continue;
            }
            if (!(bean instanceof Advised))
                continue;
            //是否重复安装
            if (findAdvice(config.getClassName(), (Advised)bean)!=null)
                continue;
            Advice advice = null;
            try {
                advice=buildAdvice(config);
                ((Advised) bean).addAdvice(advice);
            } catch (Exception e) {
                //e.printStackTrace();
                throw new RuntimeException("安装失败", e);
            }
        }
        config.setActive(true);
        try {
            storeConfigs();
        } catch (IOException e) {
            //回滚已经添加的切面
            throw new RuntimeException("激活时报", e);
        }

    }

    /**
     * 1.获取配置 2.判断指定的bean是否已切入了指定的通知 3.调用remove方法进行移除 4.保存配置至本地
     */
    @Override
    public void disablePlugin(String pluginId) {
        if (configs.containsKey(pluginId)){
            throw new RuntimeException(String.format("已存在指定的插件 id=%s", pluginId));
        }
        PluginConfig config= configs.get(pluginId);
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean= applicationContext.getBean(name);
            if (bean instanceof Advised) {
                Advice advice= findAdvice(config.getClassName(), (Advised)bean);
                ((Advised) bean).removeAdvice(advice);
            }
        }
        config.setActive(false);
        try {
            storeConfigs();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void storeConfigs() throws IOException{
        String baseDir=BASE_DIR;
        File configFile= new File(baseDir+"PluginConfigs.json");
        if (!configFile.exists()){
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
        }
        PluginStore store= new PluginStore();
        store.setPlugins(new ArrayList<>(configs.values()));
        store.setLastModify(new Date());
        String json=JSON.toJSONString(store,true);
        OutputStream outputStream= new FileOutputStream(configFile);
        outputStream.write(json.getBytes( "UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    private Advice findAdvice(String className, Advised advised) {

        for (Advisor advisor : advised.getAdvisors()) {
            if (advisor.getAdvice().getClass().getName().equals(className))
                return advisor.getAdvice();
        }
        return null;
    }

    /**
     * 安装插件
     * 1.下载远程仓库jar
     * 2.jar包动态的装载到classloader
     * 3. 动态切入spring AOP
     *
     * @param plugin
     * @param active
     */
    @Override
    public void installPlugin(PluginConfig plugin, Boolean active) {

        if (configs.containsKey(plugin.getId())){
            throw new RuntimeException(String.format("已存在指定的插件 id=%s", plugin.getId()));
        }
        //load plugins
        configs.put(plugin.getId(), plugin);

        //download the remote plugins
        try {
            buildAdvice(plugin);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private Advice buildAdvice(PluginConfig config) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (adviceCache.containsKey(config.getClassName()))
            return adviceCache.get(config.getClassName());

        File jarFile= new File(getLocalJarFile(config));
        //远程下载plg至本地
        if (!jarFile.exists()){
            URL url= new URL(config.getJarRemoteUrl());
            InputStream stream=url.openStream();
            jarFile.getParentFile().mkdirs();
            try {
                Files.copy(stream,jarFile.toPath());
            } catch (IOException e) {
                jarFile.deleteOnExit();
                throw new RuntimeException(e);
            }
            stream.close();
        }else {
            throw new RuntimeException("重复更新版本");
        }

        //本地jar加载至classloader
        URLClassLoader loader= (URLClassLoader) getClass().getClassLoader();

        URL targetUrl=jarFile.toURI().toURL();
        boolean isLoader= false;
        for (URL url : loader.getURLs()) {
            if (url.equals(targetUrl)){
                isLoader=true;
                break;
            }
        }
        //如果有更新功能，则需要用如下加载器
        //URLClassLoader newLoader= new URLClassLoader(new URL[]{targetUrl}, loader);
        //newLoader.loadClass(config.getClassName());

        if (!isLoader){
            Method addURL= URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            addURL.setAccessible(true);
            addURL.invoke(loader, targetUrl);
        }
        //初始化 Plugin Advice实例化
        Class<?> adviceClass =loader.loadClass(config.getClassName());
        if (!Advice.class.isAssignableFrom(adviceClass)){
            throw new RuntimeException(String.format("plugin 配置错误 s% 非 s%的实现类",config.getClassName(),Advice.class.getName()));
        }
        //构建plugin 实例
        adviceCache.put(adviceClass.getName(), (Advice) adviceClass.newInstance());

        return adviceCache.get(adviceClass.getName());
    }

    private String getLocalJarFile(PluginConfig config) {
        String jarName= config.getJarRemoteUrl().substring(config.getJarRemoteUrl().indexOf("."));
        return BASE_DIR+jarName;
    }

    @Override
    public void uninstallPlugin(String id) {
        if (configs.containsKey(id)){
            throw new RuntimeException(String.format("已存在指定的插件 id=%s", id));
        }
        disablePlugin(id);//禁用指定插件
        configs.remove(id);//从缓存中清除
        try {
            storeConfigs();//更新保存配置
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<PluginConfig> getPluginList() {
        return null;
    }

    @Override
    public String getPluginStatus(String id) {
        return null;
    }

    private void loadLocalPlugins() {
        try {
            Map<String, PluginConfig> localConfig=readerLocalConfigs();
            if (localConfig==null)
                return;
            configs.putAll(localConfig);
            for (PluginConfig config : localConfig.values()) {
                if (config.getActive())
                    activePlugin(config.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取本地配置
     * @return
     * @throws IOException
     */
    private Map<String, PluginConfig> readerLocalConfigs() throws IOException {
        String baseDir=BASE_DIR;
        File configFile= new File(baseDir+"PluginConfigs.json");
        if (!configFile.exists())
            return new HashMap<>();
        InputStream inputStream =new FileInputStream(configFile);
        Map<String, PluginConfig> result = new HashMap<>();
        try {
            ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
            copy(inputStream, outputStream);
            PluginStore store= JSON.parseObject(outputStream.toString("UTF-8"),PluginStore.class);
            for (PluginConfig config : store.getPlugins()) {
                result.put(config.getId(), config);
            }
        } finally {
            inputStream.close();
        }
        return result;
    }

    private void copy(InputStream inputStream, ByteArrayOutputStream outputStream) {
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        loadLocalPlugins();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= applicationContext;
    }
}
