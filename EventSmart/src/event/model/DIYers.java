package event.model;

public class DIYers extends Users{
	
	public DIYers(String userName, String password, String email, String firstName, 
			String lastName, String phone) {
		super(userName, password, email, firstName, 
			lastName, phone);
	}
	
	public DIYers(String userName) {
		super(userName);
	}
	
}
