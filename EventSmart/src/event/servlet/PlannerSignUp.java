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

@WebServlet("/plannersignup")
public class PlannerSignUp extends HttpServlet{

	protected PlannersDao plannersDao;
	
	@Override
	public void init() throws ServletException {
		plannersDao = PlannersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
  
        req.getRequestDispatcher("/PlannerSignUp.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String plannerName = req.getParameter("plannername");
        if (plannerName == null || plannerName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else
			try {
				if (plannersDao.getUserFromUserName(plannerName) != null){
					messages.put("success", "Planner already exists.");
				} else {
					String password = req.getParameter("password");
					String email = req.getParameter("email");
					String firstName = req.getParameter("firstname");
					String lastName = req.getParameter("lastname");
					String phone = req.getParameter("phone");
					String company = req.getParameter("company");
					if (phone.length() != 10) {
						messages.put("success", "Invalid phone number");
					} else {
					    try {
					    	Planners planner = new Planners(plannerName, password, email, firstName, lastName, phone, company);
					    	planner = plannersDao.create(planner);
					    	messages.put("success", "Successfully created " + plannerName);
					    } catch (SQLException e) {
							e.printStackTrace();
							throw new IOException(e);
					    }
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        
        req.getRequestDispatcher("/PlannerSignUp.jsp").forward(req, resp);
    }
}
