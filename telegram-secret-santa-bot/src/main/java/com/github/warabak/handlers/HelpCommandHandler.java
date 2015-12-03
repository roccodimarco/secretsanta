package com.github.warabak.handlers;

import com.pengrad.telegrambot.model.Update;

public class HelpCommandHandler extends CommandHandler {

	private static final String COMMAND = "/help";

	public static final String HELP_MESSAGE = 
			"Hello, I'm Secret Santa Bot. I'm here to help you create a secret santa for your group chat!\n\n"
					
				+ "You can control me by sending these commands:\n\n"
				
				+ "/create\n"
				+ "description : create a new secret santa group\n\n"
				
				+ "/register [group ID]\n"
				+ "description : register yourself with a secret santa\n"
				+ "usage : /register 1234abcd\n\n"
				
				+ "/unregister [group ID]\n"
				+ "description : unregister yourself from a secret santa\n"
				+ "usage : /unregister 1234abcd\n\n"
				
				+ "/list [group ID]\n"
				+ "description : lists all users within a group\n"
				+ "usage : /list 1234abcd";

	public HelpCommandHandler(final CommandHandler nextHandler) {
		super(nextHandler);
	}

	@Override
	public String execute(final Update update) {
		if (update == null) return nextHandler.execute(update);
		if (update.message() == null) return nextHandler.execute(update);
		if (update.message().text() == null) return nextHandler.execute(update);
		if (COMMAND.equals(update.message().text().toLowerCase())) return HELP_MESSAGE;

		return nextHandler.execute(update);
	}

}
