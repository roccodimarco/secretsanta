package com.github.warabak.handlers;

import com.pengrad.telegrambot.model.Update;

public class BotInvitedCommandHandler extends CommandHandler {

	private final Integer botId;

	public BotInvitedCommandHandler(final CommandHandler nextHandler, final Integer botId) {
		super(nextHandler);
		this.botId = botId;
	}

	@Override
	public String execute(final Update update) {
		if (update == null) return nextHandler.execute(update);
		if (update.message() == null) return nextHandler.execute(update);
		if (update.message().newChatParticipant() == null) return nextHandler.execute(update);
		if (update.message().newChatParticipant().id().equals(botId)) return HelpCommandHandler.HELP_MESSAGE;

		return nextHandler.execute(update);
	}

}
