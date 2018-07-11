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

@WebServlet("/diyersignup")
public class DIYerSignUp extends HttpServlet {

	protected DIYersDao diyersDao;
	
	@Override
	public void init() throws ServletException {
		diyersDao = DIYersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/DIYerSignUp.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else
			try {
				if (diyersDao.getUserFromUserName(userName) != null){
					messages.put("success", "UserName already exists.");
				} else {
					String password = req.getParameter("password");
					String email = req.getParameter("email");
					String firstName = req.getParameter("firstname");
					String lastName = req.getParameter("lastname");
					String phone = req.getParameter("phone");
					if (phone.length() != 10) {
						messages.put("success", "Invalid phone number");
					} else {
					    try {
					    	DIYers diyer = new DIYers(userName, password, email, firstName, lastName, phone);
					    	diyer = diyersDao.create(diyer);
					    	messages.put("success", "Successfully created " + userName);
					    } catch (SQLException e) {
							e.printStackTrace();
							throw new IOException(e);
					    }
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        
        req.getRequestDispatcher("/DIYerSignUp.jsp").forward(req, resp);
    }
}
