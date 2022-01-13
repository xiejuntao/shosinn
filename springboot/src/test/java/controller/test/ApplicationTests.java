package controller.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xjt.sb.controller.TController;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class ApplicationTests {
    @Autowired
    TController tController;
    @Test
    public void testEcho(){
        log.info("testEcho`result={}",tController.echo("a","b"));
    }
}
