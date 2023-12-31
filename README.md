# Desafio Back-end PicPay

### Setup do Projeto
* Linguagem de programação: Kotlin
* Tecnologia: Spring Boot 3.0.10
* Gerenciador de dependência: Gradle Kotlin
* Banco de dados: H2
* Java 17
* IDE IntelJ

## Sobre o ambiente da aplicação:

* Docker
* Você pode testar a aplicação com o docker-compose
* 1º passo: Na pasta docker, abrir o terminal git bash e execultar o comando 
```
docker-compose up -d
```
* Para visualizar a Observability no dash do grafana siga esses passos:
* 2º passo: Ira subir o container do Prometheus, Grafana, cardvisor, redis, pick-pay-plataform
* 3º passo: acessar url: localhost:3000
* Login no grafana: userName: admim / password: admin / depois skip
* 4º passo: na barra de pesquisa digite data sources / add data sources / prometheus
* prometheus server url: http://prometheus:9090
* save & test
* 5º passo: na barra de pesquisa digite import dashboard / Import via grafana.com/ cole esse id = 17053
* load
* visualize o dash da aplicação / Spring Boot & Endpoint Metrics 2.0 id = 17053 / https://grafana.com/grafana/dashboards/17053-spring-boot-statistics-endpoint-metrics/
* 6º passo: na barra de pesquisa digite import dashboard / Import via grafana.com/ cole esse id = 17360
* visualize o dash da aplicação / Spring HTTP Example id = 17360 / https://grafana.com/grafana/dashboards/17360-spring-http-example/
* documentação da api: http://localhost:8081/swagger-ui/index.html
* metricas da apilicação: http://localhost:8081/actuator/prometheus
* metricas circuitBreaker: http://localhost:8081/actuator/circuitbreakerevents
* end point para test das transaçoes: http://localhost:8081/transactions
* method: POST
* senderId = 1
* receiverId = 2
* COMMON_PERSON pode enviar e receber transferencia de valores
* MERCHANT_PERSON pode apenas receber transferencia de valores
* modelo payload de envio: Json
```json
{
  "data": {
    "senderId": 1,
    "receiverId": 2,
    "value": 50
  }
}
```
* end point listar usuarios: http://localhost:8081/users
* conferir os valores das transferencias
```json
{
  "data": [
    {
      "id": 1,
      "name": "Marina Lima",
      "userType": "COMMON_PERSON",
      "accounting": {
        "balance": 100.00
      }
    },
    {
      "id": 2,
      "name": "Borde Mais",
      "userType": "MERCHANT_PERSON",
      "accounting": {
        "balance": 100.00
      }
    },
    {
      "id": 3,
      "name": "Wagner Lima",
      "userType": "COMMON_PERSON",
      "accounting": {
        "balance": 100.00
      }
    }
  ]
}
```
* Ao finalizar os testes digite:
```
docker-compose down
```
## Documentando da API REST Spring usando OpenAPI 3.0

* http://localhost:8081/swagger-ui/index.html

### O Design Pattern 

* Circuit Breaker (Disjuntor)
* Este padrão de projeto ajuda a evitar a ocorrência de falhas em cascata e permite construir um serviço tolerante a falhas e resiliente, que consiga sobreviver quando os principais serviços que ele consome estiverem passando por instabilidade.
### Arquitetura

* Arquitetura hexagonal, com a divisão em camadas de acordo com suas responsabilidades e encapsulamento da lógica

## Objetivo: PicPay Simplificado

Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam transferências entre eles. Vamos nos atentar **somente** ao fluxo de transferência entre dois usuários.

Requisitos:

- Para ambos tipos de usuário, precisamos do Nome Completo, CPF, e-mail e Senha. CPF/CNPJ e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail.

- Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários.

- Lojistas **só recebem** transferências, não enviam dinheiro para ninguém.

- Validar se o usuário tem saldo antes da transferência.

- Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock para simular (https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6).

- A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia.

- No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um serviço de terceiro e eventualmente este serviço pode estar indisponível/instável. Use este mock para simular o envio (http://o4d9z.mocklab.io/notify).

- Este serviço deve ser RESTFul.
# Desenvolvido por:

* Wagner de Lima Braga Silva

