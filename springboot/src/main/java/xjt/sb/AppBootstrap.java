package xjt.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppBootstrap.class, args);
        context.close();
    }
}
