package org.example.entity;

import java.util.Random;

/**.
 * 这是一个继承了抽象类User的子类，对应小学的账号类型的用户
 */
public class PrimaryUser extends User {
  private static final String[] symbols1 = {"+", "-", "*", "/", "(", ")"}; //小学题目 中会用到的符号

  /**.
   * 这是一个PrimaryUser类的构造方法，给类的私有属性赋值
   */
  public PrimaryUser(String account, String password) {
    this.account = account;
    this.password = password;
    this.type = "小学";
    this.difficulty = this.type;
  }

  /**.
   * 这是一个getPrimaryQuestion函数，用于随机生成小学难度的题目
   */
  public  static String[] getPrimaryQuestion(int number) {
    String[] primaryQuestion = new String[number]; //用字符串存储题目
    //因为需要频繁的修改question字符串所以这里使用StringBuilder提高性能
    StringBuilder question = new StringBuilder();
    long seed = System.currentTimeMillis(); //获得变化的种子值
    Random random = new Random(seed); //产生不同序列的随机数
    for (int i = 0; i < number; i++) {
      //操作数的数量在2-5个(最少要两个，不然加减乘除没法算)
      int operationNumber = random.nextInt(4) + 2;
      int bracketsLeft = random.nextInt(operationNumber); //左括号位置
      int bracketsRight = random.nextInt(operationNumber); //右括号位置
      boolean bracketsIllegal = bracketsLeft < bracketsRight; //判断括号位置是否合法
      for (int j = 0; j < operationNumber; j++) {
        int opreation = random.nextInt(100) + 1; //操作数的大小为1-100
        if (bracketsIllegal && j == bracketsLeft) { //左括号
          question.append(symbols1[4]);
        }
        question.append(opreation); //第一个操作数
        if (bracketsIllegal && j == bracketsRight) { //右括号
          question.append(symbols1[5]);
        }
        if (j < operationNumber - 1) {
          int temp = random.nextInt(4); //从前四个符号中任选一个
          question.append(symbols1[temp]);
        }
      }
      primaryQuestion[i] = question.toString(); //得到一道题目
      question.delete(0, question.length()); //清除缓冲区里的东西，方便下次循环重新设置问题
    }
    return primaryQuestion;
  }

}
