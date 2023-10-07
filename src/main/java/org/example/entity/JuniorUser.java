package org.example.entity;

import java.util.Random;


/**.
 * 这是一个继承了抽象类User的子类，对应初中的账号类型的用户
 */
public class JuniorUser extends User {
  /**.
   * 这是一个JuniorUser类的构造方法，给类的私有属性赋值
   */
  public JuniorUser(String account, String password) {
    this.account = account;
    this.password = password;
    this.type = "初中";
    this.difficulty = this.type;
  }

  private static final String []signs = {"+", "-", "*", "/"}; //小学题目中会用到的符号

  /**.
   * 这是一个getPrimaryQuestion函数，用于随机生成初中难度的题目
   */
  public static String[] getJuniorQuestion(int number) {
    String[] juniorschoolQuestion = new String[number]; //用字符串存储题目
    Random random = new Random(System.currentTimeMillis()); //产生不同序列的随机数
    for (int j = 0; j < number; j++) {
      String display = ""; //题目
      int operationNum = random.nextInt(5) + 1; //操作数1~5个
      String []operation = new String[operationNum];
      for (int i = 0; i < operationNum; i++) {
        operation[i] = String.valueOf(random.nextInt(100) + 1);
      }
      boolean []flag = new boolean[operationNum];
      int specialSign = random.nextInt(operationNum) + 1; //1~操作数个特殊符号

      for (int i = 0; i < specialSign; i++) { //对数字加上特殊符号
        //如果操作数没有特殊符号
        int index = random.nextInt(operationNum);
        if (!flag[index]) {
          //一半一半
          if (random.nextBoolean()) {
            operation[index] = operation[index] + "²";
          } else {
            operation[index] = "√" + operation[index];
          }
          flag[index] = true;
        }
      }
      if (operationNum > 3 && random.nextBoolean()) {
        int length = random.nextInt(operationNum - 2) + 1;
        int a = random.nextInt(operationNum - length);
        int b = a + length;
        operation[a] = "(" + operation[a];
        operation[b] = operation[b] + ")";
      }
      for (int i = 0; i < operationNum - 1; i++) {
        display = display + operation[i] + signs[random.nextInt(4)];
      }
      juniorschoolQuestion[j] = display + operation[operationNum - 1];
    }
    return juniorschoolQuestion;
  }

}
