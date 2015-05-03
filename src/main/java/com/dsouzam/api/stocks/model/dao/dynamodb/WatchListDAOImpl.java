package com.dsouzam.api.stocks.model.dao.dynamodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.dsouzam.api.stocks.model.dao.WatchListDAO;
import com.dsouzam.api.stocks.model.dao.exceptions.ItemNotFoundException;
import com.dsouzam.api.stocks.model.dto.WatchListDTO;

@Component
public class WatchListDAOImpl implements WatchListDAO {


	@Autowired
	private AmazonDynamoDBClient dbClient;


	@Override
	public WatchListDTO get(String accountId, String watchListId) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		WatchListDTO watchListDTO = dbMapper.load(WatchListDTO.class, accountId, watchListId);
		 
		if(watchListDTO == null) {
			throw new ItemNotFoundException(String.format("[GetWatchlist] Item not found with id [%s]:[%s]",accountId,watchListId));
		}
		
		return watchListDTO;
	}

	@Override
	public void createOrUpdate(WatchListDTO watchList) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		dbMapper.save(watchList);
	}

	@Override
	public void delete(WatchListDTO watchList) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		dbMapper.delete(watchList);
	}

	@Override
	public List<WatchListDTO> getWatchLists(String accountId) {
		DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
		
		WatchListDTO watchListDTO = WatchListDTO.builder().accountId(accountId).build();
		
		DynamoDBQueryExpression<WatchListDTO> queryExpression 
				= new DynamoDBQueryExpression<WatchListDTO>()
					.withHashKeyValues(watchListDTO);
		return mapper.query(WatchListDTO.class,
				queryExpression);
	}

}
