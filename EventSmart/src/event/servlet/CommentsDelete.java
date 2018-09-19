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

import event.dal.CommentsDao;
import event.model.Comments;

@WebServlet("/deletecomments")
public class CommentsDelete extends HttpServlet {
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
		messages.put("title", "Delete Comment");
		req.getRequestDispatcher("/CommentsDelete.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException{
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		Integer commentId = Integer.parseInt(req.getParameter("commentid"));
		if (commentId == null || commentId.intValue() < 0) {
			messages.put("title", "Invalid Comment ID");
			messages.put("disableSubmit", "true");
		} else {
			Comments comment = new Comments(commentId);
			try {
				comment = commentsDao.delete(comment);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOException();
			}
			if (comment == null) {
				messages.put("title", "Successfully deleted Comment " + commentId);
				messages.put("disableSubmit", "true");
			} else {
				messages.put("title", "Failed to delete Comment " + commentId);
				messages.put("disableSubmit", "false");
			}
		}
		req.getRequestDispatcher("/CommentsDelete.jsp").forward(req, resp);
	}
}