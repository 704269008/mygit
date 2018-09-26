package jdbc;

import jdbc.mybatis.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcTemplate {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student= (Student) applicationContext.getBean("s");
        System.out.println(student.getName());

    }
}
