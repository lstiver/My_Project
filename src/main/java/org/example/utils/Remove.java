package org.example.utils;

/**.
 * 这是一个用于计算的工具类，里面有一些特殊的运算
 */
public class Remove {
  /**.
   * 这是一个计算传入的题目的正确答案的函数
   * 传入的式子@param str
   * 返回值@return为正确答案
   */
  public static Double rightanswer(String str) {
    String sqrt = removeSqrt(str);
    String pow = removePow(sqrt);
    String sin = removeSin(pow);
    String cos = removeCos(sin);
    String tan = removeTan(cos);
    Double answer = Caculate.compute(Caculate.getRpn(tan));
    return answer;
  }

  /**
   * 计算根号.
   */
  public static String removeSqrt(String pro) {  //remove √
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < pro.length(); i++) {
      String tempStr = "";
      if (pro.charAt(i) == '√') { //除去根号
        int index = i;
        for (int j = i + 1; j < pro.length(); j++) {
          if (pro.charAt(j) < '0' || pro.charAt(j) > '9') {
            tempStr = pro.substring(i + 1, j);
            index = j;
            break;
          } else if (pro.charAt(j) >= '0' && pro.charAt(j) <= '9' && j == pro.length() - 1) {
            tempStr = pro.substring(i + 1, j) + pro.charAt(j);
            index = j;
            break;
          }
        }
        double number = Double.parseDouble(tempStr);
        result.append(Math.sqrt(number));
        i = index;
      }
      result.append(pro.charAt(i)); //+-*/直接插入
    }
    return result.toString();
  }

  /**
   * 计算平方.
   */
  public static String removePow(String problem) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < problem.length(); i++) {
      String tempStr = "";
      if (problem.charAt(i) == '²') { //计算平方
        for (int j = i - 1; j >= 0; j--) {
          char sel = problem.charAt(j);
          if (sel == '+' || sel == '-' || sel == '*' || sel == '/' || sel == '(' || sel == ')') {
            tempStr = problem.substring(j + 1, i);
            break;
          } else if (problem.charAt(j) >= '0' && problem.charAt(j) <= '9' && j == 0) {
            tempStr = problem.substring(0, i);
            break;
          }
        }
        double number = Double.parseDouble(tempStr);
        result.append(Math.pow(number, 2));
        continue;
      } else if (problem.charAt(i) >= '0' && problem.charAt(i) <= '9') {
        int index0 = -1;
        int index1 = -1;
        for (int j = i + 1; j < problem.length(); j++) {
          if (problem.charAt(j) == '²') {
            index0 = j;
            index1 = j + 1;
            break;
          } else if (problem.charAt(j) < '0' || problem.charAt(j) > '9') {
            index1 = j;
            index0 = j + 1;
            break;
          }
        }
        if (index0 >= index1) { //²出现的时间晚于+-*/出现的时间，意味着此数字没有², 等号是遍历到了末尾也没有²以及+-*/
          result.append(problem.charAt(i));
        } else { //此数字存在平方
          i = index0 - 1; //i++直接跳至平方出现位置
        }
        continue;
      }
      result.append(problem.charAt(i)); //+-*/直接插入
    }
    return result.toString();
  }

  /**
   * 计算sin.
   */
  public static String removeSin(String pro) {
    StringBuilder result = new StringBuilder();
    int index = 0;
    for (int i = 0; i < pro.length() - 3; i++) {
      String tempStr = "";
      String temp = pro.substring(i, i + 3);
      if (temp.equals("sin")) { //i~i+2 = sin
        index = i; //index = s
        for (int j = i + 3; j < pro.length(); j++) { //从sin后的第一个数开始
          if (pro.charAt(j) < '0' || pro.charAt(j) > '9') {
            tempStr = pro.substring(i + 3, j);
            index = j;
            break;
          } else if (pro.charAt(j) >= '0' && pro.charAt(j) <= '9' && j == pro.length() - 1) {
            tempStr = pro.substring(i + 3, j) + pro.charAt(j);
            index = j;
            break;
          }
        }
        double befNumber = Double.parseDouble(tempStr);
        if (befNumber == 0.0) {
          result.append(0);
        } else {
          double number = Math.toRadians(befNumber);
          result.append(Math.sin(number));
        }
        i = index - 1;
        continue;
      }
      result.append(pro.charAt(i)); //+-*/直接插入
    }
    int length = pro.length();
    if (index != length - 1) { //不存在最后一个运算是sin的情况，则把原来的字符压入字符串，否则直接跳过
      result.append(pro, length - 3, length - 1).append(pro.charAt(length - 1));
    }
    return result.toString();
  }

  /**
   * 计算cos.
   */
  public static String removeCos(String pro) {
    StringBuilder result = new StringBuilder();
    int index = 0;
    for (int i = 0; i < pro.length() - 3; i++) {
      String tempStr = "";
      String temp = pro.substring(i, i + 3);
      if (temp.equals("cos")) {
        index = i;
        for (int j = i + 3; j < pro.length(); j++) {
          if (pro.charAt(j) < '0' || pro.charAt(j) > '9') {
            tempStr = pro.substring(i + 3, j);
            index = j;
            break;
          } else if (pro.charAt(j) >= '0' && pro.charAt(j) <= '9' && j == pro.length() - 1) {
            tempStr = pro.substring(i + 3, j) + pro.charAt(j);
            index = j;
            break;
          }
        }
        double befNumber = Double.parseDouble(tempStr);
        if (befNumber == 90.0) {
          result.append(0);
        } else {
          double number = Math.toRadians(befNumber);
          result.append(Math.sin(number));
        }
        i = index - 1;
        continue;
      }
      result.append(pro.charAt(i)); //+-*/直接插入
    }
    int length = pro.length();
    if (index != length - 1) { //不存在最后一个运算是sin的情况，则把原来的字符压入字符串，否则直接跳过
      result.append(pro, length - 3, length - 1).append(pro.charAt(length - 1));
    }
    return result.toString();
  }

  /**
   * 计算tan.
   */
  public static String removeTan(String pro) {
    StringBuilder result = new StringBuilder();
    int index = 0;
    for (int i = 0; i < pro.length() - 3; i++) {
      String tempStr = "";
      String temp = pro.substring(i, i + 3);
      if (temp.equals("tan")) {
        index = i;
        for (int j = i + 3; j < pro.length(); j++) {
          if (pro.charAt(j) < '0' || pro.charAt(j) > '9') {
            tempStr = pro.substring(i + 3, j);
            index = j;
            break;
          } else if (pro.charAt(j) >= '0' && pro.charAt(j) <= '9' && j == pro.length() - 1) {
            tempStr = pro.substring(i + 3, j) + pro.charAt(j);
            index = j;
            break;
          }
        }
        double befNumber = Double.parseDouble(tempStr);
        if (befNumber == 90.0) {
          result.append(Double.POSITIVE_INFINITY);
        } else {
          double number = Math.toRadians(befNumber);
          result.append(Math.tan(number));
        }
        i = index - 1;
        continue;
      }
      result.append(pro.charAt(i)); //+-*/直接插入
    }
    int length = pro.length();
    if (index != length - 1) { //不存在最后一个运算是sin的情况，则把原来的字符压入字符串，否则直接跳过
      result.append(pro, length - 3, length - 1).append(pro.charAt(length - 1));
    }
    return result.toString();
  }
}
