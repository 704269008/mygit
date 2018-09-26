package jdbc;

import jdbc.mapper.StudentMapper;
import jdbc.mybatis.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MybatisSpring {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentMapper studentMapper= (StudentMapper) applicationContext.getBean("studentMapper");
        List<Student> students=studentMapper.list();
        for(Student student:students){
            System.out.println("姓名:"+student.getName());
        }
    }
}
