package com.red.wms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.red.wms.dao.LoteDAO;
import com.red.wms.model.Lote;

public class LoteService {
	
	private LoteDAO loteDAO;
	
	public LoteService(Connection conexao) {
		this.loteDAO = new LoteDAO(conexao);
	}
	
	public Lote buscarProximoAVencer(int produtoId) throws SQLException {
		List<Lote> lotes = loteDAO.buscarPorProdutoOrdenadoPorValidade(produtoId);
		
		if (lotes.isEmpty()) {
			throw new IllegalStateException("Nenhum lote encontrado para o produto id " + produtoId);
		}
		return lotes.get(0);
	}

}
