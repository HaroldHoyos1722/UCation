package co.com.polijic.ucation.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CorreoEnum {
    PLANTILLA_RECUERAR_CONTRASENA("""
            <!DOCTYPE html>
            <html lang="es">
            <head>
              <meta charset="UTF-8"/>
              <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
              <title>Recuperar contraseña</title>
            </head>
            <body style="margin:0;padding:0;background-color:#f4f6f9;font-family:Arial,sans-serif;">
              <table width="100%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f9;padding:40px 0;">
                <tr>
                  <td align="center">
                    <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,0.08);">
                      <tr>
                        <td style="background-color:#1a73e8;padding:32px 40px;text-align:center;">
                          <h1 style="margin:0;color:#ffffff;font-size:28px;letter-spacing:1px;">Ucation</h1>
                        </td>
                      </tr>
                      <tr>
                        <td style="padding:40px;">
                          <h2 style="margin:0 0 16px;color:#202124;font-size:22px;">Recuperación de contraseña</h2>
                          <p style="margin:0 0 24px;color:#5f6368;font-size:15px;line-height:1.6;">
                            Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.
                            Usa el siguiente código para continuar con el proceso:
                          </p>
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td align="center" style="padding:24px 0;">
                                <span style="display:inline-block;background-color:#f0f4ff;border:2px dashed #1a73e8;border-radius:8px;padding:16px 40px;font-size:36px;font-weight:bold;letter-spacing:12px;color:#1a73e8;">
                                  {{CODIGO}}
                                </span>
                              </td>
                            </tr>
                          </table>
                          <p style="margin:0 0 8px;color:#5f6368;font-size:14px;line-height:1.6;">
                            Este código es válido solo por <strong>{{TIEMPO_RECUERAR_CONTRASENA}} minutos</strong>. Si no solicitaste restablecer tu contraseña,
                            ignora este correo y tu cuenta permanecerá segura.
                          </p>
                        </td>
                      </tr>
                      <tr>
                        <td style="background-color:#f8f9fa;padding:20px 40px;text-align:center;border-top:1px solid #e8eaed;">
                          <p style="margin:0;color:#9aa0a6;font-size:12px;">
                            © 2026 Ucation · Este es un correo automático, por favor no respondas.
                          </p>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </body>
            </html>
            """);

    private String contenido;
}
