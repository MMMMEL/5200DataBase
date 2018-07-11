package event.model;

public class Persons {
	protected String userName;
	protected String passWord;
	protected String email;
	
	public Persons (String username, String password, String email) {
		this.userName = username;
		this.passWord = password;
		this.email = email;
	}
	
	public Persons (String username) {
		this.userName = username;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
