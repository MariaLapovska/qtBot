package io.marvellab.qtbot.command;

import io.marvellab.qtbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class StartCommand extends QtBotCommand {

    public StartCommand() {
        super("start", "start using bot\n");
    }

    @Override
    public SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings) {
        return new SendMessage()
                .setChatId(chat.getId().toString())
                .setText(formatMessageText(user));
    }

    private String formatMessageText(User user) {
        return String.format("Hi, %s! Thanks for using this bot! It's still under construction, but will be ready soon :)", BotUtils.getUserName(user));
    }
}
