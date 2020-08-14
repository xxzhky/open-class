package com.gp.vip.ptn.algorithm.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Fred
 * @date: 2020/5/28 4:15 下午
 * @description: HttpUtil (类的描述)
 */
@Slf4j
public class HttpUtil {


    public static String doGet(String url, Map<String, String> map) {
        HttpGet httpGet = null;
        if (map == null || map.size() == 0) {
            httpGet = new HttpGet(url);
            log.info("【send】【doGet】无参 url:" + url);
        } else {
            try {
                URIBuilder uriBuilder = new URIBuilder(url);
                map.forEach((k, v) -> {
                    uriBuilder.addParameter(k, v);
                });
                URI build = uriBuilder.build();
                httpGet = new HttpGet(build);
                log.info("【send】【doGet】有参 url:" + build.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 请求超时时间
                .setSocketTimeout(60000)// 数据读取超时时间
                .build();
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //请求超时时间,这个时间定义了socket读数据的超时时间，也就是连接到服务器之后到从服务器获取响应数据需要等待的时间,发生超时，会抛出SocketTimeoutException异常。
    private static final int SOCKET_TIME_OUT = 60000;
    //连接超时时间,这个时间定义了通过网络与服务器建立连接的超时时间，也就是取得了连接池中的某个连接之后到接通目标url的连接等待时间。发生超时，会抛出ConnectionTimeoutException异常
    private static final int CONNECT_TIME_OUT = 60000;
    private static List<NameValuePair> createParam(Map<String, Object> param) {
        //建立一个NameValuePair数组，用于存储欲传送的参数
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        Optional.ofNullable(param).orElse(Maps.newHashMap()).forEach((k, v) -> {
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(k, v.toString());
            nameValuePairs.add(basicNameValuePair);
        });
        return nameValuePairs;
    }

    public static String doPostJson(String url, String json) {
        return doPostJson(url, json, null);
    }
    /**
     * POST 请求 json
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json, Map<String, Object> headers) {
        log.info("HTTP【doPost】, 请求url: {}, 请求体: {}, 请求头： {}", url, json, (headers));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost req = new HttpPost(url);
        String result = null;
        CloseableHttpResponse response = null;
        try {
            //setHeader,添加头文件
            Optional.ofNullable(headers).orElse(Maps.newHashMap())
                    .forEach((k, v) ->
                            req.setHeader(k, v.toString()));

            //setConfig,添加配置,如设置请求超时时间,连接超时时间
            RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
            req.setConfig(reqConfig);
            //setEntity,添加内容
            StringEntity stringEntity = new StringEntity(json, Consts.UTF_8);
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");

            req.setEntity(stringEntity);
            response = httpClient.execute(req);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String postForAPP(String url, String sMethod, Map<String, Object> param, Map<String, Object> headers) throws Exception {
        //目前HttpClient最新版的实现类为CloseableHttpClient
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity entity=null;
        try {
            if(param != null) {
                //建立Request的对象，一般用目标url来构造，Request一般配置addHeader、setEntity、setConfig
                HttpPost req = new HttpPost(url);
                entity=new UrlEncodedFormEntity(createParam(param), Consts.UTF_8);
                //setHeader,添加头文件
                headers.forEach((k, v) -> {
                    req.setHeader(k, v.toString());
                });

                //setConfig,添加配置,如设置请求超时时间,连接超时时间
                RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).build();
                req.setConfig(reqConfig);
                //setEntity,添加内容
                req.setEntity(entity);
                //执行Request请求,CloseableHttpClient的execute方法返回的response都是CloseableHttpResponse类型
                //其常用方法有getFirstHeader(String)、getLastHeader(String)、headerIterator（String）取得某个Header name对应的迭代器、getAllHeaders()、getEntity、getStatus等
                response = client.execute(req);
                entity =  response.getEntity();
                //用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串,防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
                String result= EntityUtils.toString(entity, Consts.UTF_8);

                if(response.getStatusLine().getStatusCode()==200){
                    log.info(result+"-----------success------------------");
                    return result;
                }else{
                    log.error(response.getStatusLine().getStatusCode()+"------------------fail-----------");
                    return null;
                }
            }
            return null;
        } catch(Exception e) {
            log.error("--------------------------post error: ", e);
            throw new Exception();
        }finally{
            //一定要记得把entity fully consume掉，否则连接池中的connection就会一直处于占用状态
            EntityUtils.consume(entity);
        }
    }


    /**
     * POST 请求 Form
     *
     * @param url
     * @param map
     * @return
     */
    public static String doPostForm(String url, Map<String, Object> map) {
        log.info("【send】【doPost】form, url:" + url + ",参数:" + map);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        /*List<NameValuePair> nameValuePairs = new ArrayList<>();
        map.forEach((k, v) -> {
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(k, v);
            nameValuePairs.add(basicNameValuePair);
        });*/
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(createParam(map), HTTP.UTF_8));
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST 请求 Form/上传图片
     *
     * @param url
     * @param map
     * @param file
     * @return
     */
    public static String doPostFormData(String url, Map<String, String> map, MultipartFile file) {
        log.info("【send】【doPost】formData, url:" + url + ",参数:" + map + "," + file);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.APPLICATION_JSON;
        entityBuilder.setCharset(contentType.getCharset());
        map.forEach((k, v) -> {
            entityBuilder.addTextBody(k, v, contentType);
        });
        String result = null;
        CloseableHttpResponse response = null;
        try {
            entityBuilder.addBinaryBody(file.getName(), file.getBytes(), ContentType.create("multipart/form-data"), file.getName());
            httpPost.setEntity(entityBuilder.build());
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST 请求 无规则字符
     *
     * @param url
     * @param text
     * @return
     */
    public static String doPostText(String url, String text) {
        log.info("【send】【doPost】Text, url:" + url + ",参数:" + text);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        ContentType contentType = ContentType.create("text/plain", "UTF-8");
        StringEntity stringEntity = new StringEntity(text, contentType);
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据url 返回 byte[]
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] UrlGetBytes(String url) {
        URLConnection urlConnection;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] byteArray = null;
        try {
            urlConnection = new URL(url).openConnection();
            inputStream = urlConnection.getInputStream();
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int n;
            while (-1 != (n = inputStream.read(bytes))) {
                outputStream.write(bytes, 0, n);
            }
            byteArray = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArray;
    }

    /**
     * 将url 内容保存到 本地路径
     *
     * @param url
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean UrlSaveToPath(String path, String url) {
        byte[] bytes = HttpUtil.UrlGetBytes(url);
        return HttpUtil.BytesSaveToPath(path, bytes);
    }

    /**
     * 将bytes 内容保存到 本地路径
     *
     * @param path
     * @param bytes
     * @return
     */
    public static boolean BytesSaveToPath(String path, byte[] bytes) {
        if (StringUtils.isEmpty(path) || bytes == null || bytes.length == 0) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
