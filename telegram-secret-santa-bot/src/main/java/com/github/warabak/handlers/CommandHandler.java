package com.github.warabak.handlers;

import com.pengrad.telegrambot.model.Update;

public abstract class CommandHandler {

	protected final CommandHandler nextHandler;

	public CommandHandler(CommandHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public abstract String execute(Update update);

}
