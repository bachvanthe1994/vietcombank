package model;

public class FilmInfo {
	public String filmName;
	public String filmCategory;
	public String filmDuration;
	public String filmRate;
	
	public FilmInfo(String filmName, String filmCategory, String filmDuration, String filmRate) {
		this.filmName = filmName;
		this.filmCategory = filmCategory;
		this.filmDuration = filmDuration;
		this.filmRate = filmRate;

	}
	
	public enum TypeShow {
		  HORIZONTAL, VERTICAL
	}
	
}