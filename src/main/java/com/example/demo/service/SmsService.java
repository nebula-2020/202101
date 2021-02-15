/*
 * 文件名：SmsService.java
 * 描述：发送验证码相关服务。
 * 修改人：刘可
 * 修改时间：2021-02-15
 */
package com.example.demo.service;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.tool.Rand;
import com.example.demo.vo.SmsVO;
import com.rainbow.sms.Client;

import org.springframework.stereotype.Service;

/**
 * 提供短信验证服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see send
 * @see verify
 * @since 2021-02-15
 */
@Service("smsService")
public class SmsService extends ComService
{
    public final String UNIT_OF_TIME = "分钟";
    public final String PROJECT_NAME = "星云社区";
    private final String API_URL = "https://sms_developer.zhenzikj.com";
    private final String APP_ID = "100010";
    private final String APP_SERECT =
            "1S0U08SK15BB1BV804RG1D4G006S1JOR18DR0AAC0URO0TAT14U61E491U3K05RR";
    private final Integer SAFE = 0x80000000;
    private final Integer CODE_LEN = 4;
    private final int DECIMAL = 36;
    private final String TEMPLATE_ID = "0";

    /**
     * 发送短信。
     * 
     * @param phone 收件方手机号
     * @param key 手机端传来的代码
     * @param sec 服务器生成的代码
     * @param time 验证码有效时间
     * @return 将写入session的数据。
     */
    public SmsVO send(String phone, String key, String sec, long time)
            throws NullPointerException
    {
        SmsVO ret = new SmsVO();

        try
        {

            if (tool.containsNullOrEmpty(phone, key, sec))
            {
                throw new NullPointerException();
            } // 结束：if (StrTool.containsNullOrEmpty(phone, key))

            byte count = 2;// 发送失败后重试一次
            Random random = new Random(Rand.getRandom().nextLong());
            Client client = new Client(API_URL, APP_ID, APP_SERECT);

            // 短信请求参数名
            String[] tags = new String[] {
                    "number", "templateId", "templateParams"
            };

            do
            {
                Map<String, Object> params = new HashMap<>();

                Integer codeVal = SAFE | random.nextInt();
                String code = Integer.toUnsignedString(codeVal, DECIMAL);// 得到一个四位的不分大小写的数字字母字串
                StringBuilder bulider = new StringBuilder();

                for (int i = 1; i <= CODE_LEN; i++)// 去掉首位，剩下4位作为验证码，首位无法取到全部26个字母
                {
                    bulider.append(code.charAt(i));
                } // 结束：for (int i = 1; i <= CODE_LEN; i++)
                code = bulider.toString();

                Calendar t = Calendar.getInstance();// 验证码有效时间
                t.setTimeInMillis(time);

                // 模板参数
                String[] templateParams = new String[] {
                        PROJECT_NAME, code,
                        t.get(Calendar.MINUTE) + UNIT_OF_TIME// 到期时间多少分钟，一般超不了一个小时
                };

                // 短信请求参数
                Object[] attrs = new Object[] {
                        phone, TEMPLATE_ID, templateParams
                };

                // 添加请求参数
                for (int i = 0; i < tags.length; i++)
                {
                    params.put(tags[i], attrs[i]);
                } // 结束：for (int i = 0; i < tags.length; i++)

                String result = client.send(params);// 发短信

                JSONObject jo = JSONObject.parseObject(result);// 解析发送反馈

                if (jo.containsKey("code") && jo.getIntValue("code") == 0)
                {
                    // 验证码发送成功
                    count = 0;
                    Long now = System.currentTimeMillis();
                    ret.setCode(code);
                    ret.setPhone(phone);
                    ret.setKey(key);
                    ret.setSecret(sec);
                    ret.setCreateTime(now);
                } // 结束：if(jo.containsKey("code")&&jo.getIntValue("code")==0)
                else count--;

            } while (count > 0);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        }

        return ret;
    }

    /**
     * 验证短信。
     * 
     * @param request 包括客户端和服务器生成的两段代码、验证码、手机号和当前时间
     * @param time 验证码有效时间
     * @param session 包括客户端和服务器生成的两段代码、验证码、手机号和验证码发送时间
     * @return 描述验证是否通过。
     */
    public boolean verify(SmsVO request, long time, SmsVO session)
    {

        boolean ret = false;

        if (request != null && session != null)
        {

            try
            {
                Long requestTime = request.getCreateTime();
                Long sessionTime = session.getCreateTime();

                if (requestTime != null && sessionTime != null// 时间非空
                        && requestTime - sessionTime <= time// 时间未到
                        && requestTime > 0 && sessionTime > 0)// 时间正常
                {

                    if (request.getCode().compareTo(session.getCode()) == 0
                            && request.getKey().compareTo(session.getKey()) == 0
                            && request.getPhone()
                                    .compareTo(session.getPhone()) == 0
                            && request.getSecret()
                                    .compareTo(session.getSecret()) == 0)
                    {
                        ret = true;
                    }
                } // 结束：if (requestTime != null && sessionTime != null.....

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
            }

        } // 结束：if (request != null && session != null)

        return ret;
    }
}
