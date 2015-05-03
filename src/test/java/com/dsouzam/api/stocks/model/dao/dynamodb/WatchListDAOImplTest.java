package com.dsouzam.api.stocks.model.dao.dynamodb;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.dsouzam.api.stocks.config.RootConfig;
import com.dsouzam.api.stocks.model.dao.WatchListDAO;
import com.dsouzam.api.stocks.model.dao.exceptions.ItemNotFoundException;
import com.dsouzam.api.stocks.model.dto.WatchListDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class ,loader=AnnotationConfigContextLoader.class)
public class WatchListDAOImplTest {

	private static final String ACCOUNT_ID = "acc_id";
	private static final String WATCHLIST_ID = "wl_id";
	private static final String NAME = "wl_name";
	
	private static final String WATCHLIST_ID1 = "wl_id1";
	private static final String WATCHLIST_ID2 = "wl_id2";
	private static final String WATCHLIST_ID3 = "wl_id3";
	private static final String NAME1 = "name_1";
	private static final String NAME2 = "name_2";
	private static final String NAME3 = "name_3";

	@After
	public void tearDown() {
		new TestRunner()
			.delete(ACCOUNT_ID, WATCHLIST_ID)
			.delete(ACCOUNT_ID, WATCHLIST_ID1)
			.delete(ACCOUNT_ID, WATCHLIST_ID2)
			.delete(ACCOUNT_ID, WATCHLIST_ID3);
			
	}
	
	@Test
	public void getShouldFetchWatchList() {
		new TestRunner()
			.create(ACCOUNT_ID,WATCHLIST_ID,NAME)
			.get(ACCOUNT_ID, WATCHLIST_ID)
			.validate(ACCOUNT_ID,WATCHLIST_ID,NAME);
}

	@Test(expected = ItemNotFoundException.class)
	public void getShouldThrowExceptionIfWatchListNotPresent() {
		new TestRunner()
			.get(ACCOUNT_ID, WATCHLIST_ID);
	}

	@Test
	public void createOrUpdateShouldCreateWatchList() {
		getShouldFetchWatchList();
	}

	@Test
	public void createOrUpdateShouldUpdateExistingWatchList() {
		new TestRunner()
			.create(ACCOUNT_ID,WATCHLIST_ID,NAME)
			.create(ACCOUNT_ID,WATCHLIST_ID,NAME1)
			.get(ACCOUNT_ID,WATCHLIST_ID)
			.validate(ACCOUNT_ID,WATCHLIST_ID,NAME1);
	}

	@Test(expected=ItemNotFoundException.class)
	public void deleteShouldRemoveExistingWatchlist() {
		new TestRunner()
			.create(ACCOUNT_ID,WATCHLIST_ID,NAME)
			.get(ACCOUNT_ID, WATCHLIST_ID)
			.validate(ACCOUNT_ID, WATCHLIST_ID,NAME)
			.delete(ACCOUNT_ID, WATCHLIST_ID)
			.get(ACCOUNT_ID, WATCHLIST_ID);
		
	}

	@Test
	public void getWatchListsShouldRetrieveAllWatchListsInAccount() {
		new TestRunner()
			.create(ACCOUNT_ID,WATCHLIST_ID1,NAME1)
			.create(ACCOUNT_ID,WATCHLIST_ID2,NAME2)
			.create(ACCOUNT_ID,WATCHLIST_ID3,NAME3)
			.getWatchListsBy(ACCOUNT_ID)
			.validateWatchListsContain(WATCHLIST_ID1,NAME1)
			.validateWatchListsContain(WATCHLIST_ID1,NAME1)
			.validateWatchListsContain(WATCHLIST_ID1,NAME1);
	}

	@Autowired
	private WatchListDAO testObject;
	
	class TestRunner {

		WatchListDTO dtoContainer;
		List<WatchListDTO> dtos;
		
		public TestRunner create(String accountId, String watchListId, String name) {
			WatchListDTO dto = WatchListDTO.builder()
									.accountId(accountId)
									.watchListId(watchListId)
									.name(name)
									.build();
			testObject.createOrUpdate(dto);
			return this;
		}

		public TestRunner delete(String accountId, String watchListId) {
			WatchListDTO dto = WatchListDTO.builder()
					.accountId(accountId)
					.watchListId(watchListId)
					.build();
			testObject.delete(dto);
			return this;
		}

		public TestRunner validate(String accountId, String watchlistId, String name) {
			assertEquals(accountId, dtoContainer.getAccountId());
			assertEquals(watchlistId, dtoContainer.getWatchListId());
			assertEquals(name, dtoContainer.getName());
			return this;
		}

		public TestRunner validateWatchListsContain(String watchlistId1,
				String name1) {
			assertTrue(dtos
				.stream()
				.filter(x -> x.getWatchListId().equals(watchlistId1) && x.getName().equals(name1))
				.findFirst().isPresent());
			return this;
		}

		public TestRunner getWatchListsBy(String accountId) {
			dtos = testObject.getWatchLists(accountId);
			return this;
		}


		public TestRunner get(String accountId, String watchListId) {
			dtoContainer = testObject.get(accountId, watchListId);
			return this;
		}
	}
	
}
