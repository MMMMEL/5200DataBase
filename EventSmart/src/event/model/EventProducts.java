package event.model;

public class EventProducts {
	protected int ProductID;
	protected Theme theme;
	protected int size;
	protected PriceRange pricerange;
	protected String description;
	protected String plannerName;
	
	public enum Theme {
		Business, Casual, Holiday, Family
	}
	
	public enum PriceRange {
		$, $$, $$$, $$$$
	}

	public EventProducts(int productID, Theme theme, int size, PriceRange pricerange, String description,
			String plannerName) {
		super();
		ProductID = productID;
		this.theme = theme;
		this.size = size;
		this.pricerange = pricerange;
		this.description = description;
		this.plannerName = plannerName;
	}

	public int getProductID() {
		return ProductID;
	}

	public void setProductID(int productID) {
		ProductID = productID;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PriceRange getPricerange() {
		return pricerange;
	}

	public void setPricerange(PriceRange pricerange) {
		this.pricerange = pricerange;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlannerName() {
		return plannerName;
	}

	public void setPlannerName(String plannerName) {
		this.plannerName = plannerName;
	}
	
	
	
}
