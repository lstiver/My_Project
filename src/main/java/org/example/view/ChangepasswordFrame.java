package org.example.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.example.dao.UpdateDao;
import org.example.entity.User;
import org.example.utils.Check;

/**.
 * 这是一个修改密码的界面类ChangepasswordFrame
 */
public class ChangepasswordFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;  //定义程序序列化,1L是默认定义
  private JLabel newpassword; //新密码标签
  private JLabel again; //再次输入密码标签
  private JLabel password; //原来的密码
  private JTextField passwordfield;
  private JTextField password1; //密码
  private JTextField password2; //确定密码
  private JButton surebtn; //确认按键
  private JButton exitbtn2; //退出登录按键
  private JPanel fil;
  private JPanel fill; //用于流式布局放在最中间
  private JPanel fill1;
  private JPanel fill2;
  private User user;

  public ChangepasswordFrame(User user) {
    init();
    this.user = user;
  }

  private void init() {
    setTitle("中小学数学卷子自动生成程序");  //设置标题
    setSize(600, 400); //设置窗口大小
    setUndecorated(false); // 默认，显示边框和标题栏
    setResizable(true); //不可调节窗口大小
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时的默认操作
    setLayout(new GridLayout(4, 1)); //设置布局
    setLocationRelativeTo(null); //设置在中间显示
    setLayout(new GridLayout(4, 1, 10, 10)); // 水平和垂直间隔均为 10 像素

    //初始化组件
    surebtn = new JButton("确定");
    exitbtn2 = new JButton("返回");
    again = new JLabel("确定密码:");
    newpassword = new JLabel("新密码 :");
    password = new JLabel("原密码");
    passwordfield = new JTextField(11);
    password1 = new JTextField(11);
    password2 = new JTextField(11);
    fil = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //设置字体
    surebtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    exitbtn2.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    again.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    password.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    passwordfield.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    newpassword.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    password1.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    password2.setFont(new Font(Font.SERIF, Font.BOLD, 28));

    //按钮设置监听
    surebtn.setActionCommand("确定");
    exitbtn2.setActionCommand("返回");

    //按钮绑定监听器
    surebtn.addActionListener(this);
    exitbtn2.addActionListener(this);

    //添加组件
    fil.add(password);
    fil.add(passwordfield);
    fill.add(newpassword);
    fill.add(password1);
    fill1.add(again);
    fill1.add(password2);
    fill2.add(surebtn);
    fill2.add(exitbtn2);
    add(fil);
    add(fill);
    add(fill1);
    add(fill2);
  }

  /**.
   * 这是一个事件监听回调函数
   * 监听@param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String pass1 = password1.getText();
    String pass2 = password2.getText();
    String pass = passwordfield.getText();
    switch (e.getActionCommand()) {
      case "确定":
        if (!pass.equals(user.getPassword())) {
          JOptionPane.showMessageDialog(ChangepasswordFrame.this,
              "原密码输入错误！", "", JOptionPane.YES_NO_CANCEL_OPTION);
        } else {
          int temp = Check.checkNewPassword(pass1, pass2);
          switch (temp) {
            case 0:
              JOptionPane.showMessageDialog(ChangepasswordFrame.this,
                  "密码设置不符合规范，密码应在6-10个字符之间，包含大小写字母和数字！", "", JOptionPane.YES_NO_CANCEL_OPTION);
              break;
            case 1:JOptionPane.showMessageDialog(ChangepasswordFrame.this,
                "两次密码输入不一致！", "", JOptionPane.YES_NO_CANCEL_OPTION);
            break;
            case 2:
              JOptionPane.showMessageDialog(ChangepasswordFrame.this,
                  "修改密码成功！", "", JOptionPane.YES_NO_CANCEL_OPTION);
              UpdateDao.updateUser(user.getAccount(), pass1);
              LoginFrame loginFrame = new LoginFrame();
              loginFrame.setVisible(true);
              dispose();
              break;
            default:
              System.out.println("ErrorChange1");
          }
        }
        break;
      case "返回":
        FunctionFrame frame = new FunctionFrame(user);
        frame.setVisible(true);
        dispose();
        break;
      default:
        System.out.println("ErrorChange2");
    }
  }
}
