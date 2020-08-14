package com.gp.vip.ptn.algorithm.highpfm;

import com.gp.vip.ptn.algorithm.util.RestUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: Fred
 * @date: 2020/8/12 12:01 上午
 * @description: (类的描述)
 */


@Slf4j
public class PostService {


    /**
     * 点击观看某条抖音作品详情需要请求三个微服务：
     * <p>
     * 1.         根据id获取作品信息http://host/posts/{postId} 返回 {mediaId,userId, content}
     * <p>
     * 2.         根据mediaId从多媒体服务获取音视频资源信息 http://host/resources/{mediaId} 返回  {mediaInfo}，
     * <p>
     * 3.         根据userId从用户基础服务获取用户 http://host/users/{userId} 返回 {userInfo}
     * <p>
     * 请实现根据作品id（postId） 查询各微服务获取相关数据，组装成完整的作品信息（Post）后返回。
     * <p>
     * 注意: 多媒体服务, 用户基础服务存在网络抖动、宕机情况；此接口需要保证高可用、低延时、高并发。请酌情考虑。访问各服务的rest代码无需实现，只注明接口即可。
     *
     * @param postId
     * @return
     */
    public Post get(String postId) throws ExecutionException, InterruptedException {

        //如下代码可以简化为lambda表达式，更为优雅简洁！

        //这里为便于阅读，使用了匿名内部类。
        //1。 发起获取作品请求，以下所有请求均采用线程池发起；
        CompletableFuture<Post> f = CompletableFuture.supplyAsync(new Supplier<PostInfo>() {
            @Override
            public PostInfo get() {
                return RestUtil.rest("http://host/posts/{postId}", "GET", PostInfo.class, postId);
            }
        }, defaultThreadPool())
                //2. 以上述结果为参数传入，发起并发调用并进行异常处理
                // 并发调用1：根据mediaId从多媒体服务获取音视频资源；2：根据userId从用户基础服务获取用户
                .thenComposeAsync(new Function<PostInfo, CompletionStage<Post>>() {
                    @Override
                    public CompletionStage<Post> apply(PostInfo param) {
                        return CompletableFuture.supplyAsync(new Supplier<MediaInfo>() {
                            @Override
                            public MediaInfo get() {
                                return RestUtil.rest("http://host/resources/{mediaId}", "GET", MediaInfo.class, param.getMediaId());
                            }
                        }, defaultThreadPool())
                                .handleAsync(new BiFunction<MediaInfo, Throwable, Post>() {
                                    @Override
                                    public Post apply(MediaInfo param, Throwable throwable) {
                                        Post post = new Post();
                                        if (throwable == null) {
                                            log.info("MediaInfo: {}", param);

                                            post.setMediaInfo(param);
                                        } else {
                                            log.error(throwable.getMessage());
                                            post.setMediaInfo(null);
                                        }
                                        return post;
                                    }
                                }, defaultThreadPool())
                                .thenCombineAsync(
                                        CompletableFuture.supplyAsync(new Supplier<UserInfo>() {
                                            @Override
                                            public UserInfo get() {
                                                return RestUtil.rest("http://host/users/{userId}", "GET", UserInfo.class, param.getUserId());
                                            }
                                        }, defaultThreadPool())
                                                .handleAsync(new BiFunction<UserInfo, Throwable, Post>() {
                                                    @Override
                                                    public Post apply(UserInfo param, Throwable throwable) {
                                                        Post post = new Post();
                                                        if (throwable == null) {
                                                            post.setUserInfo(param);
                                                            log.info("UserInfo: {}", param);
                                                        } else {
                                                            post.setUserInfo(null);
                                                            log.error(throwable.getMessage());
                                                        }
                                                        return post;
                                                    }
                                                }, defaultThreadPool()),
                                        //汇集并发请求1与2的结果集
                                        new BiFunction<Post, Post, Post>() {
                                            @Override
                                            public Post apply(Post t, Post u) {
                                                Post p = new Post();
                                                p.setUserInfo(u.getUserInfo());
                                                p.setMediaInfo(t.getMediaInfo());
                                                return p;
                                            }
                                        });
                    }
                }, defaultThreadPool());


        //System.out.println("result : " + f.get());

        //返回结果
        return f.get();

    }


    //采用lambda 表达式改写。
    public Post getByLambda(String postId) throws ExecutionException, InterruptedException {

        //如下代码可以简化为lambda表达式，更为优雅简洁！

        //这里为便于阅读，使用了匿名内部类。
        //1。 发起获取作品请求，以下所有请求均采用线程池发起；
        CompletableFuture<Post> f = CompletableFuture.supplyAsync(
                () -> RestUtil.rest("http://host/posts/{postId}", "GET", PostInfo.class, postId), defaultThreadPool())
                //2. 以上述结果为参数传入，发起并发调用并进行异常处理
                // 并发调用1：根据mediaId从多媒体服务获取音视频资源；2：根据userId从用户基础服务获取用户
                .thenComposeAsync(param -> CompletableFuture.supplyAsync(
                        () -> RestUtil.rest("http://host/resources/{mediaId}", "GET", MediaInfo.class, param.getMediaId()), defaultThreadPool())
                        .handleAsync((param1, throwable) -> {
                            Post post = new Post();
                            if (throwable == null) {
                                log.info("MediaInfo: {}", param1);
                                post.setMediaInfo(param1);
                            } else {
                                log.error(throwable.getMessage());
                                post.setMediaInfo(null);
                            }
                            return post;
                        }, defaultThreadPool())
                        .thenCombineAsync(
                                CompletableFuture.supplyAsync(
                                        () -> RestUtil.rest("http://host/users/{userId}", "GET", UserInfo.class, param.getUserId()), defaultThreadPool())
                                        .handleAsync((param12, throwable) -> {
                                            Post post = new Post();
                                            if (throwable == null) {
                                                post.setUserInfo(param12);
                                                log.info("UserInfo: {}", param12);
                                            } else {
                                                post.setUserInfo(null);
                                                log.error(throwable.getMessage());
                                            }
                                            return post;
                                        }, defaultThreadPool()),
                                //汇集并发请求1与2的结果集
                                (t, u) -> {
                                    Post p = new Post();
                                    p.setUserInfo(u.getUserInfo());
                                    p.setMediaInfo(t.getMediaInfo());
                                    return p;
                                }), defaultThreadPool());


        //System.out.println("result : " + f.get());

        //返回结果
        return f.get();

    }


    final int NUM_OF_CORE = Runtime.getRuntime().availableProcessors();

    private ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数量 = cpu core + 1
        threadPoolTaskExecutor.setCorePoolSize(NUM_OF_CORE + 1);
        //最大线程数量 = (cpu core * 2)+1
        threadPoolTaskExecutor.setMaxPoolSize(NUM_OF_CORE * 2 + 1);

        threadPoolTaskExecutor.setQueueCapacity(50);

        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        threadPoolTaskExecutor.setKeepAliveSeconds(300);

        threadPoolTaskExecutor.initialize();

        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        threadPoolTaskExecutor.setAwaitTerminationSeconds(120);
        return threadPoolTaskExecutor;
    }

}

@Data
class Post {

    private String content;

    private MediaInfo mediaInfo;

    private UserInfo userInfo;

}

@Data
class PostInfo {

    private String mediaId;

    private String userId;

    private String content;


}

@Data
class UserInfo {

    private String userId;
    private String userName;
    private String pwd;

}

@Data
class MediaInfo {
    private String id;
    private String Name;

}

