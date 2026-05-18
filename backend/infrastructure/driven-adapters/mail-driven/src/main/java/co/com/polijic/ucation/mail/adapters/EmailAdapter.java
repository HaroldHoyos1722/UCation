package co.com.polijic.ucation.mail.adapters;

import co.com.polijic.ucation.domain.repositories.EmailRepositoryPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailAdapter implements EmailRepositoryPort {

    private final JavaMailSender javaMailSender;

    public EmailAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Boolean enviarEmail(String destinatario, String asunto, String cuerpo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom("ucationnotificaciones@gmail.com");
        mimeMessageHelper.setTo(destinatario);
        mimeMessageHelper.setSubject(asunto);
        mimeMessageHelper.setText(cuerpo, true);

        javaMailSender.send(message);

        return true;
    }
}
