package Project;


import java.net.URL;
import java.util.ArrayList;

public class Useer {
	
	private String height;
	private String weight;
	private String name;
	private ArrayList<Sport> recom;
	private int step;
	private Sport sport;
	private int days ;
	private String training;
	private URL myURL;
	
	public Useer() {
		step=0;
		recom = new ArrayList<Sport>();
		
	}
	
	public String getTraining() {
		return training;
	}
	
	
	public int getDays() {
		return days;
	}
	
	public int getStep() {
		return step;
	}
	
	public URL getVideo() {
		return myURL;
	}
	
	public void addStep() {
		step++;
	}
	
	public void removeStep() {
		step--;
	}
	
	public void setStep(int step) {
		this.step=step;
		
	}
	
	public void setHeight(String height) {
		
			this.height=height;
		
	}
	
	public void setWeight(String weight) {
		
			this.weight=weight;

	}
	
	public void setDays(int days) {
		this.days=days;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void addRecommendation(Sport recommendation) {
			recom.add(recommendation);	
	}
	
	public void removeFirstRecommendation() {
		recom.remove(0);	
}
	public ArrayList<Sport> getRecommendationList() {
		return recom;
	}
	
	public String getHeight() {
		return height;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public String getName() {
		return name;
	}
	
	public Sport getRecommendation(int i) {
			return recom.get(i);	
	}
	
	
	public void setSport(Sport sport) {
		this.sport=sport;
	}
	
	public Sport getSport() {
		return this.sport;
	}
	
	public void setTraining(String training) {
		this.training=training;
	}
	
	public void setVideo(URL myURL) {
		this.myURL = myURL;
	}
	
}
