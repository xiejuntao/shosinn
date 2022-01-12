package xjt.sb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xjt.sb.service.JDBCService;
import xjt.sb.service.RedisService;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = {"/t"})
public class TController {
    @Resource
    RedisService redisService;
    @Resource
    JDBCService jdbcService;
    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String echo(){
        return "r";
    }
    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi(@RequestParam(name="s", defaultValue = "") String realName) throws Exception {
        //int i = 1 / 0;
        //redisService.get();
        //jdbcService.save();
        jdbcService.saveStudent(realName);
        return "若";
    }
}
