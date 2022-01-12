package xjt.sb.mapper.entity.Student;

import lombok.Data;

@Data
public class Student {
    private Integer id;
    private String realname;

    public Student() {
    }

    public Student(String realname) {
        this.realname = realname;
    }
}
