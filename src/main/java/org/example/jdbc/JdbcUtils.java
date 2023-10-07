package org.example.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**.
 * 这是一个JDBCUtils类，用于连接数据库的工具类
 */
public class JdbcUtils {
  private static String driver;
  private static String url;
  private static String username;
  private static String password;

  static {
    //使用类加载器获取Database.properties文件的输入流
    InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("Database.properties");
    //通过输入流加载文件内容。然后从Properties对象中读取驱动名、URL、用户名和密码等数据库连接信息
    Properties properties = new Properties();
    try {
      properties.load(is);
      driver = properties.getProperty("driver");
      url = properties.getProperty("url");
      username = properties.getProperty("username");
      password = properties.getProperty("password");
      System.out.println(driver + " " + url);
      Class.forName(driver);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**.
   * 这是一个静态方法 getConnection()，用于获取数据库连接对象
   */
  public static Connection getConnection() {
    try {
      System.out.println("数据库连接");
      //创建数据库连接,如果成功则返回一个数据库连接对象，否则出现异常进入catch模块
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      System.out.print("数据库连接失败");
      e.printStackTrace();
    }
    return null;
  }

  /**.
   * 这是一个静态方法 close()，用于关闭数据库连接相关的资源
   */
  public static void close(Connection connection, Statement statement, ResultSet result) {
    //判断每一个参数是否关闭，如果没有关闭就关闭他们
    try {
      if (result != null) {
        result.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
