package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import event.model.Gift;


public class GiftDao {
	//dao methods
	protected ConnectionManager connectionManager;
	
	private static GiftDao instance = null;
	protected GiftDao() {
		connectionManager = new ConnectionManager();
	}
	public static GiftDao getInstance() {
		if(instance == null) {
			instance = new GiftDao();
		}
		return instance;
	}

	public Gift create(Gift gift) throws SQLException {
		String insertGift =
			"INSERT INTO Gifts(ProductName,Price,Category,Description) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertGift,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, gift.getName());
			insertStmt.setFloat(2, gift.getPrice());
			insertStmt.setString(3, gift.getCategory());
			insertStmt.setString(4, gift.getDescription());


			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int GiftID = -1;
			if(resultKey.next()) {
				GiftID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			gift.setGiftID(GiftID);
			return gift;
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
	 * Get the Gift record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Gift instance.
	 */
	public Gift getGiftByID(int giftId) throws SQLException {
		String selectGift =
			"SELECT GiftID,ProductName,Price,Category,Description " +
			"FROM Gifts " +
			"WHERE GiftID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGift);
			selectStmt.setInt(1, giftId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultGiftID = results.getInt("GiftID");
				String giftname = results.getString("ProductName");
				float price = results.getFloat("Price");
				String category = results.getString("Category");
				String description = results.getString("Description");
		
				Gift gift = new Gift(resultGiftID, giftname, price, category, description);
				return gift;
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
	 * Delete the Gift instance.
	 * This runs a DELETE statement.
	 */
	public Gift delete(Gift gift) throws SQLException {
		
		String deletegift = "DELETE FROM Gifts WHERE GiftID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletegift);
			deleteStmt.setInt(1, gift.getGiftID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Gift instance.
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
