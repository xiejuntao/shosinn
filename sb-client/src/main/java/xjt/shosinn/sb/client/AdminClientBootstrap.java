package xjt.shosinn.sb.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AdminClientBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(AdminClientBootstrap.class).run(args);
    }
}
