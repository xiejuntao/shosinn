package xjt.autumn.test;

import lombok.extern.slf4j.Slf4j;
import xjt.autumn.context.AutumnContext;
import xjt.autumn.test.vo.Hero;
@Slf4j
//@AutumnApp(packages = "xjt.autumn.test")
public class AutumnTest {
    public static void main(String[] args) {
        AutumnContext autumnContext = new AutumnContext();
        autumnContext.start();
        Hero hero = (Hero) autumnContext.getHarvest(Hero.class);
        hero.done();
        Hero heroByName = (Hero) autumnContext.getHarvest("hero");
        log.info("is same instance={}",hero==heroByName);
        heroByName.done();
    }
}
