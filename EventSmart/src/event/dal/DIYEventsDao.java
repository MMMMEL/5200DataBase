package event.dal;

import event.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DIYEventsDao {
	//dao method
	protected ConnectionManager connectionManager;

	private static DIYEventsDao instance = null;
	protected DIYEventsDao() {
		connectionManager = new ConnectionManager();
	}
	public static DIYEventsDao getInstance() {
		if(instance == null) {
			instance = new DIYEventsDao();
		}
		return instance;
	}

	public DIYEvents create(DIYEvents event) throws SQLException {
		String insertDIYEvents =
			"INSERT INTO DIYEvents(Theme, Description, ListID, UserName) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertDIYEvents,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, event.getTheme().name());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setString(2, event.getDescription());
			insertStmt.setInt(3, event.getListID());
			insertStmt.setString(4, event.getUsername());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int eventId = -1;
			if(resultKey.next()) {
				eventId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			event.setEventID(eventId);
			return event;
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
	 * Update the content of the DIYEvent instance.
	 * This runs a UPDATE statement.
	 */
	public DIYEvents updateDescription(DIYEvents event, String newDescription) throws SQLException {
		String updateEvent = "UPDATE DIYEvents SET Description=? WHERE EventID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateEvent);
			updateStmt.setString(1, newDescription);
			updateStmt.setInt(2, event.getEventID());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			event.setDescription(newDescription);
			return event;
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
	public DIYEvents delete(DIYEvents event) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteEvent = "DELETE FROM DIYEvents WHERE EventID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteEvent);
			deleteStmt.setInt(1, event.getEventID());
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
	 * Get the all the Event for a DIYer.
	 */
	public List<DIYEvents> getDIYEventsForUser(DIYers user) throws SQLException {
		List<DIYEvents> events = new ArrayList<DIYEvents>();
		String selectevents =
			"SELECT EventID, Theme, Description, ListID, UserName " +
			"FROM DIYEvents " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectevents);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int EventId = results.getInt("EventID");
				DIYEvents.Theme theme = DIYEvents.Theme.valueOf(results.getString("Theme"));
				String discription = results.getString("Description");
				int ListID = results.getInt("ListID");
				String username = results.getString("UserName");
				DIYEvents event = new DIYEvents(EventId, theme, discription, ListID, username);
				events.add(event);
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
		return events;
	}
	
	/**
	 * Get the all the Event for a Theme.
	 */
	public List<DIYEvents> getDIYEventsForTheme(DIYEvents.Theme theme) throws SQLException {
		List<DIYEvents> events = new ArrayList<DIYEvents>();
		String selectevents =
			"SELECT EventID, Theme, Description, ListID, UserName " +
			"FROM DIYEvents " +
			"WHERE Theme=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectevents);
			selectStmt.setString(1, theme.name());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int EventId = results.getInt("EventID");
				DIYEvents.Theme theme1 = DIYEvents.Theme.valueOf(results.getString("Theme"));
				String discription = results.getString("Description");
				int ListID = results.getInt("ListID");
				String username = results.getString("UserName");
				DIYEvents event = new DIYEvents(EventId, theme1, discription, ListID, username);
				events.add(event);
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
		return events;
	}
	
	public DIYEvents getDIYEventById(int eventId) throws SQLException {
		String selectevent =
			"SELECT EventID, Theme, Description, ListID, UserName " +
			"FROM DIYEvents " +
			"WHERE EventID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectevent);
			selectStmt.setInt(1, eventId);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int EventId = results.getInt("EventID");
				DIYEvents.Theme theme1 = DIYEvents.Theme.valueOf(results.getString("Theme"));
				String discription = results.getString("Description");
				int ListID = results.getInt("ListID");
				String username = results.getString("UserName");
				DIYEvents event = new DIYEvents(EventId, theme1, discription, ListID, username);
				return event;
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
