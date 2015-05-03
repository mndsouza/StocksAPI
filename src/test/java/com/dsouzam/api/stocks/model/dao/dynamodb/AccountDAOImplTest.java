package com.dsouzam.api.stocks.model.dao.dynamodb;

import static junit.framework.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.dsouzam.api.stocks.config.RootConfig;
import com.dsouzam.api.stocks.model.dao.AccountDAO;
import com.dsouzam.api.stocks.model.dao.exceptions.ItemNotFoundException;
import com.dsouzam.api.stocks.model.dto.AccountDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class ,loader=AnnotationConfigContextLoader.class)
public class AccountDAOImplTest {

	private static final String ACCOUNT_ID = "1";
	private static final String EMAIL_ID = "mndsouza@ncsu.edu";
	private static final String NAME = "mndsouza";
	private static final String NAME2 = null;
	private static final String ACCOUNT_ID2 = "2";
	private static final String EMAIL_ID2 = "melroyndsouza@gmail.com";


	@After
	public void tearDown() {
		new TestRunner()
			.deleteAccount(ACCOUNT_ID)
			.deleteAccount(ACCOUNT_ID2);
	}
	
	@Test
	public void shouldFetchAccountByEmailId() {
		new TestRunner()
			.createAccount(ACCOUNT_ID,EMAIL_ID,NAME)
			.getAccountByEmailId(EMAIL_ID)
			.validateAccount(ACCOUNT_ID,EMAIL_ID,NAME);
		}
	
	
	@Test(expected=ItemNotFoundException.class)
	public void shouldThrowExceptionIfAccountByEmailIdNotFound() {
		new TestRunner()
			.getAccountByEmailId(EMAIL_ID);
			
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldThrowExceptionIfMoreThan1AccountFoundByEamilId() {
		new TestRunner()
			.createAccount(ACCOUNT_ID,EMAIL_ID,NAME)
			.createAccount(ACCOUNT_ID2,EMAIL_ID,NAME2)
			.getAccountByEmailId(EMAIL_ID);

	}
	
	@Test
	public void shouldFetchAccountByAccountId() {
		new TestRunner()
			.createAccount(ACCOUNT_ID,EMAIL_ID,NAME)
			.getAccountById(ACCOUNT_ID)
			.validateAccount(ACCOUNT_ID,EMAIL_ID,NAME);
	}
	
	@Test(expected=ItemNotFoundException.class)
	public void shouldThrowExceptionForGetByAccountIdIfAccountDoesNotExist() {
		new TestRunner()
			.getAccountById(ACCOUNT_ID);
	}
	
	@Test
	public void shouldCreateNewAccount() {
		//tests creation of account
		shouldFetchAccountByAccountId();
	}
	
	@Test(expected=ItemNotFoundException.class)
	public void shouldUpdateExistingAccount() {
		new TestRunner()
			.createAccount(ACCOUNT_ID,EMAIL_ID,NAME)
			.createAccount(ACCOUNT_ID,EMAIL_ID2,NAME)			
			.getAccountByEmailId(EMAIL_ID2)
			.validateAccount(ACCOUNT_ID,EMAIL_ID2,NAME)
			.getAccountByEmailId(EMAIL_ID);	//should not exist
	}
	
	
	
	@Autowired
	private AccountDAO testObject;

	@Autowired
	private AmazonDynamoDBClient dbClient;


	class TestRunner {
		private AccountDTO accountDTO;
		
		public TestRunner createAccount(String accountId, String emailId,
				String name) {
			
			testObject.createOrUpdateAccount(
					AccountDTO
						.builder()
						.accountId(accountId)
						.emailId(emailId)
						.name(name)
						.build());
			return this;
		}

		public TestRunner deleteAccount(String accountId) {
			new DynamoDBMapper(dbClient).delete(AccountDTO.builder()
														.accountId(accountId)
														.build());
			return this;
		}

		public TestRunner getAccountById(String accountId) {
			accountDTO = testObject.getAccountById(accountId);
			return this;
		}

		public TestRunner validateAccount(String accountId, String emailId,
				String name) {
			assertEquals(accountId, accountDTO.getAccountId());
			assertEquals(emailId, accountDTO.getEmailId());
			assertEquals(name, accountDTO.getName());
			return this;
		}

		public TestRunner getAccountByEmailId(String emailId) {
			accountDTO = testObject.getAccountByEmailId(emailId);
			return this;
		}
		
		
	}
	
}
