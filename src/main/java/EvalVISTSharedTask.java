/*
 * METEOR is based on: https://www.cs.cmu.edu/~alavie/METEOR/README.html
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import edu.cmu.meteor.scorer.MeteorConfiguration;
import edu.cmu.meteor.scorer.MeteorScorer;
import edu.cmu.meteor.util.Constants;
import vist.obj.VistAlbum;
import vist.obj.VistAnnotation;
import vist.obj.VistChallengeStory;
import vist.obj.VistImage;
import vist.obj.VistStory;

public class EvalVISTSharedTask {
	
	static MeteorConfiguration config;
	static MeteorScorer scorer;
	
	static GsonBuilder builder;
	static Gson gson;
	
	static File testFile;
	static File templateFile;
	static File gsFile;
	
	public static void main(String[] args) throws IOException {

		//java -jar EvalVISTSharedTask -testFile <testFile_path> -gsFile <gsFile_path>
		//test file: the file that the competition team submitted
		//gs (gold-standard) file: the file that contains worker-generated stories
		
		//set arguments
		setArguments(args);
		System.out.println("[Test File] "+testFile.getPath());
		
		//build gson builder 
		builder = new GsonBuilder();
		gson = builder.create();
		
		//created a hash (photo-seq -> list<story_text_normalized>) from gold-standard file
		Hashtable<String, List<String>> gsStoryHash = new Hashtable<String, List<String>>();
		gsStoryHash = getGsStoryHash(gsFile);
		
		//remove the photo sequences that were not included in the VIST Challenge (because owners removed the photos from Flickr)
		gsStoryHash = removeNotEvalSeq(gsStoryHash);
		
		//create testStoryHash (photo-seq -> story_text_normalized)
		Hashtable<String, String> testStoryHash = new Hashtable<String, String>();
		try{
			testStoryHash = getTestStoryHash(testFile);
			System.out.println("[Passed] Test file is in valid JSON syntax.");
			System.out.println("[Passed] Each photo sequence has only one story.");	
		}catch (JsonSyntaxException e){
			printErrorAndExit("Incorrect JSON syntax (JsonSyntaxException).", new VistChallengeStory());
		}
		
		//check if each story in gsFile has a corresponding story in testFile
		for(String nowPhotoSeqKey: gsStoryHash.keySet()){
			if(testStoryHash.get(nowPhotoSeqKey)==null){
				printErrorAndExit("One story is missing in this submission.", nowPhotoSeqKey);
			}
		}
		System.out.println("[Passed] All required stories are submitted.");
		

		config = new MeteorConfiguration();
		System.out.println("MeteorConfiguration...");
		config.setTask("hter");
		System.out.println("setTask...");
		//config.setLanguage("en");
		//config.setNormalization(Constants.NORMALIZE_KEEP_PUNCT);
		scorer = new MeteorScorer(config);
		System.out.println("scorer created...");
		
		//Find the max value of Meteor(Story[test], Story[gs(i)]) for each photo seq
		Hashtable<String, Double> scoreHash = new Hashtable<String, Double>();
		for(String nowPhotoSeqKey: gsStoryHash.keySet()){
			String nowTestStoryText = testStoryHash.get(nowPhotoSeqKey);
			double maxScore = -1;
			for(String nowGsStoryText: gsStoryHash.get(nowPhotoSeqKey)){
				double nowScore = scorer.getMeteorStats(nowTestStoryText, nowGsStoryText).score;
				//System.out.println(nowScore);
				if(nowScore>maxScore){
					maxScore = nowScore;
				}
			}
			if(maxScore!=-1){
				scoreHash.put(nowPhotoSeqKey, new Double(maxScore));
			}else{
				printErrorAndExit("Max Meteor score == -1.", nowPhotoSeqKey);
			}
		}
		
		//calculate average meteor score
		double sum = 0;
		for(String nowPhotoSeqKey: scoreHash.keySet()){
			sum += scoreHash.get(nowPhotoSeqKey).doubleValue();
		}
		double mean = sum/(double)(scoreHash.size());
		System.out.println("--------------------------");
		System.out.println("Avg. Max Meteor Score =");
		System.out.println(mean);
		
	}

	private static void setArguments(String[] args) {
		//-testFile <testFile_path> -templateFile <templateFile_path> -gsFile <gsFile_path> 
		
		boolean testFileSet = false;
		//boolean templateFileSet = false;
		boolean gsFileSet = false;
		
		for(int i=0;i<args.length;i++){
			String nowArg = args[i];
			if(nowArg.startsWith("-")){
				if(nowArg.equals("-testFile")&&!testFileSet){
					
					testFile = new File(args[i+1]); 
					i++;
					testFileSet = true;
					
				}else if(nowArg.equals("-gsFile")&&!gsFileSet){
					
					gsFile = new File(args[i+1]);
					i++;
					gsFileSet = true;
					
				}else{
					System.err.println("Incorrect args: -testFile <testFile_path> -gsFile <gsFile_path>");
					System.exit(1);
				}
			}
		}
		
		if(testFileSet&&gsFileSet){
			//do nothing
		}else{
			System.err.println("Incorrect args: -testFile <testFile_path> -gsFile <gsFile_path>");
			System.exit(1);
		}
		
	}

	/*
	 * photo-seq -> story_text_normalized
	 */
	private static Hashtable<String, String> getTestStoryHash(File testFile) throws FileNotFoundException, JsonSyntaxException {
		
		JsonReader reader = new JsonReader(new FileReader(testFile));
		JsonObject datasetObj = gson.fromJson(reader, JsonObject.class);
		
		Type challengeStoryListType = new TypeToken<ArrayList<VistChallengeStory>>(){}.getType();
		List<VistChallengeStory> storyList = new Gson().fromJson(datasetObj.get("output_stories"), challengeStoryListType);
		for(VistChallengeStory nowStory: storyList){
			if(nowStory.hasNull()){
				printErrorAndExit("One story is missing some attributes.", nowStory);
			}
		}
		
		
		HashSet<String> seenPhotoSeqSet = new HashSet<String>();
		
		Hashtable<String, String> result = new Hashtable<String, String>();
		for(VistChallengeStory nowStory: storyList){
			//if(photoSeqSet.contains(nowPhotoSeqKey)){
			//System.out.println(nowStory.getPhotoIdSeqStr());
			
			if(!seenPhotoSeqSet.contains(nowStory.getPhotoIdSeqStr())){//new story
				seenPhotoSeqSet.add(nowStory.getPhotoIdSeqStr());
				
				result.put(nowStory.getPhotoIdSeqStr(), "");
				if(nowStory.getStory_text_normalized()!=null){
					result.put(nowStory.getPhotoIdSeqStr(), nowStory.getStory_text_normalized());
				}
			}else{
				printErrorAndExit("One photo sequence has more than one stories.", nowStory);
				//System.out.println("[Error] A photo sequence has more than one stories. Photo seq: "+nowStory.getPhotoIdSeqStr());
				//System.exit(1);
			}
			
			
			
			//}
		}
		
		
		return result;
	}

	private static void printErrorAndExit(String errorMsg, String nowPhotoSeqStr) {
		// TODO Auto-generated method stub
		
		String result = "[Error] "+errorMsg;
		//if(nowStory!=null){
			//if(nowStory.getPhotoIdSeqStr()!=null){
				result+=" Photo seq: "+nowPhotoSeqStr;
			//}
		//}
		System.out.println(result);
		System.exit(1);
	}
	
	private static void printErrorAndExit(String errorMsg, VistChallengeStory nowStory) {
		// TODO Auto-generated method stub
		
		String result = "[Error] "+errorMsg;
		if(nowStory!=null){
			if(nowStory.getPhotoIdSeqStr()!=null){
				result+=" Photo seq: "+nowStory.getPhotoIdSeqStr();
			}
		}
		System.out.println(result);
		System.exit(1);
	}

	private static Hashtable<String, List<String>> removeNotEvalSeq(Hashtable<String, List<String>> gsStoryHash) throws FileNotFoundException {
		
		//read template file as JsonObj
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("vist-challenge-template.json");
		JsonReader reader = new JsonReader(new InputStreamReader(is));
		JsonObject datasetObj = gson.fromJson(reader, JsonObject.class);
		
		//convert JsonObj -> List<VistChallengeStory>
		Type challengeStoryListType = new TypeToken<ArrayList<VistChallengeStory>>(){}.getType();
		List<VistChallengeStory> storyList = new Gson().fromJson(datasetObj.get("output_stories"), challengeStoryListType);
		
		//create photo seq HashSet for List<VistChallengeStory>
		HashSet<String> photoSeqSet = new HashSet<String>();
		for(VistChallengeStory nowStory: storyList){
			photoSeqSet.add(nowStory.getPhotoIdSeqStr());
		}
		
		//only keep the photo seqs that are in photo seq HashSet
		Hashtable<String, List<String>> result = new Hashtable<String, List<String>>();
		for(String nowPhotoSeqKey: gsStoryHash.keySet()){
			if(photoSeqSet.contains(nowPhotoSeqKey)){
				result.put(nowPhotoSeqKey, gsStoryHash.get(nowPhotoSeqKey));
			}
		}
		return result;
		
	}

	/*
	 *  created a hash (photo-seq -> list<story_text_normalized>) from gold-standard file
	 */
	private static Hashtable<String, List<String>> getGsStoryHash(File gsFile) throws FileNotFoundException {
		
		JsonReader reader = new JsonReader(new FileReader(gsFile));
		JsonObject datasetObj = gson.fromJson(reader, JsonObject.class);
		
		//get annotation list from gsFile
		List<List<VistAnnotation>> annotationList = getAnnotationList(datasetObj);
		
		//convert annotation list -> (storyId, vistStory)
		Hashtable<String, VistStory> storyMap = new Hashtable<String, VistStory>();
		for(List<VistAnnotation> nowAnnotationList: annotationList){
			if(nowAnnotationList.size()==1){
				VistAnnotation nowAnnoation = nowAnnotationList.get(0);
				String nowStoryId = nowAnnoation.getStory_id();
				String nowAlbumId = nowAnnoation.getAlbum_id();
				if(storyMap.get(nowStoryId)==null){//new
					VistStory nowStory = new VistStory(nowStoryId, nowAlbumId);
					nowStory.getAnnotationList().add(nowAnnoation);
					storyMap.put(nowStoryId, nowStory);
				}else{//old
					VistStory nowStory = storyMap.get(nowStoryId);
					nowStory.getAnnotationList().add(nowAnnoation);
					storyMap.put(nowStoryId, nowStory);
				}
			}else{
				System.err.println("annotation size error");
			}
		}
		
		//convert (storyId, vistStory) -> (photo seq, List<normalized story text>)
		Hashtable<String, List<String>> result = new Hashtable<String, List<String>>();
		for(String nowStoryId: storyMap.keySet()){
			VistStory nowStory = storyMap.get(nowStoryId);
			String nowPhotoSeqStr = nowStory.getPhotoIdSeqStr();
			String nowStroyText = nowStory.getNormalizedStoryText();
			if(result.get(nowPhotoSeqStr)!=null){//old seq
				List<String> nowStoryList = result.get(nowPhotoSeqStr);
				nowStoryList.add(nowStroyText);
				result.put(nowPhotoSeqStr, nowStoryList);
			}else{//new seq
				List<String> nowStoryList = new ArrayList<String>();
				nowStoryList.add(nowStroyText);
				result.put(nowPhotoSeqStr, nowStoryList);
			}
		}
		
		return result;
		
	}
	
	/*
	 * read annotation list from gsFile. Two possible formats: 
	 * 		1. List<List<VistAnnotation>>
	 * 		2. List<VistAnnotation>
	 */
	private static List<List<VistAnnotation>> getAnnotationList(JsonObject datasetObj) {
		try{
			//1. List<List<VistAnnotation>>
			Type annotationListType = new TypeToken<ArrayList<ArrayList<VistAnnotation>>>(){}.getType();
			List<List<VistAnnotation>> annotationList = new Gson().fromJson(datasetObj.get("annotations"), annotationListType);
			return annotationList;
			
		}catch (JsonSyntaxException e) {
			//2. List<VistAnnotation>
			Type annotationListType = new TypeToken<ArrayList<VistAnnotation>>(){}.getType();
			List<VistAnnotation> annotationList = new Gson().fromJson(datasetObj.get("annotations"), annotationListType);
			List<List<VistAnnotation>> result = new ArrayList<List<VistAnnotation>>();
			for(VistAnnotation nowVistAnno: annotationList){
				List<VistAnnotation> nowSmallList = new ArrayList<VistAnnotation>();
				nowSmallList.add(nowVistAnno);
				result.add(nowSmallList);
			}
			return result;
		}
		
	}

}
