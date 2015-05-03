package com.dsouzam.api.stocks.model;

import java.util.List;

import com.dsouzam.api.stocks.model.dto.WatchListDTO;

public class WatchList {

	private WatchListDTO watchListData;
	private List<NamedURI> stocks;

	public String getURI() {
		return String.format("/account/%s/watchList/%s", watchListData.getAccountId(),watchListData.getWatchListId());
	}
	
	public String getAccountURI() {
		return String.format("/account/%s", watchListData.getAccountId());
	}
	
	public String getName() {
		return watchListData.getName();
	}
	
	public List<NamedURI> getStocks() {
		return stocks;
	}
}
