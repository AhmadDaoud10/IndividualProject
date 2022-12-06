package Project;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BotStartup {
	public static void main(String[] args) throws LoginException {
		JDABuilder jda =  JDABuilder.createDefault("MTA0Nzk5MjYxMDc2MzA0Njk1Mg.Gw74bG.iBUy-sSodfp263DW3aSdWN5UV-chaaGsCm5zUE" //Enter Server token specific to user and make sure to move to environment variable not code
				,  GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
		jda.setActivity(Activity.listening("You!"));
		jda.setStatus(OnlineStatus.ONLINE);
		jda.addEventListeners(new Commands());
		jda.build();
		
	}
}