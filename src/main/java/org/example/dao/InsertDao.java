package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.example.jdbc.JdbcUtils;

/**.
 * 这是一个InsertDao类，用于将数据存入数据库
 */
public class InsertDao {
  private static Connection cn = null;
  private static ResultSet rs = null;
  private static PreparedStatement insert = null;

  /**.
   * 这是一个repeated方法，用于检查是否某一账户下生成的题目中是否有与之前重复的题目
   */
  public static int repeated(String [] questions, String account, Timestamp t) throws SQLException {
    String sql = "INSERT INTO questions (question, account, created_time) VALUES(?,?,?)";
    int count = 0; //用于记录重复的题的数量
    try {
      //获得连接
      cn = JdbcUtils.getConnection();
      insert = cn.prepareStatement(sql);
      insert = cn.prepareCall(sql);
      for (String question : questions) { // 设置插入参数
        insert.setString(1, question);
        insert.setString(2, account);
        insert.setTimestamp(3, t);
        try {
          insert.executeUpdate();
        } catch (SQLException e) {
          System.out.println(question + "与之前的题目重复，现在准备重新出题");
          count++;
        }
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, insert, rs);
    }
    return count;
  }

  /**.
   * 这是一个repeateduser方法，用于检查是否有重复的用户
   */
  public static boolean adduser(String account, String type, String phonenumber) {
    String sql = "INSERT INTO user (account, password, type, phone) VALUES(?,?,?,?)";
    Boolean flag = false; //用于标记是否有重复的账户名,初始化表示没有重复
    try {
      //获得连接
      cn = JdbcUtils.getConnection();
      insert = cn.prepareStatement(sql);
      insert = cn.prepareCall(sql);
      insert.setString(1, account);
      insert.setString(2, "123");
      insert.setString(3, type);
      insert.setString(4, phonenumber);
      insert.executeUpdate();
    } catch (SQLException e) {
      flag = true;
      System.out.println("该账户名已存在");
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, insert, rs);
    }
    return flag;
  }
}

