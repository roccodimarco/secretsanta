package com.github.warabak;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.warabak.BotCommandEnum2.HelpCommand;
import com.github.warabak.BotCommandEnum2.UnknownCommand;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class SecretSantaBotTest {
	
	private static final Integer DUMMY_BOT_ID = 1;
	private static final Long DUMMY_CHAT_ID = 123l;
	
	@Mock private TelegramBot bot;
	
	private final BotCommandFactory underTest = new BotCommandFactory(DUMMY_BOT_ID);

	@Test
	public void testHelpCommand_lowercase() throws Exception {
		final Update update = Mockito.mock(Update.class);
		final Message message = Mockito.mock(Message.class);
		
		Mockito.when(update.message()).thenReturn(message);
		Mockito.when(message.text()).thenReturn("/help");
		
		underTest.create(update).execute(bot, DUMMY_CHAT_ID);
		Mockito.verify(bot).sendMessage(DUMMY_CHAT_ID, HelpCommand.HELP_MESSAGE);
	}
	
	@Test
	public void testHelpCommand_uppercase() throws Exception {
		final Update update = Mockito.mock(Update.class);
		final Message message = Mockito.mock(Message.class);
		
		Mockito.when(update.message()).thenReturn(message);
		Mockito.when(update.message().text()).thenReturn("/HELP");

		underTest.create(update).execute(bot, DUMMY_CHAT_ID);
		Mockito.verify(bot).sendMessage(DUMMY_CHAT_ID, HelpCommand.HELP_MESSAGE);
	}
	
	@Test
	public void testHelpCommand_spaces() throws Exception {
		final Update update = Mockito.mock(Update.class);
		final Message message = Mockito.mock(Message.class);
		
		Mockito.when(update.message()).thenReturn(message);
		Mockito.when(update.message().text()).thenReturn("/ HELP  ");

		underTest.create(update).execute(bot, DUMMY_CHAT_ID);
		Mockito.verify(bot).sendMessage(DUMMY_CHAT_ID, HelpCommand.HELP_MESSAGE);
	}
	
	@Test
	public void testUnknownCommand_noLeadingSlash() throws Exception {
		final Update update = Mockito.mock(Update.class);
		final Message message = Mockito.mock(Message.class);
		
		Mockito.when(update.message()).thenReturn(message);
		Mockito.when(update.message().text()).thenReturn("help");

		underTest.create(update).execute(bot, DUMMY_CHAT_ID);
		Mockito.verify(bot).sendMessage(DUMMY_CHAT_ID, UnknownCommand.UNKNOWN_MESSAGE);
	}
	
	@Test
	public void testWelcomeMessage_matchingBotId() throws Exception {
		final Update update = Mockito.mock(Update.class);
		final Message message = Mockito.mock(Message.class);
		final User newChatParticipant = Mockito.mock(User.class);
		
		Mockito.when(update.message()).thenReturn(message);
		Mockito.when(update.message().newChatParticipant()).thenReturn(newChatParticipant);
		Mockito.when(newChatParticipant.id()).thenReturn(DUMMY_BOT_ID);
		
		underTest.create(update).execute(bot, DUMMY_CHAT_ID);
		Mockito.verify(bot).sendMessage(DUMMY_CHAT_ID, HelpCommand.HELP_MESSAGE);
	}
	
	@Test
	public void testWelcomeMessage_nonMatchingBotId() throws Exception {
		final Integer nonBotId = 99999;
		final Update update = Mockito.mock(Update.class);
		final Message message = Mockito.mock(Message.class);
		final User newChatParticipant = Mockito.mock(User.class);
		
		Mockito.when(update.message()).thenReturn(message);
		Mockito.when(update.message().newChatParticipant()).thenReturn(newChatParticipant);
		Mockito.when(newChatParticipant.id()).thenReturn(nonBotId);
		
		underTest.create(update).execute(bot, DUMMY_CHAT_ID);
		Mockito.verify(bot).sendMessage(DUMMY_CHAT_ID, UnknownCommand.UNKNOWN_MESSAGE);
	}
	
	@Test
	public void testCreateGroup() throws Exception {
		
	}
}
