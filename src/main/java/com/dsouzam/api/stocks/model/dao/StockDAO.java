package com.dsouzam.api.stocks.model.dao;

import java.util.List;

import com.dsouzam.api.stocks.model.dto.StockDTO;

public interface StockDAO {
	public StockDTO get(String watchListId, String symbol);
	public List<StockDTO> get(String watchListId);
	public void createOrUpdate(StockDTO stock);
	public void delete(StockDTO stock);
}
