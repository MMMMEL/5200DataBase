package event.dal;

import event.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonsDao {
	protected ConnectionManager connectionManager;
	
	private static PersonsDao instance = null;
	protected PersonsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static PersonsDao getInstance() {
		if (instance == null) {
			instance = new PersonsDao();
		}
		return instance;
	}
	
	public Persons create(Persons person) throws SQLException {
		String insertPerson = "INSERT INTO Persons(UserName,Password,Email) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerson);

			insertStmt.setString(1, person.getUserName());
			insertStmt.setString(2, person.getPassWord());
			insertStmt.setString(3, person.getEmail());

			insertStmt.executeUpdate();
			
			return person;
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
	
	public Persons getPersonFromUserName(String userName) throws SQLException {
		String selectPerson = "SELECT UserName,Password,Email FROM Persons WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerson);
			selectStmt.setString(1, userName);

			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				Persons person = new Persons(resultUserName, password, email);
				return person;
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
	
	public Persons updatePassword (Persons person, String newPassword) throws SQLException {
		String updatePerson = "UPDATE Persons SET Password=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newPassword);
			updateStmt.setString(2, person.getUserName());
			updateStmt.executeUpdate();
			
			person.setPassWord(newPassword);
			return person;
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
	
	public Persons delete(Persons person) throws SQLException {
		String deletePerson = "DELETE FROM Persons WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePerson);
			deleteStmt.setString(1, person.getUserName());
			deleteStmt.executeUpdate();

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
