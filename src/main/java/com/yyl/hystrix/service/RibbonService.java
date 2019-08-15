package com.yyl.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * author:yangyuanliang Date:2019-08-15 Time:11:00
 **/
@Service
public class RibbonService {
    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "hystrixFallback")
    public String helloService(){
        return restTemplate.getForEntity("http://hello-service/hello",String.class).getBody();
    }

    public String hystrixFallback(){
        return "error";
    }

}
