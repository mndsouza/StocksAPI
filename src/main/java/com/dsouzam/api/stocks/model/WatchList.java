package com.dsouzam.api.stocks.model;

import java.util.List;

import com.dsouzam.api.stocks.model.data.NamedURI;
import com.dsouzam.api.stocks.model.data.WatchListData;

public class WatchList {

	private WatchListData watchListData;
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
