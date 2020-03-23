package io.marvellab.qtbot.command;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
public abstract class QtBotCommand extends BotCommand {

    QtBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public final void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.debug("Executing '{}' command per user [name='{}'; id='{}'] request", getCommandIdentifier(), user.getUserName(), user.getId());

        try {
            log.debug("Sending message to user [name='{}'; id='{}']", user.getUserName(), user.getId());
            absSender.execute(generateMessage(absSender, user, chat, strings));
        } catch (TelegramApiException e) {
            log.error("Failed to send message to user [name='{}'; id='{}']", user.getUserName(), user.getId(), e);
        }
    }

    public abstract SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings);
}
