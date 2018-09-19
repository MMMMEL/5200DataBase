package event.dal;

import event.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DIYersDao extends UsersDao{
	protected ConnectionManager connectionManager;
	
	private static DIYersDao instance = null;
	protected DIYersDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static DIYersDao getInstance() {
		if (instance == null) {
			instance = new DIYersDao();
		}
		return instance;
	}
	
	public DIYers create (DIYers diyer) throws SQLException {
		create(new Users (diyer.getUserName(), diyer.getPassWord(), diyer.getEmail(),
				diyer.getFirstName(), diyer.getLastName(), diyer.getPhone()));
		String insertDIYer = "INSERT INTO DIYers(UserName) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDIYer);
			
			insertStmt.setString(1, diyer.getUserName());
			insertStmt.executeUpdate();
			return diyer;
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
	
	public DIYers getDIYerFromDIYerName (String userName) throws SQLException {
		Users user = getUserFromUserName (userName);
		if (user == null) {
			return null;
		}
		DIYers diyer = new DIYers(user.getUserName(), user.getPassWord(), user.getEmail(), 
				user.getFirstName(), user.getLastName(), user.getPhone());
		return diyer;
	}
	
	public List<DIYers> getDIYerFromFirstName (String firstname) throws SQLException {
		List<Users> users = getUsersFromFirstName(firstname);
		List<DIYers> diyers = new ArrayList<DIYers> ();
		for (Users user : users) {
			DIYers diyer = new DIYers(user.getUserName(), user.getPassWord(), user.getEmail(), 
					user.getFirstName(), user.getLastName(), user.getPhone());
			diyers.add(diyer);
		}
		return diyers;
	}
	
	public DIYers updateDIYerPassword (DIYers diyer, String newPassword) throws SQLException {
		super.updatePassword(diyer, newPassword);
		return diyer;
	}
	
	public DIYers updatePhone (DIYers diyer, String newPhone) throws SQLException {
		super.updateUsersPhone(diyer, newPhone);
		return diyer;
	}
	
	public DIYers delete(DIYers diyer) throws SQLException {
		String deleteDIYer = "DELETE FROM DIYers WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDIYer);
			deleteStmt.setString (1, diyer.getUserName());
			deleteStmt.executeUpdate();
			
			super.delete(diyer);
			
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
