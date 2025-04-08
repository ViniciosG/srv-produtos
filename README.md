# Serviço de Produtos - IBAMA

## Descrição
Serviço para gerenciamento de produtos do IBAMA.

## Tecnologias
- Java 17
- Spring Boot 3.2.3
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Maven
- Lombok

## Autenticação

### Endpoints

#### Criar Usuário
```http
POST /api/auth/register
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123",
    "nome": "Administrador",
    "email": "admin@ibama.gov.br"
}
```

Resposta:
```json
{
    "id": 1,
    "username": "admin",
    "nome": "Administrador",
    "email": "admin@ibama.gov.br",
    "dataHoraRegistro": "05/04/2025 12:00:00"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```

Resposta:
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer"
}
```

### Uso do Token
Após obter o token JWT, inclua-o no header de todas as requisições que necessitam autenticação:

```http
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

## Endpoints Protegidos
Todos os endpoints abaixo requerem autenticação via token JWT:

### Categorias
- `GET /api/categorias` - Listar categorias
- `GET /api/categorias/{id}` - Buscar categoria por ID
- `POST /api/categorias` - Criar categoria
- `PUT /api/categorias/{id}` - Atualizar categoria
- `PATCH /api/categorias/{id}/inativar` - Inativar categoria
- `PATCH /api/categorias/{id}/ativar` - Ativar categoria
- `DELETE /api/categorias/{id}` - Deletar categoria

### Produtos
- `GET /api/produtos` - Listar produtos
- `GET /api/produtos/{id}` - Buscar produto por ID
- `POST /api/produtos` - Criar produto
- `PUT /api/produtos/{id}` - Atualizar produto
- `DELETE /api/produtos/{id}` - Deletar produto

## Configuração
1. Clone o repositório
2. Configure o banco de dados no `application.properties`
3. Execute `mvn clean install`
4. Execute a aplicação com `mvn spring-boot:run`

## Variáveis de Ambiente
- `SPRING_DATASOURCE_URL`: URL do banco de dados
- `SPRING_DATASOURCE_USERNAME`: Usuário do banco
- `SPRING_DATASOURCE_PASSWORD`: Senha do banco
- `APP_JWT_SECRET`: Chave secreta para JWT

## Testes
Execute os testes com:
```bash
mvn test
```

## Documentação
A documentação da API está disponível em:
```
http://localhost:8080/swagger-ui.html
```

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- MySQL
- Lombok
- SpringDoc OpenAPI (Swagger)
- Maven

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven
- MySQL
- Postman (para testes)

## 🔧 Configuração do Ambiente

1. Clone o repositório:
```bash
git clone [url-do-repositorio]
```

2. Configure o banco de dados no arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/srv_produtos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Execute o projeto:
```bash
mvn spring-boot:run
```

## 📚 Documentação da API

A documentação completa da API está disponível através do Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints

#### Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/categorias` | Criar uma nova categoria |
| GET | `/api/categorias/{id}` | Buscar categoria por ID |
| GET | `/api/categorias` | Listar todas as categorias |
| PUT | `/api/categorias/{id}` | Atualizar categoria |
| PATCH | `/api/categorias/{id}/inativar` | Inativar categoria |
| PATCH | `/api/categorias/{id}/ativar` | Ativar categoria |
| DELETE | `/api/categorias/{id}` | Deletar categoria |

#### Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/produtos` | Criar um novo produto |
| GET | `/api/produtos/{id}` | Buscar produto por ID |
| GET | `/api/produtos` | Listar todos os produtos |
| PUT | `/api/produtos/{id}` | Atualizar produto |
| PATCH | `/api/produtos/{id}/inativar` | Inativar produto |
| PATCH | `/api/produtos/{id}/ativar` | Ativar produto |
| DELETE | `/api/produtos/{id}` | Deletar produto |

### Modelos de Dados

#### Categoria
```json
{
  "id": 1,
  "nome": "Mudas e Plantas Nativas",
  "descricao": "Categoria para mudas e plantas nativas brasileiras",
  "status": "ATIVA",
  "dataHoraRegistro": "05/04/2025 12:00:00",
  "dataHoraAlteracao": "05/04/2025 12:00:00"
}
```

#### Produto
```json
{
  "id": 1,
  "nome": "Muda de Ipê",
  "descricao": "Muda de Ipê Amarelo",
  "preco": 50.00,
  "quantidadeEstoque": 100,
  "categoriaId": 1,
  "status": "ATIVO",
  "dataHoraRegistro": "05/04/2025 12:00:00",
  "dataHoraAlteracao": "05/04/2025 12:00:00"
}
```

### Códigos de Resposta

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem sucedida |
| 201 | Created - Recurso criado com sucesso |
| 204 | No Content - Recurso deletado com sucesso |
| 400 | Bad Request - Erro de validação |
| 404 | Not Found - Recurso não encontrado |
| 409 | Conflict - Conflito de dados |
| 500 | Internal Server Error - Erro interno do servidor |

## 🛠️ Estrutura do Projeto

```
src/main/java/br/gov/ibama/srv_produtos/
├── api/
│   ├── controllers/
│   ├── dtos/
│   ├── handlers/
│   └── interceptors/
├── application/
│   └── usecases/
│       ├── categoria/
│       └── produto/
├── config/
├── domain/
│   ├── entities/
│   ├── exceptions/
│   └── repositories/
└── infrastructure/
    └── persistence/
        └── repositories/
```

## 🔍 Logs

A aplicação possui um interceptor que registra:
- Método HTTP
- URI da requisição
- Status da resposta
- Erros (quando ocorrem)

## 🧪 Testes

Para executar os testes:
```bash
mvn test
```

## 📦 Coleção Postman

A coleção do Postman está disponível no arquivo `postman_collection.json` na raiz do projeto.

## 🤝 Contribuição

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença [IBAMA](https://www.ibama.gov.br/licenca).

## ✉️ Contato

IBAMA - [suporte@ibama.gov.br](mailto:suporte@ibama.gov.br) 