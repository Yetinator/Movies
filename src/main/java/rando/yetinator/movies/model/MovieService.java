package rando.yetinator.movies.model;



import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MovieService {
	
	public String testSomething(String... params){
		//from youtube https://www.youtube.com/watch?v=lkbclq2nyfk
		try {
			//create client object
			OkHttpClient client = new OkHttpClient();
			
			//define request being sent
			RequestBody postData = new FormBody.Builder()
					.add("type", "json")
					.build();
			
			
			
			okhttp3.Request request = new okhttp3.Request.Builder()
					.url("https://api.themoviedb.org/3/movie/550?api_key=e34f926e66fa7ffcaaea86c905cf10de")
					.get()
					.build();
			
			//transport request wait for response
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			
			
			return result;
			
			
			
		}catch(Exception e){
			System.out.println("waldo");
			return null;
		}
	}

}
