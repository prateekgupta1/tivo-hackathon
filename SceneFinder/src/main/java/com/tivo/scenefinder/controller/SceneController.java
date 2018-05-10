package com.tivo.scenefinder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tivo.scenefinder.beans.ClipSearchRequest;
import com.tivo.scenefinder.beans.SceneMetadata;
import com.tivo.scenefinder.wrapper.CloudinaryClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class SceneController {

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value="/findScene", method = RequestMethod.POST)
    public String movieSearch(@RequestBody ClipSearchRequest request){

        CloudinaryClientWrapper wrapper = new CloudinaryClientWrapper();
        ArrayList<SceneMetadata> scenes = wrapper.searchScenes(request.getMovieName(), request.getCategory());
        Gson g = new Gson();
        String jsonSceneList = g.toJson(scenes);
        System.out.println("scenelist "+ jsonSceneList);

        return jsonSceneList;
    }
}
