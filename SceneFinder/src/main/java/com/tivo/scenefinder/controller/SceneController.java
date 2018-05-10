package com.tivo.scenefinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.tivo.scenefinder.beans.ClipSearchRequest;
import com.tivo.scenefinder.beans.SceneMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SceneController {

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value="/findScene", method = RequestMethod.POST)
    public String movieSearch(@RequestBody ClipSearchRequest request){

        SceneMetadata scene = new SceneMetadata();

        String response = null;

        try {
            response = objectMapper.writeValueAsString(scene);
        }catch(JsonProcessingException e) {

        }
        return response;
    }
}
