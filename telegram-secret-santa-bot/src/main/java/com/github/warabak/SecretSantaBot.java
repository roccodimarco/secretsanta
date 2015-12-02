package com.github.warabak;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;

import retrofit.RetrofitError;

public class SecretSantaBot {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecretSantaBot.class);

	// Polling delay
	private static final long POLL_DELAY = TimeUnit.SECONDS.toMillis(5l);
	
	// Max polling limit
	private static final int MESSAGE_POLL_LIMIT = 100;

	// Manager for message offsets
	private MessageOffsetCounter offsetCounter = new MessageOffsetCounter();

	private final TelegramBot bot;
	private final BotCommandFactory botCommandFactory;

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException("You must provide the Telegram Bot API Token");
		}

		final String botToken = args[0];

		new SecretSantaBot(botToken).run(POLL_DELAY, MESSAGE_POLL_LIMIT);
	}

	public SecretSantaBot(final String botToken) {
		this.bot = TelegramBotAdapter.build(botToken);
		this.botCommandFactory = new BotCommandFactory(bot.getMe().user().id());
	}

	public void run(final long pollDelay, final int messagePollLimit) {
		LOGGER.info("Secret Santa Bot is online!");

		clearMessageQueue();
		
		while (true) {
			try {
				Thread.sleep(pollDelay);
			} catch (InterruptedException e) {
				throw new RuntimeException("Error pausing polling", e);
			}

			for (Update message : pollForMessages()) {
				processMessage(message);
			}
		}
	}

	private void clearMessageQueue() {
		List<Update> updates = pollForMessages();
		
		while(updates.size() > 0) {
			offsetCounter.increment(updates.get(updates.size() - 1).updateId());
			updates = pollForMessages();
		}
	}

	private List<Update> pollForMessages() {
		return bot.getUpdates(offsetCounter.offset, null, null).updates();
	}

	private void processMessage(Update update) {
		LOGGER.info("Attempting to process message [{}] : From {} {} - {}", 
				update.updateId(),
				update.message().from().firstName(), 
				update.message().from().lastName(), 
				update.message().text());

		LOGGER.debug(update.toString());
		
		try {
			botCommandFactory.create(update).execute(bot, update.message().chat().id());
		} catch (RetrofitError e) {
			LOGGER.error("Error sending message", e);
		} finally {
			// Currently no way to recover errors. Update offset and continue.
			offsetCounter.increment(update.updateId());
		}
	}
}
