package com.yyl.hystrix.conf;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * author:yangyuanliang Date:2019-08-16 Time:17:14
 **/
public class CacheCommand extends HystrixCommand<Integer> {
    private RestTemplate restTemplate;
    private Integer id;
    public CacheCommand(Setter setter, RestTemplate restTemplate, Integer id) {
        super(setter);
        this.restTemplate=restTemplate;
        this.id=id;
    }

    @Override
    protected Integer run() throws Exception {

        return restTemplate.getForObject("http://hello-service/test/cache",Integer.class);
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
    /**
     * 清理缓存
     * 开启请求缓存之后，我们在读的过程中没有问题，但是我们如果是写，那么我们继续读之前的缓存了
     * 我们需要把之前的cache清掉
     * 说明 ：   1.其中getInstance方法中的第一个参数的key名称要与实际相同
     *          2.clear方法中的cacheKey要与getCacheKey方法生成的key方法相同
     *          3.注意我们用了commandKey是test,大家要注意之后new这个Command的时候要指定相同的commandKey,否则会清除不成功
     */
    public static void flushRequestCache(Long id){
        HystrixRequestCache.getInstance(
                HystrixCommandKey.Factory.asKey("test"), HystrixConcurrencyStrategyDefault.getInstance())
                .clear(String.valueOf(id));
    }

}
