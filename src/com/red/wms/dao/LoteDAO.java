package com.red.wms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.red.wms.model.Lote;
import com.red.wms.model.Produto;

public class LoteDAO {

    private Connection conexao;

    public LoteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Lote lote) throws SQLException {
        String sql = "INSERT INTO lote (codigo, data_validade, produto_id) VALUES (?, ?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, lote.getCodigo());
        statement.setDate(2, Date.valueOf(lote.getDataValidade()));
        statement.setInt(3, lote.getProduto().getId());

        statement.executeUpdate();
    }

    public List<Lote> buscarTodos() throws SQLException {
        List<Lote> lotes = new ArrayList<>();
        String sql = "SELECT * FROM lote";

        PreparedStatement statement = conexao.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String codigo = resultado.getString("codigo");
            LocalDate dataValidade = resultado.getDate("data_validade").toLocalDate();
            int produtoId = resultado.getInt("produto_id");

            Produto produto = new Produto(produtoId, "", "", 0);

            Lote lote = new Lote(codigo, dataValidade, produto);
            lote.setId(id);
            lotes.add(lote);
        }

        return lotes;
    }
    
    public List<Lote> buscarPorProdutoOrdenadoPorValidade(int produtoId) throws SQLException {
    	List<Lote> lotes = new ArrayList<>();
    	String sql = "SELECT * FROM lote WHERE produto_id = ? ORDER BY data_validade ASC";
    	
    	PreparedStatement statement = conexao.prepareStatement(sql);
    	statement.setInt(1, produtoId);
    	ResultSet resultado = statement.executeQuery();
    	
    	while (resultado.next()) {
    		int id = resultado.getInt("id");
    		String codigo = resultado.getString("codigo");
    		LocalDate dataValidade = resultado .getDate("data_validade").toLocalDate();
    		
    		Produto produto = new Produto(produtoId, "", "", 0);
    		
    		Lote lote = new Lote(codigo, dataValidade, produto);
    		lote.setId(id);
    		lotes.add(lote);
    	}
    	return lotes;
    }
    public void atualizar(Lote lote) throws SQLException {
        String sql = "UPDATE lote SET codigo = ?, data_validade = ?, produto_id = ? WHERE id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, lote.getCodigo());
        statement.setDate(2, Date.valueOf(lote.getDataValidade()));
        statement.setInt(3, lote.getProduto().getId());
        statement.setInt(4, lote.getId());

        statement.executeUpdate();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM lote WHERE id = ?";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

}