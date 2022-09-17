package Mapper;

import Model.Medicacao;
import Response.MedicacaoResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MedicacaoMapper {

    public static Medicacao toEntity(ArrayList<String> info){
            return Medicacao.builder()
                    .substancia(info.get(0))
                    .cnpj(info.get(1))
                    .laboratorio(info.get(2))

                    .codGGREM(longParse(info.get(3)))
                    .registro(longParse(info.get(4)))
                    .ean1(longParse(info.get(5)))
                    .ean2(longParse(info.get(6)))
                    .ean3(longParse(info.get(7)))

                    .produto(info.get(8))
                    .apresentacao(info.get(9))
                    .classeTerapeutica(info.get(10))
                    .tipoProduto(info.get(11))
                    .regimePreco(info.get(12))

                    .pfIsento(decimalParse(info.get(13)))
                    .pfZero(decimalParse(info.get(14)))
                    .pf12(decimalParse(info.get(15)))
                    .pf17(decimalParse(info.get(16)))
                    .pf17ALC(decimalParse(info.get(17)))
                    .pf175(decimalParse(info.get(18)))
                    .pf175ALC(decimalParse(info.get(19)))
                    .pf18(decimalParse(info.get(20)))
                    .pf18ALC(decimalParse(info.get(21)))
                    .pf20(decimalParse(info.get(22)))
                    .pmcZero(decimalParse(info.get(23)))
                    .pmc12(decimalParse(info.get(24)))
                    .pmc17(decimalParse(info.get(25)))
                    .pmc17ALC(decimalParse(info.get(26)))
                    .pmc175(decimalParse(info.get(27)))
                    .pmc175ALC(decimalParse(info.get(28)))
                    .pmc18(decimalParse(info.get(29)))
                    .pmc18ALC(decimalParse(info.get(30)))
                    .pmc20(decimalParse(info.get(31)))

                    .restricao(booleanParse(info.get(32)))
                    .cap(booleanParse(info.get(33)))
                    .confaz(booleanParse(info.get(34)))
                    .icmsZero(booleanParse(info.get(35)))

                    .analiseRecursal(info.get(36))

                    .concessaoCredito(info.get(37))
                    .comercializacao2020(booleanParse(info.get(38)))

                    .tarja(info.get(39))
                    .build();
    }

    public static List<Medicacao> toEntityList(ArrayList<ArrayList<String>> list) {

        List<Medicacao> medicacoes = new ArrayList<>();

        for(int i = 0; i < list.size(); i++){
            ArrayList<String> med = list.get(i);
            if(med.size() != 40){
                med.addAll(list.get(i+1));
                list.remove(i+1);
                list.set(i,med);
            }
        }

        for (int i = 0; i<list.size(); i++){
            Medicacao med = MedicacaoMapper.toEntity(list.get(i));
            med.setId((long) i );
            medicacoes.add(med);
        }

        return medicacoes;
    }

    public static MedicacaoResponse toResponse(Medicacao medicacao){
        return MedicacaoResponse.builder()
                .substancia(medicacao.getSubstancia())
                .produto(medicacao.getProduto())
                .apresentacao(medicacao.getApresentacao())
                .valorPF(medicacao.getPfIsento() != null ? medicacao.getPfIsento() : BigDecimal.valueOf(0))
                .valorPMCZero(medicacao.getPmcZero() != null ? medicacao.getPmcZero() : BigDecimal.valueOf(0))
                .build();
    }
    
    private static Long longParse(String string){
        try {
            return Long.parseLong(string);
        } catch(Exception NumberFormatException){
            return null;
        }
    }

    private static BigDecimal decimalParse(String string){
        try {
            Double number = Double.parseDouble(string.replace(",", "."));
            return BigDecimal.valueOf(number);
        }catch(Exception NumberFormatException){
            return null;
        }
    }

    private static Boolean booleanParse(String string){

        return string.toUpperCase().contains("SIM");
    }
}
