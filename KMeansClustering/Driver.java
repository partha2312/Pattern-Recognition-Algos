/*
 * Name: Parthasarathy Krishnamurthy, Pavan Kumar Bodaballa
 * Z-ID: z1729253, z1729238
 * Course: CSCI 677
 * Purpose: To implement K means clustering algorithm.
 * Due Date: 02/11/2015
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver extends JFrame{

	JPanel jp = new JPanel();
	public Driver(HashMap selMap){
		Container contentPanel = getContentPane();
		contentPanel.setLayout(new BorderLayout());
		Image imgObj = new Image(selMap);
		jp.add(imgObj,BorderLayout.CENTER);
		jp.setBackground(Color.CYAN);
		contentPanel.add(jp);
		//contentPanel.setBackground(Color.RED);
	}
	
	public static void main(String[] args) throws IOException {
		
		String input="";
		int numOfClusters=0;
		String typeOfDistance="";
		List<Double> initialCenter = new ArrayList<Double>();
		int numOfRuns=0;
		HashMap<Double,List<Double>> selMap = new HashMap<Double, List<Double>>();
		/*
		 * Getting input from user
		 */
		System.out.println("Enter type of distance. 1. Euclidean 2. City Block");
		while(true){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
			if(input.equals("Euclidean")||input.equals("City Block")){
				typeOfDistance=input;
				break;
			}
			else{
				System.out.println("Enter type of distance. 1. Euclidean 2. City Block");
			}
		}
		
		System.out.println("Enter number of clusters:");
		while(true){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
			if(input.equals("0")){
				System.out.println("Enter value greater than 0");
			}
			else{
				try {
					numOfClusters=Integer.parseInt(input);
					break;
				} catch (NumberFormatException e) {
					System.out.println("Please enter number");
				}
			}
		}
		
		System.out.println("Enter initial centers, N if you dont want to give");
		int i=0;
		while(i<numOfClusters){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
			if(input.equals("N")){
				System.out.println("Enter number of runs, minimum 1");
				input = br.readLine();
				numOfRuns=Integer.parseInt(input);
				break;
			}
			double center = Double.parseDouble(input);
			if(initialCenter.contains(center)){
				System.out.println("Enter different center");
			}
			else{
				initialCenter.add(center);
				i++;
			}
		}
		
		ClusterAlg algObj = new ClusterAlg();
		if(numOfRuns>0){
			selMap = algObj.kMeansAlg2(numOfClusters, typeOfDistance, numOfRuns);
		}
		else{
			algObj.kMeansAlg1(numOfClusters, typeOfDistance, initialCenter);
		}
		//Image imgObj = new Image(selMap);
		Driver driverObj = new Driver(selMap);
		driverObj.setSize(600,600);
		driverObj.setVisible(true);
		//driverObj.getContentPane().setBackground(Color.RED);
		driverObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}