package xjt.sb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xjt.sb.mapper.StudentMapper;
import xjt.sb.mapper.entity.Student.Student;

import javax.annotation.Resource;

@Slf4j
@Service
public class JDBCService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentMapper studentMapper;
    public void save(){
        //jdbcTemplate.execute("INSERT INTO template_voice_relation VALUES(11,2,3,4,5,6,NOW(),NOW())");
        studentMapper.saveStudent(new Student("冬"));
    }
    @Transactional
    public void saveStudent(String realname) throws Exception {
        Student student = new Student();
        student.setRealname(realname);
        studentMapper.saveStudent(student);
        if (student.getRealname().equals("人")) {
            throw new RuntimeException("异常了");
        }
    }
}
