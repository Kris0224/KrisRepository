import org.junit.Test;

import java.sql.*;

public class Oracle {


    /**
     *
    *oracle连接java测试
    **/
    @Test
    public void OracleTest() throws ClassNotFoundException {

//    加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try {
//     获取connection对象
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
//       获取数据库操纵对象statement
            Statement statement = connection.createStatement();
//        执行语句
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMP");

            while (resultSet.next()){
                System.out.println(resultSet.getObject(1)+" "+resultSet.getObject(2));
                System.out.println("git-test");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
