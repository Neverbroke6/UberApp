package uber.uber.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailSenderImpl implements EmailSender{

    private JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(String to, String email){
        try {
            MimeMessage mailMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mailMessage,"utf-8");
            mimeMessageHelper.setSubject("Confirm your email address");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("israelaigbudu4@gmail.com");
            mimeMessageHelper.setText(email,true);
            javaMailSender.send(mailMessage);
        }

        catch (MessagingException e) {
            log.info("Problem 1:" + e.getMessage());
            throw new RuntimeException(e);
        }

        catch (MailException e1) {
            log.info("Problem 2:" + e1.getMessage());
            throw new RuntimeException(e1);
        }

    }
}
