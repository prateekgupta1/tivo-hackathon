package com.tivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@SpringBootApplication
public class TivoHackathonApplication {

	public TivoHackathonApplication() {
		
	}
	
	public static void main(String[] args) {
		AlgorithmiaClientWrapper wrapper = new AlgorithmiaClientWrapper();
		String input = "{"
				 + "  \"video\": \"https://www.youtube.com/watch?v=OMgIPnCnlbQ\","
				 + "  \"detector\": \"content\","
				 + "  \"output_collection\": \"MyCollection\""
				 + "}";
		String scenes = wrapper.sceneDetection(input);
		JsonParser parser = new JsonParser();
		JsonArray jsonArray =  parser.parse(scenes).getAsJsonObject().getAsJsonArray("output_collection_videos");
		String path = "/Users/vjayaprakash/hackathonws";
		for(int i = 0; i < jsonArray.size(); i++)
		{
		      JsonElement objects = jsonArray.get(i);
		      wrapper.saveFile(path, objects);
		}

		//wrapper.splitScenes(wrapper.sceneDetection(input));
		SpringApplication.run(TivoHackathonApplication.class, args);

	}
}

