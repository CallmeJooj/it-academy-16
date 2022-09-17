package Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoResponse {
    String substancia;
    String produto;
    String apresentacao;
    BigDecimal valorPF;
    BigDecimal valorPMCZero;

    public String[] toStringArray(){
        return new String[]{substancia, produto, apresentacao, valorPF != null ? valorPF.toString() : "", valorPMCZero != null ? valorPMCZero.toString() : ""};
    }
}
