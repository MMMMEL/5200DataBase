package event.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.dal.DIYEventsDao;
import event.model.DIYEvents;

@WebServlet("/updateevents")
public class EventsUpdate extends HttpServlet{
	
	protected DIYEventsDao diyEventsDao;
	
	@Override
	public void init() throws ServletException {
		diyEventsDao = DIYEventsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		Integer eventId = Integer.parseInt(req.getParameter("eventid"));
		if (eventId == null || eventId.intValue() < 0) {
			messages.put("success", "Invalid Event ID");
		} else {
			DIYEvents diyEvents;
			try {
				diyEvents = diyEventsDao.getDIYEventById(eventId);
				if (diyEvents == null) {
					messages.put("success", "Event ID does not exist. No update to perform.");
				} else {
					String newDescription = req.getParameter("description");
					if (newDescription == null || newDescription.trim().isEmpty()) {
						messages.put("success", "Please enter a valid description");
					} else {
						diyEvents = diyEventsDao.updateDescription(diyEvents, newDescription);
						messages.put("success", "Successfully updated " + newDescription);
					}
				}
				req.setAttribute("diyEvents", diyEvents);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		req.getRequestDispatcher("/EventsUpdate.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException{
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		Integer eventId = Integer.parseInt(req.getParameter("eventid"));
		if (eventId == null || eventId.intValue() < 0) {
			messages.put("success", "Invalid Event Id");
		} else {
			try {
				DIYEvents diyEvents = diyEventsDao.getDIYEventById(eventId);
				if (diyEvents == null) {
					messages.put("success", "Event ID does not exist. No update to perform.");
				} else {
					String newDescription = req.getParameter("description");
					if (newDescription == null || newDescription.trim().isEmpty()) {
						messages.put("success", "Please enter a valid description");
					} else {
						diyEvents = diyEventsDao.updateDescription(diyEvents, newDescription);
						messages.put("success", "Successfully updated description for Event ID " + eventId);
					}
				}
				req.setAttribute("diyEvents", diyEvents);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		req.getRequestDispatcher("/EventsUpdate.jsp").forward(req, resp);
	}
}
