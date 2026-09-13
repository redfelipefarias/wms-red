package com.red.wms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.red.wms.model.Endereco;
import com.red.wms.model.Pallet;

public class EnderecoDAO {

    private Connection conexao;

    public EnderecoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO endereco (bloco, posicao, nivel, pallet_id) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, endereco.getBloco());
        statement.setString(2, endereco.getPosicao());
        statement.setString(3, endereco.getNivel());

        if (endereco.getPallet() != null) {
            statement.setInt(4, endereco.getPallet().getId());
        } else {
            statement.setNull(4, Types.INTEGER);
        }

        statement.executeUpdate();
    }

    public List<Endereco> buscarTodos() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM endereco";

        PreparedStatement statement = conexao.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String bloco = resultado.getString("bloco");
            String posicao = resultado.getString("posicao");
            String nivel = resultado.getString("nivel");
            int palletId = resultado.getInt("pallet_id");
            boolean temPallet = !resultado.wasNull();

            Endereco endereco = new Endereco(bloco, posicao, nivel);
            endereco.setId(id);

            if (temPallet) {
                Pallet pallet = new Pallet(palletId, null, 0);
                endereco.setPallet(pallet);
            }

            enderecos.add(endereco);
        }

        return enderecos;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

}