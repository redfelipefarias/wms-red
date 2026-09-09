package com.red.wms.model;
import java.time.LocalDate;
public class TesteWMS {

	public static void main(String[] args) {
		Produto produto1 = new Produto(1, "Refrigerante Lata 350ml", "7894900011517", 0.35);
		System.out.println(produto1.getNome());
		
		Lote lote1 = new Lote("L2024001", LocalDate.of(2026, 12, 31), produto1);
		System.out.println(lote1.getCodigo());
		System.out.println(lote1.getProduto().getNome());

		Pallet pallet1 = new Pallet(1, lote1, 200);
		System.out.println(pallet1.getQuantidade());
		System.out.println(pallet1.getLote().getCodigo());
		System.out.println(pallet1.getLote().getProduto().getNome());
		
		Endereco endereco1 = new Endereco("A20", "21",  "100");
		System.out.println(endereco1.getBloco());
		System.out.println(endereco1.getPallet());
		
		endereco1.setPallet(pallet1);
		System.out.println(endereco1.getPallet().getQuantidade());
	}

}