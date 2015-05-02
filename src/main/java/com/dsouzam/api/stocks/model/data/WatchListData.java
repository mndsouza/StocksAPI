package com.dsouzam.api.stocks.model.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data
@DynamoDBTable(tableName="WatchList")
public class WatchListData {
	@DynamoDBHashKey(attributeName="accountId")
	private String accountId;
	
	@DynamoDBRangeKey(attributeName="watchListId")
	private String watchListId;
	
	@DynamoDBAttribute(attributeName="name")
	private String name;
	
}