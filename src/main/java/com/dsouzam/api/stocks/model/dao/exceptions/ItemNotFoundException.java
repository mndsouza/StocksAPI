package com.dsouzam.api.stocks.model.dao.exceptions;

import lombok.extern.log4j.Log4j;

@Log4j
public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(String message) {
		super(message);
		log.error(message);
	}
}
