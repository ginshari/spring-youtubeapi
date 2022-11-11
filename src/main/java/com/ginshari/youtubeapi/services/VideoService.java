package com.ginshari.youtubeapi.services;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginshari.youtubeapi.AppConfig;
import com.ginshari.youtubeapi.common.MyYouTube;
import com.ginshari.youtubeapi.models.VideoModel;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;


@Service
public class VideoService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private MyYouTube myYouTube;

    public VideoModel getVideo(String id) {

        try {

            YouTube.Videos.List videosListApi = myYouTube.videos().list("id,snippet");

            videosListApi.setKey(appConfig.apikey);
            videosListApi.setId(id);
            videosListApi.setFields("items(id,snippet/publishedAt,snippet/title)");

            VideoListResponse videoListResponse = videosListApi.execute();

            List<Video> videoItems = videoListResponse.getItems();

            if (videoItems != null && videoItems.size() > 0) {
                var video = videoItems.get(0);

                var videoModel = new VideoModel();
                videoModel.setId(video.getId());
                videoModel.setDate(dateTimeToString(video.getSnippet().getPublishedAt()));
                videoModel.setTitle(video.getSnippet().getTitle());

                return videoModel;
            }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return new VideoModel();

    }

    // com.google.api.client.util.DateTimeをローカルのuuuu-MM-dd形式文字列に変換する
    private String dateTimeToString(DateTime dateTime) {

        // googleのDateTimeはgetValueでエポック秒を返す
        var instant = Instant.ofEpochMilli(dateTime.getValue());
        var localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        var formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        return localDateTime.format(formatter);

    }

}
