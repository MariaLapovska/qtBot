package io.marvellab.qtbot.util;

import org.telegram.telegrambots.meta.api.objects.User;

public final class BotUtils {

    private BotUtils() {
    }

    public static String getUserName(User user) {
        return user.getUserName() == null ? user.getFirstName() : user.getUserName();
    }
}
