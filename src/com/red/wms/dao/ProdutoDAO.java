package com.red.wms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.red.wms.model.Produto;

public class ProdutoDAO {

    private Connection conexao;

    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, ean, peso) VALUES (?, ?, ?)";

        PreparedStatement statement = conexao.prepareStatement(sql);
        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getEan());
        statement.setDouble(3, produto.getPeso());

        statement.executeUpdate();
    }

    public List<Produto> buscarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        PreparedStatement statement = conexao.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String nome = resultado.getString("nome");
            String ean = resultado.getString("ean");
            double peso = resultado.getDouble("peso");

            Produto produto = new Produto(id, nome, ean, peso);
            produtos.add(produto);
        }

        return produtos;
    }
public void atualizar(Produto produto) throws SQLException {
	String sql = "UPDATE produto SET nome = ?, ean = ?, peso = ? WHERE id = ?";
	
	PreparedStatement statement = conexao.prepareStatement(sql);
	statement.setString(1, produto.getNome());
	statement.setString(2, produto.getEan());
	statement.setDouble(3, produto.getPeso());
	statement.setInt(4, produto.getId());
	
	statement.executeUpdate();
}

public void deletar(int id) throws SQLException {
	String sql = "DELETE FROM produto WHERE id = ?";
	
	PreparedStatement statement = conexao.prepareStatement(sql);
	statement.setInt(1,  id);
	
	statement.executeUpdate();
}
}