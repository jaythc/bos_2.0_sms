package cn.thc.bos.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import cn.thc.bos.utils.AliSmsUtils;

@Service
public class SmsConsumer implements MessageListener {
	//MQ消费者的编写
	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			//调用阿里大于的工具类, 发送短信, 从生产者中获取电话号码和验证码
			SendSmsResponse response = AliSmsUtils.sendSms(mapMessage.getString("telephone"),null, 
					 mapMessage.getString("randomCode"));
			if (response.getCode()!=null&& response.getCode().equals("OK")) {
				System.err.println("短信发送成功!");
			}
		} catch (ClientException | JMSException e) {
			e.printStackTrace();
			System.err.println("短信发送失败!");
		}
	}
}
