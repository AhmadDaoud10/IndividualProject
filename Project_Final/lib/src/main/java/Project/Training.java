package Project;

import java.util.ArrayList;

public class Training {
	
	private int hours;
	private int days;
	private ArrayList<String> excercices;
	
	public Training() {
		
	}
	
	public Training(int days, int hours, ArrayList<String> excercices) {
		this.days=days;
		this.hours=hours;
		this.excercices=excercices;
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public int getDays() {
		return days;
	}
	
	public void setDays(int days) {
		this.days = days;
	}
	
	public ArrayList<String> getExcercices() {
		return excercices;
	}
	
	public void setExcercices(ArrayList<String> excercices) {
		this.excercices = excercices;
	}
	
	
}
