# Microserviço de Vinhos

## Descrição
Microserviço desenvolvido em Spring Boot para gerenciamento de compras de vinhos, fornecendo endpoints para consulta de compras, análise de clientes fiéis e sistema de recomendações.

## Tecnologias Utilizadas
- Java 11+
- Spring Boot 2.x
- Jackson (para JSON)
- Maven

## Endpoints Disponíveis

### 4. Recomendação de Vinho
```
GET /recomendacao/{cpf}/tipo
```
Retorna recomendações de vinho baseadas no histórico do cliente.

**Exemplo:**
```
GET /recomendacao/05870189179/tipo
```

**Resposta:**
```json
{
  "cpfCliente": "05870189179",
  "tipoVinhoPreferido": "Tinto",
  "produtosRecomendados": [
    {
      "codigo": 6,
      "tipo_vinho": "Tinto",
      "preco": 327.50,
      "safra": "2016",
      "ano_compra": 2017
    },
    {
      "codigo": 11,
      "tipo_vinho": "Tinto",
      "preco": 128.99,
      "safra": "2017",
      "ano_compra": 2018
    }
  ]
}
```

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

## Observações Técnicas

- Os dados são carregados uma única vez no startup da aplicação e mantidos em memória
- A paginação é implementada manualmente devido à natureza dos dados em memória
- O sistema de recomendação considera o tipo de vinho mais comprado pelo cliente
- Tratamento de CPFs com diferentes formatos nos dados de origem
- Cálculos de valores utilizando BigDecimal para precisão monetária

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
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```1. Listar Compras
```
GET /compras?page=0&size=10&sort=valorTotal&direction=asc
```
Retorna lista paginada de compras ordenadas por valor total crescente.

**Parâmetros de Query:**
- `page`: Número da página (padrão: 0)
- `size`: Tamanho da página (padrão: 10)
- `sort`: Campo para ordenação (padrão: valorTotal)
- `direction`: Direção da ordenação - asc/desc (padrão: asc)

**Resposta:**
```json
{
  "content": [
    {
      "nomeCliente": "Nome do Cliente",
      "cpfCliente": "12345678901",
      "produto": {
        "codigo": 1,
        "tipo_vinho": "Tinto",
        "preco": 229.99,
        "safra": "2017",
        "ano_compra": 2018
      },
      "quantidade": 5,
      "valorTotal": 1149.95
    }
  ],
  "pageable": {...},
  "totalElements": 50,
  "totalPages": 5
}
```

### 2. Maior Compra por Ano
```
GET /maior-compra/{ano}
```
Retorna a maior compra do ano especificado.

**Exemplo:**
```
GET /maior-compra/2018
```

**Resposta:**
```json
{
  "nomeCliente": "Nome do Cliente",
  "cpfCliente": "12345678901",
  "produto": {
    "codigo": 1,
    "tipo_vinho": "Tinto",
    "preco": 229.99,
    "safra": "2017",
    "ano_compra": 2018
  },
  "quantidade": 10,
  "valorTotal": 2299.90
}
```

### 3. Top 3 Clientes Fiéis
```
GET /clientes-fieis?limit=3&page=0
```
Retorna os clientes com maiores gastos e mais compras recorrentes.

**Parâmetros de Query:**
- `limit`: Número de clientes no ranking (padrão: 3)
- `page`: Número da página (padrão: 0)

**Resposta:**
```json
{
  "content": [
    {
      "nome": "Nome do Cliente",
      "cpf": "12345678901",
      "totalCompras": 25,
      "valorTotalGasto": 5000.00
    }
  ],
  "totalElements": 3
}
```

###