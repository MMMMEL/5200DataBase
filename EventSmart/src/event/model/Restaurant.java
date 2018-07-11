package event.model;

public class Restaurant {
	protected int restaurantID;
	protected String name = " ";
	protected String address;
	protected String phone;
	protected String city;
	protected String state;
	
	public Restaurant(int restaurantID, String name, String address, String phone, String city, String state) {
		super();
		this.restaurantID = restaurantID;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.city = city;
		this.state = state;
	}
	
	public Restaurant(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	
	public int getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
