package xjt.shosinn.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
/*        return builder.routes().route("hi", new Function<PredicateSpec, Buildable<Route>>() {
                    @Override
                    public Buildable<Route> apply(PredicateSpec predicateSpec) {
                        return predicateSpec.before(ZonedDateTime.parse("2021-11-04T14:55:00+08:00[Asia/Shanghai]")).uri("http://localhost:8080/t/hi");
                    }
                }).build();*/
        return builder.routes()
                .route("hi",r->r.path("/t/hi").uri("http://localhost:8080/t/hi"))
                .build();
    }
}
