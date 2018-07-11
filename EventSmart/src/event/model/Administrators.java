package event.model;

import java.util.Date;

public class Administrators extends Persons{
	protected Date lastLogin;
	
	public Administrators(String userName, String passWord, String email, Date lastLogin) {
		super(userName, passWord, email);
		this.lastLogin = lastLogin;
	}
	
	public Administrators(String userName) {
		super(userName);
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
}
