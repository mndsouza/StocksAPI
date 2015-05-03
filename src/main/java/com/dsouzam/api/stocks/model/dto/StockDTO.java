package com.dsouzam.api.stocks.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.joda.time.DateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Stock")
@Data
@Builder
@AllArgsConstructor
public class StockDTO {

	public StockDTO() {}
	
	@DynamoDBHashKey(attributeName="watchListId")
	private String watchListId;
	
	@DynamoDBRangeKey(attributeName="symbol")
	private String symbol;
	
	private DateTime purchaseDate;

	@DynamoDBAttribute(attributeName="purchasePrice")
	private BigDecimal purchasePrice;
	
	@DynamoDBAttribute(attributeName="purchaseDate")
	public String getPurchaseDate() {
		return purchaseDate.toString();
	}
	
	@DynamoDBAttribute(attributeName="purchaseDate")
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = new DateTime(purchaseDate);
	}
	
}