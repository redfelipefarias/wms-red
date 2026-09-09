package com.red.wms.model;

public class Pallet {
	private int id;
	private Lote lote;
	private int quantidade;
	
	public Pallet(int id, Lote lote, int quantidade){
		this.id = id;
		this.lote = lote;
		this.quantidade = quantidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
