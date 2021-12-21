package xjt.shosinn.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xjt.sb.service.DataService;
import xjt.sb.service.PrototypeServiceImpl;
import xjt.sb.service.ServiceImpl;
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
    @Qualifier("xjt.shosinn.springboot.SController$InnerDataService")
    DataService dataService;
    @RequestMapping(value = "s", method = RequestMethod.GET)
    public String s(){
        return service.getName();
    }
    @RequestMapping(value = "p",method = RequestMethod.GET)
    public String p(){
        return "p:"+prototypeService
                +"<br>s:"+service
                +"<br>pp:" +getPrototypeService()
                +"<br>cp:"+getPrototypeServiceInCtx()
                +"<br>get:"+getServiceImpl();
    }
    @Lookup
    public PrototypeServiceImpl getPrototypeService(){
        return null;
    }
    public PrototypeServiceImpl getPrototypeServiceInCtx(){
        return applicationContext.getBean(PrototypeServiceImpl.class);
    }
    public ServiceImpl getServiceImpl(){
        return applicationContext.getBean(ServiceImpl.class);
    }
    @RequestMapping(value = "d",method = RequestMethod.GET)
    public String del(){
        dataService.delete(101);
        return "OK";
    }
    @Repository
    public class InnerDataService implements DataService {
        @Override
        public void delete(long id) {
            log.info("inner delete`id={}",id);
        }
    }
}
