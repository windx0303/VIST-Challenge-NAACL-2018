package vist.obj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VistAlbum {
	
	private String description;
	private String title;
	private String farm;
	private String date_update;
	private String primary;
	private String server;
	private String date_create;
	
	private List<VistImage> images;
	
	public VistAlbum(){
		this.images = new ArrayList<VistImage>();
	}
	
	public List<VistImage> getImages() {
		return images;
	}
	public void setImages(List<VistImage> images) {
		this.images = images;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFarm() {
		return farm;
	}
	public void setFarm(String farm) {
		this.farm = farm;
	}
	public String getDate_update() {
		return date_update;
	}
	public void setDate_update(String date_update) {
		this.date_update = date_update;
	}
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getDate_create() {
		return date_create;
	}
	public void setDate_create(String date_create) {
		this.date_create = date_create;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getVist_label() {
		return vist_label;
	}
	public void setVist_label(String vist_label) {
		this.vist_label = vist_label;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String photos;
	private String secret;
	private String owner;
	private String vist_label;
	private String id;
	
	public void sortImagesByTakenTime(){
		this.images.sort(Comparator.comparingLong(VistImage::getTimestampTakenLong));
	}
	
}


