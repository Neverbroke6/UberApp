package uber.uber.services.email;

import jakarta.mail.MessagingException;

public interface EmailSender {
    void send(String to, String email);
}
