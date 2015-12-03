package com.github.warabak;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.warabak.handlers.BotInvitedCommandHandler;
import com.github.warabak.handlers.CommandHandler;
import com.github.warabak.handlers.CreateGroupCommandHandler;
import com.github.warabak.handlers.HelpCommandHandler;
import com.github.warabak.handlers.ListMembersCommandHandler;
import com.github.warabak.handlers.RegisterMemberCommandHandler;
import com.github.warabak.handlers.UnknownCommandHandler;
import com.github.warabak.handlers.UnregisterMemberCommandHandler;
import com.github.warabak.services.GroupRegistrationService;
import com.github.warabak.services.UserRegistrationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;

import retrofit.RetrofitError;

@SpringBootApplication
public class SecretSantaBot {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecretSantaBot.class);

	@Autowired
	private MessageOffsetCounter offsetCounter;

	@Autowired
	private TelegramBot bot;
	
	@Autowired
	private CommandHandler commandHandler;
	
	@Value("${app.poll.delay.ms}")
	private long pollDelay;
	
	public static void main(final String[] args) {
		SpringApplication.run(SecretSantaBot.class, args);
	}
	
	@Configuration
	public static class Config {
		
		@Value("${app.bot.token}")
		private String botToken;
		
		@Bean
		public TelegramBot telegramBot() {
			return TelegramBotAdapter.build(botToken);
		}
		
		@Bean
		public CommandHandler commandHandler() {
			return listMembersCommandHandler();
		}
		
		@Bean
		public CommandHandler unknownCommandHandler() {
			return new UnknownCommandHandler();
		}
		
		@Bean
		public CommandHandler helpCommandHandler() {
			return new HelpCommandHandler(unknownCommandHandler());
		}
		
		@Bean 
		public CommandHandler botInvitedCommandHandler() {
			return new BotInvitedCommandHandler(helpCommandHandler(), telegramBot().getMe().user().id());
		}
		
		@Bean 
		public CommandHandler createGroupCommandHandler() {
			return new CreateGroupCommandHandler(botInvitedCommandHandler(), groupRegistrationService());
		}
		
		@Bean 
		public CommandHandler registerMemberCommandHandler() {
			return new RegisterMemberCommandHandler(createGroupCommandHandler(), userRegistrationService());
		}
		
		@Bean 
		public CommandHandler unregisterMemberCommandHandler() {
			return new UnregisterMemberCommandHandler(registerMemberCommandHandler(), userRegistrationService());
		}
		
		@Bean 
		public CommandHandler listMembersCommandHandler() {
			return new ListMembersCommandHandler(unregisterMemberCommandHandler(), userRegistrationService());
		}
		
		@Bean
		public UserRegistrationService userRegistrationService() {
			return new UserRegistrationService();
		}

		@Bean
		public GroupRegistrationService groupRegistrationService() {
			return new GroupRegistrationService();
		}
		
		@Bean
		public MessageOffsetCounter messageOffsetCounter() {
			return new MessageOffsetCounter();
		}
	}
	
	@PostConstruct
	public void run() {
		LOGGER.info("Secret Santa Bot is online!");
		clearMessageQueue();

		while (true) {
			try {
				Thread.sleep(pollDelay);
			} catch (final InterruptedException e) {
				throw new RuntimeException("Error pausing polling", e);
			}

			for (final Update message : pollForMessages()) {
				processMessage(message);
			}
		}
	}

	private void clearMessageQueue() {
		List<Update> updates = pollForMessages();

		while (updates.size() > 0) {
			offsetCounter.increment(updates.get(updates.size() - 1).updateId());
			updates = pollForMessages();
		}
	}

	private List<Update> pollForMessages() {
		return bot.getUpdates(offsetCounter.offset, null, null).updates();
	}

	private void processMessage(final Update update) {
		LOGGER.info("Attempting to process message [{}]", update.message().text());
		LOGGER.debug(update.toString());

		try {
			bot.sendMessage(update.message().chat().id(), commandHandler.execute(update));
		} catch (final RetrofitError e) {
			LOGGER.error("Error sending message", e);
		} finally {
			// Currently no way to recover errors. Update offset and continue.
			offsetCounter.increment(update.updateId());
		}
	}
}
