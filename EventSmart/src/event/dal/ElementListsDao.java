package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import event.model.*;


public class ElementListsDao {
	//dao methods
	protected ConnectionManager connectionManager;
	
	private static ElementListsDao instance = null;
	protected ElementListsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ElementListsDao getInstance() {
		if(instance == null) {
			instance = new ElementListsDao();
		}
		return instance;
	}

	public ElementLists create(ElementLists elementList) throws SQLException {
		String insertElementList =
			"INSERT INTO ElementLists(MusicID,MovieID,WineID,restaurantID,GiftID) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertElementList,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, elementList.getMusicID());
			insertStmt.setInt(2, elementList.getMovieID());
			insertStmt.setInt(3, elementList.getWineID());
			insertStmt.setInt(4, elementList.getRestaurantID());
			insertStmt.setInt(5, elementList.getGiftID());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int elementListId = -1;
			if(resultKey.next()) {
				elementListId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			elementList.setListID(elementListId);
			return elementList;
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
	public ElementLists getelementListById(int elementListId) throws SQLException {
		String selectelementList =
			"SELECT ListId,MusicID,MovieID,WineID,restaurantID,GiftID " +
			"FROM ElementLists " +
			"WHERE ListID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectelementList);
			selectStmt.setInt(1, elementListId);
			results = selectStmt.executeQuery();
			MusicDao musicDao = MusicDao.getInstance();
			MovieDao movieDao = MovieDao.getInstance();
			WineDao wineDao = WineDao.getInstance();
			RestaurantDao restaurantDao = RestaurantDao.getInstance();
			GiftDao giftDao = GiftDao.getInstance();
			if(results.next()) {
				int resultListID = results.getInt("ListId");
				int resultMusicID = results.getInt("MusicID");
				int resultMoiveID = results.getInt("MovieID");
				int resultWineID = results.getInt("WineID");
				int resultRestaurantID = results.getInt("RestaurantID");
				int resultGiftID = results.getInt("GiftID");
				
				Music music = musicDao.getMusicByID(resultMusicID);
				Movie movie = movieDao.getMovieByID(resultMoiveID);
				Wine wine = wineDao.getWineByID(resultWineID);
				Restaurant restaurant = restaurantDao.getRestaurantByID(resultRestaurantID);
				Gift gift = giftDao.getGiftByID(resultGiftID);
				
		
				ElementLists elementList = new ElementLists(resultListID, music.getMusicID(),
						movie.getMovieID(), wine.getWineID(), restaurant.getRestaurantID(), gift.getGiftID());
				return elementList;
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
	 * Delete the ElementLists instance.
	 * This runs a DELETE statement.
	 */
	public ElementLists delete(ElementLists elementList) throws SQLException {
		
		String deleteelementList = "DELETE FROM ElementLists WHERE ListID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteelementList);
			deleteStmt.setInt(1, elementList.getListID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogPosts instance.
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
