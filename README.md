# Desafio Alpha Hurb

A instalação da aplicação é feita através do Android Android Studio, basta somente conectar o cabo USB no PC, e executar o Build.

A aplicação contém 5 Telas:

# Tela principal com a lista dos Hoteis
Apresenta informações dos hoteis, como por exemplo, nome, preço, a foto, as comodidades e a quantidade de estrelas.
Esas informações sõa apresentadas de acordo com a quantidade de estrelas, na ordem decrescente.


# Tela com os Detalhes dos Hoteis
Além das informações da tela anterior, nessa tela apresenta descrição do hotel.

# Tela com Informações do Aplicativo
Versão do aplicativo e nome do desenvolvedor da aplicação

# Tela com os Pacotes
Informações do Pacote de viagens:

- Nome do Pacote
- Cidade e Estado
- Preço

Obs.: A API https://www.hurb.com/search/api?q=buzios&page=1, na maioria das vezes não retorna as informações do pacote, quando isso acontece, a aplicação exibe uma mensagem para acessar essa tela mais tarde.

# Tela com busca aos Hoteis
Apresenta nome, preço, a foto, as comodidades e a quantidade de estrelas dos hoteis, e contém um campo para fazer a busca pelo nome do hotel.


# Testes

Os testes foram executados eliminando erros como por exemplo o carregamento da API. Se algum dado da API for vazia, eliminamos o valor nulo, e a listagem continua.

Se a API retornar vazia a aplicação é carregada sem valores, evitando falha na execução do aplicativo.

Se o dispositivo estiver sem conexão a internet, é exibido uma tela dizendo que esta com falta de conexao, evitando que o aplicativo falhe e feche.

