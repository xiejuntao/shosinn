package xjt.sb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/admin"})
public class AdminController {
    @RequestMapping(value = "l",method = RequestMethod.GET)
    public String list(){
        return "心生幻象，寄身于梦。";
    }
}
