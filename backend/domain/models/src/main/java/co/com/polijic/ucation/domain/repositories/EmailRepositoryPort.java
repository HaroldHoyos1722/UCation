package co.com.polijic.ucation.domain.repositories;

import jakarta.mail.MessagingException;

public interface EmailRepositoryPort {

    Boolean enviarEmail(String destinatario, String asunto, String cuerpo) throws MessagingException;
}
