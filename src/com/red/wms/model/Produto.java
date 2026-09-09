package com.red.wms.model;

public class Produto {
	
	private int id;
	private String nome;
	private String ean;
	private double peso;

	public Produto(int id, String nome, String ean, double peso) {
		this.id = id;
		this.nome = nome;
		this.ean = ean;
		this.peso = peso;		
	}
	
	public String getNome() {
		return nome;
	}	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public double getPeso( ) {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
