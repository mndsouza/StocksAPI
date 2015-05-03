package com.dsouzam.api.stocks.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Account")
@Data
@Builder
@AllArgsConstructor
public class AccountDTO {

	public AccountDTO() {}
	
	@DynamoDBHashKey(attributeName="accountId")
	private String accountId;

	@DynamoDBAttribute(attributeName="emailId")
	@DynamoDBIndexHashKey(globalSecondaryIndexName = "emailId-index", attributeName = "emailId")
	private String emailId;
	
	@DynamoDBAttribute(attributeName="name")
	private String name;
	

	
}
