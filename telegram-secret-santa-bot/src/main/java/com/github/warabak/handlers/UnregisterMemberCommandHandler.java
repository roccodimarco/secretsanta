package com.github.warabak.handlers;

import com.github.warabak.exceptions.GroupNotFoundException;
import com.github.warabak.services.UserRegistrationService;
import com.pengrad.telegrambot.model.Update;

public class UnregisterMemberCommandHandler extends CommandHandler {
	
	private static final String COMMAND = "/unregister";
	
	public static final String UNREGISTER_MESSAGE = "You've successfully unregistered from secret santa.";
	
	private final UserRegistrationService userRegistrationService;

	public UnregisterMemberCommandHandler(final CommandHandler nextHandler, final UserRegistrationService userRegistrationService) {
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
				return "You must provide a group ID in order to unregister. Please see the /help documentation for syntax.";
			}
			
			final String groupId = arguments[1];
			final Integer userId = update.message().from().id(); 
			
			try {
				userRegistrationService.unregister(groupId, userId);
			} catch (final GroupNotFoundException e) {
				return "Sorry, but I could not find that group. Please try again with a valid group ID.";
			}
			
			return UNREGISTER_MESSAGE;
		}
		
		return nextHandler.execute(update);
	}

}
