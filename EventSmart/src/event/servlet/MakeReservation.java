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

@WebServlet("/makereservation")
public class MakeReservation extends HttpServlet{

	protected DIYersDao diyersDao;
	protected PlannersDao plannersDao;
	protected ReservationsDao reservationsDao;
	
	@Override
	public void init() throws ServletException {
		diyersDao = DIYersDao.getInstance();
		plannersDao = PlannersDao.getInstance();
		reservationsDao = ReservationsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		req.getRequestDispatcher("/MakeReservation.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		String plannerName = req.getParameter("plannername");
		if (plannerName == null || plannerName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
		} else {
			try {
				if (plannersDao.getPlannerFromPlannerName(plannerName) == null) {
					messages.put("success", "Planner does not exist.");
				}else {
					String diyerName = req.getParameter("diyername");
					if (diyersDao.getDIYerFromDIYerName(diyerName) == null) {
						messages.put("success", "DIYer does not exist.");
					}else {
						Date created = new Date();
						String year = req.getParameter("year");
						String month = req.getParameter("month");
						String day = req.getParameter("day");
						StringBuilder sb = new StringBuilder();
						sb.append(year);
						sb.append('-');
						sb.append(month);
						sb.append('-');
						sb.append(day);
						String date = sb.toString();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date eventDate = new Date();
						try {
							eventDate = dateFormat.parse(date);
						} catch (ParseException e) {
							e.printStackTrace();
							throw new IOException(e);
						}
						if (reservationsDao.getReservationsByPlanner(plannerName, eventDate) != null) {
							messages.put("success", "Date is already reserved.");
						}else {
							try {
								Reservations reservation = new Reservations(created, eventDate, diyerName, plannerName);
								reservationsDao.create(reservation);
								messages.put("success", "Successfully created reservation with " + plannerName + " for " + diyerName);
							} catch (SQLException e) {
								e.printStackTrace();
								throw new IOException(e);
						    }
						}
					}
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		req.getRequestDispatcher("/MakeReservation.jsp").forward(req, resp);
	}
	
}
