package event.servlet;

import event.dal.*;
import event.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createcomment")
public class CreateComment extends HttpServlet {
	
	protected CommentsDao commentsDao;
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		req.getRequestDispatcher("/CreateComment.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		int eventId = Integer.parseInt(req.getParameter("eventid"));
		if (eventId < 1) {
			messages.put("success", "Invalid Event");
		} else {
			Date created = new Date();
			String content = req.getParameter("content");
			String userName = req.getParameter("username");
			try {
				if (usersDao.getUserFromUserName(userName) == null) {
					messages.put("success", "User does not exist.");
				}else {
					Comments comment = new Comments (created, content, userName, eventId);
					commentsDao.create(comment);
					messages.put("success", "Successfully created comment for event.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		req.getRequestDispatcher("/CreateComment.jsp").forward(req, resp);
	}
}
