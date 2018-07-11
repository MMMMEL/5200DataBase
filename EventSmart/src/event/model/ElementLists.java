package event.model;

public class ElementLists {
	protected int listID;
	protected int musicID;
	protected int movieID;
	protected int wineID;
	protected int restaurantID;
	protected int giftID;
	
	public ElementLists (int ListID, int MusicID, int MovieID, int WineID, int RestaurantID, int GiftID) {
		this.listID = ListID;
		this.musicID = MusicID;
		this.movieID = MovieID;
		this.wineID = WineID;
		this.restaurantID = RestaurantID;
		this.giftID = GiftID;	
	}
	
	public ElementLists (int ListID) {
		this.listID = ListID;
		
	}
	
	//getters and setters
	public int getListID() {
		return listID;
	}

	public void setListID(int listID) {
		this.listID = listID;
	}

	public int getMusicID() {
		return musicID;
	}

	public void setMusicID(int musicID) {
		this.musicID = musicID;
	}

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public int getWineID() {
		return wineID;
	}

	public void setWineID(int wineID) {
		this.wineID = wineID;
	}

	public int getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}

	public int getGiftID() {
		return giftID;
	}

	public void setGiftID(int giftID) {
		this.giftID = giftID;
	}

	
}


