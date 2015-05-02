package com.dsouzam.api.stocks.model;

import java.util.List;

import com.dsouzam.api.stocks.model.data.AccountData;
import com.dsouzam.api.stocks.model.data.NamedURI;

public class Account {

	private AccountData accountData;
	private List<NamedURI> watchLists;
	
	public Account(AccountData accountData) {
		this.accountData = accountData;
	}
	
	public String getName() {
		return accountData.getName();
	}
	
	public String getURI() {
		return String.format("/account/%s/", accountData.getAccountId());
	}
	
	public String emailId() {
		return accountData.getEmailId();
	}
	
	public List<NamedURI> getWatchLists() {
		return watchLists;
	}
}
