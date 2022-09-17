import Response.ConcessaoResponse;
import Response.ConsultaCodigoResponse;
import Response.MedicacaoResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private JPanel menuPanel;
    private JTable tabelaCredito;
    private JButton consultarConcessaoButton;
    private JTabbedPane tabbedPane1;
    private JTable tabelaNome;
    private JButton importarArquivoButton;
    private JPanel consultaCredito;
    private JPanel consultaNomeCodigo;
    private JPanel consultaName;
    private JPanel consultaCodigo;
    private JButton consultaCodButton;
    private JTextField codigoMedicamentoTextField;
    private JTextField nomeMedicamentoTextField;
    private JButton consultaNomeButton;
    private JTable tabelaCodigo;
    private JLabel diferenca;
    private JTable tabelaMaiorMenor;
    private JLabel avisoNome;
    Medicacoes medicacoes = new Medicacoes();

    public Menu() {
        DefaultTableModel dataModel = new DefaultTableModel(
                null,
                new String[]{"Substancia", "Produto", "Apresentação", "Valor PF", "Valor PCM Zero"}
        );
        DefaultTableModel graphicModel = new DefaultTableModel(
                null,
                new String[]{"Tipo", "Percentual", "Grafico"}
        );

        tabelaNome.setModel(dataModel);
        TableColumnModel columnModel = tabelaNome.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(70);
        columnModel.getColumn(4).setPreferredWidth(90);

        tabelaCodigo.setModel(dataModel);
        columnModel = tabelaCodigo.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(70);
        columnModel.getColumn(4).setPreferredWidth(70);

        tabelaMaiorMenor.setModel(new DefaultTableModel(
                null,
                new String[]{"Tam.","Substancia", "Produto", "Apresentação", "Valor PF", "Valor PCM Zero"}
        ));
        columnModel = tabelaMaiorMenor.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(70);
        columnModel.getColumn(5).setPreferredWidth(70);

        tabelaCredito.setModel(graphicModel);
        columnModel = tabelaCredito.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(40);
        columnModel.getColumn(2).setPreferredWidth(400);

        diferenca.setVisible(false);
        avisoNome.setVisible(false);

        consultaCodButton.setEnabled(false);
        consultaNomeButton.setEnabled(false);
        consultarConcessaoButton.setEnabled(false);

        consultaNomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avisoNome.setVisible(false);
                DefaultTableModel tblModel = (DefaultTableModel) tabelaNome.getModel();
                tblModel.setRowCount(0);
                List<MedicacaoResponse> r = medicacoes.consultaNome(nomeMedicamentoTextField.getText());
                if(r == null){
                    avisoNome.setText("Nenhum medicamento com esse nome");
                    avisoNome.setVisible(true);
                    return;
                }
                r.stream().forEach(m -> tblModel.addRow(m.toStringArray()));
            }
        });
        consultaCodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tblModel = (DefaultTableModel) tabelaCodigo.getModel();
                DefaultTableModel tblModel2 = (DefaultTableModel) tabelaMaiorMenor.getModel();
                tblModel.setRowCount(0);
                tblModel2.setRowCount(0);
                ConsultaCodigoResponse r = medicacoes.consultaCodigo(codigoMedicamentoTextField.getText());

                if (r != null) {
                    r.getList().stream().forEach(m -> tblModel.addRow(m.toStringArray()));
                    if (r.getList().size() >= 2) {

                        ArrayList<String> maiorRow = new ArrayList<>(List.of(r.getMaior().toStringArray()));
                        maiorRow.add(0, "Maior");
                        tblModel2.addRow(maiorRow.toArray());

                        ArrayList<String> menorRow = new ArrayList<>(List.of(r.getMenor().toStringArray()));
                        menorRow.add(0,"Menor");
                        tblModel2.addRow(menorRow.toArray());

                        diferenca.setText("Diferença: " + r.getMaior().getValorPMCZero().subtract(r.getMenor().getValorPMCZero()).toString());
                    } else if (r.getList().size() == 1) {
                        diferenca.setText("Somente um valor");
                    }
                } else {
                    diferenca.setText("Nenhum medicamento com esse codigo");
                }
                diferenca.setVisible(true);
            }
        });
        importarArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(menuPanel) == JFileChooser.APPROVE_OPTION) {
                    File f = new File(chooser.getSelectedFile().getPath());
                    if (medicacoes.importaCSV(f)) {
                        consultaNomeButton.setEnabled(true);
                        consultaCodButton.setEnabled(true);
                        consultarConcessaoButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(menuPanel, "Selecione um arquivo válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        consultarConcessaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tblModel = (DefaultTableModel) tabelaCredito.getModel();
                tblModel.setRowCount(0);
                List<ConcessaoResponse> response = medicacoes.comparaListaConcessao();
                for (ConcessaoResponse concessaoResponse : response) {
                    tblModel.addRow(concessaoResponse.toStringArray());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("It Academy - Gerenciador de Medicações");

        frame.setContentPane(new Menu().menuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));
        frame.setVisible(true);
    }
}
