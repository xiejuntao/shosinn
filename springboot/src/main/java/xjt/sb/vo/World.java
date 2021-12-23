package xjt.sb.vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class World {
    private String mark;

    public World(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
    public void  back(){
        log.info("人生如斯");
    }
}
