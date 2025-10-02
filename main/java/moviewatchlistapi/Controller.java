package moviewatchlistapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	final String url = "jdbc:mysql://localhost:3306/moviewatchlist?useSSL=false&serverTimezone=UTC";
	final String username = "root";
	final String password = "";
	
	@PostMapping("/create")
	public String createMovie(@RequestParam("title") String title,
							@RequestParam("director") String director,
							@RequestParam("releaseYear") String releaseYear,
							@RequestParam("watchedStatus") boolean watchedStatus) {
		String sqlString = "INSERT INTO movies (Title, Director, ReleaseYear, WatchedStatus) VALUES (?,?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			PreparedStatement prep = connection.prepareStatement(sqlString); 
			
			prep.setString(1, title);
			prep.setString(2, director);
			prep.setString(3, releaseYear);
			prep.setBoolean(4, watchedStatus);
			
			prep.execute();
			
			return "Your movie called '" + title + "' has been created!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error inserting movie data" + e.getMessage();
		}
	}
	
	@GetMapping("/read")
	public Movie retrieveMovie(@RequestParam("title") String title) {
		String sqlString = "SELECT * from movies WHERE title = ?";
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement prep = conn.prepareStatement(sqlString);
			
			prep.setString(1, title);
			
			ResultSet result = prep.executeQuery();
			
			while(result.next()) {
				Movie movie = new Movie(title, result.getString("Director"), 
						result.getString("ReleaseYear"), result.getBoolean("WatchedStatus"));
				
				System.out.println("Movie data:\n" + movie.toString(movie)); 
				
				return movie;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping("/update")
	public String updateMovie(@RequestParam("id") int id, @RequestParam("releaseYear") String releaseYear,
							@RequestParam("watchedStatus") boolean watchedStatus) {
		
		String sqlString = "UPDATE movies SET ReleaseYear = ?, WatchedStatus = ? WHERE MovieId = ?";
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			PreparedStatement prep = conn.prepareStatement(sqlString);
			
			prep.setString(1, releaseYear);
			prep.setBoolean(2, watchedStatus);
			prep.setInt(3, id);
			
			int rows = prep.executeUpdate();
			
			if(rows > 0) {
				return "Your record has been updated!";
			} else {
				return "Your record has not being updated...";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error updating movie data" + e.getMessage();
		}
	}
	
	@DeleteMapping("/delete")
	public String deleteMovie() {
		return "";
	}
}
