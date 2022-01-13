package controller.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
//@EnableAspectJAutoProxy(exposeProxy = true)
@ServletComponentScan(value = "xjt")
@ComponentScans({@ComponentScan("xjt.sb"),@ComponentScan("xjt.shosinn")})
public class TestConfig {
}
