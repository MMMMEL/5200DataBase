package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import event.model.Restaurant;


public class RestaurantDao {
	//dao methods
	protected ConnectionManager connectionManager;
	
	private static RestaurantDao instance = null;
	protected RestaurantDao() {
		connectionManager = new ConnectionManager();
	}
	public static RestaurantDao getInstance() {
		if(instance == null) {
			instance = new RestaurantDao();
		}
		return instance;
	}

	public Restaurant create(Restaurant restaurant) throws SQLException {
		String insertRestaurant =
			"INSERT INTO Restaurants(RestaurantName,Phone,Address,City,State) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRestaurant,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, restaurant.getName());
			insertStmt.setString(2, restaurant.getPhone());
			insertStmt.setString(3, restaurant.getAddress());
			insertStmt.setString(4, restaurant.getCity());
			insertStmt.setString(5, restaurant.getState());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int RestaurantID = -1;
			if(resultKey.next()) {
				RestaurantID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			restaurant.setRestaurantID(RestaurantID);
			return restaurant;
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
	 * Get the Restaurant record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Restaurant instance.
	 */
	public Restaurant getRestaurantByID(int restaurantId) throws SQLException {
		String selectRestaurant =
			"SELECT RestaurantID,RestaurantName,Phone,Address,City,State " +
			"FROM Restaurants " +
			"WHERE RestaurantID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultRestaurantID = results.getInt("RestaurantID");
				String restaurantname = results.getString("RestaurantName");
				String phone = results.getString("Phone");
				String address = results.getString("Address");
				String city = results.getString("City");
				String state = results.getString("State");
		
				Restaurant restaurant = new Restaurant(resultRestaurantID, restaurantname, phone, address, city, state);
				return restaurant;
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
	 * Delete the Restaurant instance.
	 * This runs a DELETE statement.
	 */
	public Restaurant delete(Restaurant restaurant) throws SQLException {
		
		String deleterestaurant = "DELETE FROM Restaurants WHERE RestaurantID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleterestaurant);
			deleteStmt.setInt(1, restaurant.getRestaurantID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Restaurant instance.
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
