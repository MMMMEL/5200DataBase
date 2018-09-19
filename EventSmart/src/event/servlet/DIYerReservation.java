package event.servlet;

import event.dal.*;
import event.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/diyerreservation")
public class DIYerReservation extends HttpServlet{

	protected DIYersDao diyersDao;
	protected ReservationsDao reservationsDao;
	
	@Override
	public void init() throws ServletException {
		diyersDao = DIYersDao.getInstance();
		reservationsDao = ReservationsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<Reservations> reservations = new ArrayList<Reservations>();
		String diyerName = req.getParameter("diyername");
		
		if (diyerName == null || diyerName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid name.");
		}else {
			try {
        		reservations = reservationsDao.getReservationsByDIYer(diyerName);
        	} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
			messages.put("success", "Displaying results for " + diyerName);
        	messages.put("previousDIYer", diyerName);
		}
		req.setAttribute("reservations", reservations);
		req.getRequestDispatcher("/DIYerReservation.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
        
        List<Reservations> reservations = new ArrayList<Reservations>();
        
        String diyerName = req.getParameter("diyername");
        if (diyerName == null || diyerName.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid name.");
        } else {
        	try {
        		reservations = reservationsDao.getReservationsByDIYer(diyerName);
        	} catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + diyerName);
        }
        req.setAttribute("reservations", reservations);
        req.getRequestDispatcher("/DIYerReservation.jsp").forward(req, resp);
	}
	
}
