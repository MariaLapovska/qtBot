package io.marvellab.qtbot.api.giphy;

import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;
import io.marvellab.qtbot.api.giphy.dto.RandomGifResponse;

import java.util.Map;

public interface GiphyClient {

    @RequestLine("GET /v1/gifs/random")
    @Headers("Accept: application/json")
    RandomGifResponse getRandomGif(@QueryMap Map<String, Object> params);
}
