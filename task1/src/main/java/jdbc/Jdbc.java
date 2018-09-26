package jdbc;

import java.sql.*;

/**
 * JDBC 基本操作
 * zhh
 */
public class Jdbc
{
    public static void main( String[] args ) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println( "成功加载驱动程序" );
        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/data?characterEncoding=UTF-8","root","225510");
            //Statement statement=connection.createStatement();
            //String name1=" '会计' OR 1=1";
            //String sqk="select * from student where name=?";
            //PreparedStatement preparedStatement=connection.prepareStatement(sqk);
            //String sql="insert into student values(null,"+"'艾克'"+","+"'男'"+","+12+")";
            //String sql="update student set name = '亚索' where id = 7";
            //String sql = " SELECT  * FROM  student";
            //ResultSet resultSet=statement.executeQuery(sql);
            //while (resultSet.next()){
            //    int id= resultSet.getInt("id");
            //    String name=resultSet.getString(2);
            //    String sex=resultSet.getString("sex");
            //    int phone=resultSet.getInt(4);
            //    //System.out.printf("%d\t%s\t%s\t%d%n",id,name,sex,phone);
            //    System.out.println("S学号"+id+name+sex+phone);
            //}
            //ResultSet resultSet=statement.executeQuery(sql);
            //if(resultSet.next()){
            //    int id=resultSet.getInt(1);
            //    System.out.println("学号："+id);
            //}
            //System.out.println("查询数据成功");
            //preparedStatement.setString(1,"会计");
            //ResultSet resultSet1=preparedStatement.executeQuery();
            //while (resultSet1.next()){
            //    int id= resultSet1.getInt("id");
            //    String name=resultSet1.getString(2);
            //    String sex=resultSet1.getString("sex");
            //    int phone=resultSet1.getInt(4);
            //    //System.out.printf("%d\t%s\t%s\t%d%n",id,name,sex,phone);
            //    System.out.println("P学号"+id+name+sex+phone);
            //}
            //System.out.println("成功执行更新语句");
            DatabaseMetaData dbmd = connection.getMetaData();

            // 获取数据库服务器产品名称
            System.out.println("数据库产品名称:\t"+dbmd.getDatabaseProductName());
            // 获取数据库服务器产品版本号
            System.out.println("数据库产品版本:\t"+dbmd.getDatabaseProductVersion());
            // 获取数据库服务器用作类别和表名之间的分隔符 如test.user
            System.out.println("数据库和表分隔符:\t"+dbmd.getCatalogSeparator());
            // 获取驱动版本
            System.out.println("驱动版本:\t"+dbmd.getDriverVersion());

            System.out.println("可用的数据库列表：");
            // 获取数据库名称
            ResultSet rs = dbmd.getCatalogs();

            while (rs.next()) {
                System.out.println("数据库名称:\t"+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
