package com.red.wms.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.red.wms.service.EnderecoService;

public class TesteConexao {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/wms_db";
		String usuario = "postgres";
		String senha = "senhaBancoDeDados";

		try {
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			EnderecoService enderecoService = new EnderecoService(conexao);

			Lote loteReferencia = new Lote("", null, null);
			loteReferencia.setId(1);

			System.out.println("=== Teste 1: pallet DENTRO do limite de peso ===");
			Endereco enderecoTeste1 = new Endereco("C10", "5", "100", 500);
			Pallet palletLeve = new Pallet(100, loteReferencia, 200);

			try {
				enderecoService.associarPallet(enderecoTeste1, palletLeve);
				System.out.println("Associado com sucesso! Peso do pallet: " + enderecoService.calcularPesoDoPallet(palletLeve));
			} catch (IllegalStateException e) {
				System.out.println("Bloqueado (nao deveria acontecer!): " + e.getMessage());
			}

			System.out.println("\n=== Teste 2: pallet ACIMA do limite de peso ===");
			Endereco enderecoTeste2 = new Endereco("C10", "6", "100", 500);
			Pallet palletPesado = new Pallet(101, loteReferencia, 2000);

			try {
				enderecoService.associarPallet(enderecoTeste2, palletPesado);
				System.out.println("Associado com sucesso (nao deveria acontecer!)");
			} catch (IllegalStateException e) {
				System.out.println("Bloqueado com sucesso: " + e.getMessage());
			}

			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}