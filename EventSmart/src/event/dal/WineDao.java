package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import event.model.Wine;


public class WineDao {
	//dao methods
	protected ConnectionManager connectionManager;
	
	private static WineDao instance = null;
	protected WineDao() {
		connectionManager = new ConnectionManager();
	}
	public static WineDao getInstance() {
		if(instance == null) {
			instance = new WineDao();
		}
		return instance;
	}

	public Wine create(Wine wine) throws SQLException {
		String insertWine =
			"INSERT INTO Wines(WineName,Country,Description,Price) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertWine,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, wine.getName());
			insertStmt.setString(2, wine.getCountry());
			insertStmt.setString(3, wine.getDescription());
			insertStmt.setInt(4, wine.getPrice());


			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int WineID = -1;
			if(resultKey.next()) {
				WineID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			wine.setWineID(WineID);
			return wine;
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
	 * Get the Wine record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Wine instance.
	 */
	public Wine getWineByID(int wineId) throws SQLException {
		String selectWine =
			"SELECT WineID,WineName,Country,Description,Price " +
			"FROM Wines " +
			"WHERE WineID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWine);
			selectStmt.setInt(1, wineId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultWineID = results.getInt("WineID");
				String winename = results.getString("WineName");
				String country = results.getString("Country");
				String description = results.getString("Description");
				int price = results.getInt("Price");
		
				Wine wine = new Wine(resultWineID, winename, country, description, price);
				return wine;
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
	 * Delete the Wine instance.
	 * This runs a DELETE statement.
	 */
	public Wine delete(Wine wine) throws SQLException {
		
		String deletewine = "DELETE FROM Wines WHERE WineID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletewine);
			deleteStmt.setInt(1, wine.getWineID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Wine instance.
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
