package Mapper;

import Model.Medicacao;
import Response.MedicacaoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MedicacaoMapperTest {

    @Test
    @DisplayName("Deve retornar uma entidade valida ao receber um input Lista de String")
    public void toEntityTest() {
        ArrayList<String> info = new ArrayList<String>(List.of(
                "SALICILATO DE FENILA,ÁCIDO SALICÁLICO,ÓXIDO DE ZINCO,ENXOFRE,MENTOL",
                "33.379.884/0001-96",
                "LABORATORIO SIMOES LTDA.",
                "520500901178410",
                "057600510011",
                "7896210500354",
                "    -     ",
                "    -     ",
                "TALCO ALÍVIO",
                "TALQUEIRA C/ 100 G",
                "D10A - ANTIACNEICOS TÓPICOS",
                "Similar",
                "Regulado",
                "5,04",
                "5,65",
                "6,53",
                "6,98",
                "6,08",
                "7,03",
                "6,11",
                "7,08",
                "6,15",
                "7,29",
                "7,58",
                "8,72",
                "9,30",
                "8,41",
                "9,37",
                "8,45",
                "9,43",
                "8,50",
                "9,70",
                "Não",
                "Não",
                "Não",
                "Não",
                "",
                "Negativa",
                "Não",
                "Tarja -(*)"
        ));

        Medicacao expected = Medicacao.builder()
                .id(null)
                .substancia("SALICILATO DE FENILA,ÁCIDO SALICÁLICO,ÓXIDO DE ZINCO,ENXOFRE,MENTOL")
                .cnpj("33.379.884/0001-96")
                .laboratorio("LABORATORIO SIMOES LTDA.")
                .ean2(null)
                .pfZero(BigDecimal.valueOf(5.65))
                .concessaoCredito("Negativa")
                .comercializacao2020(false)
                .build();

        Medicacao m = MedicacaoMapper.toEntity(info);

        Assert.assertEquals(expected.getId(), m.getId());
        Assert.assertEquals(expected.getSubstancia(), m.getSubstancia());
        Assert.assertEquals(expected.getCnpj(), m.getCnpj());
        Assert.assertEquals(expected.getEan2(), m.getEan2());
        Assert.assertEquals(expected.getPfZero(), m.getPfZero());
        Assert.assertEquals(expected.getLaboratorio(), m.getLaboratorio());
        Assert.assertEquals(expected.getConcessaoCredito(), m.getConcessaoCredito());
        Assert.assertEquals(expected.getComercializacao2020(), m.getComercializacao2020());
    }

    @Test
    @DisplayName("Deve retornar os valores corretamente quando todos os inputs estão corretos")
    public void deveRetornarValoresValidosAoReceberInputsValidos() {
        Medicacao m = Medicacao.builder()
                .substancia("SALICILATO DE FENILA,ÁCIDO SALICÁLICO,ÓXIDO DE ZINCO,ENXOFRE,MENTOL")
                .produto("TALCO ALÍVIO")
                .apresentacao("TALQUEIRA C/ 100 G")
                .pfIsento(BigDecimal.valueOf(5.65))
                .pmcZero(BigDecimal.valueOf(5.65))
                .build();

        MedicacaoResponse expected = MedicacaoResponse.builder()
                .substancia("SALICILATO DE FENILA,ÁCIDO SALICÁLICO,ÓXIDO DE ZINCO,ENXOFRE,MENTOL")
                .produto("TALCO ALÍVIO")
                .apresentacao("TALQUEIRA C/ 100 G")
                .valorPF(BigDecimal.valueOf(5.65))
                .valorPMCZero(BigDecimal.valueOf(5.65))
                .build();

        MedicacaoResponse tested = MedicacaoMapper.toResponse(m);

        Assert.assertEquals(expected.getSubstancia(), tested.getSubstancia());
        Assert.assertEquals(expected.getProduto(), tested.getProduto());
        Assert.assertEquals(expected.getApresentacao(), tested.getApresentacao());
        Assert.assertEquals(expected.getValorPF(), tested.getValorPF());
        Assert.assertEquals(expected.getValorPMCZero(), tested.getValorPMCZero());
    }

    @Test
    @DisplayName("Deve retornar valores nulos ao receber inputs nulos")
    public void deveRetornarValoresNulosAoReceberInputsNulos(){
        Medicacao m = Medicacao.builder()
                .substancia("SALICILATO DE FENILA,ÁCIDO SALICÁLICO,ÓXIDO DE ZINCO,ENXOFRE,MENTOL")
                .produto("TALCO ALÍVIO")
                .apresentacao("TALQUEIRA C/ 100 G")
                .pfZero(null)
                .pmcZero(null)
                .build();

        MedicacaoResponse tested = MedicacaoMapper.toResponse(m);
        MedicacaoResponse expected = MedicacaoResponse.builder()
                .valorPMCZero(BigDecimal.valueOf(0))
                .valorPF(BigDecimal.valueOf(0))
                .build();

        Assert.assertEquals(expected.getValorPF(), tested.getValorPF());
        Assert.assertEquals(expected.getValorPMCZero(), tested.getValorPMCZero());
    }
}