package com.github.warabak.handlers;

import com.github.warabak.exceptions.DuplicateIdException;
import com.github.warabak.services.GroupRegistrationService;
import com.pengrad.telegrambot.model.Update;

public class CreateGroupCommandHandler extends CommandHandler {
	
	private static final String COMMAND = "/create";
	
	public static final String CREATE_MESSAGE = 
			"Your new secret santa group has been created with ID : %s. "
			+ "It's important to remember the group ID in order to register members.";
	
	private final GroupRegistrationService groupRegistrationService;

	public CreateGroupCommandHandler(final CommandHandler nextHandler, final GroupRegistrationService groupRegistrationService) {
		super(nextHandler);
		this.groupRegistrationService = groupRegistrationService;
	}

	@Override
	public String execute(final Update update) {
		if (update == null) return nextHandler.execute(update);
		if (update.message() == null) return nextHandler.execute(update);
		if (update.message().text() == null) return nextHandler.execute(update);
		
		if (COMMAND.equals(update.message().text().toLowerCase())) {
			try {
				return String.format(CREATE_MESSAGE, groupRegistrationService.create());
			} catch (final DuplicateIdException e) {
				return "Secret Santa Bot could not create your group. Please try again";
			}
		}
		
		return nextHandler.execute(update);
	}

}
