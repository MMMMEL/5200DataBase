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

@WebServlet("/voteplanner")
public class VotePlanner extends HttpServlet{
	protected PlannersDao plannersDao;
	
	@Override
	public void init() throws ServletException {
		plannersDao = PlannersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages =new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		String plannerName = req.getParameter("plannername");
		if(plannerName == null || plannerName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid PlannerName.");
		} else {
			try {
				Planners planner = plannersDao.getPlannerFromPlannerName(plannerName);
				if (planner == null) {
					messages.put("success", "Planner does not exist.");
				}
				req.setAttribute("planner", planner);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
		}
		req.getRequestDispatcher("/VotePlanner.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		String plannerName = req.getParameter("plannername");
		if(plannerName == null || plannerName.trim().isEmpty()) {
			messages.put("title", "Invalid PlannerName");
			messages.put("disableSubmit", "true");
		} else {
			try {
				Planners planner = plannersDao.getPlannerFromPlannerName(plannerName);
				if (planner == null) {
					messages.put("title", "Planner does not exist.");
					messages.put("disableSubmit", "false");
				} else {
					plannersDao.votePlanner(planner);
					messages.put("title", "Successfully vote for " + plannerName);
					messages.put("disableSubmit", "true");
				}
				req.setAttribute("planner", planner);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
		}	
		req.getRequestDispatcher("/VotePlanner.jsp").forward(req, resp);
	}
}
