package xjt.shosinn.eureka.manager;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xjt.shosinn.eureka.service.EchoService;

@Service
public class EchoManager {
    @Autowired
    private EurekaClient discoveryClient;
    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EchoService echoService;
    private String serviceName = "sc-eureka-client0";
    public String serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(serviceName, false);
        //instance.getDataCenterInfo().getName();
        return instance.getHomePageUrl();
    }
    public String remoteEcho() {
/*        ResponseEntity<String> result =
                this.restTemplate.getForEntity(
                        "http://"+serviceName+"/client0/echo",
                        String.class,
                        "");
        if (result.getStatusCode() == HttpStatus.OK) {
            System.out.printf(result.getBody() + " called in callProducer");
            return result.getBody();
        } else {
            System.out.printf(" is it empty");
            return " empty ";
        }*/
        return echoService.remoteEcho();
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
