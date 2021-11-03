package xjt.shosinn.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClient0Bootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaClient0Bootstrap.class).run(args);
    }
}
