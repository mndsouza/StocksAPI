package com.dsouzam.api.stocks.model.dao;

import static junit.framework.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.dsouzam.api.stocks.model.dao.utils.DAOUtils;
import com.dsouzam.api.stocks.model.data.AccountData;

public class AccountDAOTest {

	private static final String ACCOUNT_ID = "1";
	private static final String EMAIL_ID = "mndsouza@ncsu.edu";
	private static final String NAME = "mndsouza";

	private AmazonDynamoDBClient dbClient = DAOUtils.INSTANCE.getDynamoDB();;

	@Test
	public void shouldWriteAndReadFromDynamoDB() {
		AccountData accountDAO = new AccountData();
		accountDAO.setEmailId(EMAIL_ID);
		accountDAO.setName(NAME);

		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dbClient);
		dynamoDBMapper.save(accountDAO);

		AccountData accountRetrieved = dynamoDBMapper.load(AccountData.class,
				accountDAO.getAccountId());
		assertEquals(accountRetrieved.getEmailId(), EMAIL_ID);
		assertEquals(accountRetrieved.getName(), NAME);

		dynamoDBMapper.delete(accountRetrieved);
	}

	@Test
	public void shouldReadFromDynamoDB() {
		AccountData accountDAO = new AccountData();
		accountDAO.setEmailId(EMAIL_ID);
		accountDAO.setName(NAME);

		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dbClient);
		dynamoDBMapper.save(accountDAO);

		AccountData retrieveByEmail = new AccountData();
		retrieveByEmail.setEmailId(EMAIL_ID);

		DynamoDBQueryExpression<AccountData> queryExpression = new DynamoDBQueryExpression<AccountData>()
				.withHashKeyValues(retrieveByEmail);
		AccountData retrievedObj = dynamoDBMapper.query(AccountData.class, queryExpression).get(0);
		assertEquals(NAME, retrievedObj.getName());
	}
}
