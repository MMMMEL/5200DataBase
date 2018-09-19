package event.dal;

import event.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReviewsDao {
	protected ConnectionManager connectionManager;

	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}
	
	public Reviews create(Reviews review) throws SQLException {
		String insertReview =
			"INSERT INTO Reviews(Rating, Content, UserName, ProductID) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setFloat(1, review.getRating());
			insertStmt.setString(2, review.getContent());
			insertStmt.setString(3, review.getUsername());
			insertStmt.setInt(4, review.getProductid());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewid(reviewId);
			return review;
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
	
	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview =
			"SELECT * " +
			"FROM Reviews " +
			"WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			DIYersDao usersDao = DIYersDao.getInstance();
			EventProductsDao productDao = EventProductsDao.getInstance();
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewID");
				String userName = results.getString("UserName");
				int productid = results.getInt("ProductID");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				float rating = results.getFloat("Rating");
				
				DIYers user = usersDao.getDIYerFromDIYerName(userName);
				EventProducts product = productDao.getEventProductsById(productid);
				Reviews review = new Reviews(resultReviewId, created, rating, content, user.getUserName(), product.getProductID());
				return review;
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
	 * Get the all the Reviews for a user.
	 */
	public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
		List<Reviews> reviews = new ArrayList<>();
		String selectReviews =
			"SELECT * " +
			"FROM Reviews " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			EventProductsDao eventproductDao = EventProductsDao.getInstance();
			DIYersDao userDao = DIYersDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewID");
				int productId = results.getInt("ProductID");
				EventProducts product = eventproductDao.getEventProductsById(productId);
				DIYers user = userDao.getDIYerFromDIYerName(userName);
				Date created =  new Date(results.getTimestamp("CreateTime").getTime());
				String content = results.getString("Content");
				float rating = results.getFloat("Rating");
				Reviews review = new Reviews(reviewId, created, rating, content, user.getUserName(), product.getProductID());
				reviews.add(review);
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
		return reviews;
	}
	
	/**
	 * Get the all the Reviews for a product.
	 */
	public List<Reviews> getReviewsByProductId(int productId) throws SQLException {
		List<Reviews> reviews = new ArrayList<>();
		String selectReviews =
			"SELECT * " +
			"FROM Reviews " + 
			"WHERE ProductID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, productId);
			results = selectStmt.executeQuery();
			EventProductsDao eventproductDao = EventProductsDao.getInstance();
			DIYersDao userDao = DIYersDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewID");
				String userName = results.getString("UserName");
				EventProducts product = eventproductDao.getEventProductsById(productId);
				DIYers user = userDao.getDIYerFromDIYerName(userName);
				Date created =  new Date(results.getTimestamp("CreateTime").getTime());
				String content = results.getString("Content");
				float rating = results.getFloat("Rating");
				Reviews review = new Reviews(reviewId, created, rating, content, user.getUserName(), product.getProductID());
				reviews.add(review);
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
		return reviews;
	}
	
	/**
	 * Delete the Reviews instance.
	 * This runs a DELETE statement.
	 */
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewid());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
	
	public Reviews updateContent(Reviews review, String newContent) throws SQLException {
		String updateReview = "UPDATE Reviews SET Content=? WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setString(1, newContent);
			updateStmt.setInt(2, review.getReviewid());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			review.setContent(newContent);
			return review;
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
}