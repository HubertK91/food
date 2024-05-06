package pl.hk.food.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String siteOwner;

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(MailForm sender) {
        logger.debug("Wysyłam maila do {}", siteOwner);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(siteOwner);
            helper.setFrom(sender.getSenderMail());
            helper.setSubject("Wiadomość od " + sender.getName() + " z mailem " + sender.getSenderMail());
            helper.setText(sender.getContent(), true);
            helper.setReplyTo(sender.getSenderMail());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.warn("Bład podczas wysłania wiadomości", e);
        }
        logger.debug("Mail do {} wysłany poprawnie", siteOwner);
    }
}
