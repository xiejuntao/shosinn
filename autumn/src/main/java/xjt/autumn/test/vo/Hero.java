package xjt.autumn.test.vo;

import lombok.extern.slf4j.Slf4j;
import xjt.autumn.annotation.Harvest;

@Harvest(name="hero")
@Slf4j
public class Hero {
    public void done(){
        log.info("浪花淘尽英雄");
    }
}
