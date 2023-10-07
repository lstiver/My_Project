package org.example.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import java.io.InputStream;
import java.util.Properties;
import org.example.dao.RegistDao;
import org.example.jdbc.JdbcUtils;

/**.
 * 这是一个用于发送短信的工具类
 */
public class Send {
  /**.
   * 这是一个用于读取配置文件中信息的方法
   * 返回@return
   */
  public static String[] readConfig() {
    String[] str = new String[3];
    try {
      InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("ApiKey.properties");
      //通过输入流加载文件内容。然后从Properties对象中读取信息
      Properties properties = new Properties();
      properties.load(is);
      str[0] = properties.getProperty("SecretId");
      str[1] = properties.getProperty("SecretKey");
      str[2] = properties.getProperty("SDKAppID");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  /**.
   * 这是一个用于发送短信的方法
   * 电话号码@param phoneNum
   * 账户名@param account
   * 返回@return在code里的的id_pk
   */
  public static int sendMessage(String phoneNum, String account) {
    int id = 0;
    try {
      String[] str = readConfig();
      Credential cred = new Credential(str[0], str[1]); //id,key
      ClientProfile clientProfile = new ClientProfile();  //实例客户端配置对象
      clientProfile.setSignMethod(clientProfile.SIGN_TC3_256);
      final SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
      SendSmsRequest request = new SendSmsRequest();
      //填充请求参数
      request.setSmsSdkAppId(str[2]);
      String signName = "土豆雷的公众号";
      request.setSignName(signName);
      String templateId = "1936421";
      request.setTemplateId(templateId);
      int code = (int) ((Math.random() * 9 + 1) * 100000);
      String[] templateParams = { String.valueOf(code), "2"};
      request.setTemplateParamSet(templateParams);
      long expiredAt = System.currentTimeMillis() + 12 * 10000; //过期时间
      id = RegistDao.insertcode(String.valueOf(code), expiredAt); //存入code表中
      String[] phone = new String[]{phoneNum};
      request.setPhoneNumberSet(phone);
      com.tencentcloudapi.sms.v20210111.models.SendSmsResponse response = client.SendSms(request);
      System.out.println(SendSmsResponse.toJsonString(response));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return id;
  }
}
