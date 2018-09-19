package event.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import event.dal.*;
import event.model.*;

@WebServlet("/findevents")
public class EventsFind extends HttpServlet{
	protected DIYEventsDao diyEventsDao;
	
	@Override
	public void init() throws ServletException {
		diyEventsDao = DIYEventsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws  ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<DIYEvents> events = new ArrayList<>();
		
		String username = req.getParameter("username");
		if (username == null || username.trim().isEmpty()) {
			messages.put("success", "Please enter a valid name.");
		} else {
			try {
				DIYers user = new DIYers(username);
				events = diyEventsDao.getDIYEventsForUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying DIY events created by " + username);
			messages.put("previousUserName", username);
		}
		req.setAttribute("events", events);
		req.getRequestDispatcher("/FindEvents.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws  ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<DIYEvents> events = new ArrayList<>();
		
		String username = req.getParameter("username");
		if (username == null || username.trim().isEmpty()) {
			messages.put("success", "Please enter a valid name.");
		} else {
			try {
				DIYers user = new DIYers(username);
				events = diyEventsDao.getDIYEventsForUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying DIY events created by " + username);
		}
		req.setAttribute("events", events);
		req.getRequestDispatcher("/FindEvents.jsp").forward(req, resp);
	}
	
	
}
