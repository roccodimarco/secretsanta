package com.github.warabak.handlers;

import java.util.List;

import com.github.warabak.exceptions.GroupNotFoundException;
import com.github.warabak.services.UserRegistrationService;
import com.github.warabak.services.beans.User;
import com.pengrad.telegrambot.model.Update;

public class ListMembersCommandHandler extends CommandHandler {
	
	private static final String COMMAND = "/list";
	
	private final UserRegistrationService userRegistrationService;

	public ListMembersCommandHandler(final CommandHandler nextHandler, final UserRegistrationService userRegistrationService) {
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
				return "You must provide a group ID in order to list members. Please see the /help documentation for syntax.";
			}
			
			final String groupId = arguments[1];
			
			try {
				final List<User> users = userRegistrationService.listUsers(groupId);

				if(users.isEmpty()) { return "There aren't any members in this group yet."; }
				
				final StringBuilder sb = new StringBuilder();
				sb.append("Here is a list of all users :\n");
				
				for (final User user : users) {
					sb.append(user.firstName);
					sb.append(" ");
					sb.append(user.lastName);
					sb.append("\n");
				}
				
				return sb.toString();
				
			} catch (final GroupNotFoundException e) {
				return "Sorry, but I could not find that group. Please try again with a valid group ID.";
			}
		}
		
		return nextHandler.execute(update);
	}

}
