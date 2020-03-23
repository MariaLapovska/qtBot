package io.marvellab.qtbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;

public class MoodCommand extends QtBotCommand {

    public MoodCommand() {
        super("mood", "Set mood for today");
    }

    @Override
    public SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings) {
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add("Happy \uD83D\uDE0A");
        keyboardRow1.add("Sad \uD83D\uDE14");
        keyboardRow1.add("In love \uD83D\uDE0D");

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add("Anxious \uD83D\uDE31");
        keyboardRow2.add("Silly \uD83D\uDE1D");
        keyboardRow2.add("Angry \uD83D\uDE20");

        return new SendMessage()
                .setChatId(chat.getId())
                .setText("What's your mood today?")
                .setReplyMarkup(new ReplyKeyboardMarkup()
                        .setKeyboard(Arrays.asList(keyboardRow1, keyboardRow2))
                        .setResizeKeyboard(true)
                        .setOneTimeKeyboard(true));
    }
}
