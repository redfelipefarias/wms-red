package com.red.wms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.red.wms.model.Lote;
import com.red.wms.model.Pallet;

public class PalletDAO {

    private Connection conexao;

    public PalletDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Pallet pallet) throws SQLException {
        String sql = "INSERT INTO pallet (quantidade, lote_id) VALUES (?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, pallet.getQuantidade());
        statement.setInt(2, pallet.getLote().getId());

        statement.executeUpdate();
    }

    public List<Pallet> buscarTodos() throws SQLException {
        List<Pallet> pallets = new ArrayList<>();
        String sql = "SELECT * FROM pallet";

        PreparedStatement statement = conexao.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id");
            int quantidade = resultado.getInt("quantidade");
            int loteId = resultado.getInt("lote_id");

            Lote lote = new Lote("", null, null);
            lote.setId(loteId);

            Pallet pallet = new Pallet(id, lote, quantidade);
            pallets.add(pallet);
        }

        return pallets;
    }

    public void atualizar(Pallet pallet) throws SQLException {
        String sql = "UPDATE pallet SET quantidade = ?, lote_id = ? WHERE id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, pallet.getQuantidade());
        statement.setInt(2, pallet.getLote().getId());
        statement.setInt(3, pallet.getId());

        statement.executeUpdate();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM pallet WHERE id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

}