package com.tivo.scenefinder.wrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cloudinary.*;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import com.tivo.scenefinder.beans.SceneMetadata;

public class CloudinaryClientWrapper {

    Cloudinary cloudinary;
    Api api;

    public CloudinaryClientWrapper() {
        Map config = ObjectUtils.asMap(
                "cloud_name", "dffg072sf",
                "api_key", "246958384167152",
                "api_secret", "djMTOnbmm-CYHTWyYX-yZWxcq8k");
        cloudinary = new Cloudinary(config);
        api = cloudinary.api();
    }

    public ArrayList<SceneMetadata> searchScenes(String tag, String category) {
        ArrayList<SceneMetadata> sceneList = new ArrayList<SceneMetadata>();
        try {

            String categoryst = category + "_ST";
            String categoryend = category + "_END";
            ApiResponse response2 = api.resourcesByContext(categoryst, ObjectUtils.asMap("tag", tag, "resource_type", "video", "context", "true"));
            System.out.println("**** LIST OF SCENES" + response2.toString());
            ArrayList<String> list = (ArrayList<String>) response2.get("resources");
            System.out.println("****" + list.size());
            Object[] metadata = (Object[])list.toArray();
            System.out.println(" scene : " +  metadata.length);
            for(int i=0;i<metadata.length;i++) {
                HashMap resourcesMap = (HashMap)metadata[i];
                System.out.println("Metadata : " + resourcesMap.size());
                HashMap context = (HashMap)resourcesMap.get("context");
                String url = (String)resourcesMap.get("url");
                System.out.println("url " + url);
                System.out.println("Context : " + context.toString());
                HashMap custom = (HashMap) context.get("custom");
                String stvalue = (String) custom.get(categoryst);
                System.out.println("ACTION_ST = " + stvalue);
                String endvalue = (String) custom.get(categoryend);
                System.out.println("ACTION_END = " + endvalue);
                SceneMetadata buildScene = buildScene(url, stvalue, endvalue);
                sceneList.add(buildScene);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sceneList;
    }

    private SceneMetadata buildScene(String url, String stvalue, String endvalue) {
        SceneMetadata scene = new SceneMetadata();
        StringBuilder builder = new StringBuilder();
        String movieContent = url.substring(url.lastIndexOf("/")+1);
        String alteredUrl = "http://res.cloudinary.com/dffg072sf/video/upload/";
        builder.append(alteredUrl);
        System.out.println("Altered URL1 " + builder.toString());
        builder.append("so_"+stvalue + "," + "eo_"+ endvalue + "/");
        builder.append(movieContent);
        System.out.println("Altered URL2 " + builder.toString());

        scene.setUrl(builder.toString());
        scene.setMovieTitle(movieContent);
        return scene;
    }

    public String splitScenes(String url, double startTime, double endTime)
    {
        Map clip = null;
        try {
            clip = cloudinary.uploader().upload(url,
                    ObjectUtils.asMap("resource_type", "video",
                            "transformation", new Transformation().startOffset(startTime).endOffset(endTime)));
            System.out.println("Clip MAP: " + clip.toString());
            Iterator<String> it = clip.keySet().iterator();
            while(it.hasNext())
                System.out.println("key " + it.next().toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result;
        result = clip.get("url").toString();
        System.out.println("Clip url: " + result);
        return result;
    }
}

