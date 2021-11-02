package xjt.shosinn.sb.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAdminServer
public class AdminBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(AdminBootstrap.class).run(args);
    }
}
