package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "dominio_valor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DominioValorEntity implements Serializable {

    @Id
    private Integer idDominio;
    private String nombreDominio;
    private String valor;
    private String codigo;
    private String activo;
}
