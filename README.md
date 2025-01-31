# BookStore

Essa API é responsável por gerenciar e administrar dados de autores e livros armazenados no banco de dados **PostgreSQL**. Ela expõe endpoints para operações como criação, atualização, deleção e consulta de autores e seus livros. Além disso, garante segurança utilizando autenticação via token JWT, permitindo acesso seguro a partir de requisições HTTP externas.

A autenticação JWT pode ser opcional, dependendo da utilização da propriedade `jwt.enabled`.

---

## Guia de Uso

### Pré-requisitos para o Ambiente

Antes de começar, certifique-se de que o seu ambiente possui as seguintes ferramentas configuradas:

- **Java 21**: Certifique-se de ter o JDK (Java Development Kit) 21 instalado no sistema. [Guia de instalação do Java](https://openjdk.org/install/).
- **Maven**: Necessário para o gerenciamento de dependências e compilação do projeto. [Guia de instalação do Maven](https://maven.apache.org/install.html).
- **Docker**: Usado para configurar o banco de dados **PostgreSQL**. [Guia de instalação do Docker](https://docs.docker.com/get-docker/).

---

### Executar a API

#### Ambiente único de execução
A aplicação utiliza diretamente o banco de dados **PostgreSQL**, configurado por meio do Docker. Não há perfis separados (como dev, prod), apenas configurações específicas para o ambiente.

1. No diretório raiz do projeto, inicie o container do **PostgreSQL**:
   ```bash
   docker-compose -f docker-compose.local.yaml up
   ```
2. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

   Este comando iniciará a aplicação utilizando o banco de dados configurado no Docker.

#### Executando sem validação de JWT
Para desabilitar a validação de tokens JWT durante a execução da aplicação (útil para testes ou requisições não autenticadas), use a propriedade `jwt.enabled=false`. Quando essa propriedade estiver configurada:

- Qualquer valor no cabeçalho `Authorization` será aceito.
- Nenhuma validação será feita no token JWT.

Exemplo de execução:
```bash
mvn spring-boot:run -Djwt.enabled=false
```

Ao fazer requisições, adicione qualquer valor no cabeçalho `Authorization`:
```http
Authorization: test-token
```

---

### Documentação dos Controladores

Com o projeto em execução, acesse a documentação da API no **Swagger** através da seguinte URL:

- **Ambiente local**:
   - [Swagger UI - Local](http://localhost:8080/swagger-ui/index.html)

---

### Observações

- Certifique-se de que o `JAVA_HOME` esteja configurado corretamente no sistema para que o Maven funcione sem problemas.
- O arquivo `docker-compose.yaml` deve conter as configurações apropriadas para o PostgreSQL (como nome da base de dados, usuário, senha e porta).
- Caso tenha dúvidas ou dificuldades em algum ponto, consulte a [documentação do Maven](https://maven.apache.org/guides/index.html), [documentação do Docker](https://docs.docker.com/) ou [documentação do PostgreSQL](https://www.postgresql.org/docs/).

---
