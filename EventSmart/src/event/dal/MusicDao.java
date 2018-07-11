package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import event.model.Music;


public class MusicDao {
	//dao methods
	protected ConnectionManager connectionManager;
	
	private static MusicDao instance = null;
	protected MusicDao() {
		connectionManager = new ConnectionManager();
	}
	public static MusicDao getInstance() {
		if(instance == null) {
			instance = new MusicDao();
		}
		return instance;
	}

	public Music create(Music music) throws SQLException {
		String insertMusic =
			"INSERT INTO Musics(MusicName,Artist,Genres) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertMusic,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, music.getName());
			insertStmt.setString(2, music.getArtist());
			insertStmt.setString(3, music.getGenres());


			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int MusicID = -1;
			if(resultKey.next()) {
				MusicID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			music.setMusicID(MusicID);
			return music;
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
	public Music getMusicByID(int musicId) throws SQLException {
		String selectMusic =
			"SELECT MusicID,MusicName,Artist,Genres " +
			"FROM Musics " +
			"WHERE MusicID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMusic);
			selectStmt.setInt(1, musicId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultMusicID = results.getInt("MusicID");
				String musicname = results.getString("MusicName");
				String artist = results.getString("Artist");
				String genres = results.getString("Genres");			
		
				Music music = new Music(resultMusicID, musicname, artist, genres);
				return music;
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
	 * Delete the Music instance.
	 * This runs a DELETE statement.
	 */
	public Music delete(Music music) throws SQLException {
		
		String deletemusic = "DELETE FROM Musics WHERE MusicID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletemusic);
			deleteStmt.setInt(1, music.getMusicID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Music instance.
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
