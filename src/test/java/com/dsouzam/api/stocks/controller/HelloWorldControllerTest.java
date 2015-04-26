package com.dsouzam.api.stocks.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalDividendQuote;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class HelloWorldControllerTest {

	@Test
	public void testHomePage() throws Exception {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, -1); // from 1 year ago
		 
		Stock google = YahooFinance.get("GE");
		List<HistoricalDividendQuote> googleHistQuotes = google.getDividendHistory(from, to);

		googleHistQuotes.stream().forEach((quote)->{quote.getPayout();});
	}

}
