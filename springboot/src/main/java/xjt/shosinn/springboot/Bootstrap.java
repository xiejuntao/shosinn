package xjt.shosinn.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Bootstrap {
    public static void main(String[] args) {
        //new SpringApplicationBuilder().environment(env).sources(Bootstrap.class).run(args);
        new SpringApplicationBuilder().sources(Bootstrap.class).run(args);
    }
}
