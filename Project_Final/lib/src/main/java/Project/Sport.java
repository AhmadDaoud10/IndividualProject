package Project;


public class Sport {
	private String name;
	private Training training;
	
	public Sport() {

	}
	
	
	
	public Sport(String name) {
		this.name=name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTraining(Training training) {
		this.training=training;
	}
	
	public Training getTraining() {
		return training;
	}
	
	public String getName() {
		return this.name;
	}
}
