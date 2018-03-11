package vist.obj;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VistImage {
	
	private String datetaken;
	private String license;
	private String title;
	private String text;
	private String album_id;
	private String longitude;
	private String url_o;
	private String secret;
	private String media;
	private String latitude;
	private String id;
	private String tags;
	
	private static SimpleDateFormat dateFormat;
	
	private Timestamp timestampTaken;
	
	private boolean existsOnline;
	
	public VistImage(){
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public void setTimestamp(){
		//String text = "2011-10-02 18:48:05.123456";
		//System.out.println(this.datetaken);
        //Timestamp timestampTaken = Timestamp.valueOf(this.datetaken);
		try {
			Date parsedTimeStamp = dateFormat.parse(this.datetaken);
			this.timestampTaken = new Timestamp(parsedTimeStamp.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Timestamp getTimestampTaken(){
		setTimestamp();
		return timestampTaken;
	}
	
	public long getTimestampTakenLong(){
		setTimestamp();
		return timestampTaken.getTime();
	}
	
	public boolean isExistsOnline() {
		return existsOnline;
	}

	public void setExistsOnline(boolean existsOnline) {
		this.existsOnline = existsOnline;
	}

	public String toString(){
		return "["+this.id+"] "+this.title+" ("+this.album_id+", "+this.url_o+")";
	}
	
	public String getDatetaken() {
		return datetaken;
	}
	public void setDatetaken(String datetaken) {
		this.datetaken = datetaken;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getUrl_o() {
		return url_o;
	}
	public void setUrl_o(String url_o) {
		this.url_o = url_o;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	

}
