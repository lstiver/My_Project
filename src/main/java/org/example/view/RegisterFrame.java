package org.example.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.example.dao.InsertDao;
import org.example.dao.RegistDao;
import org.example.utils.Check;
import org.example.utils.Constant;
import org.example.utils.Send;

/**.
 * 这是一个继承了JFrame类的ReegisterFrame类，用于显示登录界面
 */

public class RegisterFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;  //定义程序序列化,1L是默认定义
  private JLabel welcome; //标签
  private JButton surebtn; //确定按钮
  private JButton backbtn; //返回按钮
  private JButton getbtn; //重新获取验证码按钮
  private JLabel account; //账户标签
  private JComboBox<String> choice; //下拉框
  private JLabel phone; //联系方式标签
  private JLabel check; //验证码标签
  private JTextField accountfield; //账户文本输入框
  private JTextField phonenumber; //联系方式输入框
  private JTextField checkfield; //验证码输入框
  private JPanel registerpane; //面板容器
  private JPanel fill; //用于流式布局放在最中间
  private JPanel fill1; //用于流式布局放在最中间
  private JPanel fill2; //用于流式布局放在最中间
  private JPanel fill3; //用于流式布局放在最中间
  static int id = 0;

  public RegisterFrame() {
    init();
  }

  /**.
   * 这是一个init函数，用于初始化和设置注册界面的组件
   */
  public void init() {
    setTitle("中小学数学卷子自动生成程序");  //设置标题
    setSize(600, 380); //设置窗口大小
    setUndecorated(false); // 默认，显示边框和标题栏
    setResizable(true); //可调节窗口大小
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时的默认操作
    setLayout(new BorderLayout()); //设置为东西南北中布局
    setLocationRelativeTo(null); //设置在中间显示

    //初始化
    surebtn = new JButton("确定注册");
    backbtn = new JButton("返回");
    getbtn = new JButton("获取验证码");
    account = new JLabel("账户名 ");
    accountfield = new JTextField(12);
    phone = new JLabel("电话号码");
    phonenumber = new JTextField(11);
    check = new JLabel("验证码 ");
    checkfield = new JTextField(4);
    registerpane = new JPanel(new GridLayout(3, 1));
    welcome = new JLabel("    ---------------*欢迎注册账号*---------------");
    fill = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fill3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    choice = new JComboBox<>(Constant.returnoption());

    //设置字体
    surebtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    backbtn.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    getbtn.setFont(new Font(Font.SERIF, Font.BOLD, 18));
    account.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    accountfield.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    phone.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    phonenumber.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    check.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    checkfield.setFont(new Font(Font.SERIF, Font.BOLD, 28));
    welcome.setFont(new Font(Font.SERIF, Font.BOLD, 30));
    choice.setFont(new Font(Font.SERIF, Font.BOLD, 24));
    registerpane.setBorder(BorderFactory.createEtchedBorder()); //凹槽效果的边框

    //按钮设置监听
    surebtn.setActionCommand("确定注册");
    backbtn.setActionCommand("返回");
    getbtn.setActionCommand("验证码");

    //按钮绑定监听器
    surebtn.addActionListener(this);
    backbtn.addActionListener(this);
    getbtn.addActionListener(this);
    //设置位置
    fill.add(account);
    fill.add(accountfield);
    fill1.add(phone);
    fill1.add(phonenumber);
    fill2.add(check);
    fill2.add(checkfield);
    fill2.add(getbtn);
    fill3.add(choice);
    fill3.add(surebtn);
    fill3.add(backbtn);
    registerpane.add(fill);
    registerpane.add(fill1);
    registerpane.add(fill2);
    add(welcome, BorderLayout.NORTH);
    add(registerpane, BorderLayout.CENTER);
    add(fill3, BorderLayout.SOUTH);
  }

  /**.
   * 这是一个监听器处理事件回调的方法
   * 这是一个事件监听@param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String account1 = accountfield.getText();
    String phone1 = phonenumber.getText();
    String type = (String) choice.getSelectedItem();
    String check1 = checkfield.getText();
    switch (e.getActionCommand()) {
      case "确定注册":
        int temp = Check.checkregister(account1, check1, id);
        switch (temp) {
          case 1:
            JOptionPane.showMessageDialog(RegisterFrame.this,
                "请点击获取验证码", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
            break;
          case 2:
            JOptionPane.showMessageDialog(RegisterFrame.this,
                "验证码超时，请重新获取验证码", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
            break;
          case 3:
            JOptionPane.showMessageDialog(RegisterFrame.this,
                "注册成功！初始密码为123，请及时修改密码", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
            InsertDao.adduser(account1, type, phone1);
            break;
          case 4:
            JOptionPane.showMessageDialog(RegisterFrame.this,
                "验证码错误请重新填写", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
            break;
          default:
            System.out.println("ErrorrRegister");
        }
        break;
      case "验证码":
        if (RegistDao.repeateduser(account1)) {
          JOptionPane.showMessageDialog(RegisterFrame.this,
              "用户名重复", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
        } else if (account1.isEmpty()) {
          JOptionPane.showMessageDialog(RegisterFrame.this,
              "账户名不能为空", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
        } else {
          if (!phone1.isEmpty() && Check.phoneFoormat(phone1)) {
            id = Send.sendMessage(phone1, account1);
          } else {
            JOptionPane.showMessageDialog(RegisterFrame.this,
                "电话号码格式不正确", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
          }
        }
        break;
      case "返回":
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
        break;
      default:
        System.out.println("ErrorRegister");
    }
  }
}