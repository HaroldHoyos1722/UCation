package co.com.polijic.ucation.usecases.adapters;

import co.com.polijic.ucation.domain.common.*;
import co.com.polijic.ucation.domain.enums.VariableEnum;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.repositories.*;
import co.com.polijic.ucation.domain.requesters.RegistroUsuarioRequester;
import co.com.polijic.ucation.domain.util.Encriptar;
import co.com.polijic.ucation.usecases.ports.UsuarioPort;

import java.time.LocalDate;
import java.util.Objects;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.PERSONA_REGISTRO_YA_EXISTE;
import static co.com.polijic.ucation.domain.enums.MensajeEnum.USUARIO_REGISTRO_YA_EXISTE;

public class UsuarioAdapter implements UsuarioPort {

    private final PersonaRepositoryPort personaRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DominioValorRepositoryPort dominioValorRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final MentorRepositoryPort mentorRepositoryPort;

    public UsuarioAdapter(PersonaRepositoryPort personaRepositoryPort,
                          UsuarioRepositoryPort usuarioRepositoryPort,
                          DominioValorRepositoryPort dominioValorRepositoryPort,
                          EstudianteRepositoryPort estudianteRepositoryPort,
                          MentorRepositoryPort mentorRepositoryPort) {
        this.personaRepositoryPort = personaRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.dominioValorRepositoryPort = dominioValorRepositoryPort;
        this.estudianteRepositoryPort = estudianteRepositoryPort;
        this.mentorRepositoryPort = mentorRepositoryPort;
    }

    @Override
    public void registrarUsuario(RegistroUsuarioRequester requester) throws Exception {
        PersonaModel persona = personaRepositoryPort
                .consultarPorTipoIdentificacionYNumeroIdentificacion(requester.getTipoIdentificacion(),
                        requester.getNumeroIdentificacion());

        if (Objects.nonNull(persona))
            throw new InfoException(PERSONA_REGISTRO_YA_EXISTE.getValor());

        UsuarioModel usuario = usuarioRepositoryPort.consultarUsuarioPorCorreo(requester.getCorreo());

        if (Objects.nonNull(usuario))
            throw new InfoException(USUARIO_REGISTRO_YA_EXISTE.getValor());

        PersonaModel nuevaPersona = PersonaModel.builder()
                .nombre(requester.getNombre())
                .apellido(requester.getApellido())
                .tipoIdentificacion(requester.getTipoIdentificacion())
                .numeroIdentificacion(requester.getNumeroIdentificacion())
                .correo(requester.getCorreo())
                .celular(requester.getCelular())
                .fechaNacimiento(requester.getFechaNacimiento())
                .ciudad(requester.getCiudad())
                .departamento(requester.getDepartamento())
                .genero(requester.getGenero())
                .direccion(requester.getDireccion())
                .fechaRegistro(LocalDate.now()).build();

        persona = personaRepositoryPort.guardarPersona(nuevaPersona);

        DominioValorModel dominioValorEstado = dominioValorRepositoryPort
                .consultarDominioValorPorNombreDominioAndCodigo(VariableEnum.ESTADO.getValor(), VariableEnum.SI.getValor());

        UsuarioModel nuevoUsuario = UsuarioModel.builder()
                .correo(requester.getCorreo())
                .idPersona(persona.getIdPersona())
                .contrasena(Encriptar.encriptar(requester.getContrasena()))
                .estado(dominioValorEstado.getIdDominio()).build();

        usuarioRepositoryPort.guardarUsuario(nuevoUsuario);

        DominioValorModel dominioValorTipoEstudiante = dominioValorRepositoryPort
                .consultarDominioValorPorNombreDominioAndCodigo("TIPO_USUARIO", "ESTUDIANTE");

        if (requester.getTipoUsuario().equals(dominioValorTipoEstudiante.getIdDominio())) {
            EstudianteModel estudiante = EstudianteModel.builder()
                    .idPersona(persona.getIdPersona())
                    .estado(dominioValorEstado.getIdDominio())
                    .semestreActual(requester.getSemestreActual()).build();

            estudianteRepositoryPort.guardarEstudiante(estudiante);
        } else {
            MentorModel mentor = MentorModel.builder()
                    .calificacion(0D)
                    .idPersona(persona.getIdPersona())
                    .nivelAcademico(requester.getNivelAcademico())
                    .descripcion(requester.getDescripcionMentor()).build();

            mentorRepositoryPort.guardarMentor(mentor);
        }
    }
}
