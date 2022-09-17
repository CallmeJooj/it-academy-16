
package Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcessaoResponse {

    String nome;
    String porcentagem;
    String grafico;

    public String[] toStringArray(){
        return new String[]{nome, porcentagem, grafico};
    }
}
