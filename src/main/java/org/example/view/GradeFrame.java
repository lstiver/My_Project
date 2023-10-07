package org.example.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.example.entity.User;
import org.example.utils.RandomAnswer;

/**.
 * 这是一个显示成绩的界面，显示做题人的账户名和其得分
 */
public class GradeFrame extends JFrame implements ActionListener {
  private JLabel welcome; //标签
  private JButton againbtn; //确定按钮
  private JButton exitbtn; //退出按钮
  private JPanel fill; //用于流式布局放在最中间
  private JPanel fill1;
  User user;
  Double grade;

  /**.
   * 构造方法
   * 用户类对象@param user
   * 成绩@param grade
   */
  public GradeFrame(User user, Double grade) {
    this.user = user;
    this.grade = grade;
    init();
  }

  /**.
   *这是一个init函数，主要用于初始化和设置GradeFrame面板上的组件
   */
  public void init() {
    setTitle("中小学数学卷子自动生成程序");  //设置标题
    setSize(600, 300); //设置窗口大小
    setUndecorated(false); // 默认，显示边框和标题栏
    setResizable(true); //可调节窗口大小
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时的默认操作
    setLayout(new GridLayout(2, 1, 20, 20)); //设置为东西南北中布局
    setLocationRelativeTo(null); //设置在中间显示

    //初始化
    againbtn = new JButton("继续做题");
    exitbtn = new JButton("退出登录");
    welcome = new JLabel(user.getAccount() + "  您的分数为：" + grade + "!");
    fill = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //设置字体
    againbtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    exitbtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    welcome.setFont(new Font(Font.SERIF, Font.BOLD, 28));

    //添加组件
    fill.add(exitbtn);
    fill.add(againbtn);
    fill1.add(welcome);
    add(fill1);
    add(fill);

    //设置监听
    againbtn.setActionCommand("again");
    exitbtn.setActionCommand("exit");

    //绑定监听器
    againbtn.addActionListener(this);
    exitbtn.addActionListener(this);
  }

  /**.
   * 这是一个监听器处理事件回调的方法
   * 捕获到的按键操作@param e
   */
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "again":
        FunctionFrame frame = new FunctionFrame(user);
        frame.setVisible(true);
        dispose();
        break;
      case "exit":
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
        break;
      default:
        System.out.println("ErrorGrade");
    }
  }
}
