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

@WebServlet("/deletereservation")
public class ReservationDelete extends HttpServlet{
	
	protected ReservationsDao reservationsDao;
	
	@Override
	public void init() throws ServletException {
		reservationsDao = ReservationsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Reservation");        
        req.getRequestDispatcher("/ReservationDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        int reservationid = Integer.parseInt(req.getParameter("reservationid"));
        if (reservationid < 1) {
            messages.put("title", "Invalid UserName");
            messages.put("disableSubmit", "true");
        } else {

	        Reservations reservation = new Reservations(reservationid);
	        try {
	        	reservation = reservationsDao.delete(reservation);
	        	// Update the message.
		        if (reservation == null) {
		            messages.put("title", "Successfully deleted reservation " + reservationid);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete reservation " + reservationid);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/ReservationDelete.jsp").forward(req, resp);
    }
}
