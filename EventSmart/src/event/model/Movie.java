package event.model;

public class Movie {
	protected int movieID;
	protected String name = " ";
	protected String director;
	protected String genres;
	protected String rating;
	
	public Movie(int movieID, String name, String director, String genres, String rating) {
		this.movieID = movieID;
		this.name = name;
		this.director = director;
		this.genres = genres;
		this.rating = rating;
	}
	
	public Movie(int movieID) {
		this.movieID = movieID;
	}
	
	//getters and setters
	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
