package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import event.model.Movie;


public class MovieDao {
	//dao methods
	protected ConnectionManager connectionManager;
	
	private static MovieDao instance = null;
	protected MovieDao() {
		connectionManager = new ConnectionManager();
	}
	public static MovieDao getInstance() {
		if(instance == null) {
			instance = new MovieDao();
		}
		return instance;
	}

	public Movie create(Movie movie) throws SQLException {
		String insertMovie =
			"INSERT INTO Movies(MovieName,DirectorName,Genres,ContentRating) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertMovie,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, movie.getName());
			insertStmt.setString(2, movie.getDirector());
			insertStmt.setString(3, movie.getGenres());
			insertStmt.setString(4, movie.getRating());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int MovieID = -1;
			if(resultKey.next()) {
				MovieID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			movie.setMovieID(MovieID);
			return movie;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	/**
	 * Get the ElementLists record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single ElementLists instance.
	 */
	public Movie getMovieByID(int movieId) throws SQLException {
		String selectMovie =
			"SELECT MovieID,MovieName,DirectorName,Genres,ContentRating " +
			"FROM Movies " +
			"WHERE MovieID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMovie);
			selectStmt.setInt(1, movieId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultMovieID = results.getInt("MovieID");
				String moviename = results.getString("MovieName");
				String director = results.getString("DirectorName");
				String genres = results.getString("Genres");
				String rating = results.getString("ContentRating");
		
				Movie movie = new Movie(resultMovieID, moviename, director, genres, rating);
				return movie;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	
	/**
	 * Delete the Movie instance.
	 * This runs a DELETE statement.
	 */
	public Movie delete(Movie movie) throws SQLException {
		
		String deletemovie = "DELETE FROM Movies WHERE MovieID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletemovie);
			deleteStmt.setInt(1, movie.getMovieID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Movie instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

}
