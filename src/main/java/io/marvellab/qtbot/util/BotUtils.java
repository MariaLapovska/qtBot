package io.marvellab.qtbot.util;

import com.github.rodionmoiseev.c10n.C10N;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class BotUtils { //TODO: store message objects in memory, to avoid creation

    private static final String DEFAULT_LANGUAGE_CODE = "en";
    private static final Set<String> SUPPORTED_LANGUAGES = new HashSet<>(Arrays.asList(DEFAULT_LANGUAGE_CODE, "ru"));

    private BotUtils() {
    }

    public static <T> T getUserLocale(User user, Class<T> messageClass) {
        String languageCode = user.getLanguageCode();

        if (languageCode == null || !SUPPORTED_LANGUAGES.contains(languageCode)) {
            languageCode = DEFAULT_LANGUAGE_CODE;
        }

        return C10N.get(messageClass, new Locale(languageCode));
    }
}
