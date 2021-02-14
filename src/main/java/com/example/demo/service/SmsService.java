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
import com.rainbow.sms.Client;

import org.springframework.stereotype.Service;

/**
 * 提供短信验证服务。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @see getMap
 * @see send
 * @see verify
 * @since 2021-02-15
 */
@Service("smsService")
public class SmsService extends ComService
{
    public final String UNIT_OF_TIME = "分钟";
    public final String PROJECT_NAME = "星云社区";
    public final long MSEC_60000 = 60000;
    private final String API_URL = "https://sms_developer.zhenzikj.com";
    private final String APP_ID = "100010";
    private final String APP_SERECT =
            "1S0U08SK15BB1BV804RG1D4G006S1JOR18DR0AAC0URO0TAT14U61E491U3K05RR";
    private final Integer MASK = 0x39aa3ff;
    private final Integer SAFE = 0x19a100;
    private final Integer CODE_LEN = 4;
    private final int DECIMAL = 36;
    private final String TEMPLATE_ID = "0";
    /**
     * 手机号之键。
     */
    private final String KEY_PHONE = "p";
    /**
     * 服务器生成的代码之键。
     */
    private final String KEY_SECRET = "s";
    /**
     * 验证码之键。
     */
    private final String KEY_CODE = "c";
    /**
     * 客户端传来的代码之键。
     */
    private final String KEY_KEY = "k";
    /**
     * 时间之键。
     */
    private final String KEY_TIME = "t";

    /**
     * 用预设的键来构造表。
     * 
     * @param phone 收件方手机号
     * @param code 短信验证码
     * @param key 手机端传来的代码
     * @param sec 服务器生成的代码
     * @param time 验证码有效时间
     * @return 表，传给<code>verify</code>用。
     */
    public final Map<String, Object>
            getMap(String phone, String code, String key, String sec, long now)
    {
        Map<String, Object> ret = new HashMap<>();
        ret.put(KEY_PHONE, phone);
        ret.put(KEY_CODE, code);
        ret.put(KEY_KEY, key);
        ret.put(KEY_SECRET, sec);
        ret.put(KEY_TIME, now);
        return ret;
    }

    /**
     * 发送短信。
     * 
     * @param phone 收件方手机号
     * @param key 手机端传来的代码
     * @param sec 服务器生成的代码
     * @param time 验证码有效时间
     * @return 将写入session的数据。
     */
    public Map<String, Object>
            send(String phone, String key, String sec, long time)
                    throws NullPointerException
    {
        Map<String, Object> ret = new HashMap<>();

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

                Integer codeVal = MASK & random.nextInt() | SAFE;
                String code = Integer.toUnsignedString(codeVal, DECIMAL);// 得到一个四位的不分大小写的数字字母字串
                StringBuilder bulider = new StringBuilder();

                for (int i = 1; i <= CODE_LEN; i++)//去掉首位，剩下4位作为验证码，首位无法取到全部26个字母
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
                    ret = getMap(phone, code, key, sec, now);
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
     * @param session 包括客户端和服务器生成的两段代码、验证码、手机号和验证码发送时间
     * @return 描述验证是否通过。
     */
    public boolean
            verify(Map<String, Object> request, Map<String, Object> session)
    {

        boolean ret = true;

        if (request == null || session == null)
        {
            ret = false;
        }
        else
        {

            try
            {

                String[] keys = new String[] {
                        KEY_CODE, KEY_KEY, KEY_PHONE, KEY_SECRET
                };

                Long requestTime = (Long)request.get(KEY_TIME);
                Long sessionTime = (Long)session.get(KEY_TIME);

                if (requestTime != null && sessionTime != null// 时间非空
                        && requestTime - sessionTime <= MSEC_60000// 时间未到
                        && requestTime > 0 && sessionTime > 0)// 时间正常
                {

                    for (int i = 0; i < keys.length; i++)
                    {
                        String requestVal = (String)request.get(keys[i]);
                        String sessionVal = (String)session.get(keys[i]);

                        if (tool.containsNullOrEmpty(requestVal, sessionVal)
                                || requestVal.compareTo(sessionVal) != 0)
                        {
                            ret = false;
                            break;
                        } // 结束：if (requestVal.compareTo(sessionVal) != 0)

                    } // 结束：for (int i = 0; i < keys.length; i++)

                } // 结束：if (requestTime != null && sessionTime != null.....

            }
            catch (Exception e)
            {
                e.printStackTrace();
                ret = false;
            }
            finally
            {
            }

        } // 结束：if (request == null || session == null)

        return ret;
    }
}
