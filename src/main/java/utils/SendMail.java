package utils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMultipart;

public class SendMail {
    public Message message;
    public Properties mailpro;
    public SendMail(){
        ProUtil proUtil = new ProUtil("mail.properties");
        mailpro = proUtil.properties;
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", mailpro.getProperty("mail.transport.protocol"));// 连接协议
        properties.put("mail.smtp.host", mailpro.getProperty("mail.smtp.host"));// 主机名
        properties.put("mail.smtp.port", mailpro.getProperty("mail.smtp.port"));// 端口号
        properties.put("mail.smtp.auth", mailpro.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.ssl.enable", mailpro.getProperty("mail.smtp.ssl.enable"));// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", mailpro.getProperty("mail.debug"));// 设置是否显示debug信息 true 会在控制台显示相关信息

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //设置邮箱账户和密码
                return new PasswordAuthentication(mailpro.getProperty("mail.from"),mailpro.getProperty("mail.password"));
            }
        });

        message = new MimeMessage(session);
    }

    //发送文字内容
    /**
     *
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMail(String subject,String content){

        try {
            //设置邮件发送人
            message.setFrom(new InternetAddress(mailpro.getProperty("mail.from")));
            //设置收件人
            List list = new ArrayList();
            String towho = mailpro.getProperty("mail.towho");
            String[] recipients = towho.split(";");
            Address[] address = new Address[recipients.length];
            for(int i = 0; i < recipients.length; i ++){
                address[i] = new InternetAddress(recipients[i]);
            }
            message.setRecipients(RecipientType.TO , address);

            //设置抄送人
            message.setRecipient(RecipientType.CC , new InternetAddress(mailpro.getProperty("mail.from")));

            //2.4设置邮件的主题
            message.setSubject(subject);

            //2.5设置邮件的内容
            message.setContent(content, "text/html;charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMailAddAttachment(String subject,String content,String[] files){
        try {
            //设置邮件发送人
            message.setFrom(new InternetAddress(mailpro.getProperty("mail.from")));
            //设置收件人
            List list = new ArrayList();
            String towho = mailpro.getProperty("mail.towho");
            String[] recipients = towho.split(";");
            Address[] address = new Address[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                address[i] = new InternetAddress(recipients[i]);
            }
            message.setRecipients(RecipientType.TO, address);

            //设置抄送人
            message.setRecipient(RecipientType.CC , new InternetAddress(mailpro.getProperty("mail.from")));

            //2.4设置邮件的主题
            message.setSubject(subject);

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setText(content);

            //设置多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            //附件部分
            try {
                for(int i = 0; i < files.length; i++){
                    messageBodyPart = new MimeBodyPart();
                    ((MimeBodyPart) messageBodyPart).attachFile(files[i]);
                    multipart.addBodyPart(messageBodyPart);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //添加完成消息
            message.setContent(multipart);
            //发送消息
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SendMail sendMail = new SendMail();
//        sendMail.sendMail("测试邮件","这是一个测试邮件");
        sendMail.sendMailAddAttachment("带附件邮件","这是一个带附件邮件",new String[]{"test0925.jpg","D:\\workspace\\Appium\\pic\\my.jpg"});
    }
}


