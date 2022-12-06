package Project;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	Useer user = new Useer();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String args = event.getMessage().getContentRaw();
		
		
		if(!event.getAuthor().isBot()&& (args.toLowerCase().contains("tweet")||args.toLowerCase().contains("twitter")))
		{
			event.getChannel().sendTyping();
			event.getChannel().sendMessage( "Give me a twitter username by using \"/tuser \" before your username and I can send you their display name and recent tweet").queue();			
		}

		if(!event.getAuthor().isBot()&& args.toLowerCase().contains("article"))
		{
			event.getChannel().sendTyping();
			event.getChannel().sendMessage( "Give me a search request by using \"/wiki \" before your search and I can send you Wikipedia Resources").queue();			
		}
		if(!event.getAuthor().isBot()&& args.toLowerCase().contains("/tuser"))
		{
			String username = StringUtils.substringAfter(args, "/tuser ");
			if(username == "" || username == null || username.contains(" "))
			{
				event.getChannel().sendTyping();
				event.getChannel().sendMessage("You need to enter a proper username!").queue();
			}
			else
			{
				Twitter twitter = new Twitter(username);
				String name = twitter.getName();
				event.getChannel().sendTyping();
				event.getChannel().sendMessage("The username has the Twitter Name of: " + name).queue();
				event.getChannel().sendTyping();
				event.getChannel().sendMessage(name +" tweeted this last: " + twitter.getLatest()).queue();
			}
		}
		if(!event.getAuthor().isBot()&&args.toLowerCase().contains("/wiki"))
		{
			String query = StringUtils.substringAfter(args, "/wiki ");
			if(query == "" || query == null)
			{
				event.getChannel().sendTyping();
				event.getChannel().sendMessage("You need to enter a proper search result!").queue();
			}
			else
			{
				Wikipedia wiki = new Wikipedia(query);
				event.getChannel().sendTyping();
				event.getChannel().sendMessage("The Search Suggestions based on your search query are: " + wiki.getSearchSuggestions()).queue();
				event.getChannel().sendTyping();
				event.getChannel().sendMessage("The Suggested Wikipedia articles based on your search query are: " + wiki.getRelevantArticles()).queue();
			}
		}
		switch (user.getStep()) {
		case 0:
			if (!event.getAuthor().isBot()) {
			if (args.toLowerCase().contains("hello")||(args.toLowerCase().contains("hey")||(args.toLowerCase().contains("hi")))&&!event.getAuthor().isBot()) {
				user.addStep();
				EmbedBuilder greeting = new EmbedBuilder();
				greeting.setTitle("⚽ Basic Information");
				greeting.setDescription("Hello! I'm your Sports Coach! I'm going to help you to be more active and choose a sport! Please"
						+ " follow the instructions since I'm not very good at understanding humans yet. Let's see what sport we can find for you! What's your name? //If you want to reset the conversation type '~reset'");
				greeting.setColor(0x42b6f5);
				event.getChannel().sendTyping();
				event.getChannel().sendMessageEmbeds(greeting.build()).queue();
				user.setName(event.getMember().getUser().getName());
				greeting.clear();
				
				}
			}
			
			break;
			
		case 1:
			if (!event.getAuthor().isBot()&&!args.contains("~")) {
				user.setName(args);
				user.addStep();
				event.getChannel().sendMessage("Hi "+user.getName()+"! How tall are you? (short, average or tall)").queue();
			}
		case 2:
			if (!event.getAuthor().isBot()) {
				if ((args.toLowerCase().contains("short"))) {
					user.setHeight("short");
					event.getChannel().sendTyping();
					user.addStep();
					event.getChannel().sendMessage("Nice! And are you skinny, fit or chubby?").queue();
					}
				
				else if ((args.toLowerCase().contains("average"))) {
					user.setHeight("average");
						event.getChannel().sendTyping();
						user.addStep();
						event.getChannel().sendMessage("I'm impressed! And are you skinny, fit or chubby?").queue();	
					}
	
				else if ((args.toLowerCase().contains("tall"))) {
					user.setHeight("tall");
					event.getChannel().sendTyping();
					user.addStep();
					event.getChannel().sendMessage("Awesome! And are you skinny, fit or chubby?").queue();
					}
				
				//else if (!args.toLowerCase().contains("tall")&&!args.toLowerCase().contains("short")&&!args.toLowerCase().contains("average"))
					//event.getChannel().sendMessage("sorry, I didn't understand you...please specify if you are 'short', 'average' or 'tall'").queue();
				}
			break;
			
		case 3:
			if (!event.getAuthor().isBot()) {
				
			if ((args.toLowerCase().contains("skinny"))) {
				user.setWeight("skinny");
				user.addStep();
				event.getChannel().sendMessage("Perfect! There are plenty of sports that you could do!").queue();
				
			}
			
			else if ((args.toLowerCase().contains("fit"))) {
				user.setWeight("fit");
				user.addStep();
				event.getChannel().sendMessage("Cool! I have some ideas then!").queue();
				
			}
			
			else if ((args.toLowerCase().contains("chubby"))) {
				user.setWeight("chubby");
				user.addStep();
				event.getChannel().sendMessage("Amazing! let's see what you could do!").queue();
				}
			else if(!(args.toLowerCase().contains("chubby")&&!(args.toLowerCase().contains("fit")&&(args.toLowerCase().contains("fit"))&&!args.contains("~"))))
				event.getChannel().sendMessage("sorry, I didn't understand you...please specify if you are 'chubby', 'fit' or 'skinny'").queue();
			}
			break;
			
		case 4:
			event.getChannel().sendMessage("Maybe you should try "+user.getRecommendation(0).getName()+" ("
					+ "type 'yes' if you like it and 'no' if you want another one)").queue();	
			user.addStep();
			break;
			
		case 5:
			
			 if (args.toLowerCase().contains("yes")&&!event.getAuthor().isBot()) {
				 	user.setSport(user.getRecommendation(0));
				 	user.addStep();
					event.getChannel().sendMessage("Congratulations for choosing "+ user.getRecommendation(0).getName()+" "+user.getName()+"! Let's get working on your training routine!").queue();
					event.getChannel().sendMessage("How much time do you have available to train? (times per week as a number)").queue();
			 	}
				else if (args.toLowerCase().contains("no")&&!event.getAuthor().isBot()) { 
					user.removeFirstRecommendation();
					user.removeStep();
					event.getChannel().sendMessage("mmmh... let's see").queue();
					}
				else if (!args.toLowerCase().contains("yes")&&!args.toLowerCase().contains("no")&&!event.getAuthor().isBot()&&!args.contains("~"))
					event.getChannel().sendMessage("sorry, I only understand phrases containing 'yes' or 'no'").queue();
			 break;	
			 
		case 6: 
			if (args.contains("0")&&!event.getAuthor().isBot()) {
				 event.getChannel().sendMessage("Well... this is not going to work then :( try again or type ~reset").queue();
				 
			 }
			else if (args.contains("1")&&!event.getAuthor().isBot()) {
				 user.setDays(1);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 
			 else if (args.contains("2")&&!event.getAuthor().isBot()) {
				 user.setDays(2);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 
			 else if (args.contains("3")&&!event.getAuthor().isBot()) {
				 user.setDays(3);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 
			 else if (args.contains("4")&&!event.getAuthor().isBot()) {
				 user.setDays(4);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 
			 else if (args.contains("5")&&!event.getAuthor().isBot()) {
				 user.setDays(5);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 
			 else if (args.contains("6")&&!event.getAuthor().isBot()) {
				 user.setDays(6);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 
			 else if (args.contains("7")&&!event.getAuthor().isBot()) {
				 user.setDays(7);
				 user.addStep();
				 event.getChannel().sendMessage("Perfect!").queue();
				 event.getChannel().sendMessage("Say thanks to get your training routine").queue();
			 }
			 else if(!event.getAuthor().isBot()&&!isNumeric(args))
				 event.getChannel().sendMessage("sorry, please input a number between 1 and 7").queue();
		
			
			 break;
			 
		case 7:
			if (!event.getAuthor().isBot()&&args.toLowerCase().contains("thanks")) {
				
			if (user.getSport().getName()=="Muay Thai") {
				user.addStep();
				user.setTraining("You should start with cardio! At least 30 minutes of running, "+ user.getDays()+ " days per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=vXv6uvrUjKo"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
				 
			 }
			
			else if (user.getSport().getName()=="Swimming") {
				user.addStep();
				user.setTraining("You should start with cardio! At least 45 minutes of running, "+ user.getDays()+ " days per week! And breathing practice!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=R_LK7uu2-oQ"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
				 
			 }
			 
			 else if (user.getSport().getName()=="Boxing") {
				 user.addStep();
				 user.setTraining("Time to get that rope spinning! At least 20 minutes, "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=XIHvfwYVy4A"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			 
			 else if (user.getSport().getName()=="Mixed Martial Arts") {
				 user.addStep();
				 user.setTraining("Start by doing 50 jumping jacks and squads, "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=8-aI8Fp2bPU"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			 
			 else if (user.getSport().getName()=="Football") {
				 user.addStep();
				 user.setTraining("Do burpees and pull-ups! At least 30, "+ user.getDays()+ " per week each!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=lAuZL_bgXs8"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			 
			 else if (user.getSport().getName()=="American Football") {
				 user.addStep();
				 user.setTraining("Push ups! Bench press! And a good diet!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=siNmDqGMhqM"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			 
			 else if (user.getSport().getName()=="Sumo") {
				 user.addStep();
				 user.setTraining("Just eat! 24/7");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=qRNjVwRaaLg"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			 
			 else if (user.getSport().getName()=="Climbing") {
				 user.addStep();
				 user.setTraining("Push-ups and pull-ups with your finger tips! At least 15 each, "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=NjmU34zhXvA"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if(user.getSport().getName()=="Baseball") {
				 user.addStep();
				 user.setTraining("Do wall bounce fielding! It's the best way to start!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=aMqbi7hmWJk"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if (user.getSport().getName()=="Basketball") {
				 user.addStep();
				 user.setTraining("Get a box and do seated box jumps! "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=HRGU3igMytM"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			 
			 else if (user.getSport().getName()=="Snowboard") {
				 user.addStep();
				 user.setTraining("Get a longboard and hit the streets! "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=SfekYZS00-Y"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if(user.getSport().getName()=="Yoga") {
				 user.addStep();
				 user.setTraining("Best way to start is doing basic core excercises!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=Eml2xnoLpYE"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if (user.getSport().getName()=="Skiing") {
				 user.addStep();
				 user.setTraining("Train your balance! "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=bEAPfOB-ZDw"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if(user.getSport().getName()=="Cycling") {
				 user.addStep();
				 user.setTraining("Best to train the lengs is jumpin rope!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=sFK5QR3hGrA"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if(user.getSport().getName()=="Marathons") {
				 user.addStep();
				 user.setTraining("Run Forrest! Run!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=XsRD2gU1LaA"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			
			 else if (user.getSport().getName()=="Mountain Biking") {
				 user.addStep();
				 user.setTraining("Train your balance and legs! "+ user.getDays()+ " per week!");
				 try {
						user.setVideo(new URL("https://www.youtube.com/watch?v=EzlfqTH9dCQ"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					event.getChannel().sendMessage(user.getTraining()).queue();
					event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
				 
			 else if (user.getSport().getName()=="Rugby") {
				 user.addStep();
				 user.setTraining("Push ups, pull ups, squads and high protein diet!");
				 try {
					user.setVideo(new URL("https://www.youtube.com/watch?v=74NCzjf_caM"));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				event.getChannel().sendMessage(user.getTraining()).queue();
				event.getChannel().sendMessage(user.getVideo().toString()).queue();
			 }
			}
			
			 break;
			 
		case 8:
			
			break;
			
		case 9:
			event.getChannel().sendMessage("Goodbye! And thanks for your patience. If you want to see a summary of your info just type '~data', and ~reset to termite the program!").queue();
			user.addStep();
			break;
		}
		
		if (user.getHeight()=="tall"&&user.getWeight()=="chubby") {
			user.addRecommendation(new Sport("American Football"));
			user.addRecommendation(new Sport("Rugby"));
			user.addRecommendation(new Sport("Boxing"));
			}
		
		else if (user.getHeight()=="tall"&&user.getWeight()=="fit") {
			user.addRecommendation(new Sport("Basketball"));
			user.addRecommendation(new Sport("Rugby"));
			user.addRecommendation(new Sport("Boxing"));
			}
		
		else if (user.getHeight()=="tall"&&user.getWeight()=="skinny") {
			user.addRecommendation(new Sport("Climbing"));
			user.addRecommendation(new Sport("Basketball"));
			user.addRecommendation(new Sport("Baseball"));
			}
		
		else if (user.getHeight()=="average"&&user.getWeight()=="chubby") {
			user.addRecommendation(new Sport("Sumo"));
			user.addRecommendation(new Sport("American Football"));
			user.addRecommendation(new Sport("Boxing"));
			}
		
		else if (user.getHeight()=="average"&&user.getWeight()=="fit") {
			user.addRecommendation(new Sport("Football"));
			user.addRecommendation(new Sport("Mixed Martial Arts"));
			user.addRecommendation(new Sport("Snowboarding"));
			}
		
		else if (user.getHeight()=="average"&&user.getWeight()=="skinny") {
			user.addRecommendation(new Sport("Swimming"));
			user.addRecommendation(new Sport("Football"));
			user.addRecommendation(new Sport("Climbing"));
			}
		
		else if (user.getHeight()=="short"&&user.getWeight()=="chubby") {
			user.addRecommendation(new Sport("Weight Lifting"));
			user.addRecommendation(new Sport("Baseball"));
			user.addRecommendation(new Sport("Cycling"));
			}
		
		else if (user.getHeight()=="short"&&user.getWeight()=="fit") {
			user.addRecommendation(new Sport("Muay Thai"));
			user.addRecommendation(new Sport("Skiing"));
			user.addRecommendation(new Sport("Baseball"));
			}
		
		else if (user.getHeight()=="short"&&user.getWeight()=="skinny") {
			user.addRecommendation(new Sport("Marathons"));
			user.addRecommendation(new Sport("Mountain Bike"));
			user.addRecommendation(new Sport("Skiing"));
			}
		
		if (args.equalsIgnoreCase("~reset")) {
			event.getChannel().sendMessage("Done!");
			user.getRecommendationList().clear();
			user.setDays(0);
			user.setHeight("");
			user.setName("");
			user.setSport(null);
			user.setWeight(null);
			user.setStep(0);
		}
		
		else if(args.equalsIgnoreCase("~data")) {
			try {
			EmbedBuilder greeting = new EmbedBuilder();
			greeting.setTitle(user.getName());
			greeting.setDescription("You are "+ user.getHeight()+" and "+ user.getWeight()+" for the moment, chose "+user.getSport().getName()+ ", and my training advise is: "+user.getTraining());
			greeting.setColor(0x42b6f5);
			event.getChannel().sendTyping();
			event.getChannel().sendMessageEmbeds(greeting.build()).queue();
			}catch (Exception e) {
				event.getChannel().sendMessage("Missing data for now...");
				}
			}
		}
	
	
	public static boolean isNumeric(String str) { 
		  try {  
		    if ( Double.parseDouble(str)<8)
		    	return true;
		    
		    else return false;
		  } catch(NumberFormatException e){  
		    return false;  
		  	}  
	}
}
	
	
	
