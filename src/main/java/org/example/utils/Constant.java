package org.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.example.dao.InsertDao;
import org.example.entity.JuniorUser;
import org.example.entity.PrimaryUser;
import org.example.entity.SeniorUser;
import org.example.entity.User;


/**.
 * 这是一个Constant类，系统中的一些常量和常规可能修改的方法写在这个类里面
 */
public class Constant {
  public static final String PRIMARY = "小学"; //小学难度
  public static final String JUNIOR = "初中"; //初中难度
  public static final String HIGH = "高中"; //高中难度
  private static final int max = 50; //最小题值
  private static final int min = 1; //最大题值
  static String[] options = {"小学", "初中", "高中"};

  /**.
   * 这是一个返回option字符串数组的方法
   */
  public static String[] returnoption() {
    return options;
  }

  /**.
   * 这是一个testNumber方法，用于判断题目数量是否正常
   */
  public static boolean testNumber(int number) {
    return number >= min && number <= max;
  }

  /**.
   * 这是一个testString方法，用于判断是否是小学、初中、高中这三个中的一个
   */
  public static boolean testString(String s) {
    return s.equals(Constant.PRIMARY) || s.equals(Constant.JUNIOR) || s.equals(Constant.HIGH);
  }

  /**.
   * 这是一个getQuestiontype方法，用于获得不同难度的题目
   */
  public static String[] getQuestion(String difficulty, String account, int n) throws SQLException {
    String[] questions = new String[n];
    switch (difficulty) {
      case PRIMARY:
        questions = PrimaryUser.getPrimaryQuestion(n);
        break;
      case JUNIOR:
        questions = JuniorUser.getJuniorQuestion(n);
        break;
      case HIGH:
        questions = SeniorUser.getHighQuestion(n);
        break;
      default:
        System.out.println("Error");
        return null;
    }
    while (InsertDao.repeated(questions, account, new Timestamp(System.currentTimeMillis())) > 0) {
      getQuestion(difficulty, account, n);
    }
    return questions;
  }

  /**.
   * 这是一个writeToFile方法，用于将不重复的题目写入文件夹中
   */
  public static Boolean writeToFile(String[] questions, int number, User user) {
    try {
      String account = user.getAccount();
      if (InsertDao.repeated(questions, account, Timestamp.valueOf(LocalDateTime.now())) != 0) {
        return false;
      } else {
        int n = 1; // 题号
        LocalDateTime currentDateTime = LocalDateTime.now(); //当前时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年-MM月-dd日-HH时-mm分-ss秒");
        String name = currentDateTime.format(formatter); //文件名
        File file = new File("questionData/" + user.getAccount()); // 构建文件对象(在当前路径下建)这里使用的是相对路径
        if (!file.exists()) { // 如果文件不存在，则创建文件
          if (file.mkdir()) {
            System.out.println("为用户" + account + "创建了文件夹");
          }
        }
        // 创建FileWriter对象
        FileWriter writer = new FileWriter(file.getPath() + "/" + name + ".txt");
        for (String question : questions) { // 通过循环来将字符串写入文件
          writer.write("Question " + n + " :" + question + "\r\n\r\n"); // 加入换行符
          n++;
        }
        // 写完之后关闭写入
        writer.close();
        System.out.println("题目已成功写入文件！");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      System.out.println("!");
    }
    return true;
  }
}
