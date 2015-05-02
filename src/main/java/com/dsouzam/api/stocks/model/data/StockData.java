package com.dsouzam.api.stocks.model.data;

import org.joda.time.DateTime;

import lombok.Data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@Data
@DynamoDBTable(tableName="Stock")
public class StockData {
	
	@DynamoDBHashKey(attributeName="watchListId")
	private String watchListId;
	
	@DynamoDBRangeKey(attributeName="symbol")
	private String symbol;
	
	@DynamoDBAttribute(attributeName="purchaseDate")
	private String purchaseDate;

	@DynamoDBAttribute(attributeName="purchasePrice")
	private String purchasePrice;
	
	
	public DateTime getDateOfPurchase() {
		return new DateTime(purchaseDate);
	}

	public void setDateOfPurchase(DateTime purchaseDate) {
		this.purchaseDate = purchaseDate.toString();
	}
	
	
	
}