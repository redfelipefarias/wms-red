package com.red.wms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.red.wms.dao.EnderecoDAO;

public class TesteConexao {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/wms_db";
		String usuario = "postgres";
		String senha = "senha";

		try {
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);

			System.out.println("=== 1. Buscando todos os enderecos ===");
			List<Endereco> enderecos = enderecoDAO.buscarTodos();
			for (Endereco endereco : enderecos) {
				String infoPallet = (endereco.getPallet() != null) ? "pallet_id: " + endereco.getPallet().getId() : "vazio";
				System.out.println(endereco.getId() + " - " + endereco.getBloco() + " - " + endereco.getPosicao() + " - " + endereco.getNivel() + " - " + infoPallet);
			}

			System.out.println("\n=== 2. Inserindo endereco VAZIO (sem pallet) ===");
			Endereco enderecoVazio = new Endereco("A20", "22", "100");
			enderecoDAO.inserir(enderecoVazio);
			System.out.println("Endereco vazio inserido!");

			System.out.println("\n=== 3. Buscando novamente ===");
			List<Endereco> enderecosFinais = enderecoDAO.buscarTodos();
			for (Endereco endereco : enderecosFinais) {
				String infoPallet = (endereco.getPallet() != null) ? "pallet_id: " + endereco.getPallet().getId() : "vazio";
				System.out.println(endereco.getId() + " - " + endereco.getBloco() + " - " + endereco.getPosicao() + " - " + endereco.getNivel() + " - " + infoPallet);
			}

			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}