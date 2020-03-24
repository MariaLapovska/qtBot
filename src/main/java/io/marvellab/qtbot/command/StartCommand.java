package io.marvellab.qtbot.command;

import io.marvellab.qtbot.message.Messages;
import io.marvellab.qtbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class StartCommand extends QtBotCommand {

    public StartCommand() {
        super("start", "Start using this bot\n");
    }

    @Override
    public SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings) {
        Messages messages = BotUtils.getUserLocale(user, Messages.class);

        return new SendMessage()
                .setChatId(chat.getId().toString())
                .setText(messages.startMessage(user.getFirstName()));
    }
}
