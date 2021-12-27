package xjt.sb.vo;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;

@Slf4j
@NoArgsConstructor
public class World {
    @Size(min = 1024)
    private Long id;
    private String mark;

    public World(String mark) {
        this.mark = mark;
        this.id = Long.MAX_VALUE;
    }

    public Long getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }
    public void  back(){
        log.info("人生如斯");
    }
}
