package xjt.sb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xjt.sb.vo.World;
@Slf4j
@Service
public class LifeService {
    private String name = "x";
    public  World self = new World("self");
    public LifeService() {
    }

    public LifeService(String name) {
        this.name = name;
    }

    public void close(){
        log.info("白茫茫一片真干净");
    }
    public String t(){
        log.info("林花谢了春红");
        //this.c();
        //((LifeService)AopContext.currentProxy()).c();
        return name;
    }
    public void c(){
        log.info("太匆匆");
        log.info(self.getMark());
    }
    public World getSelf(){
        return self;
    }
}
