package com.red.wms.model;

public class Endereco {
	
	private int id;
	private String bloco;
	private String posicao;
	private String nivel;
	private double pesoMaximo;
	private Pallet pallet;

	public Endereco(String bloco, String posicao, String nivel, double pesoMaximo) {
		this.bloco = bloco;
		this.posicao = posicao;
		this.nivel = nivel;
		this.pesoMaximo = pesoMaximo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}
	
	public double getPesoMaximo() {
		return pesoMaximo;
	}

	public void setPesoMaximo(double pesoMaximo) {
		this.pesoMaximo = pesoMaximo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public Pallet getPallet() {
		return pallet;
	}

	public void setPallet(Pallet pallet) {
		this.pallet = pallet;
	}

}