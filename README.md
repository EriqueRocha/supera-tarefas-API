# API de ocorrências

<a href="https://ocorrencia-api.onrender.com/swagger-ui/index.html" target="_blank">Acesse a API pela documentação do swagger clicando aqui</a>

#### Autor
[Erique Rocha](https://github.com/EriqueRocha)

## Apresentação:
Esta aplicação tem como objetivo implementar uma API de Tarefas. Desenvolvida com base nos requisitos do teste tecnico da empresa Supera Tecnologia

## Descrição:
Nesta API é possivel criar pastas, onde serão adicionadas tarefas. Será possivel buscar uma unica pasta e escolher o tipo de ordenação, buscar a lista de pastas com suas tarefas ou apenas tarefas, ambas com a ordenação preferida

### Tipos de ordenaçãp:
```JAVA
public enum Order {
    PRIORITY, STATUS, DATE
}
```

### Ordenação de prioridade:
```JAVA
public enum Priority {
    HIGH, MEDIUM, LOW
}
```

### Ordenação de status:
```JAVA
public enum Status {
    NOT_STARTED, IN_PROGRESS
}
```
### Tecnologias:
* Java 17
* SpringBoot 3.2.1
* PostgreSQL
* SpringDataJpa
* Hibernate
* SpringWeb
* SpringTest
* Swagger
* Flyway

## Dependências:

### SpringDataJpa:
```XML
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
Implementa uma camada de acesso aos dados de forma facilitada, faça seus métodos personalizados e o Spring fornecerá a implementação

### Flyway:
```XML
<dependency>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-core</artifactId>
</dependency>
```
ajuda com a migração de banco de dados, no caso, está sendo utilizado para que a aplicação inicie com um administrador e um usuário cadastrado

### PostgreSQL:
```XML
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```
drive para a utilização do SGBD PostgreSQL

### Springdoc openapi ui:
```XML
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.1.0</version>
</dependency>
```
biblioteca que ajuda a automatizar a geração de documentação da API usando projetos de inicialização do spring.

### Hibernate validator:
```XML
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>8.0.0.Final</version>
</dependency>
```
valida os dados em objetos Java de acordo com as anotações de validação.

### Junit:
```XML
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.11.0</version>
  <scope>test</scope>
</dependency>
```
fornece a API do JUnit 5 para escrever testes.

### Mockito:
```XML
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-core</artifactId>
  <version>5.13.0</version>
  <scope>test</scope>
</dependency>
```
facilita a criação de objetos simulados (mocks) para testar o comportamento

### Endpoints:
<img src="Captura de tela.png" style="width: 100%;">
