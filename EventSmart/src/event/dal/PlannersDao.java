package event.dal;

import event.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlannersDao extends UsersDao{
	protected ConnectionManager connectionManager;
	
	private static PlannersDao instance = null;
	protected PlannersDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static PlannersDao getInstance() {
		if (instance == null) {
			instance = new PlannersDao();
		}
		return instance;
	}
	
	public Planners create (Planners planner) throws SQLException {
		create(new Users (planner.getUserName(), planner.getPassWord(), planner.getEmail(), 
				planner.getFirstName(), planner.getLastName(), planner.getPhone()));
		String insertPlanner = "INSERT INTO Planners(PlannerName,Company) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPlanner);
			
			insertStmt.setString(1, planner.getUserName());
			insertStmt.setString(2, planner.getCompany());
			insertStmt.executeUpdate();
			return planner;
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
		}
	}
	
	public Planners getPlannerFromPlannerName (String plannerName) throws SQLException {
		Users user = getUserFromUserName (plannerName);
		
		String selectPlanner = 
				"SELECT Planners.PlannerName AS PlannerName, Company "
				+ "FROM Planners WHERE Planners.PlannerName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPlanner);
			selectStmt.setString(1, plannerName);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String resultPlannerName = results.getString("PlannerName");
				String company = results.getString("Company");
				Planners planner = new Planners (resultPlannerName, user.getPassWord(), user.getEmail(), 
						user.getFirstName(), user.getLastName(), user.getPhone(), company);
				return planner;
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
	
	public List<Planners> getPlannerFromCompanyName (String company) throws SQLException {
		List<Planners> planners = new ArrayList<Planners>();
		String selectPlanners = 
				"SELECT Planners.PlannerName AS PlannerName, Password, Email, FirstName, LastName, Phone, Company "
				+ "FROM Planners "
				+ "INNER JOIN Users ON Planners.PlannerName = Users.UserName "
				+ "INNER JOIN Persons ON Users.UserName = Persons.UserName "
				+ "WHERE Planners.Company=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPlanners);
			selectStmt.setString(1, company);
			results = selectStmt.executeQuery();
			while (results.next()) {
				String plannerName = results.getString("PlannerName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String phone = results.getString("Phone");
				String resultCompany = results.getString("Company");
				Planners planner = new Planners(plannerName, password, email, firstName, lastName, 
						phone, resultCompany);
				planners.add(planner);
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
		return planners;
	}
	
	public Planners updatePlannerPassword (Planners planner, String newPassword) throws SQLException {
		super.updatePassword(planner, newPassword);
		return planner;
	}
	
	public Planners updatePlannerCompany (Planners planner, String newCompany) throws SQLException {
		String updatePlanner = "UPDATE Planners SET Company=? WHERE PlannerName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePlanner);
			updateStmt.setString(1, newCompany);
			updateStmt.setString(2, planner.getUserName());
			updateStmt.executeUpdate();
			
			planner.setCompany(newCompany);
			return planner;
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
	
	public Planners delete(Planners planner) throws SQLException {
		String deletePlanner = "DELETE FROM Planners WHERE PlannerName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePlanner);
			deleteStmt.setString(1, planner.getUserName());
			deleteStmt.executeUpdate();
			
			super.delete(planner);
			
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
