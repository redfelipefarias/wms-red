package com.red.wms.model;

public class Endereco {
	private String bloco;
	private String posicao;
	private String nivel;
	private Pallet pallet;
	
	public Endereco(String bloco, String posicao, String nivel) {
		this.bloco = bloco;
		this.posicao = posicao;
		this.nivel = nivel;
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
