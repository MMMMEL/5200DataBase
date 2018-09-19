package event.servlet;

import event.dal.*;
import event.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatecomment")
public class UpdateComment extends HttpServlet{
	protected CommentsDao commentsDao;
	
	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages =new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		int commentid = Integer.parseInt(req.getParameter("commentid"));
		if (commentid < 1) {
			messages.put("success", "Please enter a valid comment ID.");
		} else {
			try {
				Comments comment = commentsDao.getCommentById(commentid);
				if (comment == null) {
					messages.put("success", "Comment does not exist.");
				}
				req.setAttribute("comment", comment);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		req.getRequestDispatcher("/UpdateComment.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages =new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		int commentid = Integer.parseInt(req.getParameter("commentid"));
		if (commentid < 1) {
			messages.put("success", "Please enter a valid comment ID.");
		} else {
			try {
				Comments comment = commentsDao.getCommentById(commentid);
				if (comment == null) {
					messages.put("success", "Comment does not exist. No update to perform.");
				} else {
					String newContent = req.getParameter("content");
					comment = commentsDao.updateContent(comment, newContent);
					messages.put("success", "Successfully updated content.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		req.getRequestDispatcher("/UpdateComment.jsp").forward(req, resp);
	}
}
