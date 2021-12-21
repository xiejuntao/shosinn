package xjt.sb.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceRequest {
    @Bean
    public String serviceName(){
        return "匆匆";
    }

}
