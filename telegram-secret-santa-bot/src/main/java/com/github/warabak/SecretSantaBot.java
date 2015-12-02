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

	private final TelegramBot bot;
	private Integer messageUpdateOffset = null;

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException("You must provide the Telegram Bot API Token");
		}

		final String botToken = args[0];

		new SecretSantaBot(botToken).run(POLL_DELAY, MESSAGE_POLL_LIMIT);
	}

	public SecretSantaBot(final String botToken) {
		this.bot = TelegramBotAdapter.build(botToken);
	}

	public void run(final long pollDelay, final int messagePollLimit) {
		LOGGER.info("Secret Santa Bot is online!");

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

	private List<Update> pollForMessages() {
		return bot.getUpdates(messageUpdateOffset, null, null).updates();
	}

	private void processMessage(Update update) {
		// Initialize offset (one-time only)
		if (messageUpdateOffset == null) {
			messageUpdateOffset = update.updateId();
		}

		LOGGER.info("Attempting to process message [{}] : From {} {} - {}", 
				update.updateId(),
				update.message().from().firstName(), 
				update.message().from().lastName(), 
				update.message().text());

		final String msg = String.format("I'm still being developed, %s", update.message().from().lastName());

		try {
			bot.sendMessage(update.message().chat().id(), msg);
		} catch (RetrofitError e) {
			LOGGER.error("Error sending message", e);
		} finally {
			// Currently no way to recover. Update offset and continue.
			messageUpdateOffset++;
		}
	}
	
}
