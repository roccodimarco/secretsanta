package com.github.warabak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageOffsetCounter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageOffsetCounter.class);

	public Integer offset = null;

	public void increment(Integer updateId) {
		if(offset == null) offset = updateId;
		offset++;
		
		LOGGER.info("Updating message offset to {}", offset);
	}
}
