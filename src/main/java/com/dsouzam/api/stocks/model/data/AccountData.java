package com.dsouzam.api.stocks.model.data;

import lombok.Data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Account")
@Data
public class AccountData {
	
	@DynamoDBHashKey
	private String accountId;
	
	@DynamoDBAttribute(attributeName="name")
	private String name;
	
	@DynamoDBAttribute(attributeName="emailId")
	private String emailId;
	
}
