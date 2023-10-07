package org.example.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**.
 * 这是一个获取随机数的工具类RandomUtils
 */
public class RandomAnswer {
  static Random random = new Random(System.currentTimeMillis());
  static Double answer;
  //创建一个线程池，因为getFourRandom这函数调用太频繁了
  private static ExecutorService executor = Executors.newFixedThreadPool(3);

  /**.
   * 这是一个生成四个随机答案函数
   */
  public static ArrayList getFourRandom(String question) {
    GenerateInternal generateInternal =  new GenerateInternal(question);
    executor.execute(generateInternal);
    return generateInternal.options;
  }

  // 实现 Runnable 接口的内部类
  private static class GenerateInternal implements Runnable {
    public ArrayList options;
    String question;

    private GenerateInternal(String question) {
      this.question = question;
      Double a;
      Set<Double> set = new HashSet<>();
      answer = Remove.rightanswer(question);
      answer = standard(answer);
      set.add(answer);
      int x = random.nextInt(60) - random.nextInt(60);
      while (set.size() < 4) {
        a = answer * x * (random.nextDouble()) + random.nextInt(60);
        set.add(standard(a));
      }
      options = new ArrayList(set);
      Collections.shuffle(options);
      options.add(options.indexOf(answer));
    }

    @Override
    public void run() {
    }
  }

  public static void shutdownExecutor() {
    executor.shutdown();
  }

  public static Double standard(Double d) {
    return Math.round(d * 100) / 100.0;
  }
}

