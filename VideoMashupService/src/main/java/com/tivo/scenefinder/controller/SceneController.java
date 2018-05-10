package com.tivo.scenefinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.tivo.scenefinder.beans.SceneMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SceneController {

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/findScene")
    public String movieSearch(@RequestParam("movieName") String movieName, @RequestParam("category") String category){

        SceneMetadata scene = new SceneMetadata();

        String response = null;

        try {
            response = objectMapper.writeValueAsString(scene);
        }catch(JsonProcessingException e) {

        }
        return response;
    }
}
