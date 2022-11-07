package com.ginshari.youtubeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ginshari.youtubeapi.models.VideoModel;
import com.ginshari.youtubeapi.services.VideoService;

@RestController
public class MainController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public String index() {
        return "root";
    }

    @GetMapping("/video")
    public VideoModel video(@RequestParam(name = "id") String id) {
        return videoService.getVideo(id);
    }
}

