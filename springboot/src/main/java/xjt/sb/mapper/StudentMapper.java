package xjt.sb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import xjt.sb.mapper.entity.Student.Student;

@Mapper
public interface StudentMapper {
    @Insert("INSERT INTO `student`(`realname`) VALUES (#{realname})")
    void saveStudent(Student student);
}
