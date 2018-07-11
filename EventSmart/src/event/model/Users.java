package event.model;

public class Users extends Persons{
	protected String firstName;
	protected String lastName;
	protected String phone;
	
	public Users(String userName, String password, String email, String firstName, 
			String lastName, String phone) {
		super(userName, password, email);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	
	public Users(String userName) {
		super(userName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
