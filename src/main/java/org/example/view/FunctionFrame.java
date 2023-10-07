package org.example.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.example.entity.User;
import org.example.utils.Constant;

/**.
 * 这是一个继承了JFrame类的LoginFrame类，用于显示登录界面
 */
public class FunctionFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;  //定义程序序列化,1L是默认定义
  private JTextField command; //命令
  private JLabel prompt; //提示标签
  private JButton surebtn; //确认按键
  private JButton changepassword; //修改密码按键
  private JButton exitbtn1; //退出登录按键
  private JComboBox<String> choice; //下拉框
  private JPanel fill; //用于流式布局放在最中间
  private JPanel fill1;
  private JLabel placeholder = new JLabel(" "); //用于占位
  User user;

  public FunctionFrame(User user) {
    init();
    this.user = user;
  }

  /**.
   * 这是一个初始化和设置功能选择界面的函数init
   */
  private void init() {
    setTitle("中小学数学卷子自动生成程序");  //设置标题
    setSize(600, 400); //设置窗口大小
    setUndecorated(false); // 默认，显示边框和标题栏
    setResizable(true); //不可调节窗口大小
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时的默认操作
    setLayout(new GridLayout(4, 1)); //设置布局
    setLocationRelativeTo(null); //设置在中间显示
    setLayout(new GridLayout(4, 1, 10, 10)); // 水平和垂直间隔均为 10 像素

    //初始化
    prompt = new JLabel("请输入指令");
    command = new JTextField(4);
    surebtn = new JButton("出题");
    exitbtn1 = new JButton("退出登录");
    changepassword = new JButton("修改密码");
    choice = new JComboBox<>(Constant.returnoption());
    fill = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //设置字体
    changepassword.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    prompt.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    command.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    surebtn.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    exitbtn1.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    choice.setFont(new Font(Font.SERIF, Font.BOLD, 24));

    //添加组件
    fill.add(choice);
    fill.add(command);
    fill.add(surebtn);
    fill1.add(exitbtn1);
    fill1.add(placeholder);
    fill1.add(changepassword);
    add(placeholder);
    add(fill);
    add(fill1);

    //按钮设置监听
    surebtn.setActionCommand("生成题目");
    exitbtn1.setActionCommand("退出登录");
    changepassword.setActionCommand("修改密码");
    //按钮绑定监听器
    surebtn.addActionListener(this);
    exitbtn1.addActionListener(this);
    changepassword.addActionListener(this);
  }

  /**.
   * 这是一个事件监听回调函数
   * 事件监听@param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "生成题目":
        if (!command.getText().isEmpty()) {
          int number = Integer.parseInt(command.getText());
          String difficulty = (String) choice.getSelectedItem();
          user.setDifficulty(difficulty);
          if (Constant.testNumber(number)) {
            QuestionFrame questionFrame = null;
            try {
              questionFrame = new QuestionFrame(user, number);
              questionFrame.setVisible(true);
            } catch (SQLException ex) {
              throw new RuntimeException(ex);
            }
            dispose();
            System.out.println("A");
          } else {
            JOptionPane.showMessageDialog(FunctionFrame.this,
                "题目数量在1-50之间", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
          }

        } else {
          JOptionPane.showMessageDialog(FunctionFrame.this,
              "请输入要生成的题目数量", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
        }
        break;
      case "退出登录":
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
        break;
      case "修改密码":
        ChangepasswordFrame changepasswordFrame = new ChangepasswordFrame(user);
        changepasswordFrame.setVisible(true);
        dispose();
        break;
      default:
        System.out.println("！");
    }
  }
}

