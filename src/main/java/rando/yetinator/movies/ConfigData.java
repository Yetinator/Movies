package rando.yetinator.movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigData {
	private String api_key;
	private String image_base_url;
	private String image_secure_base_url;
	private List<String> backdrop_sizes= new ArrayList();
	
	public ConfigData(){
		api_key = "e34f926e66fa7ffcaaea86c905cf10de";
		image_base_url = "https://image.tmdb.org/t/p/";
		backdrop_sizes.add("w300");
		backdrop_sizes.add("w780");
		backdrop_sizes.add("w1280");
		backdrop_sizes.add("original");
				
	}
	
	public String getImageBaseURL(int imageSize) {
		//returns base url
		if(imageSize < backdrop_sizes.size())
			return image_base_url + backdrop_sizes.get(imageSize);
		else
			return image_base_url + backdrop_sizes.get(1);
	}
	
}
