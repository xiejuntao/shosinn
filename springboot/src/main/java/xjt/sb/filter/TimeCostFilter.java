package xjt.sb.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@Slf4j
@WebFilter
public class TimeCostFilter implements Filter {
    public TimeCostFilter() {
        log.info("timeCostFilter construct");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("开始计算接口耗时");
        long start = System.currentTimeMillis();
        //final int i = 2 / 0;
        chain.doFilter(request, response);
        long end = System.currentTimeMillis();
        long time = end - start;

        log.info("执行时间(ms)={}",time);
    }
}
