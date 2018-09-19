package event.dal;

import event.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventProductsDao {
	//dao method
	protected ConnectionManager connectionManager;

	private static EventProductsDao instance = null;
	protected EventProductsDao() {
		connectionManager = new ConnectionManager();
	}
	public static EventProductsDao getInstance() {
		if(instance == null) {
			instance = new EventProductsDao();
		}
		return instance;
	}

	public EventProducts create(EventProducts product) throws SQLException {
		String insertEventProducts =
			"INSERT INTO EventProducts(Theme, Size, PriceRange, Description, PlannerName) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertEventProducts,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, product.getTheme().name());
			insertStmt.setInt(2, product.getSize());
			insertStmt.setString(3, product.getPricerange().name());
			insertStmt.setString(4, product.getDescription());
			insertStmt.setString(5, product.getPlannerName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int productId = -1;
			if(resultKey.next()) {
				productId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			product.setProductID(productId);
			return product;
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
	 * Update the content of the EventProduct instance.
	 * This runs a UPDATE statement.
	 */
	public EventProducts updateDescription(EventProducts product, String newDescription) throws SQLException {
		String updateProduct = "UPDATE EventProducts SET Description=? WHERE ProductID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateProduct);
			updateStmt.setString(1, newDescription);
			updateStmt.setInt(2, product.getProductID());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			product.setDescription(newDescription);
			return product;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete the BlogPosts instance.
	 * This runs a DELETE statement.
	 */
	public EventProducts delete(EventProducts product) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteEvent = "DELETE FROM EventProducts WHERE ProductID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteEvent);
			deleteStmt.setInt(1, product.getProductID());
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

	/**
	 * Get the all the Event for a Planner.
	 */
	public List<EventProducts> getEventProductsForPlanner(Planners user) throws SQLException {
		List<EventProducts> products = new ArrayList<EventProducts>();
		String selectproducts =
			"SELECT ProductID, Theme, Size, PriceRange, Description, PlannerName" +
			"FROM EventProducts " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectproducts);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int ProductId = results.getInt("ProductID");
				EventProducts.Theme theme = EventProducts.Theme.valueOf(results.getString("Theme"));
				int size = results.getInt("Size");
				EventProducts.PriceRange pr = EventProducts.PriceRange.valueOf(results.getString("PriceRange"));
				String discription = results.getString("Description");
				String username = results.getString("PlannerName");
				EventProducts product = new EventProducts(ProductId, theme, size, pr, discription, username);
				products.add(product);
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
		return products;
	}

	/**
	 * Get the all the Event for a theme.
	 */
	public List<EventProducts> getEventProductsForPlanner(EventProducts.Theme theme) throws SQLException {
		List<EventProducts> products = new ArrayList<EventProducts>();
		String selectproducts =
			"SELECT ProductID, Theme, Size, PriceRange, Description, PlannerName" +
			"FROM EventProducts " +
			"WHERE Theme=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectproducts);
			selectStmt.setString(1, theme.name());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int ProductId = results.getInt("ProductID");
				EventProducts.Theme theme1 = EventProducts.Theme.valueOf(results.getString("Theme"));
				int size = results.getInt("Size");
				EventProducts.PriceRange pr = EventProducts.PriceRange.valueOf(results.getString("PriceRange"));
				String discription = results.getString("Description");
				String username = results.getString("PlannerName");
				EventProducts product = new EventProducts(ProductId, theme1, size, pr, discription, username);
				products.add(product);
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
		return products;
	}
	
	public EventProducts getEventProductsById(int productid) throws SQLException {
		String selectproduct =
			"SELECT ProductID, Theme, Size, PriceRange, Description, PlannerName" +
			"FROM EventProducts " +
			"WHERE ProductID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectproduct);
			selectStmt.setInt(1, productid);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int ProductId = results.getInt("ProductID");
				EventProducts.Theme theme = EventProducts.Theme.valueOf(results.getString("Theme"));
				int size = results.getInt("Size");
				EventProducts.PriceRange pr = EventProducts.PriceRange.valueOf(results.getString("PriceRange"));
				String discription = results.getString("Description");
				String username = results.getString("PlannerName");
				EventProducts product = new EventProducts(ProductId, theme, size, pr, discription, username);
				return product;
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
}
