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

@WebServlet("/findcomments")
public class FindComments extends HttpServlet {
	protected CommentsDao commentsDao;
	
	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<Comments> comments = new ArrayList<Comments>();
        
        Integer eventid = Integer.parseInt(req.getParameter ("eventid"));
        if (eventid == null || eventid.intValue() < 0) {
        	messages.put("success", "Please enter a valid Event ID.");
        } else {
        	try {
        		comments = commentsDao.getCommentsByDIYEventID(eventid);
        	} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for Event " + eventid);
        	messages.put("previousEventId", eventid.toString());
        }
        req.setAttribute("comments", comments);
        req.getRequestDispatcher("/FindComments.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		 Map<String, String> messages = new HashMap<String, String>();
	        req.setAttribute("messages", messages);
	        
	        List<Comments> comments = new ArrayList<Comments>();
	        
	        Integer eventid = Integer.parseInt(req.getParameter ("eventid"));
	        if (eventid == null || eventid.intValue() < 0) {
	        	messages.put("success", "Please enter a valid Event ID.");
	        } else {
	        	try {
	        		comments = commentsDao.getCommentsByDIYEventID(eventid);
	        	} catch (SQLException e) {
	    			e.printStackTrace();
	    			throw new IOException(e);
	            }
	        	messages.put("success", "Displaying results for Event " + eventid);
	        	messages.put("previousEventId", eventid.toString());
	        }
	        req.setAttribute("comments", comments);
	        req.getRequestDispatcher("/FindComments.jsp").forward(req, resp);
	}
}