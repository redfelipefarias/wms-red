package com.red.wms.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.red.wms.dao.LoteDAO;
import com.red.wms.dao.ProdutoDAO;
import com.red.wms.model.Lote;
import com.red.wms.model.Produto;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/wms_db";
        String usuario = "postgres";
        String senha = "senhaDoBancoDeDados";

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);
            LoteDAO loteDAO = new LoteDAO(conexao);

            while (continuar) {
                System.out.println("\n===== WMS - MENU PRINCIPAL =====");
                System.out.println("1. Cadastrar produto");
                System.out.println("2. Listar produtos");
                System.out.println("3. Cadastrar Lote");
                System.out.println("4. Lista Lotes");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opcao: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do produto: ");
                        String nome = scanner.nextLine();

                        System.out.print("EAN: ");
                        String ean = scanner.nextLine();

                        System.out.print("Peso (kg): ");
                        double peso = scanner.nextDouble();
                        scanner.nextLine();

                        Produto novoProduto = new Produto(0, nome, ean, peso);
                        produtoDAO.inserir(novoProduto);
                        System.out.println("Produto cadastrado com sucesso!");
                        break;

                    case 2:
                        List<Produto> produtos = produtoDAO.buscarTodos();
                        System.out.println("\n--- Lista de Produtos ---");
                        for (Produto produto : produtos) {
                            System.out.println(produto.getId() + " - " + produto.getNome() + " - " + produto.getEan() + " - " + produto.getPeso() + "kg");
                        }
                        break;

                    case 3:
                    	List<Produto> produtosDisponiveis = produtoDAO.buscarTodos();
                    	System.out.println("\n--- Produtos disponíveis ---");
                    	for (Produto produto : produtosDisponiveis) {
                    		System.out.println(produto.getId() + " - " + produto.getNome());
                    	}
                    	
                    	System.out.println("Digite o ID do produto para este lote: ");
                    	int produtoId = scanner.nextInt();
                    	scanner.nextLine();
                    	
                    	System.out.println("Códico do Lote: ");
                    	String codigo = scanner.nextLine();
                    	
                    	System.out.println("Data de Validade (formato AAAA-MM-DD): ");
                    	String dataTexto = scanner.nextLine();
                    	LocalDate dataValidade = LocalDate.parse(dataTexto);
                    	
                    	Produto produtoDoLote = new Produto(produtoId, "", "", 0);
                    	Lote novoLote = new Lote(codigo, dataValidade, produtoDoLote);
                    	loteDAO.inserir(novoLote);
                    	System.out.println("Lote cadastrado com sucesso!");
                    	break;
                    	
                    case 4:
                    	List<Lote> lotes = loteDAO.buscarTodos();
                    	System.out.println("\n--- Lista de Lotes ---");
                    	for (Lote lote : lotes) {
                    		Produto produtoDoLoteAtual = produtoDAO.buscarPorId(lote.getProduto().getId());
                    		System.out.println(lote.getId() + " - Produto: " + produtoDoLoteAtual.getNome() + " - Lote: " + lote.getCodigo() + " - Validade: " + lote.getDataValidade() + " - Peso unitário: " + produtoDoLoteAtual.getPeso() + "kg");
                    	}
                    	break;
                    	
                    case 0:
                        continuar = false;
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opcao invalida!");
                }
            }

            conexao.close();
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        scanner.close();
    }

}