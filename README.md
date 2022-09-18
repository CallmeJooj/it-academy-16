# It Academy Dell edição 16
Esse projeto foi realizado por volta de 28/05/2022 como parte do processo seletivo do It Academy, projeto de estágio da Dell
# Descrição
Trata-se de uma solução para armazenar, ler e exibir um arquivo “.csv” com
dados referentes a medicamentos. O projeto foi construído em Java, utilizando
Swing para criar uma interface gráfica, a fim de trazer uma usabilidade maior que a
encontrada em um programa de linha de comando.
Funcionalidades
- Importar CSV
A aplicação é capaz de importar arquivos CSV de qualquer diretório acessível
- Consulta de medicamento
É possivel consultar medicamentos por nome ou por código de barras.
- Consulta de Concessão de Crédito.
# Pré-requisitos
Para executar o programa é necessário possuir pelo menos o Java 11
instalado. A versão mais recente do Java JRE pode ser encontrada gratuitamente
<a href=https://adoptium.net/temurin>aqui</a>.

# Fluxo
Após aberta a aplicação é necessário importar o aquivo CSV. As outras
funcionalidades ficam desativadas até a importação ser feita.
## Consulta de medicamentos:
A consulta de medicamentos pode ser feita por Código de Barras ou por
Nome. A consulta por nome é feita baseada na substância que define o
medicamento e funciona mesmo com uma busca parcial do nome. Também
busca apenas por medicamentos comercializados em 2020
## Consulta de concessão de crédito:
Ao clicar no botão de consulta de concessão de crédito, abaixo da tabela de
consulta por nome e código, é possível ver as estatísticas de concessão de
crédito, com os percentuais e um gráfico
