package xjt.sb.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceImpl {
    private String serviceName;
    public ServiceImpl(String serviceName) {
        this.serviceName = serviceName;
    }
/*    public ServiceImpl(String serviceName, String otherStringParameter){
        this.serviceName = serviceName;
    }*/
    public String getName(){
        return this.serviceName;
    }
}
