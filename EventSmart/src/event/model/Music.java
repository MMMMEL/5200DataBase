package event.model;

public class Music {
	protected int musicID;
	protected String name = " ";
	protected String artist;
	protected String Genres;
	
	public Music(int musicID, String name, String artist, String genres) {
		this.musicID = musicID;
		this.name = name;
		this.artist = artist;
		this.Genres = genres;
	}
	
	public Music(int musicID) {
		this.musicID = musicID;
	}
	
	public int getMusicID() {
		return musicID;
	}

	public void setMusicID(int musicID) {
		this.musicID = musicID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenres() {
		return Genres;
	}

	public void setGenres(String genres) {
		Genres = genres;
	}

}
