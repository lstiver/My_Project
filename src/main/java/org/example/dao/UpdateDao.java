package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.jdbc.JdbcUtils;

/**.
 * 这是一个用于更新数据库中表的信息的UpdateDao类
 */
public class UpdateDao {
  private static Connection cn = null;
  private static ResultSet rs = null;
  private static PreparedStatement update = null;


  /**.
   * 这是一个用于更新user表中的password的方法
   * 账户名@param account
   * 新密码@param password
   */
  public static void updateUser(String account, String password) {
    String sql = "UPDATE user SET password=? WHERE account=?";
    try {
      //获得连接
      cn = JdbcUtils.getConnection();
      update = cn.prepareStatement(sql);
      update.setString(1, password);
      update.setString(2, account);
      update.executeUpdate();
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, update, rs);
    }
  }
}
