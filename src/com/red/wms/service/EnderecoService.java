package com.red.wms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.red.wms.dao.EnderecoDAO;
import com.red.wms.dao.LoteDAO;
import com.red.wms.dao.ProdutoDAO;
import com.red.wms.model.Endereco;
import com.red.wms.model.Lote;
import com.red.wms.model.Pallet;
import com.red.wms.model.Produto;

public class EnderecoService {

    private EnderecoDAO enderecoDAO;
    private LoteDAO loteDAO;
    private ProdutoDAO produtoDAO;

    public EnderecoService(Connection conexao) {
        this.enderecoDAO = new EnderecoDAO(conexao);
        this.loteDAO = new LoteDAO(conexao);
        this.produtoDAO = new ProdutoDAO(conexao);
    }

    public double calcularPesoDoPallet(Pallet pallet) throws SQLException {
        int loteId = pallet.getLote().getId();

        Lote lote = buscarLotePorId(loteId);
        Produto produto = buscarProdutoPorId(lote.getProduto().getId());

        return pallet.getQuantidade() * produto.getPeso();
    }

    private Lote buscarLotePorId(int loteId) throws SQLException {
        for (Lote lote : loteDAO.buscarTodos()) {
            if (lote.getId() == loteId) {
                return lote;
            }
        }
        throw new IllegalStateException("Lote id " + loteId + " nao encontrado");
    }

    private Produto buscarProdutoPorId(int produtoId) throws SQLException {
        for (Produto produto : produtoDAO.buscarTodos()) {
            if (produto.getId() == produtoId) {
                return produto;
            }
        }
        throw new IllegalStateException("Produto id " + produtoId + " nao encontrado");
    }

    public void associarPallet(Endereco endereco, Pallet pallet) throws SQLException {
        Endereco enderecoExistente = enderecoDAO.buscarPorPalletId(pallet.getId());

        if (enderecoExistente != null) {
            throw new IllegalStateException("O pallet id " + pallet.getId() + " ja esta associado ao endereco id " + enderecoExistente.getId());
        }

        double pesoDoPallet = calcularPesoDoPallet(pallet);

        if (pesoDoPallet > endereco.getPesoMaximo()) {
            throw new IllegalStateException("Peso do pallet (" + pesoDoPallet + ") excede o limite do endereco (" + endereco.getPesoMaximo() + ")");
        }

        endereco.setPallet(pallet);
    }

}