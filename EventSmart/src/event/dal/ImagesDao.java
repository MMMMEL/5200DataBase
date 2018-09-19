package event.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import event.model.Images;

public class ImagesDao {
	
	protected ConnectionManager connectionManager;
	
	private static ImagesDao instance = null;
	protected ImagesDao() {
		connectionManager = new ConnectionManager();
	}
	public static ImagesDao getInstance() {
		if(instance == null) {
			instance = new ImagesDao();
		}
		return instance;
	}
	
	public Images create(Images image) throws SQLException {
		String insertImage =
			"INSERT INTO Images(URL) " +
			"VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// ElementList has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertImage,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, image.getImage());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int ImageID = -1;
			if(resultKey.next()) {
				ImageID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			image.setImageID(ImageID);
			return image;
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

	public Images getImageByID(int imageId) throws SQLException {
		String selectImage =
			"SELECT ImageID, URL" +
			"FROM Images " +
			"WHERE ImageID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectImage);
			selectStmt.setInt(1, imageId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultImageID = results.getInt("ImageID");
				String image = results.getString("URL");
		
				Images images = new Images(resultImageID, image);
				return images;
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
	
	public Images delete(Images image) throws SQLException {
		
		String deleteimage = "DELETE FROM Images WHERE ImageID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteimage);
			deleteStmt.setInt(1, image.getImageID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Gift instance.
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
