package com.ginshari.youtubeapi.common;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Videos;

@Component
public class MyYouTube {

    private NetHttpTransport NET_HTTP_TRANSPORT = new NetHttpTransport();

    private JacksonFactory JACKSON_FACTORY = new JacksonFactory();

    private YouTube youtube;

    public MyYouTube() {
        this.youtube = new YouTube.Builder(NET_HTTP_TRANSPORT, JACKSON_FACTORY,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {}
                }).setApplicationName("spring-youtubeapi").build();
    }

    public Videos videos() {
        return this.youtube.videos();
    }

}
