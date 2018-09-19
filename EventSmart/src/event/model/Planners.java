package event.model;

public class Planners extends Users{
	protected String company;
	protected int liked = 0;
	
	public Planners(String userName, String password, String email, String firstName, 
			String lastName, String phone, String company, int liked) {
		super (userName, password, email, firstName, lastName, phone);
		this.company = company;
		this.liked = liked;
	}
	
	public Planners(String userName, String password, String email, String firstName, 
			String lastName, String phone, String company) {
		super (userName, password, email, firstName, lastName, phone);
		this.company = company;
	}
	
	public Planners(String plannerName) {
		super(plannerName);
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}
	
	public void vote () {
		++this.liked;
	}
	
}
