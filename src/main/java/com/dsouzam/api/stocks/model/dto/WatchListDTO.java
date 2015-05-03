package com.dsouzam.api.stocks.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="WatchList")
@Data
@Builder
@AllArgsConstructor
public class WatchListDTO {
	public WatchListDTO() {}
	
	@DynamoDBHashKey(attributeName="accountId")
	private String accountId;
	
	@DynamoDBRangeKey(attributeName="watchListId")
	private String watchListId;
	
	@DynamoDBAttribute(attributeName="name")
	private String name;
	
}