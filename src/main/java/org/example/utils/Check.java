package org.example.utils;

import org.example.dao.RegistDao;

/**.
 * 这是一个用于检查的工具类Check
 */
public class Check {
  /**.
   * 这是一个用于检测验证码是否正确的函数
   * 账号名@param account
   * 验证码@param checknumber
   * 验证码号@param id
   * 返回值@return返回1表示没有验证码，返回2表示验证码超时，返回3表示验证码正确，返回4表示验证码错误
   */
  public static int checkregister(String account, String checknumber, int id) {
    if (id == 0) {
      return 1; //请点击获取验证码
    } else {
      String result = RegistDao.checkcode(id);
      System.out.println(result);
      switch (result) {
        case "1":
          return 2; //验证码超时
        default:
          if (checknumber.equals(result)) {
            return 3; //验证码正确
          }
      }
    }
    return 4; //验证码不正确
  }

  /**.
   * 这是一个检查新密码是否符合规范以及两次输入密码是否一致的函数
   * 密码1@param str1
   * 密码2@param str2
   * 返回值@return为0表示没有满足密码规范，返回值为1表示前后两次密码不一样
   * 返回值为2表示新密码符合规范且前后两次密码一样
   */
  public static int checkNewPassword(String str1, String str2) {
    int number = str1.length();
    String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"; //正则表达式模式
    if (number < 6 || number > 10 || !str1.matches(pattern)) {
      return 0;
    } else if (!str1.equals(str2)) {
      return 1;
    }
    return 2;
  }

  /**.
   * 这是一个检查填入的选项是否正确的函数
   * 答案@param str
   * 返回值@return
   */
  public static Boolean answerFormat(String s) {
    if (s.equals("aA") || s.equals("aB") || s.equals("aC") || s.equals("aD") || s.equals("a")) {
      return true;
    }
    return false;
  }

  /**.
   * 这是一个用于校验输入的电话号码是否格式正确的方法
   * 电话号码@param phone
   * 返回@return
   */
  public static Boolean phoneFoormat(String phone) {
    String pattern = "^1[3456789]\\d{9}$";
    return phone.matches(pattern);
  }
}
