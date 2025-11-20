# 🛡️ API de Autenticação e Controle de Acesso

API desenvolvida em **Java**, utilizando **Spring Boot** e **Spring
Security**, responsável pela autenticação, autorização e gerenciamento
de usuários, perfis e permissões para aplicações clientes.

## 📌 Funcionalidades

-   Registro, autenticação e gerenciamento de usuários
-   Autenticação baseada em **JWT (JSON Web Token)**
-   Controle de acessos baseado em **roles** e **permissões**
-   Endpoints protegidos com filtros de segurança
-   Renovação de tokens (opcional)
-   Middleware para validação automática de tokens
-   Suporte a CORS configurável
-   Estrutura preparada para integração com serviços externos

## 🏗️ Tecnologias Utilizadas

-   **Java 17+**
-   **Spring Boot (Web, Security, Validation)**
-   **Spring Data JPA / Hibernate**
-   **JWT (io.jsonwebtoken / Auth0)**
-   **Banco de Dados**: PostgreSQL / MySQL / H2
-   **Maven/Gradle**
-   **Lombok**

## 📁 Estrutura do Projeto (exemplo)

    src/
     └── main/
          ├── java/
          │    └── com.seuprojeto
          │          ├── controller/
          │          ├── service/
          │          ├── repository/
          │          ├── security/
          │          │      ├── config/
          │          │      ├── filters/
          │          │      ├── jwt/
          │          └── model/
          └── resources/
               ├── application.properties
               └── schema.sql / data.sql

## ▶️ Como Executar o Projeto

### 1. Clonar o repositório

``` bash
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo
```

### 2. Configurar variáveis de ambiente

No arquivo `application.properties`:

``` properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/minha_api
spring.datasource.username=usuario
spring.datasource.password=senha

jwt.secret=minha_chave_secreta_super_segura
jwt.expiration=3600000
```

### 3. Executar o projeto

``` bash
./mvnw spring-boot:run
```

ou

``` bash
mvn spring-boot:run
```

## 🔑 Autenticação

### Fluxo de Login

1.  O cliente envia e-mail e senha para o endpoint:

``` http
POST /auth/login
```

2.  A API retorna:

``` json
{
  "accessToken": "jwt_token_aqui",
  "expiresIn": 3600,
  "tokenType": "Bearer"
}
```

3.  Usar o token para acessar endpoints protegidos:

``` http
Authorization: Bearer <token>
```

## 🔐 Endpoints Principais

### Autenticação

  Método   Endpoint           Descrição
  -------- ------------------ --------------------------
  POST     `/auth/register`   Registrar novo usuário
  POST     `/auth/login`      Autenticar usuário
  POST     `/auth/refresh`    Renovar token (opcional)

### Usuários

  Método   Endpoint        Descrição
  -------- --------------- -------------------------
  GET      `/users`        Listar usuários (ADMIN)
  GET      `/users/{id}`   Buscar usuário
  PUT      `/users/{id}`   Atualizar dados
  DELETE   `/users/{id}`   Remover usuário

## 🔒 Configuração de Segurança

Exemplo (simplificado):

``` java
http
    .csrf().disable()
    .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/auth/**").permitAll();
        auth.anyRequest().authenticated();
    })
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
```

## 🧪 Testes

``` bash
mvn test
```

## 🐳 Docker (opcional)

``` bash
docker build -t minha-api-auth .
docker run -p 8080:8080 minha-api-auth
```

## 📄 Licença

Projeto sob licença **MIT**.
