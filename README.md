# BookStore

Essa API é responsável por gerenciar e administrar dados de autores e livros armazenados no banco de dados **PostgreSQL**. Ela expõe endpoints para operações como criação, atualização, deleção e consulta de autores e seus livros. Além disso, garante segurança utilizando autenticação via token JWT, permitindo acesso seguro a partir de requisições HTTP externas.

A autenticação JWT está desabilitada por padrão, mas pode ser habilitada alterando o valor da variável `JWT_ENABLED` no arquivo `docker-compose.yml`.

---

## Guia de Uso

### Pré-requisitos para o Ambiente

Antes de começar, certifique-se de que o seu ambiente possui as seguintes ferramentas configuradas:

- **Java 21**: Certifique-se de ter o JDK (Java Development Kit) 21 instalado no sistema. [Guia de instalação do Java](https://openjdk.org/install/).
- **Maven**: Necessário para o gerenciamento de dependências e compilação do projeto. [Guia de instalação do Maven](https://maven.apache.org/install.html).
- **Docker e Docker Compose**: Usados para configurar o banco de dados **PostgreSQL** e executar a aplicação. [Guia de instalação do Docker](https://docs.docker.com/get-docker/).

---

### Executando a aplicação com `docker-compose`

Para executar a aplicação e todas as suas dependências, como o banco de dados **PostgreSQL**, utilize o comando:

```bash
docker-compose up
```

Isso iniciará:
1. O container da aplicação (`app`).
2. O container do banco de dados PostgreSQL (`bookstore-db`).

#### Configuração padrão

- **JWT desabilitado**: Por padrão, a validação de tokens JWT está desabilitada (configuração `JWT_ENABLED: false` no arquivo `docker-compose.yml`). Isso significa que nenhuma validação será feita em tokens JWT ao realizar requisições.
- **Cuidado com o cabeçalho `Authorization`**: Mesmo com o JWT desabilitado, **é obrigatório enviar algum valor no cabeçalho `Authorization`** durante as requisições à API.

Exemplo:
```http
Authorization: qualquer-valor
```

---

### Habilitando o JWT

Caso precise habilitar o JWT, siga os passos abaixo:

1. Abra o arquivo `docker-compose.yml`.
2. Encontre a variável de ambiente `JWT_ENABLED` na seção `app -> environment`.
3. Altere o valor de `false` para `true`. Por exemplo:

```yaml
environment:
  JWT_ENABLED: true
```

4. Reinicie os containers para aplicar a mudança:

```bash
docker-compose down && docker-compose up
```

Ao habilitar o JWT, um token JWT válido deverá ser enviado no cabeçalho `Authorization` para acessar os recursos protegidos. Tokens inválidos ou ausentes resultam em erro de autenticação.


### Executando manualmente (dependência do Docker local)

1. Certifique-se de que o **PostgreSQL** esteja configurado localmente ou inicie o banco de dados utilizando o Docker:
   ```bash
   docker-compose -f docker-compose.local.yaml up
   ```

2. Para rodar a aplicação localmente com o **Maven**:
   ```bash
   mvn spring-boot:run
   ```

3. Para criar o **JAR** da aplicação:
   ```bash
   mvn clean package
   ```

4. Execute o **JAR** gerado:
   ```bash
   java -jar ./target/com.bookstore-0.0.1-SNAPSHOT.jar
   ```

5. Ao realizar requisições, não esqueça de incluir o cabeçalho `Authorization`:
    - Com JWT desabilitado: **Qualquer valor será aceito no cabeçalho.**
    - Com JWT habilitado: **Um token JWT válido deve ser enviado.**

---

### Documentação dos Endpoints

Com a aplicação em execução, a documentação da API estará disponível através do **Swagger**:

- **Ambiente local**:
    - [Swagger UI - Local](http://localhost:8080/bookstore-api/swagger-ui/index.html)

---

### Observações

- Certifique-se de que o `JAVA_HOME` esteja configurado corretamente no sistema para que o Maven funcione sem problemas.
- Caso tenha dúvidas ou dificuldades em algum ponto, consulte a [documentação do Maven](https://maven.apache.org/guides/index.html), [documentação do Docker](https://docs.docker.com/) ou [documentação do PostgreSQL](https://www.postgresql.org/docs/).

---
