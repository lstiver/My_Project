package org.example.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.example.entity.User;
import org.example.utils.Check;
import org.example.utils.Constant;
import org.example.utils.RandomAnswer;

/**.
 * 这是一个显示答题界面的类
 */
public class QuestionFrame extends JFrame implements ActionListener, Runnable {
  private static final long serialVersionUID = 1L;  //定义程序序列化,1L是默认定义
  private JLabel[] questionLabel; //题目标签
  private JLabel label; //题号标签
  private JTextField choice; //选项填空的地方
  private JLabel[] optionA;
  private JLabel[] optionB;
  private JLabel[] optionC;
  private JLabel[] optionD;
  private JLabel opA;
  private JLabel opB;
  private JLabel opC;
  private JLabel opD;
  private JButton surebtn; //提交按键
  private JButton previous; //上一题按键
  private JButton next; //下一题按键
  private JPanel fill; //用于流式布局放在最中间
  private JPanel fill1;
  private JPanel cf;
  private JPanel cf1;
  private JLabel placeholder = new JLabel(" "); //用于占位
  static CardLayout care = new CardLayout();
  static FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
  static GridLayout gridLayout = new GridLayout(4, 1, 10, 10);
  static Font font = new Font(Font.SERIF, Font.BOLD, 24);
  int[] answer; //答案??正确
  String[] myanswer; //选择的答案
  User user;
  int number;
  int current = 1;
  String[] questions;
  ArrayList options;

  /**.
   * 这是一个构造函数
   * 用户@param user
   * 题目数量@param number
   * 抛出异常@throws SQLException
   */
  public QuestionFrame(User user, int number) throws SQLException {
    this.user = user;
    this.number = number;
    questions = Constant.getQuestion(user.getDifficulty(), user.getAccount(), number);
    setTitle("中小学数学卷子自动生成程序");  //设置标题
    setSize(600, 400); //设置窗口大小
    setUndecorated(false); // 默认，显示边框和标题栏
    setResizable(true); //不可调节窗口大小
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时的默认操作
    setLayout(new BorderLayout()); // 设置布局
    setLocationRelativeTo(null); //设置在中间显示
    init();
  }

  /**.
   * 这是一个初始化界面上组件的init方法
   */
  public void init() {
    //初始化
    questionLabel = new JLabel[number]; //题
    answer = new int[number]; //答案??正确
    myanswer = new String[number]; //选择的答案
    optionA = new JLabel[number];
    optionB = new JLabel[number];
    optionC = new JLabel[number];
    optionD = new JLabel[number];
    opA = new JLabel("A.");
    opB = new JLabel("B.");
    opC = new JLabel("C.");
    opD = new JLabel("D.");
    choice = new JTextField(4);
    label = new JLabel("第1题");
    previous = new JButton("上一题");
    next = new JButton("下一题");
    surebtn = new JButton("提交答案");
    fill = new JPanel(flowLayout);
    fill1 = new JPanel(flowLayout);
    cf = new JPanel(care);
    cf1 = new JPanel(care);

    //设置字体
    label.setFont(font);
    surebtn.setFont(font);
    previous.setFont(font);
    next.setFont(font);
    choice.setFont(font);

    //按钮设置监听
    surebtn.setActionCommand("提交答案");
    previous.setActionCommand("上一题");
    next.setActionCommand("下一题");

    //按钮绑定监听器
    surebtn.addActionListener(this);
    previous.addActionListener(this);
    next.addActionListener(this);

    //添加组件
    fill.add(label);
    for (int i = 0; i < number; i++) { //添加题目和选项填空的组件
      final JPanel jPanel = new JPanel(flowLayout);
      final JPanel grid = new JPanel(gridLayout);
      final JPanel flow = new JPanel(flowLayout);
      questionLabel[i] = new JLabel(questions[i]);
      options = RandomAnswer.getFourRandom(questions[i]);
      optionA[i] = new JLabel("A.  " + options.get(0).toString());
      optionB[i] = new JLabel("B.  " + options.get(1).toString());
      optionC[i] = new JLabel("C.  " + options.get(2).toString());
      optionD[i] = new JLabel("D.  " + options.get(3).toString());
      optionA[i].setFont(font);
      optionB[i].setFont(font);
      optionC[i].setFont(font);
      optionD[i].setFont(font);
      questionLabel[i].setFont(font);
      answer[i] = (int) options.get(4);
      System.out.println(answer[i]);
      grid.add(optionA[i]);
      grid.add(optionB[i]);
      grid.add(optionC[i]);
      grid.add(optionD[i]);
      jPanel.add(grid);
      flow.add(questionLabel[i]);
      cf.add(flow);
      cf1.add(jPanel);
    }
    System.out.println("B");
    final JPanel jl = new JPanel(new BorderLayout());
    final JPanel jl1 = new JPanel(flowLayout);
    fill.add(cf);
    fill.add(choice);
    jl1.add(cf1);
    jl.add(fill, BorderLayout.NORTH);
    jl.add(jl1);
    fill1.add(previous);
    fill1.add(surebtn);
    fill1.add(next);
    add(placeholder, BorderLayout.NORTH);
    add(jl, BorderLayout.CENTER);
    add(fill1, BorderLayout.SOUTH);
    setVisible(true);
  }

  /**.
   * 这是一个监听器处理事件回调的方法
   * 监听到的事件@param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    myanswer[current - 1] = choice.getText();
    Boolean flag = Check.answerFormat("a" + myanswer[current - 1]);
    switch (e.getActionCommand()) {
      case "上一题":
        if (current > 1) {
          if (flag) {
            care.previous(cf);
            care.previous(cf1);
            current--;
            choice.setText(myanswer[current - 1]);
          } else {
            JOptionPane.showMessageDialog(QuestionFrame.this,
                "请输入A,B,C,D中任意一个", "提示", JOptionPane.WARNING_MESSAGE);
            choice.setText(null);
          }
          label.setText("第" + current + "题");
        }
        break;
      case "下一题":
        if (current < number) {
          if (flag) {
            care.next(cf);
            care.next(cf1);
            current++;
            choice.setText(myanswer[current - 1]);
          } else {
            JOptionPane.showMessageDialog(QuestionFrame.this,
                    "请输入A,B,C,D中任意一个", "提示", JOptionPane.WARNING_MESSAGE);
            choice.setText(null);
          }
          label.setText("第" + current + "题");
        }
        break;
      case "提交答案":
        if (flag) {
          int right = 0; //正确的题目数量
          int temp = 0;
          for (int i = 0; i < number; i++) {
            if (null == myanswer[i]) {
              myanswer[i] = "E";
            }
            switch (myanswer[i]) {
              case "A": temp = 0;
                break;
              case "B": temp = 1;
                break;
              case "C": temp = 2;
                break;
              case "D": temp = 3;
                break;
              default:
                temp = 4;
            }
            if (temp == answer[i]) {
              right++;
            }
          }
          Double grade = (Double.valueOf(right) / number) * 100;
          GradeFrame gradeFrame = new GradeFrame(user, RandomAnswer.standard(grade));
          gradeFrame.setVisible(true);
          dispose();
        } else {
          JOptionPane.showMessageDialog(QuestionFrame.this,
              "请输入A,B,C,D中任意一个", "提示", JOptionPane.WARNING_MESSAGE);
        }
        break;
      default:
        dispose();
    }
  }

  @Override
  public void run() {
    init();
  }
}
