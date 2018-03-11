package vist.obj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class VistStory {
	
	//private List<VistImage> imageList;
	private List<VistAnnotation> annotationList;
	
	private String storyId;
	private String albumId;
	
	public VistStory(String storyId, String albumId){
		//this.imageList = new ArrayList<VistImage>();
		this.annotationList = new ArrayList<VistAnnotation>();
		this.storyId = storyId;
		this.albumId = albumId;
	}
	
	public String getNormalizedStoryText(){
		String result = "";
		this.sortStoryLet();
		//System.out.println(this.annotationList.size());
		for(VistAnnotation nowAnnotation: this.annotationList){
			result += nowAnnotation.getText()+" ";
		}
		return result.trim();
	}
	
	public void sortStoryLet(){
		
		this.annotationList.sort(Comparator.comparingInt(VistAnnotation::getWorker_arranged_photo_order));
		
	}
	
	/*
	public List<VistImage> getImageList() {
		return imageList;
	}
	public void setImageList(List<VistImage> imageList) {
		this.imageList = imageList;
	}
	*/
	
	public List<VistAnnotation> getAnnotationList() {
		return annotationList;
	}
	public void setAnnotationList(List<VistAnnotation> annotationList) {
		this.annotationList = annotationList;
	}
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public boolean allStoryletImagesExist(Hashtable<String, VistImage> imageHash) {
		for(VistAnnotation nowAnnotation: this.annotationList){
			if(imageHash.get(nowAnnotation.getPhoto_flickr_id())!=null){
				if(!imageHash.get(nowAnnotation.getPhoto_flickr_id()).isExistsOnline()){
					return false;
				}
			}else{
				return false;
			}
			
		}
		return true;
	}

	public VistAlbum getAlbum(Hashtable<String, VistAlbum> albumMap) {
		// TODO Auto-generated method stub
		return albumMap.get(this.getAlbumId());
	}

	public String getPhotoIdSeqStr() {
		// TODO Auto-generated method stub
		String result = "";
		for(VistAnnotation nowAnnotation: this.annotationList){
			result += nowAnnotation.getPhoto_flickr_id()+"-";
		}
		return result;
	}

	/*
	public boolean allStoryletImagesExist(Hashtable<String, VistAlbum> albumMap) {
		
		for(VistAnnotation nowAnnotation: this.annotationList){
			List<VistImage> nowImages = albumMap.get(nowAnnotation.getAlbum_id()).getImages();
			for(VistImage nowVistImage: nowImages){
				if(nowVistImage.getId().equals(nowAnnotation.getPhoto_flickr_id())){
					if(!nowVistImage.isExistsOnline()){
						return false;
					}
					break;
				}
			}
		}
		return true;
		
	}
	*/
	
	
}
