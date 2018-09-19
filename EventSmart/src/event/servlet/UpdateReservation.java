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

@WebServlet("/updatereservation")
public class UpdateReservation extends HttpServlet{

	protected ReservationsDao reservationsDao;
	
	@Override
	public void init() throws ServletException {
		reservationsDao = ReservationsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages =new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		int reservationid = Integer.parseInt(req.getParameter("reservationid"));
		if(reservationid < 1) {
			messages.put("success", "Please enter a valid reservation ID.");
		} else {
			try {
				Reservations reservation = reservationsDao.getReservationById(reservationid);
				if (reservation == null) {
					messages.put("success", "Reservation does not exist.");
				}
				req.setAttribute("reservation", reservation);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
		}
		req.getRequestDispatcher("/UpdateReservation.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		int reservationid = Integer.parseInt(req.getParameter("reservationid"));
		if(reservationid < 1) {
			messages.put("success", "Please enter a valid reservation ID.");
		} else {
			try {
				Reservations reservation = reservationsDao.getReservationById(reservationid);
				if (reservation == null) {
					messages.put("success", "Reservation does not exist. No update to perform.");
				} else {
					String newYear = req.getParameter("year");
					String newMonth = req.getParameter("month");
					String newDay = req.getParameter("day");
					if (newYear == null || newYear.trim().isEmpty() ||
						newMonth == null || newMonth.trim().isEmpty() || 
						newDay == null || newDay.trim().isEmpty()) {
						messages.put("success", "Please enter a valid date.");
					} else {
						StringBuilder sb = new StringBuilder();
						sb.append(newYear);
						sb.append('-');
						sb.append(newMonth);
						sb.append('-');
						sb.append(newDay);
						String newDate = sb.toString();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date newEventDate = new Date();
						try {
							newEventDate = dateFormat.parse(newDate);
							reservation = reservationsDao.updateEventDate(reservation, newEventDate);
							messages.put("success", "Successfully updated reservation");
						} catch (ParseException e) {
							e.printStackTrace();
							throw new IOException(e);
						}	
					}	
				}
				req.setAttribute("reservation", reservation);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
		}
		req.getRequestDispatcher("/UpdateReservation.jsp").forward(req, resp);
	}
	
	
}
