package com.yyl.hystrix.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.yyl.hystrix.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangyuanliang
 **/
@RestController
public class RibbonController {
    @Autowired
    private RibbonService ribbonService;
    @RequestMapping("/hystrix/test")
    public String testHystrix(){
        return ribbonService.helloService();
    }


    @RequestMapping("/sync")
    public String sync(){
        return ribbonService.syncRequest();
    }

    @GetMapping("/cacheOn")
    public void openCacheTest(){
        //初始化Hystrix请求上下文
        HystrixRequestContext.initializeContext();
        //开启请求缓存并测试两次
        ribbonService.openCacheByExtends();
        //清除缓存
        ribbonService.clearCacheByExtends();
        //再次开启请求缓存并测试两次
        ribbonService.openCacheByExtends();
    }
}
