/*
 * Tree class
 */
import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node nodeParent;
	public Node[] nodeChildren;
	private List<Inputs> data;
	private double entropy;
	private boolean flag;
	private String attrName;
	private String splitAttr;
	
	public Node() {
		super();
		this.data = new ArrayList<Inputs>();
		setEntropy(0.0);
		setNodeParent(null);
		setNodeChildren(null);
		setFlag(false);
		setAttrName(null);
		setSplitAttr(null);
	}
	
	public Node getNodeParent() {
		return nodeParent;
	}
	public void setNodeParent(Node nodeParent) {
		this.nodeParent = nodeParent;
	}
	
	public Node[] getNodeChildren() {
		return nodeChildren;
	}
	public void setNodeChildren(Node[] nodeChildren) {
		this.nodeChildren = nodeChildren;
	}
	
	public List<Inputs> getData() {
		return data;
	}
	public void setData(List<Inputs> data) {
		this.data = data;
	}
	
	public double getEntropy() {
		return entropy;
	}
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getSplitAttr() {
		return splitAttr;
	}

	public void setSplitAttr(String splitAttr) {
		this.splitAttr = splitAttr;
	}
}