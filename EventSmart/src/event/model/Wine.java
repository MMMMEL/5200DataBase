package event.model;

public class Wine {

	protected int wineID;
	protected String name = " ";
	protected String country;
	protected float price;
	protected String description;
	
	public Wine(int wineID, String name, String country, float price, String description) {
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
