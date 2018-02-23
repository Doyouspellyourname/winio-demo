package xyz.supermoonie.email;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import xyz.supermoonie.image.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MailSender {

    public static void send(String base64Img, String path) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.addTo("邮件接收人");
        email.setAuthentication("发送者邮箱", "邮箱密码");
        email.setCharset("utf-8");
        email.setSmtpPort(587);
        email.setHostName("smtp.qq.com");
        email.setSubject("test");
        email.setFrom("发送者邮箱");
        email.setHtmlMsg("<img src='data:image/png;base64," + base64Img + " ' />");
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("minsheng.png");
        attachment.setName("minsheng.png");
        email.attach(attachment);
        email.send();
    }

    public static void main(String[] args) throws EmailException, IOException {
        BufferedImage image = ImageIO.read(new File("D:\\mingxi.png"));
        String base64Img = ImageConverter.imageToBase64(image, "png");
        send(base64Img, "D:\\mingxi.png");
    }
}
