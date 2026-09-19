# WMS — Sistema de Gerenciamento de Armazém (Java)

Projeto de estudo e portfólio: um sistema de gerenciamento de armazém (WMS — *Warehouse Management System*) construído do zero em Java, com base em experiência real de trabalho em logística.

## 📦 Sobre o projeto

O objetivo é modelar e implementar, de forma incremental, os principais processos de um armazém: cadastro de produtos, controle de lotes (rastreabilidade), movimentação de pallets e gestão de endereços de estoque, com persistência real em banco de dados relacional, regras de negócio que refletem operações reais de um centro de distribuição, e uma interface funcional de linha de comando.

O projeto é desenvolvido seguindo a filosofia de **MVP (Minimum Viable Product)**: começar com o menor conjunto de funcionalidades que já representa o problema real, evoluindo em versões sucessivas, numeradas seguindo o padrão de **Versionamento Semântico** (`MAJOR.MINOR.PATCH`).

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

A classe `Main` oferece um menu interativo no terminal com o ciclo completo do fluxo de armazenagem:

1. Cadastrar produto
2. Listar produtos
3. Cadastrar lote (associado a um produto existente)
4. Listar lotes (com nome do produto e peso unitário)
5. Cadastrar pallet (associado a um lote existente)
6. Cadastrar endereço (com limite de peso configurável)
7. Associar pallet a endereço — aplica automaticamente as regras de negócio (ocupação e limite de peso)

Essa interface é um protótipo funcional que valida, de ponta a ponta, o funcionamento das camadas de dados, serviço e regras de negócio. As próximas interfaces (aplicativo móvel e painel web) reutilizarão a mesma lógica de DAO e Service já implementada, cada uma voltada ao seu público (operadores de armazém e gestores, respectivamente).

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

## 🏷️ Versionamento

Este projeto segue o [Versionamento Semântico](https://semver.org/lang/pt-BR/) (`MAJOR.MINOR.PATCH`):

| Versão | Marco |
|---|---|
| `v0.1.0` | MVP completo: modelagem, banco de dados, DAOs, regras de negócio e interface de linha de comando funcionando de ponta a ponta |

## 🚧 Status atual

- [x] Modelagem de domínio (Produto, Lote, Pallet, Endereço)
- [x] Banco de dados PostgreSQL com as 4 tabelas relacionadas
- [x] Conexão Java ↔ PostgreSQL via JDBC
- [x] CRUD completo (Create, Read, Update, Delete) para todas as entidades
- [x] Camada de acesso a dados organizada em DAOs
- [x] Camada de regras de negócio (Service): ocupação de endereço, FEFO, limite de peso
- [x] Interface de linha de comando completa (produtos, lotes, pallets, endereços e associação com regras aplicadas)
- [ ] Autenticação e perfis de usuário (separador/conferente vs. gestor/administrador)
- [ ] Interface gráfica (aplicativo Android para operadores, dashboard web para gestão)

## 🎯 Visão de futuro

O projeto tem como objetivo de longo prazo evoluir para um sistema comercialmente viável, capaz de atender múltiplas empresas com diferentes convenções de endereçamento e processos logísticos — motivo pelo qual campos configuráveis (como a identificação de blocos/vagas e limites de peso) foram desenhados de forma flexível desde o início, cabendo ao sistema apenas registrar e validar, nunca impor, a estrutura física definida por cada cliente.

Em um WMS real, diferentes perfis de usuário utilizam interfaces distintas: operadores de armazém (separadores, conferentes) utilizam telas simples e rápidas, tipicamente em coletores com leitor de código de barras; gestores e administradores utilizam painéis mais completos, com cadastros, relatórios e configurações. A camada de persistência foi construída sobre PostgreSQL visando compatibilidade com ambientes multiusuário, permitindo que essas interfaces futuras compartilhem a mesma base de dados e regras de negócio centralizadas.

---

*Projeto em desenvolvimento contínuo, como parte dos estudos em Ciência da Computação.*
