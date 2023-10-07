package org.example.entity;

import org.example.dao.LoginDao;

/**.
 * 这是一个抽象类，里面定义了一些get、set函数以及getuser函数
 */
public abstract class User {
  protected String account; //账号
  protected String password; //密码
  protected String type; //账户类型
  protected String difficulty; //难度（默认为账户类型，后期可以切换

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  /**.
   * 这是一个getUser函数，根据传参不同返回不同的子类对象
   */
  public static User getUser(String account, String password) {
    LoginDao loginDao = new LoginDao();
    String type = loginDao.login(account, password);
    if (type != null) {
      switch (type) {
        case "小学":
          return new PrimaryUser(account, password);
        case "初中":
          return new JuniorUser(account, password);
        case "高中":
          return new SeniorUser(account, password);
        default:
          System.out.println("Error!aaa");
      }
    } else {
      System.out.println("Error!a");
    }
    return null;
  }
}