/*
 * Class to construct the ID3 decision tree
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Attr;

public class Tree {
	
	List<Inputs> inputList;

	public Tree(List<Inputs> inputList) {
		super();
		this.inputList = inputList;
	}
	
	/*
	 * Construct the tree
	 * Input arguments are list of attributes class and a Node
	 * Returns the tree
	 */
	public Node structTree(List<Inputs> inputList, Node root){
		int bestAttribute=-1;
		double bestGain=0.0;
		
		root.setEntropy(new ID3Main().calcEntropy(inputList));
		
		if(root.getEntropy() == 0.0) {
			return root;
		}
		
		for(int i=0;i<4;i++){
			if(new ID3Main().isUsedAttribute(i)){
				double entropy = 0.0;
				List<Double> entropyList = new ArrayList<Double>();
				List<Integer> setSizes = new ArrayList<Integer>();
				
				HashSet<String> values = new LinkedHashSet<String>();
				for(Inputs ip : inputList){
					String temp = ip.getAttrList().get(i).value;
					values.add(temp);
				}
				
				for(String str : values){
					List<Inputs> subInput = getSubInput(root, i, str);
					setSizes.add(subInput.size());
					entropy = new ID3Main().calcEntropy(subInput);
					entropyList.add(entropy);
				}
				
				double gain = new ID3Main().calcGain(root.getEntropy(), entropyList, setSizes, root.getData().size());
				
				if(gain > bestGain){
					bestGain = gain;
					bestAttribute = i;
				}
			}
		}
			if(bestAttribute != -1){
				int setSize = new ID3Main().setSize(ID3Main.mapAttributes.get(bestAttribute));
				root.nodeChildren = new Node[setSize];
				root.setFlag(true);
				root.setSplitAttr(ID3Main.mapAttrNames.get(bestAttribute));
				ID3Main.usedAttributes.add(bestAttribute);
				int j=0;
				HashSet<String> values = new LinkedHashSet<String>();
				for(Inputs ip :inputList){
					String str = ip.attrList.get(bestAttribute).value;
					values.add(str);
				}
				for(String str:values){
					root.nodeChildren[j] = new Node();
					root.nodeChildren[j].setNodeParent(root);
					root.nodeChildren[j].setData(getSubInput(root, bestAttribute, str));
					root.nodeChildren[j].setAttrName(str);
					j++;
				}
				for(int k=0;k<setSize;k++){
					structTree(root.nodeChildren[k].getData(), root.nodeChildren[k]);
				}
				root.setData(null);
			}
			else{
				return root;
			}
		return root;
	}
	
	/*
	 * Returns the reduced list of inputs for particular attribute
	 */
	public List<Inputs> getSubInput(Node root, int attr, String val){
		List<Inputs> subInput = new ArrayList<Inputs>();
		for(int i=0;i<root.getData().size();i++){
			Inputs input = root.getData().get(i);
			if(input.attrList.get(attr).value.equals(val)){
				subInput.add(input);
			}
		}
		return subInput;
	}

	/*
	 * Returns number of the attribute based on the name
	 */
	public int returnKey(String attr){
		for(Entry<Integer, String> entry : ID3Main.mapAttrNames.entrySet()){
			if(entry.getValue().equals(attr)){
				return entry.getKey();
			}
		}
		return -1;
	}
	
	/*
	 * Tree traversal algorithm to print the decision tree
	 */
	public void modifiedBFSTree(Node root){
		System.out.println("Tree:");
		System.out.println();
		List<Node> listQue = new ArrayList<Node>();
		listQue.add(root);
		while(!listQue.isEmpty()){
			Node temp = listQue.remove(0);
			HashMap<String, String> mapLabel = new HashMap<String, String>();
			System.out.println("Split Feature: "+returnKey(temp.getSplitAttr())+" "+temp.getSplitAttr());
			if(temp.nodeChildren != null){
				int childCount=0;
				System.out.println("Children:");
				for(Node child : temp.getNodeChildren()){
					System.out.println("Child "+(++childCount)+" Branch value: "+child.getAttrName());
					if(child.getSplitAttr() != null){
						listQue.add(child);
					}
					if(child.nodeChildren == null){
						String label = child.getData().get(0).getAttrList().get(4).value;
						if(label.equalsIgnoreCase("yes")){
							label="Play Tennis";
						}
						else if(label.equalsIgnoreCase("no")){
							label="Don't Play Tennis";
						}
						mapLabel.put(child.getAttrName(), label);
					}
				}
			}
			for(Entry<String, String> entry : mapLabel.entrySet()){
				System.out.println("Label for "+entry.getKey() + " is "+entry.getValue());
			}
			System.out.println();
		}
	}
	
	/*
	 * Function to test the decision tree with sample data
	 */
	public void testTree(Node root, List<String> input){
		System.out.print("Testing case ");
		for(String str : input){
			System.out.print(str+" ");
		}
		System.out.println();
		if(root == null){
			System.out.println("Tree empty");
			return;
		}
		Node temp = root;
		while(temp.nodeChildren != null){
			int splitRule = returnKey(temp.getSplitAttr());
			System.out.println("Testing node: "+temp.getSplitAttr());
			int childCount = 0;
			for(Node child : temp.nodeChildren){
				System.out.println("Checking child "+(childCount++)+" with split rule feature "+splitRule);
				if(input.contains(child.getAttrName())){
					temp = child;
				}
			}
		}
		System.out.println("Testing node: "+temp.getAttrName());
		String label = temp.getData().get(0).getAttrList().get(4).value;
		System.out.println("Reach leave node: "+label);
		if(label.equalsIgnoreCase("yes")){
			label="Will play tennis on such a day";
		}
		else if(label.equalsIgnoreCase("no")){
			label="Won't play tennis on such a day";
		}
		System.out.println(label);
		System.out.println();
	}
}