package com.github.warabak.handlers;

import com.github.warabak.exceptions.GroupNotFoundException;
import com.github.warabak.services.UserRegistrationService;
import com.github.warabak.services.beans.User;
import com.pengrad.telegrambot.model.Update;

public class RegisterMemberCommandHandler extends CommandHandler {
	
	private static final String COMMAND = "/register";
	
	public static final String REGISTER_MESSAGE = 
			"You've successfully registered for secret santa as %s %s.\n" +
			"Remember : this is the name your secret santa will see.\n" +
			"If you'd prefer a different name, /unregister yourself and update your Telegram settings";
	
	private final UserRegistrationService userRegistrationService;

	public RegisterMemberCommandHandler(final CommandHandler nextHandler, final UserRegistrationService userRegistrationService) {
		super(nextHandler);
		this.userRegistrationService = userRegistrationService;
	}

	@Override
	public String execute(final Update update) {
		
		if (update == null) return nextHandler.execute(update);
		if (update.message() == null) return nextHandler.execute(update);
		if (update.message().text() == null) return nextHandler.execute(update);
		
		final String requestedCommand = update.message().text().trim();
		
		if (requestedCommand.toLowerCase().startsWith(COMMAND)) {
			
			final String[] arguments = requestedCommand.split("\\s+");
			
			// Just use the default group ID
			if(arguments.length != 2) {
				return "You must provide a group ID in order to register. Please see the /help documentation for syntax.";
			}
			
			final String groupId = arguments[1];
			
			final User user = new User(
					update.message().from().id(), 
					update.message().from().firstName(), 
					update.message().from().lastName());
			
			try {
				userRegistrationService.register(groupId, user);
			} catch (final GroupNotFoundException e) {
				return "Sorry, but I could not find that group. Please try again with a valid group ID.";
			}
			
			return String.format(REGISTER_MESSAGE, user.firstName, user.lastName);
		}
		
		return nextHandler.execute(update);
	}

}
