package xjt.shosinn.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
//@EnableAspectJAutoProxy(exposeProxy = true)
@ServletComponentScan(value = "xjt")
@ComponentScans({@ComponentScan("xjt.sb"),@ComponentScan("xjt.shosinn")})
public class Bootstrap {
    public static void main(String[] args) {
        //new SpringApplicationBuilder().environment(env).sources(Bootstrap.class).run(args);
        new SpringApplicationBuilder().sources(Bootstrap.class).run(args);
    }
}
