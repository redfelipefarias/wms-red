package com.red.wms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.red.wms.dao.EnderecoDAO;
import com.red.wms.dao.PalletDAO;
import com.red.wms.model.Endereco;
import com.red.wms.model.Lote;
import com.red.wms.model.Pallet;

public class EstoqueService {

    private PalletDAO palletDAO;
    private EnderecoDAO enderecoDAO;
    private LoteService loteService;

    public EstoqueService(Connection conexao) {
        this.palletDAO = new PalletDAO(conexao);
        this.enderecoDAO = new EnderecoDAO(conexao);
        this.loteService = new LoteService(conexao);
    }

    public void separarProduto(int produtoId, int quantidadeDesejada) throws SQLException {
        Lote lote = loteService.buscarProximoAVencer(produtoId);

        List<Pallet> pallets = palletDAO.buscarPorLoteId(lote.getId());

        if (pallets.isEmpty()) {
            throw new IllegalStateException("Nenhum pallet encontrado para o lote " + lote.getCodigo());
        }

        Pallet pallet = pallets.get(0);

        if (pallet.getQuantidade() < quantidadeDesejada) {
            throw new IllegalStateException("Quantidade insuficiente no pallet. Disponivel: " + pallet.getQuantidade() + ", solicitado: " + quantidadeDesejada);
        }

        int novaQuantidade = pallet.getQuantidade() - quantidadeDesejada;

        if (novaQuantidade == 0) {
            Endereco endereco = enderecoDAO.buscarPorPalletId(pallet.getId());

            if (endereco != null) {
                endereco.setPallet(null);
                enderecoDAO.atualizar(endereco);
            }

            palletDAO.deletar(pallet.getId());
            System.out.println("Separado " + quantidadeDesejada + " do lote " + lote.getCodigo() + " (pallet id " + pallet.getId() + "). Pallet esvaziado e removido, endereco liberado.");
        } else {
            pallet.setQuantidade(novaQuantidade);
            palletDAO.atualizar(pallet);
            System.out.println("Separado " + quantidadeDesejada + " do lote " + lote.getCodigo() + " (pallet id " + pallet.getId() + "). Restante no pallet: " + novaQuantidade);
        }
    }

}