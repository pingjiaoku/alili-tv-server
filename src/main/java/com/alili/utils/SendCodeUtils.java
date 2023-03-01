package com.alili.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import jakarta.annotation.Resource;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 短信/邮件发送工具类
 */
@Component
@Slf4j
public class SendCodeUtils {

    @Value("${SMS.signName}")
    private String signName;

    @Value("${SMS.templateCode}")
    private String templateCode;

    @Value("${SMS.accessKeyId}")
    private String accessKeyId;

    @Value("${SMS.secret}")
    private String secret;

    @Value("${spring.mail.setting.subject}")
    private String subject;

    @Value("${spring.mail.setting.text}")
    private String text;

    @Value("${spring.mail.setting.from}")
    private String from;

    @Value("${spring.mail.username}")
    private String emailNum;

    @Resource
    private JavaMailSender mailSender;

    /**
     * 发送短信
     * @param phone 手机号
     * @param code        参数-验证码
     */
    public void sendSMS(String phone, String code) throws ClientException {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phone);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse response = client.getAcsResponse(request);
        log.warn("{}", response.toString());
    }

    /**
     * 发送邮件
     * @param email
     * @param code
     */
    public void sendEmail(String email, String code) throws UnsupportedEncodingException, AddressException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text.replace("{}", code));
        message.setTo(email);
        message.setFrom(new InternetAddress(MimeUtility.encodeText(from) + " <" + emailNum + ">").toString());
        //发送邮件
        mailSender.send(message);
    }
}
