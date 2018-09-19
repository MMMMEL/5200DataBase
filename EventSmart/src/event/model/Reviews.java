package event.model;

import java.sql.Date;

public class Reviews {
	protected int reviewid;
	protected Date created;
	protected float rating;
	protected String content;
	protected String username;
	protected int productid;
	
	public Reviews(int reviewid, Date created, float rating, String content, String username, int productid) {
		super();
		this.reviewid = reviewid;
		this.created = created;
		this.rating = rating;
		this.content = content;
		this.username = username;
		this.productid = productid;
	}

	public int getReviewid() {
		return reviewid;
	}

	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}
	
	

}
