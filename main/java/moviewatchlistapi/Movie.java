package moviewatchlistapi;

public class Movie {
	String title;
	String director;
	String releaseYear;
	boolean watchedStatus;
	
	public Movie(String title, String director, String releaseYear, boolean watchedStatus) {
		this.title = title;
		this.director = director;
		this.releaseYear = releaseYear;
		this.watchedStatus = watchedStatus;
	}
	
	public String toString(Movie movie) {
		return "Title: " + movie.title + "\n" + "Director: " + movie.director + "\n" + "Release Year: " + movie.releaseYear + "\n" + "Watched Status: " + movie.watchedStatus;
	}
}
