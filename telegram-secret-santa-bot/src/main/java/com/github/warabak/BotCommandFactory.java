package com.github.warabak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.warabak.BotCommandEnum.BotCommand;
import com.pengrad.telegrambot.model.Update;

public class BotCommandFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BotCommandFactory.class);
	
	private final Integer botId;
	
	public BotCommandFactory(final Integer botId) {
		LOGGER.debug("Initializing factory with bot ID [{}]", botId);
		this.botId = botId;
	}

	public BotCommand create(final Update update) {
		if(isInvitedToGroup(update)) {
			return BotCommandEnum.HELP.botCommand;
		}
		
		try {
			if(update.message().text().startsWith("/")) {
				return BotCommandEnum.valueOf(update.message().text().substring(1).trim().toUpperCase()).botCommand;
			} else {
				return BotCommandEnum.UNKNOWN.botCommand;
			}
		} catch (Exception e) {
			return BotCommandEnum.UNKNOWN.botCommand;
		}
	}

	private boolean isInvitedToGroup(final Update update) {
		return update.message().newChatParticipant() != null && update.message().newChatParticipant().id().equals(botId);
	}
	
}
