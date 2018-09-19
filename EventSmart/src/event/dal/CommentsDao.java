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

public class CommentsDao {
	protected ConnectionManager connectionManager;

	private static CommentsDao instance = null;
	protected CommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CommentsDao getInstance() {
		if(instance == null) {
			instance = new CommentsDao();
		}
		return instance;
	}
	
	public Comments create(Comments comment) throws SQLException {
		String insertComment =
			"INSERT INTO Comments(Content, UserName, EventID) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertComment,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, comment.getContent());
			insertStmt.setString(2, comment.getUsername());
			insertStmt.setInt(3, comment.getEventID());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int commentId = -1;
			if(resultKey.next()) {
				commentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			comment.setCommentid(commentId);
			return comment;
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
	
	public Comments getCommentById(int commentId) throws SQLException {
		String selectComment =
			"SELECT * " +
			"FROM Comments " +
			"WHERE CommentID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, commentId);
			results = selectStmt.executeQuery();
			DIYersDao diyerDao = DIYersDao.getInstance();
			DIYEventsDao eventDao = DIYEventsDao.getInstance();
			if(results.next()) {
				int resultCommentId = results.getInt("CommentID");
				Date created = results.getDate("CreateTime");
				String content = results.getString("Content");
				String userName = results.getString("UserName");
				int eventid = results.getInt("EventID");	
				DIYers diyer = diyerDao.getDIYerFromDIYerName(userName);
				DIYEvents event = eventDao.getDIYEventById(eventid);
				Comments comment = new Comments(resultCommentId, created, content, diyer.getUserName(), event.getEventID());
				return comment;
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
	 * Get the all the Comments for a user.
	 */
	public List<Comments> getCommentsByDIYer(String userName) throws SQLException {
		List<Comments> comments = new ArrayList<>();
		String selectComments =
			"SELECT * " +
			"FROM Comments " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComments);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			DIYersDao diyerDao = DIYersDao.getInstance();
			DIYEventsDao eventDao = DIYEventsDao.getInstance();
			while(results.next()) {
				int resultCommentId = results.getInt("CommentID");
				Date created = results.getDate("CreateTime");
				String content = results.getString("Content");
				int eventid = results.getInt("EventID");
				DIYers diyer = diyerDao.getDIYerFromDIYerName(userName);
				DIYEvents event = eventDao.getDIYEventById(eventid);
				Comments comment = new Comments(resultCommentId, created, content, diyer.getUserName(), event.getEventID());
				comments.add(comment);
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
		return comments;
	}
	
	/**
	 * Get the all the Comments for a product.
	 */
	public List<Comments> getCommentsByDIYEventID(int eventid) throws SQLException {
		List<Comments> comments = new ArrayList<>();
		String selectComments =
			"SELECT * " +
			"FROM Comments " + 
			"WHERE EventID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComments);
			selectStmt.setInt(1, eventid);
			results = selectStmt.executeQuery();
			DIYersDao diyerDao = DIYersDao.getInstance();
			DIYEventsDao eventDao = DIYEventsDao.getInstance();
			while(results.next()) {
				int resultCommentId = results.getInt("CommentID");
				Date created = results.getDate("CreateTime");
				String content = results.getString("Content");
				String userName = results.getString("UserName");	
				DIYers diyer = diyerDao.getDIYerFromDIYerName(userName);
				DIYEvents event = eventDao.getDIYEventById(eventid);
				Comments comment = new Comments(resultCommentId, created, content, diyer.getUserName(), event.getEventID());
				comments.add(comment);
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
		return comments;
	}
	
	/**
	 * Delete the Comments instance.
	 * This runs a DELETE statement.
	 */
	public Comments delete(Comments comment) throws SQLException {
		String deleteComment = "DELETE FROM Comments WHERE CommentID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteComment);
			deleteStmt.setInt(1, comment.getCommentid());
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
	
	public Comments updateContent(Comments comment, String newContent) throws SQLException {
		String updateComment = "UPDATE Comments SET Content=? WHERE CommentID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateComment);
			updateStmt.setString(1, newContent);
			updateStmt.setInt(2, comment.getCommentid());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			comment.setContent(newContent);
			return comment;
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