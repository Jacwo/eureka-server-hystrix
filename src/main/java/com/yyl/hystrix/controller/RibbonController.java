package com.yyl.hystrix.controller;

import com.yyl.hystrix.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:yangyuanliang Date:2019-08-15 Time:11:00
 **/
@RestController
public class RibbonController {
    @Autowired
    private RibbonService ribbonService;
    @RequestMapping("/hystrix/test")
    public String testHystrix(){
        return ribbonService.helloService();
    }
}
