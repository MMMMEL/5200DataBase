package event.dal;

import event.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationsDao {
	protected ConnectionManager connectionManager;

	private static ReservationsDao instance = null;
	protected ReservationsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReservationsDao getInstance() {
		if(instance == null) {
			instance = new ReservationsDao();
		}
		return instance;
	}
	
	public Reservations create(Reservations reservation) throws SQLException {
		String insertReservation =
			"INSERT INTO Reservations(EventDate, UserName, PlannerName) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReservation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, new Timestamp(reservation.getEventdate().getTime()));
			insertStmt.setString(2, reservation.getUsername());
			insertStmt.setString(3, reservation.getPlannername());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reservationId = -1;
			if(resultKey.next()) {
				reservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			reservation.setReservationID(reservationId);
			return reservation;
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
	
	public Reservations getReservationById(int reservationId) throws SQLException {
		String selectReservation =
			"SELECT * " +
			"FROM Reservations " +
			"WHERE ReservationID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservation);
			selectStmt.setInt(1, reservationId);
			results = selectStmt.executeQuery();
			DIYersDao diyerDao = DIYersDao.getInstance();
			PlannersDao plannerDao = PlannersDao.getInstance();
			if(results.next()) {
				int resultReservationId = results.getInt("ReservationID");
				Date created = results.getDate("CreateTime");
				Date eventdate = results.getDate("EventDate");
				String userName = results.getString("UserName");
				String plannerName = results.getString("PlannerName");	
				DIYers diyer = diyerDao.getDIYerFromDIYerName(userName);
				Planners planner = plannerDao.getPlannerFromPlannerName(plannerName);
				Reservations reservation = new Reservations(resultReservationId, created, eventdate, diyer.getUserName(), planner.getUserName());
				return reservation;
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
	 * Get the all the Reservations for a user.
	 */
	public List<Reservations> getReservationsByDIYer(String userName) throws SQLException {
		List<Reservations> reservations = new ArrayList<>();
		String selectReservations =
			"SELECT * " +
			"FROM Reservations " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			DIYersDao diyerDao = DIYersDao.getInstance();
			PlannersDao plannerDao = PlannersDao.getInstance();
			while(results.next()) {
				int resultReservationId = results.getInt("ReservationID");
				Date created = results.getDate("CreateTime");
				Date eventdate = results.getDate("EventDate");
				String plannerName = results.getString("PlannerName");	
				DIYers diyer = diyerDao.getDIYerFromDIYerName(userName);
				Planners planner = plannerDao.getPlannerFromPlannerName(plannerName);
				Reservations reservation = new Reservations(resultReservationId, created, eventdate, diyer.getUserName(), planner.getUserName());
				reservations.add(reservation);
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
		return reservations;
	}
	
	/**
	 * Get the Reservation for a planner at a date.
	 */
	public Reservations getReservationsByPlanner(String plannerName, Date date) throws SQLException {
		//Reservations reservation = new ArrayList<>();
		String selectReservations =
			"SELECT * " +
			"FROM Reservations " + 
			"WHERE PlannerName=? AND EventDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setString(1, plannerName);
			selectStmt.setDate(2, new java.sql.Date(date.getTime()));
			results = selectStmt.executeQuery();
			DIYersDao diyerDao = DIYersDao.getInstance();
			PlannersDao plannerDao = PlannersDao.getInstance();
			if(results.next()) {
				int resultReservationId = results.getInt("ReservationID");
				Date created = results.getDate("CreateTime");
				Date eventdate = results.getDate("EventDate");
				String userName = results.getString("UserName");	
				DIYers diyer = diyerDao.getDIYerFromDIYerName(userName);
				Planners planner = plannerDao.getPlannerFromPlannerName(plannerName);
				Reservations reservation = new Reservations(resultReservationId, created, eventdate, diyer.getUserName(), planner.getUserName());
				return reservation;
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
	 * Delete the Reservations instance.
	 * This runs a DELETE statement.
	 */
	public Reservations delete(Reservations reservation) throws SQLException {
		String deleteReservation = "DELETE FROM Reservations WHERE ReservationID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReservation);
			deleteStmt.setInt(1, reservation.getReservationID());
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
	
	public Reservations updateEventDate(Reservations reservation, Date date) throws SQLException {
		String updateReservation = "UPDATE Reservations SET EventDate=? WHERE ReservationID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReservation);
			updateStmt.setTimestamp(1, new Timestamp (date.getTime()));
			updateStmt.setInt(2, reservation.getReservationID());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			reservation.setEventdate(date);
			return reservation;
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