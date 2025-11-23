# 📘 ListaVip Cadastro API

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white)
![Google Sign-In](https://img.shields.io/badge/Google%20Sign--In-OAuth2-4285F4?logo=google&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI%203-Swagger-85EA2D?logo=swagger&logoColor=black)

---

# 📚 Sobre o projeto

**ListaVip Cadastro API** é um projeto **de estudo**, desenvolvido para aprofundar conhecimentos em:

- Autenticação e autorização com **Spring Security**
- Geração e validação de **JWT**
- Login social com **Google Sign-In**
- Arquitetura em camadas
- Documentação com **Swagger/OpenAPI**
- Criação de filtros e handlers globais
- Boas práticas REST

---

# 🎯 Visão do Produto

A ListaVip Cadastro API foi criada com o objetivo de oferecer uma plataforma simples, segura e escalável para o gerenciamento de alunos que farão parte de uma lista VIP exclusiva.
A aplicação permite que escolas, cursos, eventos ou instituições mantenham um controle estruturado de seus participantes mais importantes, garantindo que cada aluno seja registrado, atualizado e autenticado de forma confiável.

Além do cadastro tradicional, o sistema também integra mecanismos modernos de autenticação, como login via Google e autenticação JWT, refletindo práticas amplamente utilizadas em sistemas reais.

O foco do produto é proporcionar:

✔ Gestão prática de alunos

✔ Segurança robusta baseada em tokens JWT

✔ Experiência de login moderna, incluindo Google Sign-In

✔ Arquitetura limpa, seguindo boas práticas de API REST

Embora seja um projeto de estudo, ele foi estruturado com qualidade e visão prática, permitindo que futuras funcionalidades sejam facilmente incorporadas — como envio de notificações, presença digital, ranking de alunos VIP, entre outras evoluções possíveis.

---

## 🚀 Como rodar o projeto

### **Pré-requisitos**
- Java **17+**
- Maven **3.9+**
- Uma IDE (IntelliJ, Eclipse, VSCode) – *opcional*

---

### **1. Clone o repositório**
```bash
git clone https://github.com/seu-repositorio/listavip-cadastro-api.git
cd listavip-cadastro-api
```

### **2. Instale as dependências**
```bash
mvn clean install
```

### **3. Execute o projeto**
```bash
mvn spring-boot:run
```

### **4. Acesse o Swagger**
```
http://localhost:8080/swagger-ui/index.html
```

---

# 🏗️ Arquitetura

| Camada | Descrição |
|-------|-----------|
| Controller | Endpoints REST |
| Service | Regras de negócio |
| Repository | Persistência com Spring Data JPA |
| Security | JWT, filtros e Google OAuth |
| DTOs | Objetos de transferência |
| Entities | Modelos JPA |

---

# 🔐 Autenticação

## 1. Login padrão
```
POST /usuario/login
```
Retorna um token JWT.

## 2. Google Sign-In
```
POST /auth/google
```
Fluxo:
1. Front coleta idToken do Google  
2. API valida  
3. Usuário é criado/recuperado  
4. JWT da aplicação é retornado  

---

# 🎓 CRUD de Alunos

Prefixo:
```
/alunos
```

Endpoints:
- Criar
- Listar
- Buscar por ID
- Atualizar
- Remover

---

# 🔒 Segurança (JWT)

- Endpoints públicos:
```
/usuario/login
/usuario/cadastro
/auth/google
```

- Swagger liberado  
- JWT Filter configurado  
- Sessão Stateless  

---

# 🧪 Exemplo de uso

### Login
```json
{
  "login": "email@example.com",
  "senha": "123456"
}
```

Retorno:
```json
{
  "token": "jwt-gerado..."
}
```

---

# 📁 Estrutura do projeto

```
src/main/java/com/listaVip/cadastro
 ├── aluno
 ├── auth
 ├── security
 ├── usuario
 ├── config
 └── exception
```

---

# 📝 Considerações finais

Este projeto continua evoluindo e pode ganhar futuramente:

- Refresh Token  
- Permissões avançadas  
- Testes unitários e integrados  
- Deploy em nuvem  
- Migrações com Flyway  

