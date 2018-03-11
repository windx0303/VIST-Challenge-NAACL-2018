package vist.obj;

public class VistChallengeStory {

	private String album_id;
	private String[] photo_sequence;
	
	public String getPhotoIdSeqStr() {
		if(this.photo_sequence==null){
			return null;
		}
		
		// TODO Auto-generated method stub
		String result = "";
		for(String nowId: this.photo_sequence){
			result += nowId+"-";
		}
		return result;
	}

	public String getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}
	public String[] getPhoto_sequence() {
		return photo_sequence;
	}
	public String getStory_text_normalized() {
		return story_text_normalized;
	}


	public void setStory_text_normalized(String story_text_normalized) {
		this.story_text_normalized = story_text_normalized;
	}


	public void setPhoto_sequence(String[] photo_sequence) {
		this.photo_sequence = photo_sequence;
	}
	
	
	private String story_text_normalized;

	public boolean hasNull() {
		if(album_id==null||photo_sequence==null||story_text_normalized==null){
			return true;
		}
		return false;
	}
	
	
	
}
