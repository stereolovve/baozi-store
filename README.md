# Baozi Store — API REST

API REST simples para a Baozi Store (loja de pão chinês): cadastro de clientes, produtos e pedidos.
Atividade prática da disciplina Desenvolvimento Web Back-End — UNINTER, Análise e Desenvolvimento de Sistemas.
Aluno: Lucas Diego de Freitas Melo — RU 4769317.

**Stack:** Java 17 · Spring Boot 3.3 · Spring Data JPA · MySQL (ou H2) · Postman

## Estrutura (MVC)

```
src/main/java/br/com/baozistore
├── BaoziStoreApplication.java
├── model/        Cliente, Produto, Pedido (entidades JPA)
├── repository/   ClienteRepository, ProdutoRepository, PedidoRepository
├── controller/   ClienteController, ProdutoController, PedidoController
└── dto/          PedidoRequest (JSON de entrada do pedido)
```

## Como rodar

### Opção 1 — MySQL (padrão)

1. Crie o banco: `CREATE DATABASE baozi_store;`
2. Ajuste usuário e senha em `src/main/resources/application.properties`.
3. Rode: `mvn spring-boot:run` (ou o botão Run do IntelliJ em `BaoziStoreApplication`).

As tabelas `cliente`, `produto` e `pedido` são criadas automaticamente (`ddl-auto=update`).

### Opção 2 — H2 (sem instalar nada)

```
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

Console do banco: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:file:./data/baozi_store`, usuário `sa`, sem senha).

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| POST | /clientes | Cria cliente |
| GET | /clientes | Lista todos |
| GET | /clientes/{id} | Consulta por ID |
| PUT | /clientes/{id} | Atualiza (opcional) |
| DELETE | /clientes/{id} | Apaga |
| POST | /produtos | Cria produto |
| GET | /produtos | Lista todos |
| GET | /produtos/{id} | Consulta por ID |
| PUT | /produtos/{id} | Atualiza (opcional) |
| DELETE | /produtos/{id} | Apaga |
| POST | /pedidos | Cria pedido |
| GET | /pedidos | Lista todos |
| GET | /pedidos/{id} | Consulta por ID |
| PUT | /pedidos/{id} | Atualiza (opcional) |
| DELETE | /pedidos/{id} | Apaga |

### Exemplos de JSON

```json
POST /clientes
{ "nome": "Lucas4769317", "clienteDesde": "2026-10-22" }

POST /produtos
{ "nome": "Baozi de Porco", "preco": 8.50, "estoque": true }

POST /pedidos
{ "clienteId": 1, "produtoId": 1, "quantidade": 6 }
```

A resposta do pedido traz o cliente e o produto completos.

## Postman

Importe `baozi-store.postman_collection.json`. Os IDs criados nos POSTs são salvos automaticamente
em variáveis da coleção, então é só executar as requisições na ordem.
