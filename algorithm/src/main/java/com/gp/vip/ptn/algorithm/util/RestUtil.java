package com.gp.vip.ptn.algorithm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author: Fred
 * @date: 2020/5/28 3:51 下午
 * @description: HttpUtils (类的描述)
 */
@Slf4j
public class RestUtil {

    public static <T> T postTemplate(String url, String body, Class<T> clazz) {
        return JSON.parseObject(postTemplate(url, body), clazz);
    }

    public static <T> T postTemplate(String url, String body, Map<String, Object> headers, Class<T> clazz) {
        return JSON.parseObject(HttpUtil.doPostJson(url, body, headers), clazz);
    }


    public static String postTemplate(String url, String body) {
        //StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON.withCharset(Charsets.UTF_8));
        log.info("调用接口, url:{}, 参数:{}", url, body);
        String result = HttpUtil.doPostJson(url, body);

        return result;
    }


    private static RestTemplate template = new RestTemplate();

    public static <T> T rest(String url, String method, Class<T> responseType) {
        return rest(url, method, () -> new HttpEntity<>(null, headers()), responseType);
    }

    public static <T> T rest(String url, String method, String body, Class<T> responseType) {
        return rest(url, method, () -> new HttpEntity<>(body, headers()), responseType);
    }

    public static <T> T rest(String url, String method, Class<T> responseType, Object... uriVariables) {
        return rest(url, method, () -> new HttpEntity<>(null, headers()), responseType, uriVariables);
    }

    public static <T> T rest(String url, String method, String body, Class<T> responseType, Object... uriVariables) {
        return rest(url, method, () -> new HttpEntity<>(body, headers()), responseType, uriVariables);
    }

    public static <T> T rest(String url, String method, Class<T> responseType, Map<String, ?>... uriVariables) {
        return rest(url, method, () -> new HttpEntity<>(null, headers()), responseType, uriVariables);
    }

    public static <T> T rest(String url, String method, @Nullable String body, Class<T> responseType, Map<String, ?>... uriVariables) {
        return rest(url, method, () -> new HttpEntity<>(body, headers()), responseType, uriVariables);
    }

    private static MultiValueMap<String, String> headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAcceptCharset();
        //headers.add("_security_token_inc","91586594337790255");
        return headers;
    }

    public static <T> T rest(String url, String method, Supplier<HttpEntity> entitySupplier, Class<T> responseType) {
        //Class<T> responseType = null;
        ResponseEntity<T> exchange = template
                .exchange(url, HttpMethod.resolve(method.toUpperCase()), entitySupplier.get(), responseType);
        return exchange.getBody();
    }

    public static <T> T  rest(String url, String method, Supplier<HttpEntity> entitySupplier, Class<T> responseType, Object... uriVariables) {

        ResponseEntity<T> exchange = template
                .exchange(url, HttpMethod.resolve(method.toUpperCase()), entitySupplier.get(), responseType, uriVariables);
        return exchange.getBody();
    }

    public static <T> T rest(String url, String method, Supplier<HttpEntity> entitySupplier, Class<T> responseType, Map<String, ?> uriVariables) {

        ResponseEntity<T> exchange = template
                .exchange(url, HttpMethod.resolve(method.toUpperCase()), entitySupplier.get(), responseType, uriVariables);
        return exchange.getBody();
    }

    public static <T> T rest(String url, String method, String body, HttpHeaders headers, BiFunction<HttpHeaders, String, HttpEntity> entityCascade, Class<T> responseType, Object... uriVariables) {

        ResponseEntity<T> exchange = template
                .exchange(url, HttpMethod.resolve(method.toUpperCase()), entityCascade.apply(headers, body), responseType, uriVariables);
        return exchange.getBody();
    }


    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject(null);
        System.out.println("---" + jsonObject + "---");
    }
}
