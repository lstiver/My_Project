package org.example.entity;

import java.util.Random;

/**.
 * 这是一个继承了抽象类User的子类，对应高中的账号类型的用户
 */
public class SeniorUser extends User {
  /**.
   * 这是一个SeniorUser类的构造方法，给类的私有属性赋值
   */
  public SeniorUser(String account, String password) {
    this.account = account;
    this.password = password;
    setType("高中");
    this.difficulty = this.type;
  }

  private static final String[] symbols1 = {"+", "-", "*", "/", "(", ")"}; //小学题目中会用到的符号
  private static final String[] symbols2 = {"√", "²"}; //初中题目中会用到的符号
  private static final String[] symbols3 = {"sin", "cos", "tan"}; //高中题目中会用到的符号
  static StringBuilder question = new StringBuilder(); //因为需要频繁的修改question字符串所以这里使用StringBuilder提高性能

  /**.
   * 这是一个getPrimaryQuestion函数，用于随机生成高中难度的题目
   */
  public static String[] getHighQuestion(int number) {
    String[] highschoolQuestion = new String[number]; //用字符串存储题目
    Random random = new Random(System.currentTimeMillis()); //产生不同序列的随机数
    for (int i = 0; i < number; i++) {
      int operationNumber = random.nextInt(4) + 2; //操作数的数量在2-5个
      int high = 0;
      boolean[] flag = new boolean[operationNumber];
      for (int j = 0; j < operationNumber; j++) {
        int opreation = random.nextInt(88) + 1; //操作数的大小为1-89
        if (!flag[j] && opreation % 2 == 0) { //判断是否加"√"
          question.append(symbols2[0]);
          flag[j] = true;
        }
        if (!flag[j] && random.nextBoolean()) {
          question.append(symbols3[random.nextInt(3)]);
          flag[j] = true;
        }
        question.append(opreation); //添加操作数
        if (!flag[j] && opreation % 2 == 1) { //判断是否加"²"
          question.append(symbols2[1]);
          flag[j] = true;
        }
        question.append(symbols1[random.nextInt(4)]); //从前四个符号中任选一个
        if (high == 0 && j == operationNumber - 2) {
          question.append(symbols3[random.nextInt(3)]);
          question.append(random.nextInt(88) + 1);
          j++;
        }
      }
      highschoolQuestion[i] = question.toString(); //得到一道题目
      question.delete(0, question.length()); //清除缓冲区里的东西，方便下次循环重新设置问题
    }
    return highschoolQuestion;
  }
}
