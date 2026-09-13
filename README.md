# WMS — Sistema de Gerenciamento de Armazém (Java)

Projeto de estudo e portfólio: um sistema de gerenciamento de armazém (WMS — *Warehouse Management System*) construído do zero em Java, com base em experiência real de trabalho em logística.

## 📦 Sobre o projeto

O objetivo é modelar e implementar, de forma incremental, os principais processos de um armazém: cadastro de produtos, controle de lotes (rastreabilidade), movimentação de pallets e gestão de endereços de estoque, com persistência real em banco de dados relacional.

O projeto é desenvolvido seguindo a filosofia de **MVP (Minimum Viable Product)**: começar com o menor conjunto de funcionalidades que já representa o problema real, evoluindo em versões sucessivas.

## 🧱 Modelo de domínio

O sistema é modelado em torno de quatro entidades principais:

| Entidade | Descrição |
|---|---|
| `Produto` | Representa o tipo genérico de um item (identificador interno, nome, código EAN, peso) |
| `Lote` | Representa um grupo de fabricação de um produto, usado para rastreabilidade (código, data de validade) |
| `Pallet` | Unidade física de movimentação, associada a um único lote e quantidade (simplificação da versão inicial) |
| `Endereco` | Representa uma vaga de armazenagem física, identificada por bloco, posição e nível — comporta no máximo um pallet por vez (ou nenhum) |

O relacionamento entre as entidades segue a cadeia: **Produto → Lote → Pallet → Endereço**, refletindo o fluxo real de recebimento, armazenagem e rastreabilidade em um centro de distribuição.

## 🛠️ Tecnologias

- **Java** (JDK 25)
- **Eclipse IDE**
- **PostgreSQL** (banco de dados relacional)
- **JDBC** (driver oficial PostgreSQL)

## 📂 Estrutura do projeto

```
src/
└── com/red/wms/
    ├── model/
    │   ├── Produto.java
    │   ├── Lote.java
    │   ├── Pallet.java
    │   ├── Endereco.java
    │   └── TesteConexao.java   (classe de testes manuais)
    └── dao/
        ├── ProdutoDAO.java
        ├── LoteDAO.java
        ├── PalletDAO.java
        └── EnderecoDAO.java
```

O projeto segue o padrão **DAO (Data Access Object)**: cada entidade possui uma classe responsável exclusivamente pela comunicação com o banco de dados (inserir, buscar, atualizar, remover), mantendo a lógica de acesso a dados separada das demais responsabilidades do sistema.

## 🗄️ Banco de Dados

O banco `wms_db` (PostgreSQL) possui quatro tabelas espelhando as entidades do modelo, conectadas por chaves estrangeiras:

```sql
produto (id, nome, ean, peso)
lote (id, codigo, data_validade, produto_id → produto.id)
pallet (id, quantidade, lote_id → lote.id)
endereco (id, bloco, posicao, nivel, pallet_id → pallet.id, opcional)
```

> **Nota de segurança:** as credenciais de conexão com o banco não são versionadas com valores reais — o arquivo de teste utiliza um valor de espaço reservado para a senha, que deve ser preenchido localmente por quem for executar o projeto.

## 🚧 Status atual

- [x] Modelagem de domínio (Produto, Lote, Pallet, Endereço)
- [x] Banco de dados PostgreSQL com as 4 tabelas relacionadas
- [x] Conexão Java ↔ PostgreSQL via JDBC
- [x] CRUD completo (Create, Read, Update, Delete) para todas as entidades
- [x] Camada de acesso a dados organizada em DAOs
- [ ] Regras de negócio (ex.: impedir ocupação duplicada de endereço, priorização por validade — FEFO, limite de peso)
- [ ] Interface de uso (aplicativo Android para operadores, dashboard web para gestão)

## 🎯 Visão de futuro

O projeto tem como objetivo de longo prazo evoluir para um sistema comercialmente viável, capaz de atender múltiplas empresas com diferentes convenções de endereçamento e processos logísticos — motivo pelo qual campos configuráveis (como a identificação de blocos/vagas) foram desenhados de forma flexível desde o início.

A camada de persistência foi construída sobre PostgreSQL visando compatibilidade com ambientes multiusuário, permitindo, no futuro, que diferentes interfaces (aplicativo móvel para operadores e painel web para gestores) compartilhem a mesma base de dados e regras de negócio centralizadas.

---

*Projeto em desenvolvimento contínuo, como parte dos estudos em Ciência da Computação.*
