package Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicacao
{
    Long id;
    String substancia;
    String cnpj;
    String laboratorio;
    Long codGGREM;
    Long registro;
    Long ean1;
    Long ean2;
    Long ean3;
    String produto;
    String apresentacao;
    String classeTerapeutica;
    String tipoProduto;
    String regimePreco;
    BigDecimal pfIsento;
    BigDecimal pfZero;
    BigDecimal pf12;
    BigDecimal pf17;
    BigDecimal pf17ALC;
    BigDecimal pf175;
    BigDecimal pf175ALC;
    BigDecimal pf18;
    BigDecimal pf18ALC;
    BigDecimal pf20;
    BigDecimal pmcZero;
    BigDecimal pmc12;
    BigDecimal pmc17;
    BigDecimal pmc17ALC;
    BigDecimal pmc175;
    BigDecimal pmc175ALC;
    BigDecimal pmc18;
    BigDecimal pmc18ALC;
    BigDecimal pmc20;
    Boolean restricao;
    Boolean cap;
    Boolean confaz;
    Boolean icmsZero;
    String analiseRecursal;
    String concessaoCredito;
    Boolean comercializacao2020;
    String tarja;
}
