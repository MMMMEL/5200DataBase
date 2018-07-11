package event.servlet;

import event.dal.*;
import event.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findplanners")
public class FindPlanners extends HttpServlet{

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
		
		List<Planners> planners = new ArrayList<Planners>();
		
		String companyName = req.getParameter("company");
		if (companyName == null || companyName.trim().isEmpty()) {
			messages.put("success", "Please enter a company.");
		} else {
			try {
				planners = plannersDao.getPlannerFromCompanyName(companyName);
			} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
			messages.put("success", "Displaying results for " + companyName);
			messages.put("previousCompanyName", companyName);
		}
		req.setAttribute("planners", planners);
		req.getRequestDispatcher("/FindPlanners.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<Planners> planners = new ArrayList<Planners>();
		
		String companyName = req.getParameter("company");
		if (companyName == null || companyName.trim().isEmpty()) {
			messages.put("success", "Please enter a company.");
		} else {
			try {
				planners = plannersDao.getPlannerFromCompanyName(companyName);
			} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
			messages.put("success", "Displaying results for " + companyName);
		}
		req.setAttribute("planners", planners);
		req.getRequestDispatcher("/FindPlanners.jsp").forward(req, resp);
	}
}
