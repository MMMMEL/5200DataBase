package event.servlet;

import event.dal.*;
import event.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatephone")
public class UpdatePhone extends HttpServlet{

	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages =new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		String userName = req.getParameter("username");
		if(userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid UserName.");
		} else {
			try {
				Users user = usersDao.getUserFromUserName(userName);
				if (user == null) {
					messages.put("success", "UserName does not exist.");
				}
				req.setAttribute("user", user);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
		}
		req.getRequestDispatcher("/UpdatePhone.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		String userName = req.getParameter("username");
		if(userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid UserName.");
		} else {
			try {
				Users user = usersDao.getUserFromUserName(userName);
				if (user == null) {
					messages.put("success", "UserName does not exist. No update to perform.");
				} else {
					String newPhone = req.getParameter("phone");
					if (newPhone == null || newPhone.trim().isEmpty() || newPhone.length() != 10) {
						messages.put("success", "Please enter a valid phone number.");
					} else {
						user = usersDao.updateUsersPhone(user, newPhone);
						messages.put("success", "Successfully updated phone for " + userName);
					}
				}
				req.setAttribute("user", user);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
		}	
		req.getRequestDispatcher("/UpdatePhone.jsp").forward(req, resp);
	}
}
