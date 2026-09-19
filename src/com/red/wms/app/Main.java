package com.red.wms.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.red.wms.dao.EnderecoDAO;
import com.red.wms.dao.LoteDAO;
import com.red.wms.dao.PalletDAO;
import com.red.wms.dao.ProdutoDAO;
import com.red.wms.model.Endereco;
import com.red.wms.model.Lote;
import com.red.wms.model.Pallet;
import com.red.wms.model.Produto;
import com.red.wms.service.EnderecoService;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/wms_db";
        String usuario = "postgres";
        String senha = "";

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);
            LoteDAO loteDAO = new LoteDAO(conexao);
            PalletDAO palletDAO = new PalletDAO(conexao);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conexao);
            EnderecoService enderecoService = new EnderecoService(conexao);

            while (continuar) {
                System.out.println("\n===== WMS - MENU PRINCIPAL =====");
                System.out.println("1. Cadastrar produto");
                System.out.println("2. Listar produtos");
                System.out.println("3. Cadastrar lote");
                System.out.println("4. Listar lotes");
                System.out.println("5. Cadastrar pallet");
                System.out.println("6. Cadastrar endereco");
                System.out.println("7. Associar pallet a endereco");
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
                        System.out.println("\n--- Produtos disponiveis ---");
                        for (Produto produto : produtosDisponiveis) {
                            System.out.println(produto.getId() + " - " + produto.getNome());
                        }

                        System.out.print("Digite o id do produto para este lote: ");
                        int produtoId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Codigo do lote: ");
                        String codigo = scanner.nextLine();

                        System.out.print("Data de validade (formato AAAA-MM-DD): ");
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
                            System.out.println(lote.getId() + " - Produto: " + produtoDoLoteAtual.getNome() + " - Lote: " + lote.getCodigo() + " - Validade: " + lote.getDataValidade() + " - Peso unitario: " + produtoDoLoteAtual.getPeso() + "kg");
                        }
                        break;

                    case 5:
                        List<Lote> lotesDisponiveis = loteDAO.buscarTodos();
                        System.out.println("\n--- Lotes disponiveis ---");
                        for (Lote lote : lotesDisponiveis) {
                            System.out.println(lote.getId() + " - " + lote.getCodigo());
                        }

                        System.out.print("Digite o id do lote para este pallet: ");
                        int loteId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Quantidade: ");
                        int quantidade = scanner.nextInt();
                        scanner.nextLine();

                        Lote loteDoPallet = new Lote("", null, null);
                        loteDoPallet.setId(loteId);
                        Pallet novoPallet = new Pallet(0, loteDoPallet, quantidade);
                        palletDAO.inserir(novoPallet);
                        System.out.println("Pallet cadastrado com sucesso!");
                        break;

                    case 6:
                        System.out.print("Bloco: ");
                        String bloco = scanner.nextLine();

                        System.out.print("Posicao: ");
                        String posicao = scanner.nextLine();

                        System.out.print("Nivel: ");
                        String nivel = scanner.nextLine();

                        System.out.print("Peso maximo (kg): ");
                        double pesoMaximo = scanner.nextDouble();
                        scanner.nextLine();

                        Endereco novoEndereco = new Endereco(bloco, posicao, nivel, pesoMaximo);
                        enderecoDAO.inserir(novoEndereco);
                        System.out.println("Endereco cadastrado com sucesso!");
                        break;

                    case 7:
                        List<Pallet> pallets = palletDAO.buscarTodos();
                        System.out.println("\n--- Pallets ---");
                        for (Pallet pallet : pallets) {
                            System.out.println(pallet.getId() + " - quantidade: " + pallet.getQuantidade());
                        }

                        System.out.print("Digite o id do pallet: ");
                        int palletId = scanner.nextInt();
                        scanner.nextLine();

                        List<Endereco> enderecos = enderecoDAO.buscarTodos();
                        System.out.println("\n--- Enderecos ---");
                        for (Endereco endereco : enderecos) {
                            System.out.println(endereco.getId() + " - " + endereco.getBloco() + " - " + endereco.getPosicao() + " - " + endereco.getNivel() + " - limite: " + endereco.getPesoMaximo() + "kg");
                        }

                        System.out.print("Digite o id do endereco: ");
                        int enderecoId = scanner.nextInt();
                        scanner.nextLine();

                        Pallet palletEscolhido = null;
                        for (Pallet pallet : pallets) {
                            if (pallet.getId() == palletId) {
                                palletEscolhido = pallet;
                            }
                        }

                        Endereco enderecoEscolhido = null;
                        for (Endereco endereco : enderecos) {
                            if (endereco.getId() == enderecoId) {
                                enderecoEscolhido = endereco;
                            }
                        }

                        try {
                            enderecoService.associarPallet(enderecoEscolhido, palletEscolhido);
                            enderecoDAO.atualizar(enderecoEscolhido);
                            System.out.println("Pallet associado ao endereco com sucesso!");
                        } catch (IllegalStateException e) {
                            System.out.println("Nao foi possivel associar: " + e.getMessage());
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