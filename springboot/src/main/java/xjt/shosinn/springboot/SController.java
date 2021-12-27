package xjt.shosinn.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import xjt.sb.service.DataService;
import xjt.sb.service.LifeService;
import xjt.sb.service.PrototypeServiceImpl;
import xjt.sb.service.ServiceImpl;
import xjt.sb.vo.World;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
public class SController {
    @Autowired
    ServiceImpl service;
    @Autowired
    PrototypeServiceImpl prototypeService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    //@Qualifier("xjt")
    LifeService lifeService;
    @Autowired
    @Qualifier("xjt.shosinn.springboot.SController$InnerDataService")
    DataService dataService;
    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;

    @RequestMapping(value = "s", method = RequestMethod.GET)
    public String s() {
        return "username:" + username + "<br/>password:" + password;
    }

    @RequestMapping(value = "p", method = RequestMethod.GET)
    public String p() {
        return "p:" + prototypeService
                + "<br>s:" + service
                + "<br>pp:" + getPrototypeService()
                + "<br>cp:" + getPrototypeServiceInCtx()
                + "<br>get:" + getServiceImpl();
    }

    @Lookup
    public PrototypeServiceImpl getPrototypeService() {
        return null;
    }

    public PrototypeServiceImpl getPrototypeServiceInCtx() {
        return applicationContext.getBean(PrototypeServiceImpl.class);
    }

    public ServiceImpl getServiceImpl() {
        return applicationContext.getBean(ServiceImpl.class);
    }

    @RequestMapping(value = "d", method = RequestMethod.GET)
    public String del() {
        dataService.delete(101);
        return "OK";
    }

    @Repository
    public class InnerDataService implements DataService {
        @Override
        public void delete(long id) {
            log.info("inner delete`id={}", id);
        }
    }

    @RequestMapping(value = "l", method = RequestMethod.GET)
    public String life() {
        ///lifeService.getSelf().back();
        lifeService.c();
        return "";
    }

    @RequestMapping(path = "/hi1", method = RequestMethod.GET)
    public String hi1(@RequestParam("name") String name) {
        return name;
    }

    @RequestMapping(path = "/hi2", method = RequestMethod.GET,produces = "application/json")
    public String hi2(@RequestParam(defaultValue = "") String name, @RequestHeader() HttpHeaders map, HttpServletResponse response) {
        log.info("myheader={}",map.get("MyHeader").toString());
        response.addHeader("mHeader","m");
        response.addHeader(HttpHeaders.CONTENT_TYPE,"application/json");
        return map.toString();
    }
    @RequestMapping(path = "w",method = RequestMethod.GET)
    public World getWork(@Valid @RequestBody World world){
        return world;
    }
}
