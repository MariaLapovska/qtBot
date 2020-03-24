package io.marvellab.qtbot.api.giphy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RandomGifResponse {
    private String url;

    @JsonProperty("data")
    private void unpackNested(Map<String, Object> data) {
        this.url = (String) data.get("url");
    }
}
