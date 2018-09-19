package event.model;

import java.sql.Blob;

public class Images {
	protected int imageID;
	protected String url;
	
	public Images (String image) {
		this.url = image;
	}
	
	public Images (int imageID) {
		this.imageID = imageID;
	}
	
	public Images (int imageID, String image) {
		this.imageID = imageID;
		this.url = image;
	}
	
	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getImage() {
		return url;
	}

	public void setImage(String image) {
		this.url = image;
	}
}
