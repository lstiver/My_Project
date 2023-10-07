package org.example;

import org.example.view.LoginFrame;

/**.
 * Hello world!
 */
public class App {
  /**.
   * 这是一个main类，是这个程序的入口
   * 传参@param args
   */
  public static void main(String[] args) {
    LoginFrame loginFrame = new LoginFrame();
    loginFrame.setVisible(true);
    loginFrame.repaint();
  }
}
