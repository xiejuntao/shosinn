package xjt.shosinn.gateway;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GatewayBootstrap {
    public static void main(String[] args) {
        //new SpringApplicationBuilder().environment(env).sources(Bootstrap.class).run(args);
        new SpringApplicationBuilder().sources(GatewayBootstrap.class).run(args);
    }
}
