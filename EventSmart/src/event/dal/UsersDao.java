package event.dal;

import event.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao extends PersonsDao{
	protected ConnectionManager connectionManager;
	
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static UsersDao getInstance() {
		if (instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
	
	public Users create (Users user) throws SQLException {
		create(new Persons (user.getUserName(), user.getPassWord(), user.getEmail()));
		String insertUser = "INSERT INTO Users(UserName,FirstName,LastName,Phone) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getFirstName());
			insertStmt.setString(3, user.getLastName());
			insertStmt.setString(4, user.getPhone());
			insertStmt.executeUpdate();
			return user;
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
	
	public Users getUserFromUserName (String userName) throws SQLException {
		String selectUser = 
				"SELECT Users.UserName AS UserName, Password, Email, FirstName, LastName, Phone "
				+ "FROM Users INNER JOIN Persons "
				+ " ON Users.UserName = Persons.UserName "
				+ "WHERE Users.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String phone = results.getString("Phone");
				Users user = new Users(resultUserName, password, email, firstName, lastName, phone);
				return user;
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
	
	public List<Users> getUsersFromFirstName(String firstName) throws SQLException {
		List<Users> users = new ArrayList<Users>();
		String selectUsers =
			"SELECT UserName,Password,Email,FirstName,LastName,Phone FROM Users WHERE FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userName = results.getString("UserName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String phone = results.getString("Phone");
				Users user = new Users(userName, password, email, resultFirstName, lastName, phone);
				users.add(user);
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
		return users;
	}
	
	public Users updateUsersPassword (Users user, String newPassword) throws SQLException{
		super.updatePassword(user, newPassword);
		return user;
	}
	
	public Users updateUsersPhone (Users user, String newPhone) throws SQLException {
		String updateUser = "UPDATE Users SET newPhone=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newPhone);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();
			
			user.setPhone(newPhone);
			return user;
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
	
	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			deleteStmt.executeUpdate();
			
			super.delete(user);
			
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
