package xjt.shosinn.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Throwable.class)
    public void advice(HttpServletResponse resp) throws IOException {
        log.error("cause error");
        resp.getWriter().write("goal");
    }
}
