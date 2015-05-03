package com.dsouzam.api.stocks.model.dao;

import java.util.List;

import com.dsouzam.api.stocks.model.dto.WatchListDTO;

public interface WatchListDAO {
	public WatchListDTO get(String accountId, String watchListId);
	public void createOrUpdate(WatchListDTO watchList);
	public void delete(WatchListDTO watchList);
	public List<WatchListDTO> getWatchLists(String accoutId);
}
