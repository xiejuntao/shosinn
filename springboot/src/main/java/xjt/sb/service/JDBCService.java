package xjt.sb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xjt.sb.mapper.StudentMapper;
import xjt.sb.mapper.entity.Student.Student;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    @Transactional()
    public void saveStudent(String realname){
        Student student = new Student();
        student.setRealname("时间的朋友");
        studentMapper.saveStudent(student);

        try {
            doSaveStudent(realname);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    @Transactional(rollbackFor=RuntimeException.class,propagation = Propagation.REQUIRES_NEW)
    public void doSaveStudent(String realname){
        Student student = new Student();
        student.setRealname(realname);
        studentMapper.saveStudent(student);
        if (student.getRealname().equals("人")) {
            throw new RuntimeException("异常了");
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try{
            connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/world?useUnicode=true&characterEncoding=UTF-8", "root", "root");
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO student(realname) VALUES('让子弹飞')");
            int i = 1 / 0;
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
