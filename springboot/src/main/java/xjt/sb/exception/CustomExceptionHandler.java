package xjt.sb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView ex(){
        return new ModelAndView("/static/ex.html");
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handle404() {
        System.out.println("404");
        return "{\"resultCode\": 404}";
    }
}
