import CSVReader.CSVReader;
import Mapper.MedicacaoMapper;
import Model.Medicacao;
import Response.ConcessaoResponse;
import Response.ConsultaCodigoResponse;
import Response.MedicacaoResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Medicacoes {
    
    List<Medicacao> medicacoes;
    Boolean ready;

    public Medicacoes(){
        ready = false;
    }

    public Boolean importaCSV(File file){
        try{
            ArrayList<ArrayList<String>> lido = CSVReader.readRows(file);
            medicacoes = MedicacaoMapper.toEntityList(lido);
            ready = true;
            return true;
        }catch(IOException e){
            return false;
        }
    }

    public List<MedicacaoResponse> consultaNome(String nome) {
        List<Medicacao> medicacoesByName = medicacoes
                .stream()
                .filter(medicacao -> medicacao.getComercializacao2020() && medicacao.getSubstancia().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());

        if (medicacoesByName.size() < 1) {
            System.out.println("Nenhuma Medicação contém esse nome");
            return null;
        }

        List<MedicacaoResponse> responses = medicacoesByName.stream()
                .map(MedicacaoMapper::toResponse)
                .collect(Collectors.toList());

        return responses;
    }

    public ConsultaCodigoResponse consultaCodigo(String codigo) {
        try {
            Long cod = Long.parseLong(codigo);

            List<Medicacao> medicacoesByCode = medicacoes
                    .stream()
                    .filter(medicacao -> medicacao.getEan1() != null && medicacao.getEan1().equals(cod))
                    .collect(Collectors.toList());

            if (medicacoesByCode.size() == 0) {

                return null;
            }
            if (medicacoesByCode.size() == 1) {
                MedicacaoResponse response = MedicacaoMapper.toResponse(medicacoesByCode.get(0));
                return new ConsultaCodigoResponse(
                        null,
                        null,
                        List.of(response)
                );
            }

            Medicacao menor = medicacoesByCode.get(0), maior = medicacoesByCode.get(0);
            for (int i = 0; i < medicacoesByCode.size(); i++) {
                Medicacao m = medicacoesByCode.get(i);
                if (m.getPmcZero() == null) continue;
                if (m.getPmcZero().compareTo(maior.getPmcZero()) > 0) {
                    maior = m;
                }
                if (m.getPmcZero().compareTo(menor.getPmcZero()) < 0) {
                    menor = m;
                }
            }
            return new ConsultaCodigoResponse(
                    MedicacaoMapper.toResponse(maior),
                    MedicacaoMapper.toResponse(menor),
                    medicacoesByCode.stream().map(MedicacaoMapper::toResponse).collect(Collectors.toList())
            );

        } catch (NumberFormatException e) {
            System.out.println("Codigo Inválido");
            return null;
        }
    }

    public List<ConcessaoResponse> comparaListaConcessao() {
        List<Medicacao> medicacoes2020 = medicacoes.stream().filter(medicacao -> medicacao.getComercializacao2020()).collect(Collectors.toList());

        Integer negativa = 0;
        Integer positiva = 0;
        Integer neutra = 0;

        for (Medicacao medicacao : medicacoes2020) {
            if (medicacao.getConcessaoCredito().toUpperCase().contains("POSITIVA")) {
                positiva++;
            } else if (medicacao.getConcessaoCredito().toUpperCase().contains("NEGATIVA")) {
                negativa++;
            } else {
                neutra++;
            }
        }

        Double porcentagemNegativa = (negativa * 100d / medicacoes2020.size());
        Double porcentagemNeutra = (neutra * 100d / medicacoes2020.size());
        Double porcentagemPositiva = (positiva * 100d / medicacoes2020.size());
        Double porcentagemTotal = porcentagemNeutra + porcentagemPositiva + porcentagemNegativa;

        return List.of(
                new ConcessaoResponse("Negativa", String.format( "%.2f",porcentagemNegativa), asterisco(porcentagemNegativa)),
                new ConcessaoResponse("Neutra", String.format( "%.2f",porcentagemNeutra), asterisco(porcentagemNeutra)),
                new ConcessaoResponse("Positiva", String.format( "%.2f",porcentagemPositiva), asterisco(porcentagemPositiva)),
                new ConcessaoResponse("Total", String.format( "%.2f", porcentagemTotal), "")
        );
    }

    private String asterisco(Double num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
    
}
