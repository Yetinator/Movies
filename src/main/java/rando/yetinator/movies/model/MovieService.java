package rando.yetinator.movies.model;



import java.sql.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.boot.json.JsonParser;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rando.yetinator.movies.ConfigData;

public class MovieService {
	
	//Responses from API (relevant)
	protected Boolean adult;
	protected String backdrop_path;
	//int budget;
	protected int[] genreIds; //genre ids and also titles
	protected String genreNames[];
	protected String homepage;
	protected int id;//tmbd id
	//String imdb_id;
	//String original_language;
	//String original_title;
	protected String overview; // yes please
	//int popularity;
	protected String poster_path;
	//production companies
	//production countries
	protected String release_date;
	//int revenue;
	protected int runtime;
	//spoken languages
	protected String status;//released or not?  
	//String tagline;
	protected String title;
	//boolean video
	//vote average
	//vote count
	ConfigData data = new ConfigData();//creates a data object to reference the api_key
	String APIKey = data.getApiKey();
	
	
	
	
	
	
	
	
	

	/**
	 * @param backdrop_path
	 * @param homepage
	 * @param id
	 * @param overview
	 * @param poster_path
	 * @param release_date
	 * @param runtime
	 * @param status
	 * @param title
	 */
	public MovieService(String movieName) {
		//TODO - choose a movie to use (maybe invoke the other constructor based on movie id)
		String JsonInput = accessDbByName(movieName);
		
		JsonParser parser = new JsonJsonParser();
		
		//JSONObject implementation
		JSONObject jsonObject = new JSONObject(JsonInput);
		JSONArray Objects = (JSONArray) jsonObject.get("results");
		JSONObject firstObject= (JSONObject) Objects.get(0);
		
		Object results = (JSONObject) jsonObject.query("results");
		
		
		Map obj = parser.parseMap(JsonInput);

		this.backdrop_path = obj.get("backdrop_path").toString();
		this.homepage = obj.get("homepage").toString();
		this.id = Integer.parseInt(obj.get("id").toString());
		this.overview = obj.get("overview").toString();
		this.poster_path = obj.get("poster_path").toString();
		this.release_date = obj.get("release_date").toString();
		this.runtime = Integer.parseInt(obj.get("runtime").toString());
		this.status = obj.get("status").toString();
		this.title = obj.get("title").toString();
		this.adult = (Boolean) obj.get("adult");
		//setGenre();
	}
	
	
	/*
	 * Constructor using movieId instead of movieName - This Works!
	 */
	public MovieService(int movieId) {
		
		String JsonInput = accessDbById(movieId);
		
		JsonParser parser = new JsonJsonParser();
		
		//JSONObject implementation
		JSONObject obj = new JSONObject(JsonInput);
		
		//System.out.println(obj.get("results"));
		
		
		this.backdrop_path = obj.get("backdrop_path").toString();
		this.homepage = obj.get("homepage").toString();
		this.id = Integer.parseInt(obj.get("id").toString());
		this.overview = obj.get("overview").toString();
		this.poster_path = obj.get("poster_path").toString();
		this.release_date = obj.get("release_date").toString();
		this.runtime = Integer.parseInt(obj.get("runtime").toString());
		this.status = obj.get("status").toString();
		this.title = obj.get("title").toString();
		this.adult = (Boolean) obj.get("adult");
		setGenre(obj);
		
	}
	
	protected void setGenre(JSONObject obj){
		//creates the genre settings for the constructor for both id and title
		//parser setup
		JsonParser parser = new JsonJsonParser();
		//parse genres
		JSONArray jsonGenreArray = obj.getJSONArray("genres");
		this.genreIds = new int[jsonGenreArray.length()];
		this.genreNames = new String[jsonGenreArray.length()];
		//JSONObject t = jsonGenreArray.getJSONObject(0);
		for(int i = 0; i < jsonGenreArray.length(); i ++){
			
			JSONObject JSONGenreObject = jsonGenreArray.getJSONObject(i);
			//name and id here
			int id = (int) JSONGenreObject.get("id");
			String name = JSONGenreObject.get("name").toString();
			//please ad id to this.int[] genreIds;
			this.genreIds[i] = id;
			this.genreNames[i] = name;
			
		}
//		
//		JSONArray JSONGenreIds = (JSONArray) JSONGenreObject.get("id");
//		System.out.println(JSONGenreIds);
//		//JSONObject jsonObject = new JSONObject(JsonInput);
//		//JSONArray Objects = (JSONArray) jsonObject.get("results");
//		
//		
//		//Map genreObj = parser.parseMap(JSONGenreString);
//		System.out.println(JSONGenreIds.toString());
//		this.genreIds = new int[JSONGenreIds.length()];
//		for(int i = 0; i < genreIds.length; i++){
//				//Integer.parseInt(genreObj.get("id"));
//				this.genreIds[i] = (int) JSONGenreIds.get(i);
//				System.out.println(JSONGenreIds.get(i));
//		}
//		System.out.println("setGenre says: " + this.genreIds.toString());
		
	}
	
	
	/**
	 * @return the backdrop_path
	 */
	public String getBackdrop_path() {
		return backdrop_path;
	}


	/**
	 * @param backdrop_path the backdrop_path to set
	 */
	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}





	/**
	 * @return the homepage
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage the homepage to set
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return the id
	 */
	@Id
	public int getId() {
		return id;
	}
	public boolean getAdult() {
		return adult;
	}
	
	public int[] getGenreIds(){
		return genreIds;
	}
	
	public String[] getGenreNames(){
		return genreNames;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the overview
	 */
	public String getOverview() {
		return overview;
	}

	/**
	 * @param overview the overview to set
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}

	/**
	 * @return the poster_path
	 */
	public String getPoster_path() {
		return poster_path;
	}

	/**
	 * @param poster_path the poster_path to set
	 */
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	/**
	 * @return the release_date
	 */
	public String getRelease_date() {
		return release_date;
	}

	/**
	 * @param release_date the release_date to set
	 */
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	/**
	 * @return the runtime
	 */
	public int getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	//Test function
	/*
	public void parseSomething(String JsonString){
		JsonParser parser = new JsonJsonParser();
		
		Map obj = parser.parseMap(JsonString);
        //JSONArray array = (JSONArray)obj;
			
        System.out.println("The 2nd element of array");
        //System.out.println(array.get(1));
        System.out.println(obj.get("title"));
        System.out.println();
		
		
		
	}
	*/
	//Test function
	/*
	public static String testSomething(String... params){
		//from youtube https://www.youtube.com/watch?v=lkbclq2nyfk
		try {
			//create client object
			OkHttpClient client = new OkHttpClient();
			
			//define request being sent
			RequestBody postData = new FormBody.Builder()
					.add("type", "json")
					.build();
			
			ConfigData data = new ConfigData();
			
			okhttp3.Request request = new okhttp3.Request.Builder()
					.url("https://api.themoviedb.org/3/movie/550?api_key=" + data.getApiKey())
					.get()
					.build();
			
			//transport request wait for response
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			
			
			return result;
			
			
			
		}catch(Exception e){
			System.out.println("bad waldo");
			return null;
		}
	}
	*/
	public static int[] allMoviesOfName(String movieName){
		//setup
		String JsonInput = MovieService.accessDbByName(movieName);
		JsonParser parser = new JsonJsonParser();
		//JSONObject implementation Create an array of results called Objects
		JSONObject jsonObject = new JSONObject(JsonInput);
		JSONArray Objects = (JSONArray) jsonObject.get("results");
			
		//the Array
		int[] theList = new int[Objects.length()];
		for(int i = 0; i < Objects.length(); i++){
			//create a loop object to append
			JSONObject loopObject = Objects.getJSONObject(i);
			String idLocal = loopObject.get("id").toString();
			
			//just pass back the ids
			theList[i] = Integer.parseInt(loopObject.get("id").toString());
		}
		
		return theList;
	}
	
	protected static String accessDbByName(String movieName){
		//from youtube https://www.youtube.com/watch?v=lkbclq2nyfk
		
		ConfigData data = new ConfigData();
		String url = "https://api.themoviedb.org/3/search/movie?api_key=";
		String APIKey = data.getApiKey();
		String queryUrl = "&query=";
		String urlFinished = url + APIKey + queryUrl + movieName;
		try {
			//create client object
			OkHttpClient client = new OkHttpClient();
			
			//define request being sent
			RequestBody postData = new FormBody.Builder()
					.add("type", "json")
					.build();
			okhttp3.Request request = new okhttp3.Request.Builder()
					.url(urlFinished)
					.get()
					.build();
			//transport request wait for response
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			return result;
		}catch(Exception e){
			System.out.println("There was a problem accessing JSON Data by name in MovieService.");
			return null;
		}
	}
	
	protected String accessDbById(int movieId){
		//from youtube https://www.youtube.com/watch?v=lkbclq2nyfk
		
		String url = "https://api.themoviedb.org/3/movie/";
		String APIKey = data.getApiKey();
		String midurl = "?api_key=";
		String urlFinished = url +  movieId + midurl + APIKey;
		try {
			//create client object
			OkHttpClient client = new OkHttpClient();
			
			//define request being sent
			RequestBody postData = new FormBody.Builder()
					.add("type", "json")
					.build();
			okhttp3.Request request = new okhttp3.Request.Builder()
					.url(urlFinished)
					.get()
					.build();
			
			//transport request wait for response
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			return result;
		}catch(Exception e){
			System.out.println("bad waldo");
			return null;
		}
	}

}
