package event.model;

public class Gift {

	protected int giftID;
	protected String name = " ";
	protected float price;
	protected String category;
	protected String description;
	
	public Gift(int giftID, String name, float price, String category, String description) {
		super();
		this.giftID = giftID;
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
	}
	
	public Gift(int giftID) {
		this.giftID = giftID;
	}

	public int getGiftID() {
		return giftID;
	}

	public void setGiftID(int giftID) {
		this.giftID = giftID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
