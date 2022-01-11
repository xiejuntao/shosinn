package xjt.sb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xjt.sb.service.RedisService;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = {"/t"})
public class TController {
    @Resource
    RedisService redisService;
    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String echo(){
        return "r";
    }
    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi(){
        //int i = 1 / 0;
        redisService.get();
        return "若";
    }
}
