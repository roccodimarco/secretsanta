package com.github.warabak;

import com.pengrad.telegrambot.TelegramBot;

public enum BotCommandEnum {
	
	HELP(new HelpCommand()),
	UNKNOWN(new UnknownCommand()),
	CREATE(new CreateCommand());
	
	public final BotCommand botCommand;

	private BotCommandEnum(BotCommand botCommand) {
		this.botCommand = botCommand;
	}
	
	public interface BotCommand {
		void execute(TelegramBot bot, Long chatId);
	}
	
	public static class HelpCommand implements BotCommand {
		public static final String HELP_MESSAGE = 
				"Hello, I'm Secret Santa Bot. I'm here to help you create a secret santa for your group chat.\n\n"
				+ "You can control me by sending these commands: \n\n"
				+ "/create - create a new secret santa group";

		public void execute(final TelegramBot bot, final Long chatId) {
			bot.sendMessage(chatId, HELP_MESSAGE);
		}
	}
	
	public static class UnknownCommand implements BotCommand {
		public static final String UNKNOWN_MESSAGE = 
				"Sorry, I didn't understand that command. You can enter /help to see a list of supported commands.";

		public void execute(final TelegramBot bot, final Long chatId) {
			bot.sendMessage(chatId, UNKNOWN_MESSAGE);
		}
	}
	
	public static class CreateCommand implements BotCommand {
		public static final String CREATE_MESSAGE = 
				"This is currently being developed. It will be the command responsible for creating a new secret santa group.";

		public void execute(final TelegramBot bot, final Long chatId) {
			bot.sendMessage(chatId, CREATE_MESSAGE);
		}
	}
	
}
