package event.model;

public class Wine {

	protected int wineID;
	protected String name = " ";
	protected String country;
	protected int price;
	protected String description;
	
	public Wine(int wineID, String name, String country,  String description, int price) {
		this.wineID = wineID;
		this.name = name;
		this.country = country;
		this.price = price;
		this.description = description;
	}
	
	public Wine(int wineID) {
		this.wineID = wineID;
	}
	
	public int getWineID() {
		return wineID;
	}

	public void setWineID(int wineID) {
		this.wineID = wineID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
