package com.yyl.hystrix.service;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yyl.hystrix.conf.CacheCommand;
import com.yyl.hystrix.conf.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * author:yangyuanliang Date:2019-08-15 Time:11:00
 **/
@Service
public class RibbonService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hystrixFallback")
    public String helloService(){
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello",String.class).getBody();
    }

    public String hystrixFallback(){
        return "error";
    }

    public String syncRequest(){

        return (String) new UserCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000)), restTemplate).execute();
    }


    /**
     * 继承方式开启请求缓存,注意commandKey必须与清除的commandKey一致
     */
    public void openCacheByExtends(){
        CacheCommand command1 = new CacheCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("group")).andCommandKey(HystrixCommandKey.Factory.asKey("test")),
                restTemplate,1);
        CacheCommand command2 = new CacheCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("group")).andCommandKey(HystrixCommandKey.Factory.asKey("test")),
                restTemplate,1);
        Integer result1 = command1.execute();
        Integer result2 = command2.execute();
        System.out.println(result1+"-------"+result2);
    }

    /**
     * 继承方式清除请除缓存
     */
    public void clearCacheByExtends(){
        CacheCommand.flushRequestCache(1L);
        LOGGER.info("请求缓存已清空！");
    }
}
