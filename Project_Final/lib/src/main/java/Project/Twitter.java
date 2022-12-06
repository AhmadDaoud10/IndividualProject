package Project;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.google.gson.Gson;



public class Twitter {
	
	public String baseUrl;
	public String username;
	public String token; //should store in environment variable not in code
	public TwitterUser user;
	public Tweet tweet;
	
	//constructor to initialize class
	public Twitter(String username)
	{
		this.username = username;
		this.baseUrl = "https://api.twitter.com/2/users/";
		this.token = "AAAAAAAAAAAAAAAAAAAAAKXijwEAAAAATFyvIXgknxmxGbEKcy9%2FUjodlNU%3DGBshxKZSyxkZAbtrxCXWl1HYC24Q0loZEfP1m38ZZLsZGK1KsX"; //move to env variable
	}
	
	private TwitterUser getUser()
	{
		 HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL(baseUrl+ "by/username/" + username);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    
		    //adds authorization header to request to authenticate api
		    connection.setRequestProperty("Authorization","Bearer "+token);
	       
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);


		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    
		    //creates string builder to build each line of response into readable string
		    StringBuilder response = new StringBuilder();
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    //done with our call so we can close connection
		    rd.close();
		   
		    //Using Gson library to parse JSON string into a Java object of our choice
		    TwitterUser user = new Gson().fromJson(response.toString(), TwitterUser.class);
		    return user;

		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
		
	
	private Tweet getUserTweets()
	{
		//Same notes as above
		 String userId = getUser().getTwitterData().getId();
		 HttpURLConnection connection = null;
		 
		  try {
		    //Create connection
		    URL url = new URL(baseUrl+ userId + "/tweets");
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    
		    //adds authorization header to request to authenticate api
		    connection.setRequestProperty("Authorization","Bearer "+token);
	       
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder();
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		  
		    Tweet tweet = new Gson().fromJson(response.toString(), Tweet.class);
		    return tweet;

		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
		
	public String getName()
	{
		try {
			String name = this.getUser().getTwitterData().getName();
			if(name == null)
			{
				return "User does not exist!";
			}
			else
			{
				return name;
			}
			
		}
		catch(Exception e)
		{
			throw e;	
		}
	}
	
	public String getLatest()
	{
		try {
			String tweet = this.getUserTweets().getTweetData().getText();
			if( tweet == null)
			{
				return "No Tweets!";
			}
			else
			{
				return tweet;
			}
			
		}
		catch(Exception e)
		{
			throw e;	
		}
	}
}
