package jdbc.mybatis;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisMain {
    public static void main(String[] args) throws IOException {
        InputStream inputStream= Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        Map<String,Object> map=new HashMap<>();
        map.put("id",7);
       // map.put("name","亚索");
        //List<Student> students=sqlSession.selectList("listStudentByIdAndName",map);
        List<Student> students=sqlSession.selectList("listStudentByName",map);
        for(Student student:students){
            System.out.println("name:"+student.getName());
        }
    }
}
