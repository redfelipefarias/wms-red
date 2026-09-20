-- ============================================================
-- WMS - Schema do Banco de Dados (PostgreSQL)
-- ============================================================
-- Este arquivo documenta a estrutura completa do banco "wms_db".
-- Execute os comandos abaixo, na ordem apresentada, em um banco
-- PostgreSQL vazio para recriar o schema utilizado pelo projeto.
--
-- Ordem de criação: produto -> lote -> pallet -> endereco,
-- respeitando a ordem de dependencia das chaves estrangeiras.
-- ============================================================

-- Tabela: produto
-- Representa o tipo generico de um item (nao um carregamento especifico).
CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ean VARCHAR(20),
    peso DOUBLE PRECISION
);

-- Tabela: lote
-- Representa um grupo de fabricacao de um produto, usado para rastreabilidade.
CREATE TABLE lote (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL,
    data_validade DATE,
    produto_id INTEGER REFERENCES produto(id)
);

-- Tabela: pallet
-- Representa uma unidade fisica de movimentacao. No MVP, cada pallet
-- esta associado a um unico lote (e, por consequencia, a um unico produto).
CREATE TABLE pallet (
    id SERIAL PRIMARY KEY,
    quantidade INTEGER NOT NULL,
    lote_id INTEGER REFERENCES lote(id)
);

-- Tabela: endereco
-- Representa uma vaga de armazenagem fisica. O campo pallet_id e
-- opcional (pode ser NULL), pois um endereco pode estar vazio.
-- O campo peso_maximo permite que cada empresa configure o limite
-- fisico de peso suportado por aquele endereco especifico.
CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    bloco VARCHAR(20) NOT NULL,
    posicao VARCHAR(20) NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    peso_maximo DOUBLE PRECISION,
    pallet_id INTEGER REFERENCES pallet(id)
);

-- ============================================================
-- Historico de alteracoes no schema (registradas para referencia)
-- ============================================================

-- Adicionada apos a criacao inicial da tabela endereco, para suportar
-- a regra de negocio de limite de peso por endereco:
-- ALTER TABLE endereco ADD COLUMN peso_maximo DOUBLE PRECISION;
