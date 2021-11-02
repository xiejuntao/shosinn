package xjt.shosinn.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Bootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Bootstrap.class).run(args);
    }
}
