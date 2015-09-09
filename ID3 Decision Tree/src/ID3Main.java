/*
 * Name: Parthasarathy Krishnamurthy, Pavan Kumar Bodaballa
 * Z-ID: z1729253, z1729238
 * Course: CSCI 677
 * Purpose: To implement ID3 decision tree
 * Due Date: 03/24/2015
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class ID3Main {
	
	public static List<Integer> usedAttributes = new ArrayList<Integer>();
	public static List<String> mapAttributes=Arrays.asList("Outlook","Temperature","Humidity","Wind","PlayTennis");
	public static HashMap<Integer, String> mapAttrNames = new HashMap<Integer, String>(){{
		put(0,"OUTLOOK");
		put(1,"TEMPERATURE");
		put(2,"HUMIDITY");
		put(3,"WIND");
		put(4,"PLAY TENNIS");
	}};
	
	/*
	 * Function to determine if an attribute is already used for ID3 decision making
	 */
	public boolean isUsedAttribute(int attribute){
		if(usedAttributes.contains(attribute)){
			return false;
		}
		return true;
	}
	
	/*
	 * Function to determine number of unique values for each attribute
	 */
	public int setSize(String str){
		switch(str){
		case "Outlook":
			return 3;
		case "Wind":
			return 2;
		case "Temperature":
			return 3;
		case "Humidity":
			return 2;
		case "PlayTennis":
			return 2;
			default:
				return 0;
		}
	}
	
	/*
	 * Function to calculate entropy
	 */
	public double calcEntropy(List<Inputs> data){
		double entropy=0.0;
		int yesCount=0;
		int noCount=0;
		for(int j=0;j<data.size();j++){
			String key = data.get(j).attrList.get(4).value;
			if(key.equalsIgnoreCase("Yes")){
				++yesCount;
			}
			else if(key.equalsIgnoreCase("No")){
				++noCount;
			}
		}
		double yesProb = 0.0;
		double noProb=0.0;
		if(yesCount > 0){
			yesProb = yesCount/(double)data.size();
			yesProb = -(yesProb*(Math.log(yesProb)/Math.log(2)));
		}
		if(noCount > 0){
			noProb = noCount/(double)data.size();
			noProb = -(noProb*(Math.log(noProb)/Math.log(2)));
		}
		entropy=yesProb+noProb;
		return entropy;
	}
	
	/*
	 * Function to calculate gain
	 */
	public double calcGain(double mainEntropy, List<Double> listEntropy, List<Integer> sizes, int data){
		double gain = mainEntropy;
		for(int i=0;i<listEntropy.size();i++){
			gain+= -((sizes.get(i)/(double)data)*listEntropy.get(i));
		}
		return gain;
	}
	
	/*
	 * Main function
	 */
	public static void main(String[] args) throws IOException {
		//Read the data from file
		File file = new File("input.txt");
	    FileReader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    
	    String line;
	    line = br.readLine();
	    List<Attributes> attrList;
	    List<Inputs> inputList = new ArrayList<Inputs>();
	    
	    while((line = br.readLine()) != null){
	    	
	    	Inputs record = new Inputs();
	    	attrList=new ArrayList<Attributes>();
	    	line = line.replaceAll("\\s+", ",");
	    	String[] arr = line.split(",");
	    	String outlook=arr[1];
	    	String temperature=arr[2];
	    	String humidity=arr[3];
	    	String wind=arr[4];
	    	String playTennis=arr[5];
	    	
	    	attrList.add(new Attributes("Outlook",outlook));
	    	attrList.add(new Attributes("Temperature",temperature));
	    	attrList.add(new Attributes("Humidity",humidity));
	    	attrList.add(new Attributes("Wind",wind));
	    	attrList.add(new Attributes("PlayTennis",playTennis));
	    	
	    	record.attrList = attrList;
	    	inputList.add(record);
	    }
	    
	    Tree treeObj = new Tree(inputList);
	    Node root = new Node();
	    root.getData().addAll(inputList);
	    treeObj.structTree(inputList, root);
	    treeObj.modifiedBFSTree(root);
	    System.out.println("----------------------------------------------------------------");
	    List<String> testData1 = new ArrayList<String>();
	    testData1.add("Rain");
	    testData1.add("Mild");
	    testData1.add("Normal");
	    testData1.add("Strong");
	    treeObj.testTree(root, testData1);
	    System.out.println("----------------------------------------------------------------");
	    List<String> testData2 = new ArrayList<String>();
	    testData2.add("Sunny");
	    testData2.add("Mild");
	    testData2.add("Normal");
	    testData2.add("Strong");
	    treeObj.testTree(root, testData2);
	}
}