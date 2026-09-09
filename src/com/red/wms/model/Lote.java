package com.red.wms.model;
import java.time.LocalDate;

public class Lote {
	private String codigo;
	private LocalDate dataValidade;
	private Produto produto;
	
	public Lote(String codigo, LocalDate dataValidade, Produto produto) {
		this.codigo = codigo;
		this.dataValidade = dataValidade;
		this.produto = produto;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public LocalDate getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
