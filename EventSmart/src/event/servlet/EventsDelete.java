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

@WebServlet("/deleteevents")
public class EventsDelete extends HttpServlet {
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
		messages.put("title", "Delete Events");
		req.getRequestDispatcher("/EventsDelete.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException{
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		Integer eventId = Integer.parseInt(req.getParameter("eventid"));
		if (eventId == null || eventId.intValue() < 0) {
			messages.put("title", "Invalid Event Id");
			messages.put("disableSubmit", "true");
		} else {
			DIYEvents diyEvents = new DIYEvents(eventId);
			try {
				diyEvents = diyEventsDao.delete(diyEvents);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException();
			}
			if (diyEvents == null) {
				messages.put("title", "Successfully deleted Event " + eventId);
				messages.put("disableSubmit", "true");
			} else {
				messages.put("title", "Failed to delete Event " + eventId);
				messages.put("disableSubmit", "false");
			}
		}
		req.getRequestDispatcher("/EventsDelete.jsp").forward(req, resp);
	}
}
