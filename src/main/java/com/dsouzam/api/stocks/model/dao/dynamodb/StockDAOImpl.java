package com.dsouzam.api.stocks.model.dao.dynamodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.dsouzam.api.stocks.model.dao.StockDAO;
import com.dsouzam.api.stocks.model.dao.exceptions.ItemNotFoundException;
import com.dsouzam.api.stocks.model.dto.StockDTO;

@Component
public class StockDAOImpl implements StockDAO{

	@Autowired
	private AmazonDynamoDBClient dbClient;


	@Override
	public StockDTO get(String watchListId, String symbol) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		StockDTO stockDTO = dbMapper.load(StockDTO.class, watchListId, symbol);
		 
		if(stockDTO == null) {
			throw new ItemNotFoundException(String.format("[GetStock] Item not found with id [%s]:[%s]",watchListId,symbol));
		}
		
		return stockDTO;
	}

	@Override
	public void createOrUpdate(StockDTO stock) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		dbMapper.save(stock);
	}

	@Override
	public void delete(StockDTO stock) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		dbMapper.delete(stock);
	}

	@Override
	public List<StockDTO> get(String watchListId) {
		DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
		
		StockDTO stockDTO = StockDTO.builder().watchListId(watchListId).build();
		
		DynamoDBQueryExpression<StockDTO> queryExpression 
				= new DynamoDBQueryExpression<StockDTO>()
					.withHashKeyValues(stockDTO);
		return mapper.query(StockDTO.class,
				queryExpression);
	}

}
