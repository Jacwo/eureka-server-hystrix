package com.yyl.hystrix.conf;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.web.client.RestTemplate;

/**
 * author:yangyuanliang Date:2019-08-16 Time:11:17
 **/
public class UserCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;

    public UserCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate=restTemplate;
    }

    @Override
    protected String run() throws Exception {
        //return restTemplate.getForObject("http://localhost:8761/hello", String.class);
        return restTemplate.getForObject("http://hello-service/hello",String.class);
    }


    public static void main(String[] args) {
        String o= (String) new UserCommand(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000)),
                new RestTemplate()).execute();
        System.out.println(0);
    }
}
