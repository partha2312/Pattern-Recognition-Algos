import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.*;

public class ClusterAlg {

	LinkedHashMap<List<Double>, Double> sumMap = new LinkedHashMap<List<Double>, Double>();
	HashSet<LinkedHashMap<Double, List<Double>>> clusterData = new HashSet<LinkedHashMap<Double,List<Double>>>();
	HashMap<Double, List<Double>> selMap = new HashMap<Double, List<Double>>();
	DecimalFormat dfObj = new DecimalFormat("#.00");
	static int COUNT=0;
	
	/*
	 * Function to calculate the distance between 2 points
	 */
	public double calcDistance(Double sample, Double center){
		return Math.abs(sample-center);
	}
	
	/*
	 * Calculates the city block distance
	 */
	public double calcCityBlock(List<Double> sampleList, Double centroid){
		double sumOfSquares = 0.0;
		for(Double data : sampleList){
			sumOfSquares+=Math.abs(centroid-data);
		}
		return sumOfSquares;
	}
	
	/*
	 * Calculate Euclidean distance
	 */
	public double calcEuclidean(List<Double> sampleList, Double centroid){
		double sumOfSquares = 0.0;
		for(Double data : sampleList){
			sumOfSquares+=(centroid-data)*(centroid-data);
		}
		return sumOfSquares;
	}
	
	/*
	 * Check if the centers are already present
	 */
	public boolean checkIteration(List<Double> initialCenter){
		if(sumMap.containsKey(initialCenter))
			return false;
		return true;
	}
	
	/*
	 * Decides the number of runs based on input
	 */
	public HashMap<Double,List<Double>> kMeansAlg2(int numOfClusters, String typeOfDist, int numOfRuns){
		for(int i=0;i<numOfRuns;i++){
			kMeansAlg1(numOfClusters, typeOfDist, new ArrayList<Double>());
		}
		//Image imgObject = new Image(selMap);
		return selMap;
	}
	
	/*
	 * K means clustering algorithm
	 */
	public void kMeansAlg1(int numOfClusters, String typeOfDist, List<Double> initialCenter){
		
		List<Double> sampleList = new ArrayList<Double>();
		try {
    	    BufferedReader in = new BufferedReader(new FileReader("src/image.dat"));
    	    String str;
    	    while ((str = in.readLine()) != null)
    	    	sampleList.add(new Double(str));
    	    in.close();
    	} catch (IOException e) {
    	}
		/*sampleList.add(1.0);
		sampleList.add(2.0);
		sampleList.add(3.0);
		sampleList.add(4.0);
		sampleList.add(5.0);
	    sampleList.add(6.0);
		sampleList.add(7.0);
		sampleList.add(8.0);
		sampleList.add(9.0);
		sampleList.add(10.0);
		sampleList.add(11.0);
		sampleList.add(12.0);
		sampleList.add(13.0);
		sampleList.add(14.0);
		sampleList.add(15.0);
	    sampleList.add(16.0);
		sampleList.add(17.0);
		sampleList.add(18.0);
		sampleList.add(19.0);
		sampleList.add(20.0);
		sampleList.add(21.0);
		sampleList.add(22.0);
		sampleList.add(23.0);
		sampleList.add(24.0);
		sampleList.add(25.0);
	    sampleList.add(226.0);
		sampleList.add(227.0);
		sampleList.add(228.0);
		sampleList.add(229.0);
		sampleList.add(230.0);
		sampleList.add(231.0);
		sampleList.add(232.0);
		sampleList.add(233.0);
		sampleList.add(234.0);
		sampleList.add(235.0);
	    sampleList.add(236.0);
		sampleList.add(237.0);
		sampleList.add(238.0);
		sampleList.add(239.0);
		sampleList.add(240.0);
		sampleList.add(241.0);
		sampleList.add(242.0);
		sampleList.add(243.0);
		sampleList.add(244.0);
		sampleList.add(245.0);
	    sampleList.add(246.0);
		sampleList.add(247.0);
		sampleList.add(248.0);
		sampleList.add(249.0);
		sampleList.add(250.0);*/
		if(initialCenter.isEmpty()){
			Collections.shuffle(sampleList);
			int count=0;
			while(count<numOfClusters){
				if(!initialCenter.contains(sampleList.get(count))){
					initialCenter.add(sampleList.get(count));
					count++;
				}
			}
		}
		
		while(checkIteration(initialCenter)){
			
			LinkedHashMap<Double, List<Double>> clusterMap = new LinkedHashMap<Double, List<Double>>();
			List<Double> centroidList = new ArrayList<Double>();
			sumMap.put(initialCenter, 0.0);
			double sumOfSquares = 0.0;
			
			for(Double center : initialCenter){
				clusterMap.put(center, new ArrayList<Double>());
			}
			
			for(Double data : sampleList){
				double min = Double.MAX_VALUE;
				double selVal = 0.0;
				for(Entry<Double, List<Double>> entry:clusterMap.entrySet()){
					double diff = calcDistance(data, (Double)entry.getKey());
					if(diff < min){
						selVal=(Double)entry.getKey();
						min = diff;
					}
				}
				clusterMap.get(selVal).add(data);
			}
			
			for(Entry<Double, List<Double>> entry : clusterMap.entrySet()){
				double sumOfClusters = 0.0;
				double centroid = 0.0;
				for(Double data : entry.getValue()){
					sumOfClusters+=data;
				}
				centroid=sumOfClusters/entry.getValue().size();
				sumOfSquares+=calcEuclidean(entry.getValue(), centroid);
				centroidList.add(new Double(dfObj.format(centroid)));
			}
			
			sumOfSquares=new Double(dfObj.format(Math.sqrt(sumOfSquares)));
			sumMap.put(initialCenter, sumOfSquares);
			
			initialCenter = new ArrayList<Double>(centroidList);
			
			clusterData.add(clusterMap);
		}
		print();
	}
	
	public void print(){
		
		PrintWriter writer=null;
		try {
			if(!new File("output.txt").exists()){
				writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double min = Double.MAX_VALUE;
		List<Double> selCenter = new ArrayList<Double>();
		for(Entry<List<Double>,Double> entry : sumMap.entrySet()){
			if(entry.getValue() < min){
				min = entry.getValue();
				selCenter = entry.getKey();
			}
		}
		for(LinkedHashMap<Double, List<Double>> map : clusterData){
			List<Double> temp = new ArrayList<Double>();
			for(Entry<Double, List<Double>> entry : map.entrySet()){
				temp.add(entry.getKey());
			}
			if(temp.equals(selCenter)){
				for(Entry<Double, List<Double>> entry : map.entrySet()){
					selMap=map;
					
					writer.append('\n');
					writer.append("Center: "+entry.getKey().toString());
					writer.append('\n');
					writer.append("Clusters:");
					writer.append('\n');
					writer.append(entry.getValue().toString());
					writer.append('\n');
					
					//System.out.println(entry.getKey());
					//System.out.println(entry.getValue().size());
					//System.out.println(entry.getValue());
					//System.out.println();
				}
			}
		}
		writer.close();
		sumMap.clear();
		clusterData.clear();
	}
}