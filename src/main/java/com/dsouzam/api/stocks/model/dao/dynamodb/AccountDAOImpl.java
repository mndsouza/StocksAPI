package com.dsouzam.api.stocks.model.dao.dynamodb;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.dsouzam.api.stocks.model.dao.AccountDAO;
import com.dsouzam.api.stocks.model.dao.exceptions.ItemNotFoundException;
import com.dsouzam.api.stocks.model.dto.AccountDTO;

@Log4j
@Component
public class AccountDAOImpl implements AccountDAO {

	private static final String EMAIL_INDEX = "emailId-index";

	@Autowired
	private AmazonDynamoDBClient dbClient;

	@Override
	public AccountDTO getAccountByEmailId(String emailId) {
		AccountDTO accountDTO = AccountDTO
									.builder()
									.emailId(emailId)
									.build();

		DynamoDBMapper mapper = new DynamoDBMapper(dbClient);
		DynamoDBQueryExpression<AccountDTO> queryExpression = new DynamoDBQueryExpression<AccountDTO>()
																.withIndexName(EMAIL_INDEX)
																.withConsistentRead(false)
																.withHashKeyValues(accountDTO);
		List<AccountDTO> accounts = mapper.query(AccountDTO.class,
				queryExpression);

		if (accounts.isEmpty())
			throw new ItemNotFoundException("[GetAccountByEmailId]" + accountDTO);
		if (accounts.size() != 1) {
			String message = "[GetAccountByEmailId] Multiple accounts with same email :"
					+ accounts
						.stream()
						.map(i -> i.toString())
						.collect(Collectors.joining(",", "[", "]"));
			log.fatal(message);
			throw new IllegalStateException(message);
		}
		return accounts.get(0);
	}

	@Override
	public AccountDTO getAccountById(String accountId) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		AccountDTO accountDTO = dbMapper.load(AccountDTO.class, accountId);
		 
		if(accountDTO == null) {
			throw new ItemNotFoundException(String.format("[GetAccountByAccountId] Item not found with id [%s]",accountId));
		}
		
		return accountDTO;
	}

	@Override
	public void createOrUpdateAccount(AccountDTO accountDTO) {
		DynamoDBMapper dbMapper= new DynamoDBMapper(dbClient);
		dbMapper.save(accountDTO);
		
		return;
	}

}
