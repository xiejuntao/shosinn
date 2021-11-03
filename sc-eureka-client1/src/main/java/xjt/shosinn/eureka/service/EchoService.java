package xjt.shosinn.eureka.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient("sc-eureka-client0")
public interface EchoService {
    @RequestMapping(value = "/client0/echo", method = GET)
    String remoteEcho();
}
