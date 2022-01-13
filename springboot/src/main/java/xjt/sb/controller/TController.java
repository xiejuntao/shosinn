package xjt.sb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xjt.sb.service.JDBCService;
import xjt.sb.service.RedisService;

import javax.annotation.Resource;
@Slf4j
@RestController
@RequestMapping(value = {"/t"})
public class TController {
    @Resource
    RedisService redisService;
    @Resource
    JDBCService jdbcService;
    @RequestMapping(value = "echo", method = RequestMethod.POST)
    public String echo(@RequestParam(value = "para1",defaultValue = "") String para1, @RequestParam(value = "para2",defaultValue = "") String para2){
        log.info("echo");
        return "r:"+para1 + "," + para2;
    }
    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi(@RequestParam(name="s", defaultValue = "") String realName) throws Exception {
        //int i = 1 / 0;
        //redisService.get();
        //jdbcService.save();
        jdbcService.saveStudent(realName);
        return "若";
    }

    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
/*        Map paramMap = new HashMap();
        paramMap.put("para1", "001");
        paramMap.put("para2", "002");*/

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("para1", "趣");
        paramMap.add("para2", "002");
        String url = "http://localhost:8080/t/echo";
        String result = template.postForObject(url, paramMap, String.class);
        System.out.println(result);
    }
}
