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

@WebServlet("/createevent")
public class EventCreate extends HttpServlet {

	protected DIYEventsDao diyEventsDao;
	
	@Override
	public void init() throws ServletException {
		diyEventsDao = DIYEventsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/EventsCreate.jsp").forward(req, resp);
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
            messages.put("success","Invalid UserName");
        } else
			try {
		        DIYersDao diyersDao = DIYersDao.getInstance();
				if (diyersDao.getUserFromUserName(userName) == null){
					messages.put("success", "Could not find user.");
				} else {
					String theme = req.getParameter("theme");
					String description = req.getParameter("description");
					Integer musicId = Integer.parseInt(req.getParameter("musicId"));
					Integer movieId = Integer.parseInt(req.getParameter("movieId"));
					Integer wineId = Integer.parseInt(req.getParameter("wineId"));
					Integer restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
					Integer giftId = Integer.parseInt(req.getParameter("giftId"));
					if (theme == null || description == null || musicId == null || movieId == null || 
							wineId == null || restaurantId == null || giftId == null) {
						messages.put("success", "Please enter a valid element id.");
					} else {
					    try {
					    ElementLists list = new ElementLists(musicId, movieId, wineId, restaurantId, giftId);
					    ElementListsDao	elementListsDao = ElementListsDao.getInstance();
					    ElementLists elementList = elementListsDao.create(list);
					    int listId = elementList.getListID();
					    DIYEvents event = new DIYEvents(DIYEvents.Theme.valueOf(theme), description, listId, userName);
					    event = diyEventsDao.create(event);
					    	messages.put("success", "Successfully created Event " + event.getEventID());
					    } catch (SQLException e) {
							e.printStackTrace();
							throw new IOException(e);
					    }
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        
        req.getRequestDispatcher("/EventsCreate.jsp").forward(req, resp);
    }
}
