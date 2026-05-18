package co.com.polijic.ucation.domain.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class Encriptar {

    @Value("${key.secret}")
    private String keySecretValue;

    private static String keySecret;

    private static final String ALGORITMO = "AES";

    @PostConstruct
    private void init() {
        keySecret = keySecretValue;
    }

    public static String encriptar(String valor) throws Exception {
        Key aesKey = new SecretKeySpec(keySecret.getBytes(), ALGORITMO);
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encriptado = cipher.doFinal(valor.getBytes());
        return Base64.getEncoder().encodeToString(encriptado);
    }

    public static String desencriptar(String datosEncriptados) throws Exception {
        Key aesKey = new SecretKeySpec(keySecret.getBytes(), ALGORITMO);
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(datosEncriptados));
        return new String(desencriptado);
    }
}
