package org.example.view;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.example.dao.LoginDao;
import org.example.entity.User;

/**.
 * 这是一个继承了JFrame类的LoginFrame类，用于显示登录界面
 */
public class LoginFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;  //定义程序序列化,1L是默认定义
  private JLabel welcome; //标签
  private JLabel account; //账户标签
  private JLabel password; //密码标签
  private JButton loginbtn; //登录按钮
  private JButton registerbtn; //注册按钮
  private JButton exitbtn; //退出按钮
  private JPanel loginpane; //面板容器
  private JTextField accountfield; //账户文本输入框
  private JPasswordField passwordfield; //密码文本输入框
  JLayeredPane layeredPane = new JLayeredPane();
  LoginDao loginDao = new LoginDao();

  public LoginFrame() {
    init();
  }

  /**.
   * 这是一个init函数，用于初始化登录界面的组件
   */
  private void init() {
    setTitle("中小学数学卷子自动生成程序");  //设置标题
    setSize(800, 520); //设置窗口大小
    setUndecorated(false); // 默认，显示边框和标题栏
    setResizable(false); //不可调节窗口大小
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时的默认操作
    setLayout(null); //设置为空布局
    setLocationRelativeTo(null); //设置在中间显示

    //初始化
    welcome = new JLabel("---------------*欢迎登录中小学数学卷子自动生成系统*----------------");
    account = new JLabel("账户:");
    password = new JLabel("密码:");
    loginbtn = new JButton("登录");
    registerbtn = new JButton("注册");
    exitbtn = new JButton("退出");
    loginpane = new JPanel();
    accountfield = new JTextField(12);
    accountfield.setToolTipText("输入不能超过12个字符");
    passwordfield = new JPasswordField(15);
    passwordfield.setToolTipText("输入不能超过15个字符");
    //设置字体
    welcome.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    account.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    password.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    accountfield.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    passwordfield.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    loginbtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    registerbtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    exitbtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    //设置位置和边框
    loginpane.setBounds(new Rectangle(0,  5,  700, 390));
    loginpane.setBorder(BorderFactory.createEtchedBorder()); //凹槽效果的边框
    layeredPane.setBounds(50, 60, 700, 400);
    welcome.setBounds(50, 15, 700, 30);
    account.setBounds(180, 65, 80, 30);
    password.setBounds(180, 135, 80, 30);
    accountfield.setBounds(250, 60, 200, 45);
    passwordfield.setBounds(250, 130, 200, 45);
    loginbtn.setBounds(450, 250, 120, 50);
    registerbtn.setBounds(290, 250, 120, 50);
    exitbtn.setBounds(120, 250, 120, 50);

    //按钮设置监听
    loginbtn.setActionCommand("登录");
    registerbtn.setActionCommand("注册");
    exitbtn.setActionCommand("退出");
    //按钮绑定监听器
    loginbtn.addActionListener(this);
    registerbtn.addActionListener(this);
    exitbtn.addActionListener(this);
    //添加组件
    add(welcome);
    layeredPane.add(loginpane, JLayeredPane.DEFAULT_LAYER);
    layeredPane.add(account, JLayeredPane.POPUP_LAYER);
    layeredPane.add(accountfield, JLayeredPane.POPUP_LAYER);
    layeredPane.add(passwordfield, JLayeredPane.POPUP_LAYER);
    layeredPane.add(password, JLayeredPane.POPUP_LAYER);
    layeredPane.add(loginbtn, JLayeredPane.POPUP_LAYER);
    layeredPane.add(registerbtn, JLayeredPane.POPUP_LAYER);
    layeredPane.add(exitbtn, JLayeredPane.POPUP_LAYER);
    getContentPane().add(layeredPane);
  }

  /**.
   * 这是一个actionPerformed函数，用于根据监听到的不同动作做出反应，比如登录和退出两个动作
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String account = accountfield.getText();
    String password = new String(passwordfield.getPassword());
    switch (e.getActionCommand()) {
      case "登录":
        User user = User.getUser(account, password);
        if (user == null) {
          JOptionPane.showMessageDialog(LoginFrame.this,
                  "账户或密码输入错误！", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
        } else {
          JOptionPane.showMessageDialog(LoginFrame.this,
                  "登录成功！", "", JOptionPane.YES_NO_CANCEL_OPTION);
          FunctionFrame functionFrame = new FunctionFrame(user);
          functionFrame.setVisible(true);
          dispose();
        }
        break;
      case "注册":
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
        //dispose();
        break;
      case "退出":
        System.exit(0);
        break;
      default:
        System.out.println("GetAction Error！");
    }
  }
}
