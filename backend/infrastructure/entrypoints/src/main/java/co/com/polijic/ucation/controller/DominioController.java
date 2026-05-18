package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.enums.TipoMensajeEnum;
import co.com.polijic.ucation.services.DominioService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.INFORMACION_CONSULTADA_CON_EXITO;

@Validated
@CrossOrigin("*")
@RestController
@RequestMapping("/dominios")
public class DominioController {

    private final DominioService dominioService;

    public DominioController(DominioService dominioService) {
        this.dominioService = dominioService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<List<DominioValorModel>>> consultarDominiosPorNombreDominio(@RequestParam String nombreDominio) {
        List<DominioValorModel> response = dominioService.consultarDominiosPorNombreDominio(nombreDominio);

        if (Objects.nonNull(response) && !response.isEmpty()) {
            return ResponseEntity.ok(GeneralResponse.<List<DominioValorModel>>builder()
                    .respuesta(response)
                    .tipoMensaje(TipoMensajeEnum.OK.getValor())
                    .mensaje(INFORMACION_CONSULTADA_CON_EXITO.getValor())
                    .build());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{nombreDominio}/{valor}")
    public DominioValorModel consultarDominioValorPorNombreDominioAndValor(@PathVariable String nombreDominio, @PathVariable String valor) {
        return dominioService.consultarDominioValorPorNombreDominioAndValor(nombreDominio, valor);
    }
}
