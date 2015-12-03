package com.github.warabak.handlers;

import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;

// Terminal handler
@Component
public class UnknownCommandHandler extends CommandHandler {

	public static final String UNKNOWN_MESSAGE =
			"Sorry, I didn't understand that command. "
			+ "You can enter /help to see a list of supported commands.";

	public UnknownCommandHandler() {
		super(null);
	}

	@Override
	public String execute(final Update update) {
		return UNKNOWN_MESSAGE;
	}

}
