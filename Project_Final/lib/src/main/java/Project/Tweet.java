package Project;

import java.util.ArrayList;

public class Tweet {
	private ArrayList<TweetData> data;

	public TweetData getTweetData() {return data.get(0);}
	public void setTweetData(TweetData tweet) { this.data.add(0, tweet);}
}
