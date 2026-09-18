# WMS — Sistema de Gerenciamento de Armazém (Java)

Projeto de estudo e portfólio: um sistema de gerenciamento de armazém (WMS — *Warehouse Management System*) construído do zero em Java, com base em experiência real de trabalho em logística.

## 📦 Sobre o projeto

O objetivo é modelar e implementar, de forma incremental, os principais processos de um armazém: cadastro de produtos, controle de lotes (rastreabilidade), movimentação de pallets e gestão de endereços de estoque, com persistência real em banco de dados relacional, regras de negócio que refletem operações reais de um centro de distribuição, e uma interface funcional de linha de comando.

O projeto é desenvolvido seguindo a filosofia de **MVP (Minimum Viable Product)**: começar com o menor conjunto de funcionalidades que já representa o problema real, evoluindo em versões sucessivas.

## 🧱 Modelo de domínio

O sistema é modelado em torno de quatro entidades principais:

| Entidade | Descrição |
|---|---|
| `Produto` | Representa o tipo genérico de um item (identificador interno, nome, código EAN, peso) |
| `Lote` | Representa um grupo de fabricação de um produto, usado para rastreabilidade (código, data de validade) |
| `Pallet` | Unidade física de movimentação, associada a um único lote e quantidade (simplificação da versão inicial) |
| `Endereco` | Vaga de armazenagem física, identificada por bloco, posição, nível e limite de peso configurável — comporta no máximo um pallet por vez (ou nenhum) |

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
    ├── dao/
    │   ├── ProdutoDAO.java
    │   ├── LoteDAO.java
    │   ├── PalletDAO.java
    │   └── EnderecoDAO.java
    ├── service/
    │   ├── LoteService.java
    │   └── EnderecoService.java
    └── app/
        └── Main.java   (interface de linha de comando)
```

O projeto segue uma arquitetura em camadas:
- **DAO (Data Access Object):** cada entidade possui uma classe responsável exclusivamente pela comunicação com o banco de dados (inserir, buscar, atualizar, remover).
- **Service:** camada responsável pelas regras de negócio, utilizando os DAOs como ferramenta, sem conhecer detalhes de SQL.
- **App:** interface de linha de comando que permite o cadastro e a consulta de dados de forma interativa, sem exigir alteração de código para uso.

## 🖥️ Interface de linha de comando

A classe `Main` oferece um menu interativo no terminal com as seguintes funcionalidades:

- Cadastro de produtos
- Listagem de produtos cadastrados
- Cadastro de lotes, associando-os a um produto já existente
- Listagem de lotes, exibindo o nome do produto associado e o peso unitário

Essa interface é um protótipo funcional que valida, de ponta a ponta, o funcionamento das camadas de dados e serviço. As próximas interfaces (aplicativo móvel e painel web) reutilizarão a mesma lógica de DAO e Service já implementada.

## ⚙️ Regras de negócio implementadas

- **Ocupação de endereço:** um pallet não pode ser associado a um endereço se já estiver associado a outro, evitando duplicidade de localização física.
- **FEFO (First Expired, First Out):** ao buscar o próximo lote de um produto a ser utilizado, o sistema prioriza automaticamente o lote com a data de validade mais próxima.
- **Limite de peso:** cada endereço possui um limite de peso configurável; o sistema calcula o peso total de um pallet (quantidade × peso unitário do produto) e impede a associação caso o limite seja excedido.

## 🗄️ Banco de Dados

O banco `wms_db` (PostgreSQL) possui quatro tabelas espelhando as entidades do modelo, conectadas por chaves estrangeiras:

```sql
produto (id, nome, ean, peso)
lote (id, codigo, data_validade, produto_id → produto.id)
pallet (id, quantidade, lote_id → lote.id)
endereco (id, bloco, posicao, nivel, peso_maximo, pallet_id → pallet.id, opcional)
```

> **Nota de segurança:** as credenciais de conexão com o banco não são versionadas com valores reais — os arquivos de teste e a interface utilizam um valor de espaço reservado para a senha, que deve ser preenchido localmente por quem for executar o projeto.

## 🚧 Status atual

- [x] Modelagem de domínio (Produto, Lote, Pallet, Endereço)
- [x] Banco de dados PostgreSQL com as 4 tabelas relacionadas
- [x] Conexão Java ↔ PostgreSQL via JDBC
- [x] CRUD completo (Create, Read, Update, Delete) para todas as entidades
- [x] Camada de acesso a dados organizada em DAOs
- [x] Camada de regras de negócio (Service): ocupação de endereço, FEFO, limite de peso
- [x] Interface de linha de comando para cadastro e consulta de produtos e lotes
- [ ] Interface de linha de comando para pallets e endereços (com validação das regras de negócio)
- [ ] Interface gráfica (aplicativo Android para operadores, dashboard web para gestão)

## 🎯 Visão de futuro

O projeto tem como objetivo de longo prazo evoluir para um sistema comercialmente viável, capaz de atender múltiplas empresas com diferentes convenções de endereçamento e processos logísticos — motivo pelo qual campos configuráveis (como a identificação de blocos/vagas e limites de peso) foram desenhados de forma flexível desde o início, cabendo ao sistema apenas registrar e validar, nunca impor, a estrutura física definida por cada cliente.

A camada de persistência foi construída sobre PostgreSQL visando compatibilidade com ambientes multiusuário, permitindo, no futuro, que diferentes interfaces (aplicativo móvel para operadores e painel web para gestores) compartilhem a mesma base de dados e regras de negócio centralizadas.

---

*Projeto em desenvolvimento contínuo, como parte dos estudos em Ciência da Computação.*
