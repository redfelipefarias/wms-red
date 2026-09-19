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
        String sql = "INSERT INTO endereco (bloco, posicao, nivel, peso_maximo, pallet_id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, endereco.getBloco());
        statement.setString(2, endereco.getPosicao());
        statement.setString(3, endereco.getNivel());
        statement.setDouble(4, endereco.getPesoMaximo());

        if (endereco.getPallet() != null) {
            statement.setInt(5, endereco.getPallet().getId());
        } else {
            statement.setNull(5, Types.INTEGER);
        }

        statement.executeUpdate();
    }
    
    public void atualizar(Endereco endereco) throws SQLException {
    	String sql = "UPDATE endereco SET bloco = ?, posicao = ?, nivel = ?, peso_maximo = ?, pallet_id = ? WHERE id = ?";
    	
    	PreparedStatement statement = conexao.prepareStatement(sql);
    	statement.setString(1, endereco.getBloco());
    	statement.setString(2, endereco.getPosicao());
    	statement.setString(3, endereco.getNivel());
    	statement.setDouble(4, endereco.getPesoMaximo());
    	
    	if (endereco.getPallet() != null) {
    		statement.setInt(5, endereco.getPallet().getId());
    	} else {
    		statement.setNull(5, Types.INTEGER);
    	}
    	
    	statement.setInt(6,  endereco.getId());
    	
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
            double pesoMaximo = resultado.getDouble("peso_maximo");
            int palletId = resultado.getInt("pallet_id");
            boolean temPallet = !resultado.wasNull();

            Endereco endereco = new Endereco(bloco, posicao, nivel, pesoMaximo);
            endereco.setId(id);

            if (temPallet) {
                Pallet pallet = new Pallet(palletId, null, 0);
                endereco.setPallet(pallet);
            }

            enderecos.add(endereco);
        }

        return enderecos;
    }

    public Endereco buscarPorPalletId(int palletId) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE pallet_id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, palletId);
        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            int id = resultado.getInt("id");
            String bloco = resultado.getString("bloco");
            String posicao = resultado.getString("posicao");
            String nivel = resultado.getString("nivel");
            double pesoMaximo = resultado.getDouble("peso_maximo");

            Endereco endereco = new Endereco(bloco, posicao, nivel, pesoMaximo);
            endereco.setId(id);

            Pallet pallet = new Pallet(palletId, null, 0);
            endereco.setPallet(pallet);

            return endereco;
        }

        return null;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

}