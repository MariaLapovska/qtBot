package io.marvellab.qtbot.response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.marvellab.qtbot.api.giphy.GiphyClient;
import io.marvellab.qtbot.message.Buttons;
import io.marvellab.qtbot.message.Messages;
import io.marvellab.qtbot.util.BotUtils;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class AnswerProcessor {

    private static final String GIF_URL_PATTERN = "https://media.giphy.com/media/%s/giphy.gif";

    private GiphyClient giphyClient;

    public AnswerProcessor() {
        giphyClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(new ObjectMapper()
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)))
                .logger(new Slf4jLogger(GiphyClient.class))
                .logLevel(Logger.Level.BASIC)
                .target(GiphyClient.class, "https://api.giphy.com");
    }

    public void processMessage(Message message, User user, TelegramLongPollingCommandBot bot) throws TelegramApiException {
        Messages messages = BotUtils.getUserLocale(user, Messages.class);
        Buttons buttons = BotUtils.getUserLocale(user, Buttons.class);

        String tag;
        String caption;

        log.debug("Processing answer '{}' from user [name='{}'; id='{}'] request", message.getText(), user.getUserName(), user.getId());

        if (buttons.sad().equals(message.getText())) {
            tag = "corgi";
            caption = messages.sorry(user.getFirstName());
        } else if (buttons.happy().equals(message.getText())) {
            tag = "dancing";
            caption = messages.great();
        } else {
            tag = "confused";
            caption = messages.defaultAnswer(user.getFirstName());
        }

        String gifPageUrl = giphyClient.getRandomGif(getRequestParams(tag)).getUrl();
        String gifUrl = getGifUrl(gifPageUrl);

        log.debug("Sending gif {}", gifUrl);

        SendAnimation answer = new SendAnimation()
                .setChatId(message.getChatId())
                .setAnimation(gifUrl)
                .setCaption(caption);

        bot.execute(answer);
    }

    private Map<String, Object> getRequestParams(String tag) {
        Map<String, Object> queryParams = new HashMap<>();

        queryParams.put("api_key", System.getenv("GIPHY_TOKEN"));
        queryParams.put("tag", tag);

        return queryParams;
    }

    private String getGifUrl(String gifPageUrl) {
        String[] splittedUrl = gifPageUrl.split("-");
        String gifCode = splittedUrl[splittedUrl.length - 1];
        return String.format(GIF_URL_PATTERN, gifCode);
    }
}
