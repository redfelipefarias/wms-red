# WMS — Sistema de Gerenciamento de Armazém (Java)

Projeto de estudo e portfólio: um sistema de gerenciamento de armazém (WMS — *Warehouse Management System*) construído do zero em Java, com base em experiência real de trabalho em logística.

## 📦 Sobre o projeto

O objetivo é modelar e implementar, de forma incremental, os principais processos de um armazém: cadastro de produtos, controle de lotes (rastreabilidade), movimentação de pallets e gestão de endereços de estoque.

O projeto é desenvolvido seguindo a filosofia de **MVP (Minimum Viable Product)**: começar com o menor conjunto de funcionalidades que já representa o problema real, evoluindo em versões sucessivas.

## 🧱 Modelo de domínio (v0.1)

O sistema é modelado em torno de quatro entidades principais:

| Entidade | Descrição |
|---|---|
| `Produto` | Representa o tipo genérico de um item (identificador interno, nome, código EAN, peso) |
| `Lote` | Representa um grupo de fabricação de um produto, usado para rastreabilidade (código, data de validade) |
| `Pallet` | Unidade física de movimentação, associada a um único lote e quantidade (simplificação da versão inicial) |
| `Endereco` | Representa uma vaga de armazenagem física, identificada por bloco, posição e nível — comporta no máximo um pallet por vez |

O relacionamento entre as entidades segue a cadeia: **Produto → Lote → Pallet → Endereço**, refletindo o fluxo real de recebimento, armazenagem e rastreabilidade em um centro de distribuição.

## 🛠️ Tecnologias

- **Java** (JDK 25)
- **Eclipse IDE**

## 📂 Estrutura do projeto

```
src/
└── com/red/wms/model/
    ├── Produto.java
    ├── Lote.java
    ├── Pallet.java
    ├── Endereco.java
    └── TesteWMS.java   (classe de testes manuais das entidades)
```

## 🚧 Status atual

- [x] Modelagem de domínio inicial (Produto, Lote, Pallet, Endereço)
- [x] Testes manuais de criação e associação entre entidades
- [ ] Persistência de dados (banco de dados)
- [ ] Regras de negócio (ex.: priorização por validade — FEFO, validação de ocupação de endereço)
- [ ] Interface de uso (aplicativo ou web)

## 🎯 Visão de futuro

O projeto tem como objetivo de longo prazo evoluir para um sistema comercialmente viável, capaz de atender múltiplas empresas com diferentes convenções de endereçamento e processos logísticos — motivo pelo qual campos configuráveis (como a identificação de blocos/vagas) foram desenhados de forma flexível desde o início.

---

*Projeto em desenvolvimento contínuo, como parte dos estudos em Ciência da Computação.*
