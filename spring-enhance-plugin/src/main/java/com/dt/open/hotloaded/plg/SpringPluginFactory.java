package com.dt.open.hotloaded.plg;

import java.util.List;

public interface SpringPluginFactory {
    /**
     * 激活制定的插件
     */
    public void activePlugin(String pluginId);

    /**
     * 禁用插件
     */
    public void disablePlugin(String pluginId);

    /**
     * 安装插件
     * 1\下载一个jar
     * 2\ classloader 这个jar
     * 3\ 实例化
     * 4\ 把她切入到IOC bean
     */
    public void installPlugin(PluginConfig pluginConfig, Boolean active);

    /**
     * uninstall
     */
    public void uninstallPlugin(String id);

    /**
     * 获取插件列表
     */
    public List<PluginConfig> getPluginList();

    /**
     * 获取插件状态
     */
    public String getPluginStatus(String id);

}
