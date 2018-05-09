package com.tivo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AlgorithmiaClientWrapper {

	AlgorithmiaClient client;

	
	public AlgorithmiaClientWrapper() {
		client = Algorithmia.client("simiwIurKaXAoTZnzZoQZQxDu9d1");
		
	}
	
	public String sceneDetection(String input) {
		Algorithm algo = client.algo("media/SceneDetection/0.1.6");
		AlgoResponse result;
		try {
			result = algo.pipeJson(input);
			System.out.println("Scenes " + result.asJsonString());
			return result.asJsonString();
		} catch (APIException | AlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public void splitScenes(String input) {
		Algorithm algo = client.algo("zeryx/VideoSplits/1.0.1");
		AlgoResponse result;
		try {
			result = algo.pipe(input);
			System.out.println("Splits : " + result.asJsonString());
		} catch (APIException | AlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveFile(String destDir, JsonElement objects) {
		// Download the file
			String mpg_file = objects.getAsString();
			   FileOutputStream outputStream = null;
			try {
			    if (client.file(mpg_file).exists()) {
			        File localfile = client.file(mpg_file).getFile();
			        URL url = new URL(localfile.getName());
			        String saveFilePath = destDir + File.separator + localfile.getName();
			        outputStream = new FileOutputStream(new File(saveFilePath), true);
	                URLConnection con = url.openConnection();
	                InputStream inputStream = con.getInputStream();

	                byte[] buffer = new byte[4096];
	                int bytesRead = 0, bytesBuffered = 0;
	                while ((bytesRead = inputStream.read(buffer)) > -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                    bytesBuffered += bytesRead;
	                    if (bytesBuffered > 1024 * 1024) {
	                        bytesBuffered = 0;
	                        outputStream.flush();
	                    }
	                }
		           
			    } else {
			        System.out.println("Please check that your file exists");
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
			finally {
				 try {
					outputStream.flush();
					 outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
			}
	}
}
