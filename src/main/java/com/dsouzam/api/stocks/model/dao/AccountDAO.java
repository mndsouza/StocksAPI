package com.dsouzam.api.stocks.model.dao;

import com.dsouzam.api.stocks.model.dto.AccountDTO;

public interface AccountDAO {

	public AccountDTO getAccountByEmailId(String emailId);
	public AccountDTO getAccountById(String accountId);
	public void createOrUpdateAccount(AccountDTO accountDTO);

}
