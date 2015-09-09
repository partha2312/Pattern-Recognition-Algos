import java.awt.*;
import java.awt.Event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;

public class Image extends JPanel{
	
	HashMap<Double, List<Double>> imgMap = new HashMap<Double, List<Double>>();
	HashSet<List<Double>> clusters = new HashSet<List<Double>>();
	double[] sizesArray =new double[10]; 
	
	public Image(HashMap imgMap){
		this.imgMap=imgMap;
	}
	
	/*
	 * Paints the image based on coordinates
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Dimension dim = getSize();
		int i=0;
		for(Double entry : imgMap.keySet()){
			sizesArray[i]=entry;
			i++;
		}
		
		List<Double> temp = imgMap.get(sizesArray[0]);
		List<Double> temp1 = imgMap.get(sizesArray[1]);
		//System.out.println(temp.size());
		//System.out.println(temp1.size());
		for(int j=0;j<temp.size();j++){
			for(int k=0;k<temp1.size();k++){
				g.drawLine(temp.get(j).intValue(), temp1.get(k).intValue(), temp.get(j).intValue(), temp1.get(k).intValue());
			}
		}
	}
}