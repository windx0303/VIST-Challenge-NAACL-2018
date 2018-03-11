package vist.obj;

public class VistAnnotation {
	
	private String original_text;
	private String album_id;
	private String photo_flickr_id;
	private String setting;
	private String worker_id;
	private String story_id;
	private String tier;
	private Integer worker_arranged_photo_order;
	private String text;
	private String storylet_id;
	
	public String toString(){
		return "["+this.story_id+"-"+this.storylet_id+":"+this.worker_arranged_photo_order+"] "+this.original_text+" ("+this.album_id+"/"+this.photo_flickr_id+", by "+this.worker_id+")";
	}
	
	public Integer getWorker_arranged_photo_order() {
		return worker_arranged_photo_order;
	}
	public void setWorker_arranged_photo_order(Integer worker_arranged_photo_order) {
		this.worker_arranged_photo_order = worker_arranged_photo_order;
	}
	public String getOriginal_text() {
		return original_text;
	}
	public void setOriginal_text(String original_text) {
		this.original_text = original_text;
	}
	public String getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}
	public String getPhoto_flickr_id() {
		return photo_flickr_id;
	}
	public void setPhoto_flickr_id(String photo_flickr_id) {
		this.photo_flickr_id = photo_flickr_id;
	}
	public String getSetting() {
		return setting;
	}
	public void setSetting(String setting) {
		this.setting = setting;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}
	public String getStory_id() {
		return story_id;
	}
	public void setStory_id(String story_id) {
		this.story_id = story_id;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getStorylet_id() {
		return storylet_id;
	}
	public void setStorylet_id(String storylet_id) {
		this.storylet_id = storylet_id;
	}
	
	
	
}


