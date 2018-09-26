package jdbc.mapper;

import jdbc.mybatis.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StudentMapperTest {
    @Autowired
    StudentMapper studentMapper;
    @Test
    public void list() throws Exception {
        List<Student> students=studentMapper.list();
        for(Student student:students){
            System.out.println("姓名:"+student.getName());
        }
    }

}