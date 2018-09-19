package event.model;

import java.util.Date;

public class Reservations {
	protected int reservationID;
	protected Date created;
	protected Date eventdate;
	protected String username;
	protected String plannername;
	
	public Reservations(int reservationID, Date created, Date eventdate, String username, String plannername) {
		this.reservationID = reservationID;
		this.created = created;
		this.eventdate = eventdate;
		this.username = username;
		this.plannername = plannername;
	}
	
	public Reservations(int reservationID) {
		this.reservationID = reservationID;
	}
	
	public Reservations(Date created, Date eventdate, String username, String plannername) {
		this.created = created;
		this.eventdate = eventdate;
		this.username = username;
		this.plannername = plannername;
	} 

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getEventdate() {
		return eventdate;
	}

	public void setEventdate(Date eventdate) {
		this.eventdate = eventdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlannername() {
		return plannername;
	}

	public void setPlannername(String plannername) {
		this.plannername = plannername;
	}
	
	
	

}
