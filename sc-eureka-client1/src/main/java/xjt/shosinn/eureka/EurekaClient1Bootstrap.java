package xjt.shosinn.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class EurekaClient1Bootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaClient1Bootstrap.class).run(args);
    }
}
