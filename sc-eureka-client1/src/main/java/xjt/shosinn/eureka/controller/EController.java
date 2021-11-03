package xjt.shosinn.eureka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xjt.shosinn.eureka.manager.EchoManager;

@RestController
@RequestMapping(value = {"/client1"})
public class EController {
    @Autowired
    private EchoManager echoManager;

    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String echo() {
        return "client1";
    }

    @RequestMapping(value = "u", method = RequestMethod.GET)
    public String u() {
        return echoManager.serviceUrl();
    }
    @RequestMapping(value = "r", method = RequestMethod.GET)
    public String remoteEcho(){
        return echoManager.remoteEcho();
    }
}
