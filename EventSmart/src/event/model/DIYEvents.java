package event.model;

public class DIYEvents {
	protected int eventID;
	protected Theme theme;
	protected String description;
	protected int listID;
	protected String username;
	
	public enum Theme {
		Business, Casual, Holiday, Family
	}
	
	public DIYEvents(int eventID, Theme theme, String description, int listID, String username) {
		super();
		this.eventID = eventID;
		this.theme = theme;
		this.description = description;
		this.listID = listID;
		this.username = username;
	}

	public DIYEvents(Theme theme, String description, int listID, String username) {
		super();
		this.theme = theme;
		this.description = description;
		this.listID = listID;
		this.username = username;
	}
	
	public DIYEvents(int eventId) {
		// TODO Auto-generated constructor stub
		super();
		this.eventID = eventId;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getListID() {
		return listID;
	}

	public void setListID(int listID) {
		this.listID = listID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
