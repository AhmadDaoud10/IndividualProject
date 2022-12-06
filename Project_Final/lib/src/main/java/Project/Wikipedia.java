package Project;

import java.util.ArrayList;
import org.json.JSONArray;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//twitter class has comments and notes to further illustrate api calls 


public class Wikipedia {
	
	public String args;
	public String nameSpace;
	public String baseApi;
	public int limit;
	public ArrayList<String> apiResult;
	
	//constructor to initialize the class with values and to inita=ialize list to avoid null object
	public Wikipedia(String args)
	{
		this.args = args;
		this.baseApi = "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=";
		this.nameSpace = "&namespace=0&";
		this.limit = 10;
		this.apiResult = new ArrayList<String>();
	}

	//private since it never gets called outside this class, method that calls api
	private ArrayList<String> RunApi()
	{
		HttpURLConnection connection = null;
		 try {
			    //Create connection
			    URL url = new URL(baseApi + args.replace(" ", "%20") + nameSpace + "limt=" + limit);
			    connection = (HttpURLConnection) url.openConnection();
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
			    connection.setRequestProperty("Content-Language", "en-US");  

			    connection.setUseCaches(false);
			    connection.setDoOutput(true);

			    //Send request
			    DataOutputStream wr = new DataOutputStream (
			        connection.getOutputStream());
			    wr.close();

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
			    
			    //need to manipulate response a little bit because wikipedia api does not return proper JSON format, so would break otherwise
			    int l = args.length();
				String q = "["+ addChar(response.toString(),l+3);
				
				JSONArray jsonArray = new JSONArray(q);

				for (int i=0; i<jsonArray.length(); i++) {
				    JSONArray element = jsonArray.getJSONArray(i);
				    apiResult.add(element.toString().replace('[', ' ').replace(']', ' '));
				}

				return apiResult;	
			  } catch (Exception e) {
			    e.printStackTrace();
			    return null;
			  } finally {
			    if (connection != null) {
			      connection.disconnect();
			    }
			  }
			
		
					
	}
	public String getRelevantArticles()
	{
		try {
			String articles = this.RunApi().get(3);
			if(articles.isBlank())
			{
				return "There are 0 relevant articles, sorry!";
			}
			else
			{
				StringBuilder sb = new StringBuilder(articles);
	
			    for (int i = 0; i < sb.length(); i++) {
			        if (sb.charAt(i) == ',') {
			            sb.insert(i + 1, ' ');
			        }
			    }
	
			    return sb.toString().trim();
			}
			
		}
		catch(Exception e)
		{
			throw e;	
		}
	}
	
	public String getSearchSuggestions()
	{
		try
		{
			String suggestions = this.RunApi().get(1);	
			if(suggestions.isBlank())
			{
				return "There are 0 relevant suggestions, sorry!";
			}
			else
			{
				return suggestions;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
			
	}
	
	private String addChar(String str, int position) {
	    StringBuilder sb = new StringBuilder(str);
	    sb.insert(position, ']');
	    return sb.toString();
	}
}
