package org.example.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.example.jdbc.JdbcUtils;

/**.
 * 这是一个RegisterDao类，用于注册时对数据库的一些操作
 */
public class RegistDao {
  private static Connection cn = null;
  private static ResultSet rs = null;
  private static PreparedStatement insert = null;
  private static PreparedStatement select = null;
  private static CallableStatement call = null;

  /**.
   * 这是一个insertcode方法，用于把验证码和用户名和时间存入表code中
   * 返回值为true表示插入成功
   */
  public static int insertcode(String code, Long expiredAt) {
    String sql = "{call insert_to_code(?,?,?)}";
    int myid;
    try {
      cn = JdbcUtils.getConnection();
      call = cn.prepareCall(sql);
      call.setString(1, code);
      call.setLong(2, expiredAt);
      call.registerOutParameter(3, Types.INTEGER); //返回值
      call.execute();
      myid = call.getInt(3);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, call, rs);
    }
    return myid;
  }

  /**.
   * 这是一个checkcode方法，用于检查验证码
   * 返回值为1表示验证码超时
   * 以上错误都没有就返回code
   */
  public static String checkcode(int id) {
    String sql = "{call get_code_expireAt(?,?,?)}";
    String code;
    long time;
    try {
      cn = JdbcUtils.getConnection();
      call = cn.prepareCall(sql);
      call.setInt(1, id);
      call.registerOutParameter(2, Types.VARCHAR); //返回值
      call.registerOutParameter(3, Types.BIGINT); //返回值
      call.execute();
      code = call.getString(2);
      time = call.getLong(3);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, call, rs);
    }
    System.out.println(time);
    if (time <= System.currentTimeMillis()) {
      return "1";
    }
    return code;
  }

  /**.
   * 这是一个repeateduser方法，用于检查是否有重复的用户
   * 返回值为true表示有重复用户
   */
  public static boolean repeateduser(String myaccount) {
    String sql = "SELECT account FROM user WHERE account=?";
    Boolean flag = false; //用于标记是否有重复的账户名,初始化表示没有重复
    try {
      //获得连接
      cn = JdbcUtils.getConnection();
      select = cn.prepareStatement(sql);
      select.setString(1, myaccount);
      rs = select.executeQuery();
      if (rs.next()) {
        flag = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, select, rs);
    }
    return flag;
  }
}
