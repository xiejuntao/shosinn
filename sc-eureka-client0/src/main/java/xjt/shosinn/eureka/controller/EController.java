package xjt.shosinn.eureka.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/client0"})
public class EController {
    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String echo(){
        return "天凉好个秋";
    }
}
