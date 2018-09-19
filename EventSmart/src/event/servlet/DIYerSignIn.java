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

@WebServlet("/diyersignin")
public class DIYerSignIn extends HttpServlet {
	
	protected DIYersDao diyersDao;
	
	@Override
	public void init() throws ServletException {
		diyersDao = DIYersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        messages.put("notSignin", "true");
        //Just render the JSP.   
        req.getRequestDispatcher("/DIYerSignIn.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        messages.put("notSignin", "true");
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else
			try {
				DIYers diyer = diyersDao.getDIYerFromDIYerName(userName);
				if (diyer == null){
					messages.put("success", "UserName does not exist.");
				} else {
					String password;
					password = req.getParameter("password");
					if (!diyer.getPassWord().equals(password)) {
						messages.put("success", "Incorrect password.");	
					}else {
						messages.put("success", "Welcome!");
						messages.put("notSignin", "false");
					}
				}
				req.setAttribute("diyer", diyer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        req.getRequestDispatcher("/DIYerSignIn.jsp").forward(req, resp);
	}
	
	
}
