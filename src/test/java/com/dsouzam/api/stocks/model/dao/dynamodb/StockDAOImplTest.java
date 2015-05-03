package com.dsouzam.api.stocks.model.dao.dynamodb;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.dsouzam.api.stocks.config.RootConfig;
import com.dsouzam.api.stocks.model.dao.StockDAO;
import com.dsouzam.api.stocks.model.dao.exceptions.ItemNotFoundException;
import com.dsouzam.api.stocks.model.dto.StockDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class ,loader=AnnotationConfigContextLoader.class)
public class StockDAOImplTest {
	private static final String WL_ID1 = "wl_id";
	private static final String SYMBOL1 = "sym1";
	private static final String WL_ID2 = "wl_id2";
	private static final String SYMBOL2 = "sym2";
	private static final DateTime PD_1 = DateTime.now();
	private static final BigDecimal PP_1 = new BigDecimal("100.36");
	private static final BigDecimal PP_2 = new BigDecimal("75.34");
	private static final DateTime PD_2 = DateTime.now().minus(Period.years(1));
	

	@After
	public void tearDown() {
		new TestRunner()
			.delete(WL_ID1, SYMBOL1)
			.delete(WL_ID2, SYMBOL2);
	}
	
	@Test
	public void getShouldFetchStock() {
		new TestRunner()
			.create(WL_ID1, SYMBOL1, PD_1, PP_1)
			.get(WL_ID1, SYMBOL1)
			.validate(WL_ID1, SYMBOL1, PD_1, PP_1);
}

	@Test(expected = ItemNotFoundException.class)
	public void getShouldThrowExceptionIfStockNotPresent() {
		new TestRunner()
			.get(WL_ID1, SYMBOL1);
	}

	@Test
	public void createOrUpdateShouldCreateStock() {
		getShouldFetchStock();
	}

	@Test
	public void createOrUpdateShouldUpdateExistingStock() {
		new TestRunner()
			.create(WL_ID1, SYMBOL1, PD_1, PP_1)
			.create(WL_ID1, SYMBOL1, PD_2, PP_2)
			.get(WL_ID1, SYMBOL1)
			.validate(WL_ID1, SYMBOL1, PD_2, PP_2);
	}

	@Test(expected=ItemNotFoundException.class)
	public void deleteShouldRemoveExistingStock() {
		new TestRunner()
			.create(WL_ID1, SYMBOL1, PD_1, PP_1)
			.get(WL_ID1, SYMBOL1)
			.validate(WL_ID1, SYMBOL1, PD_1, PP_1)
			.delete(WL_ID1, SYMBOL1)
			.get(WL_ID1, SYMBOL1);
		
	}

	@Test
	public void getStocksShouldRetrieveAllStocksInWatchList() {
		new TestRunner()
			.create(WL_ID1, SYMBOL1, PD_1, PP_1)
			.create(WL_ID1, SYMBOL2, PD_2, PP_2)
			.getStocksBy(WL_ID1)
			.validateStocksContain(SYMBOL1, PD_1, PP_1)
			.validateStocksContain(SYMBOL2, PD_2, PP_2);
	}

	@Autowired
	private StockDAO testObject;
	
	class TestRunner {

		StockDTO dtoContainer;
		List<StockDTO> dtos;
		
		public TestRunner create(String watchListId, String symbol, DateTime purchaseDate, BigDecimal purchasePrice) {
			StockDTO dto = StockDTO.builder()
									.watchListId(watchListId)
									.symbol(symbol)
									.purchaseDate(purchaseDate)
									.purchasePrice(purchasePrice)
									.build();
			testObject.createOrUpdate(dto);
			return this;
		}

		public TestRunner delete(String watchListId, String symbol) {
			StockDTO dto = StockDTO.builder()
					.watchListId(watchListId)
					.symbol(symbol)
					.build();
			testObject.delete(dto);
			return this;
		}

		public TestRunner validate(String watchlistId, String symbol, DateTime purchaseDate, BigDecimal purchasePrice) {
			assertEquals(watchlistId, dtoContainer.getWatchListId());
			assertEquals(symbol, dtoContainer.getSymbol());
			assertEquals(purchaseDate.toString(), dtoContainer.getPurchaseDate());
			assertEquals(purchasePrice, dtoContainer.getPurchasePrice());
			return this;
		}

		public TestRunner validateStocksContain(String symbol, DateTime purchaseDate, BigDecimal purchasePrice) {
			assertTrue(dtos
				.stream()
				.filter(x -> x.getSymbol().equals(symbol) && x.getPurchaseDate().equals(purchaseDate.toString()) && x.getPurchasePrice().equals(purchasePrice))
				.findFirst().isPresent());
			return this;
		}

		public TestRunner getStocksBy(String watchListId) {
			dtos = testObject.get(watchListId);
			return this;
		}


		public TestRunner get(String watchListId, String symbol) {
			dtoContainer = testObject.get(watchListId, symbol);
			return this;
		}
	}

}
