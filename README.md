# Microserviço de Vinhos

## Descrição
Microserviço desenvolvido em Spring Boot para gerenciamento de compras de vinhos, fornecendo endpoints para consulta de compras, análise de clientes fiéis e sistema de recomendações.

## Tecnologias Utilizadas
- Java 8: Stream API, Lambda Expressions, Optional, Method References e Functional Interfaces
- Java 10: Local Variable Type Inference (var)
- Java 14: Records
- Java 16: Stream.toList()
- Maven para gerenciamento de dependências
- Spring Boot 3.2.5 com as seguintes dependências:
1. Spring Boot Starter Web para criar uma API RESTful
2. Spring Boot Starter Validation para validação de dados
3. Sping Data para paginação e ordenação de resultados
4. Jackson (para serialização e desserialização de JSON)

## Padrões de Arquitetura  
**Layered Architecture:** sistema dividido em camadas
- **Controller:** responsável por receber e processar solicitações HTTP
- **Model:** entidades de domínio e DTOs
- **Service:** contém a lógica de negócio 
- (para este cenário não tem a camada de REPOSITORY)

**Objetivos principais ao utilizar essa arquitetura**
- Implementar a separação de responsabilidades (SRP - Single Responsibility Principle)
- Reduzir acoplamento
- Facilitar manutenção, testes e evolução do sistema

## Como Executar

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6+

### Passos para execução
1. Clone o repositório
```bash
git clone [seu-repositorio]
cd vinhos-microservice
```

2. Execute o build
```bash
mvn clean install
```

3. Execute a aplicação
```bash
mvn spring-boot:run
```

Ou execute o JAR gerado:
```bash
java -jar target/vinhos-microservice-1.0.0.jar
```

4. A aplicação estará disponível em `http://localhost:8080`

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/empresa/vinhos/
│   │       ├── VinhosApplication.java
│   │       ├── config/
│   │       │   └── DataConfig.java
│   │       ├── controller/
│   │       │   ├── ClienteController.java
│   │       │   ├── CompraController.java
│   │       │   ├── ProdutoController.java
│   │       │   └── exception/
│   │       │       └── InitializeApplicationException.java
│   │       ├── model/
│   │       │   ├── Cliente.java
│   │       │   ├── Compra.java
│   │       │   ├── Produto.java
│   │       │   └── dto/
│   │       │       ├── ClienteFielResponseDTO.java
│   │       │       ├── CompraResponseDTO.java
│   │       │       └── RecomendacaoResponseDTO.java
│   │       └── service/
│   │           ├── ClienteService.java
│   │           ├── CompraService.java
│   │           ├── MockDataConsumer.java
│   │           └── RecomendacaoService.java
│   └── resources/
│       └── application.properties
docs/
└── swagger.yaml

```

## Lógica de Negócio

### CompraService
- **listarCompras()**: Constrói lista de todas as compras combinando dados de clientes e produtos, calcula valores totais e implementa paginação
- **getMaiorCompraPorAno()**: Filtra compras por ano e encontra a de maior valor

### ClienteService
- **getClientesFieis()**: Calcula valor total gasto e quantidade de compras por cliente, ordena por valor gasto (decrescente) e implementa ranking

### RecomendacaoService
- **getRecomendacaoDeVinho()**: Analisa histórico de compras do cliente, identifica tipo de vinho preferido e recomenda produtos do mesmo tipo que ainda não foram comprados

### MockDataConsumer
- Carrega dados dos endpoints externos no startup da aplicação
- Utiliza HttpClient nativo do Java 11+ para requisições HTTP
- Implementa cache em memória dos dados carregados

## Funcionalidades Implementadas

✅ Consumo de dados dos mocks externos  
✅ Endpoint de listagem de compras com paginação e ordenação  
✅ Endpoint de maior compra por ano  
✅ Endpoint de top 3 clientes fiéis  
✅ Endpoint de recomendação de vinhos  
✅ Tratamento de erros e exceções  
✅ Logs de inicialização e operações  
✅ Validação de carregamento de dados

## Testes

Para testar os endpoints, você pode usar curl, Postman ou qualquer cliente HTTP:

### Exemplo com curl:
```bash
# Listar compras
curl "http://localhost:8080/compras?page=0&size=5"

# Maior compra de 2018
curl "http://localhost:8080/maior-compra/2018"

# Top 3 clientes fiéis
curl "http://localhost:8080/clientes-fieis"

# Recomendação para cliente
curl "http://localhost:8080/recomendacao/05870189179/tipo"
```

## Dependências no pom.xml

Certifique-se de que seu `pom.xml` inclui as seguintes dependências:

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.2.5</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>

    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>

</dependencies>
```

## Pontos de melhoria para o projeto

1. Adicionar camada de persistencia
2. Rate limiting para APIs
3. Testes unitário com JUnit e Mockito
4. Utilização de containers Docker e Docker Compose