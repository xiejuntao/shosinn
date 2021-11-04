package xjt.shosinn.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/t"})
public class TController {
    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String echo(){
        return "r";
    }
    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi(){
        return "若";
    }
}
