# Fraud Detection Service

Serviço de detecção de fraudes em tempo real, construído com **Spring Boot** e **Apache Kafka Streams**. Ele consome eventos de transações a partir de tópicos Kafka, aplica regras de detecção sobre o fluxo de dados e expõe uma API REST documentada com OpenAPI/Swagger.

> **Status:** em desenvolvimento (`0.0.1-SNAPSHOT`).

## Sumário

- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Como executar](#como-executar)
- [Documentação da API](#documentação-da-api)
- [Testes](#testes)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Roadmap](#roadmap)
- [Referências](#referências)

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 27 | Linguagem |
| Spring Boot | 4.1.1 | Framework base |
| Spring Web | (Boot) | API REST |
| Spring for Apache Kafka | (Boot) | Produção/consumo de mensagens |
| Kafka Streams | (Boot) | Processamento de fluxo em tempo real |
| springdoc-openapi | 3.0.0 | Swagger UI / OpenAPI |
| Lombok | 1.18.48 | Redução de boilerplate |
| Maven | - | Build e dependências |
| Docker | - | Infraestrutura local (pasta `docker/`) |

**Coordenadas Maven:** `br.com.pedrosa:fraud-detection-service`

## Arquitetura

Fluxo geral do serviço:

```
Transações  ──►  Tópico Kafka (entrada)  ──►  Kafka Streams  ──►  Tópico Kafka (alertas de fraude)
                                              (regras de
                                               detecção)                  │
                                                                          ▼
                                                                  API REST / consumidores
```

1. Os eventos de transação chegam a um tópico Kafka de entrada.
2. A topologia do **Kafka Streams** processa cada evento e aplica as regras de detecção de fraude.
3. Transações suspeitas são publicadas em um tópico de saída (alertas).
4. A API REST (Spring Web) expõe endpoints do serviço, documentados via Swagger UI.

> Ajuste o diagrama e os nomes dos tópicos conforme a implementação atual em `src/`.

## Pré-requisitos

- **JDK 27**
- **Maven 3.9+** (ou use o wrapper, caso exista no projeto)
- **Docker** e **Docker Compose** (para subir o Kafka localmente)

## Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/fabiopedrosa1980/fraud-detection-service.git
cd fraud-detection-service
```

### 2. Subir a infraestrutura (Kafka)

Os arquivos de infraestrutura ficam na pasta `docker/`:

```bash
cd docker
docker compose up -d
cd ..
```

Para verificar se o Kafka está no ar:

```bash
docker compose -f docker/docker-compose.yml ps
```

> O nome do arquivo pode variar. Confira o conteúdo da pasta `docker/`.

Guia de comandos úteis do Kafka (criar tópicos, produzir e consumir mensagens):
<https://github.com/basanta-spring-boot/documents/blob/main/README.md>

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

Ou, gerando o JAR:

```bash
mvn clean package
java -jar target/fraud-detection-service-0.0.1-SNAPSHOT.jar
```

## Documentação da API

Com a aplicação em execução, a documentação interativa (springdoc-openapi) fica disponível em:

- **Swagger UI:** <http://localhost:8080/swagger-ui.html>
- **OpenAPI (JSON):** <http://localhost:8080/v3/api-docs>

> As URLs acima usam a porta padrão (`8080`). Ajuste se `server.port` for alterado.

## Testes

```bash
mvn test
```

O projeto utiliza `spring-boot-starter-test` e `spring-kafka-test` (Kafka embarcado para testes de integração).

## Estrutura do projeto

```
fraud-detection-service/
├── docker/          # Infraestrutura local (Kafka e dependências)
├── src/
│   ├── main/        # Código-fonte e configurações da aplicação
│   └── test/        # Testes automatizados
├── pom.xml          # Dependências e build (Maven)
├── .gitignore
└── README.md
```

## Configuração

As propriedades ficam em `src/main/resources/` (`application.properties` ou `application.yml`). Exemplo dos parâmetros mais comuns:

```properties
server.port=8080

spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.streams.application-id=fraud-detection-service
```


## Referências

- [Apache Kafka](https://kafka.apache.org/documentation/)
- [Kafka Streams](https://kafka.apache.org/documentation/streams/)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
- [springdoc-openapi](https://springdoc.org/)
- [Comandos de startup do Kafka](https://github.com/basanta-spring-boot/documents/blob/main/README.md)