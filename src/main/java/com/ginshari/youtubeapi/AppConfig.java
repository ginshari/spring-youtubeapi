package com.ginshari.youtubeapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@ConfigurationProperties(prefix = "youtube")
@Data
public class AppConfig {
    public String apikey;
}
