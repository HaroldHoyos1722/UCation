package co.com.polijic.ucation.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MensajeEnum {
    MENSAJE_RECUERAR_CONTRASENA_OK("Si el correo electrónico es correcto, se le enviará un código de recuperación."),
    MENSAJE_CAMBIAR_CONTRASENA_OK("La contraseña se ha cambiado con éxito."),
    MENSAJE_CAMBIO_CONTRASENA_ERROR("No fue posible cambiar la contraseña, vuelva a intentar más tarde"),
    CODIGO_RECUPERAR_CONTRASENA_VENCIDO("El código de recuperación ha expirado, genere un nuevo código y vuelva a intentar"),
    CODIGO_RECUPERACION_ERRONEO("El código de recuperación es incorrecto, vuelva a intentar"),
    PERSONA_REGISTRO_YA_EXISTE("Ya existe un usuario para el número de identificación ingresado"),
    USUARIO_REGISTRO_YA_EXISTE("Ya existe un usuario con el correo electrónico ingresado"),
    USUARIO_REGISTRADO_CON_EXITO("Usuario registrado con éxito."),
    INFORMACION_CONSULTADA_CON_EXITO("Información consultada con éxito."),
    DATOS_VALIDAOS_CON_EXITO("Los datos han sido validados con éxito."),
    USUARIO_CONTRASENA_INVALIDO("El correo electrónico o la contraseña es incorrecto, vuelva a intentar"),
    USUARIO_NO_ACTIVO("El usuario no se encuentra activo, intente recuperando la contraseña.")
    ;

    private String valor;
}
