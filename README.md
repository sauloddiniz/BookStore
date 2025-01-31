# BookStore

Essa API é responsável por gerenciar e administrar dados de autores e livros armazenados no banco de dados **PostgreSQL**. Ela expõe endpoints para operações como criação, atualização, deleção e consulta de autores e seus livros. Além disso, garante segurança utilizando autenticação via token JWT, permitindo acesso seguro a partir de requisições HTTP externas.

---

## Guia de Uso

### Pré-requisitos para ambiente local

Antes de começar, certifique-se de que o seu ambiente possui as seguintes ferramentas configuradas:

- **Java 21**: Certifique-se de ter o JDK (Java Development Kit) 21 instalado no sistema. [Guia de instalação do Java](https://openjdk.org/install/).
- **Maven**: Necessário para o gerenciamento de dependências e compilação do projeto. [Guia de instalação do Maven](https://maven.apache.org/install.html).
- **Docker**: Usado para configurar o banco de dados **PostgreSQL**. [Guia de instalação do Docker](https://docs.docker.com/get-docker/).

---

### Executar a API

#### Ambiente local e de desenvolvimento
Para a execução local ou em ambiente de desenvolvimento, o projeto depende do banco de dados **PostgreSQL**, configurado por meio do Docker.

1. No diretório raiz do projeto, inicie o container do **PostgreSQL**:
   ```bash
   docker-compose -f docker-compose.local.yaml up
   ```

   Esse arquivo `docker-compose.local.yaml` deve estar configurado corretamente com os detalhes do banco de dados (porta, usuário, senha, etc.).

2. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

   Este comando iniciará a aplicação no perfil **dev**, utilizando o banco de dados PostgreSQL configurado no container.

---

#### Ambiente de produção
Para execução em ambiente de produção, o projeto também utiliza o banco de dados **PostgreSQL**, com as configurações definidas no arquivo `docker-compose.yaml`.

1. Construa o projeto:
   ```bash
   mvn clean package
   ```

   Isso gerará o arquivo `JAR` necessário para execução.

2. Inicie o banco de dados com o Docker:
    - Certifique-se de que o Docker está instalado e em execução no sistema.
    - No diretório raiz do projeto, execute:
      ```bash
      docker-compose up
      ```

3. Execute o `JAR` gerado:
   ```bash
   java -jar target/bookstore-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

   Isso garantirá que a configuração de produção seja utilizada.

---

### Documentação dos Controladores

Com o projeto em execução, acesse a documentação da API no **Swagger**:

- **Ambiente local**:
    - [Swagger UI - Local](http://localhost:8080/swagger-ui/index.html)
---

### Observações

- Certifique-se de que o `JAVA_HOME` esteja configurado corretamente no sistema para que o Maven funcione sem problemas.
- O arquivo `docker-compose.local.yaml` deve conter as configurações apropriadas para o PostgreSQL local (como nome da base de dados, usuário, senha e porta).
- Caso tenha dúvidas ou dificuldades em algum ponto, consulte a [documentação do Maven](https://maven.apache.org/guides/index.html), [documentação do Docker](https://docs.docker.com/) ou [documentação do PostgreSQL](https://www.postgresql.org/docs/).

---
