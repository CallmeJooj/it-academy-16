package Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ConsultaCodigoResponse {
    MedicacaoResponse maior;
    MedicacaoResponse menor;
    List<MedicacaoResponse> list;
}
