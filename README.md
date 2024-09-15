# API de Tarefas

<a href="https://supera-tarefas-api-production.up.railway.app/swagger-ui/index.html" target="_blank">Acesse a API pela documentação do swagger clicando aqui</a>

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

### Scripts SQL para uso do flyway:
```SQL
CREATE TABLE task_folder (
   id SERIAL PRIMARY KEY,
   folder_name VARCHAR(35) NOT NULL
);

CREATE TABLE task (
   id SERIAL PRIMARY KEY,
   task_name VARCHAR(100) NOT NULL,
   priority VARCHAR(10) NOT NULL,
   status VARCHAR(20) NOT NULL,
   creation_date TIMESTAMP NOT NULL,
   task_folder_id INTEGER,
   FOREIGN KEY (task_folder_id) REFERENCES task_folder(id) ON DELETE CASCADE
);

-- Inserir TaskFolder 'trabalho'
INSERT INTO task_folder (folder_name)
VALUES ('trabalho');

-- Inserir TaskFolder 'rotina diária'
INSERT INTO task_folder (folder_name)
VALUES ('rotina diária');

-- Inserir Task e associar à TaskFolder 'trabalho'
WITH inserted_task_folder AS (
    SELECT id FROM task_folder WHERE folder_name = 'trabalho'
)
INSERT INTO task (task_name, priority, status, creation_date, task_folder_id)
VALUES
    ('Finalizar relatório', 'HIGH', 'NOT_STARTED', '2024-09-20 14:30', (SELECT id FROM inserted_task_folder)),
    ('Reunião com equipe', 'MEDIUM', 'IN_PROGRESS', '2024-09-21 09:00', (SELECT id FROM inserted_task_folder));

-- Inserir Task e associar à TaskFolder 'rotina diária'
WITH inserted_task_folder AS (
    SELECT id FROM task_folder WHERE folder_name = 'rotina diária'
)
INSERT INTO task (task_name, priority, status, creation_date, task_folder_id)
VALUES
    ('Verificar e-mails', 'LOW', 'NOT_STARTED', '2024-09-20 08:00', (SELECT id FROM inserted_task_folder)),
    ('Planejar dia', 'MEDIUM', 'IN_PROGRESS', '2024-09-20 09:30', (SELECT id FROM inserted_task_folder));

```

### Endpoints:
<img src="Captura de tela.png" style="width: 100%;">
