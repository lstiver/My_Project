package org.example.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.example.jdbc.JdbcUtils;

/**.
 * 这是一个LoginDao类，用于登录时查询数据库
 */
public class LoginDao {
  private static Connection cn = null;
  private static ResultSet rs = null;
  private static CallableStatement call = null;

  /**.
   * 这是一个login函数，用于登录时调用存储过程检查账户密码是否正确
   */
  public String login(String account, String password) {
    String sql = "{call login(?,?,?)}";
    String result;
    try {
      //获得连接
      cn = JdbcUtils.getConnection();
      call = (CallableStatement) cn.prepareCall(sql);
      // 设置输入参数
      call.setString(1, account);
      call.setString(2, password);
      // 设置输出参数
      call.registerOutParameter(3, Types.VARCHAR); //返回值
      call.execute();
      System.out.println(call.getString(3));
      result = call.getString(3);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      //最后不管有没有异常都调用close(),保证所有资源都被释放
      JdbcUtils.close(cn, call, rs);
    }
    return result;
  }
}
