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

@WebServlet("/finddiyers")
public class FindDIYers extends HttpServlet {
	protected DIYersDao diyersDao;
	
	@Override
	public void init() throws ServletException {
		diyersDao = DIYersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<DIYers> diyers = new ArrayList<DIYers>();
        
        String firstName = req.getParameter ("firstname");
        if (firstName == null || firstName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid name.");
        } else {
        	try {
        		diyers = diyersDao.getDIYerFromFirstName(firstName);
        	} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + firstName);
        	messages.put("previousFirstName", firstName);
        }
        req.setAttribute("diyers", diyers);
        req.getRequestDispatcher("/FindDIYers.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<DIYers> diyers = new ArrayList<DIYers>();
        
        String firstName = req.getParameter ("firstname");
        if (firstName == null || firstName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid name.");
        } else {
        	try {
        		diyers = diyersDao.getDIYerFromFirstName(firstName);
        	} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + firstName);
        }
        req.setAttribute("diyers", diyers);
        req.getRequestDispatcher("/FindDIYers.jsp").forward(req, resp);
	}
}
