import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Controller implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553640819306325516L;
	private NodeList nodelist = new NodeList();
	private LinkList linklist = new LinkList();
	private IDNumber ID = IDNumber.instance();
	
	private FileOutputStream file; 
	private ObjectOutputStream output; 
	
	private FileInputStream fileIn; 
	private ObjectInputStream input;
	
	private static Controller singleton;
	
	
	//Instance method for singleton purposes.
	public static Controller instance() {
		
		if(singleton == null)
			singleton = new Controller();
		
		
		return singleton;
	}
	
	public String addNode(String name, String x, String y) {
		Node n;
		int intX = 0;
		int intY = 0;
		
		try {
			intX = Integer.parseInt(x);
			intY = Integer.parseInt(y);
		}
		catch(NumberFormatException nfe) {
			return "Please give valid coordinates!";
		}
		
		if(checkExistanceOfNode(name) == true) 
			return "Node " + name + " already exists. Failed to create node!";
		
		if(checkCoords(intX, intY) == true)
			return "Please give valid coordinates! Nodes are too close.";
		
		boolean flag = false;
		if(name.equals("")) {
			while(flag == false) {
				int temp = ID.generateNodeID();
				String tempStr = "NODE-" + temp;
				if(checkExistanceOfNode(tempStr) == false) {
					name = tempStr;
					flag = true;
				}
			}
		}
		
		n = new Node(name, intX, intY);
		
		if(nodelist.addNode(n) == true)
			return "Node " + n.getID() + " successfully created!";
		else
			return "Failed to create node!";
		
	}
	
	public String addLink(String name, String n1, String n2) {
		Link l;
		
		
		
		if(n1.equals(n2))
			return "Nodes have the same name! Failed to create link!";
		
		if(checkExistanceOfNode(n1) == false) 
			return "Node " + n1 + " doesn't exist. Failed to create link!";
		
		if(checkExistanceOfNode(n2) == false)
			return "Node " + n2 + " doesn't exist. Failed to create link!";
			
		if(checkExistanceOfLink(name) == true)
			return "Link already exists! Failed to create link!";
		
		if(checkExistanceOfConnectedNodes(n1, n2) == true)
			return "Link already exists that connects these nodes! Failed to create link!";
		
		
		boolean flag = false;
		if(name.equals("")) {
			while(flag == false) {
				int temp = ID.generateLinkID();
				String tempStr = "LINK-" + temp;
				if(checkExistanceOfLink(tempStr) == false) {
					name = tempStr;
					flag = true;
				}
			}
		}
		
		int xDifference = getNodeByName(n1).getXCoord() - getNodeByName(n2).getXCoord();
		int yDifference = getNodeByName(n1).getYCoord() - getNodeByName(n2).getYCoord();
		int distance = (int) (Math.sqrt(xDifference * xDifference + yDifference * yDifference));
		int ms = (int) distance;
		
		
		l = new Link(name, n1, n2, ms);
		
		
		if(linklist.addLink(l) == true) {
			getNodeByName(n1).addLink(l.getID());
			getNodeByName(n2).addLink(l.getID());
			return "Link " + l.getID() + " successfully created!";
		}
		else
			return "Failed to create link!";
		
	}
	
	
	public void printNodes() {
		System.out.println("Node List:");
		for(int i = 0; i < nodelist.getSize(); i++)
			System.out.println(nodelist.getNode(i).getID());
		System.out.print("\n");
	}
	
	public void printLinks() {
		System.out.println("Link List:");
		for(int i = 0; i < linklist.getSize(); i++)
			System.out.println(linklist.getLink(i).getID());
		System.out.print("\n");
	}
	
	public ArrayList<String> getNodes() {
		ArrayList<String> temp = new ArrayList<String>();
		
		for(int i = 0; i < nodelist.getSize(); i++) {
			temp.add("Node " + nodelist.getNode(i).getID() + " has links: " + nodelist.getNode(i).getLinks());
		}
		
		return temp;
	}
	
	public NodeList getNodeObjects() {
		return nodelist;
	}
	
	public ArrayList<String> getLinks() {
		ArrayList<String> temp = new ArrayList<String>();
		
		for(int i = 0; i < linklist.getSize(); i++) {
			temp.add("Link " + linklist.getLink(i).getID() + " connects " + linklist.getLink(i).getNodes() + " with a delay of " + linklist.getLink(i).getMs() + " ms");
		}
		
		return temp;
	}
	
	public LinkList getLinkObjects() {
		return linklist;
	}
	
	public boolean checkExistanceOfNode(String name) {
		for(int i = 0; i < nodelist.getSize(); i++) {
			if(name.equals(nodelist.getNode(i).getID()))
				return true;
		}
		return false;
	}
	
	public boolean checkExistanceOfLink(String name) {
		for(int i = 0; i < linklist.getSize(); i++) {
			if(name.equals(linklist.getLink(i).getID()))
				return true;
		}
		return false;
	}
	
	public Node getNodeByName(String name) {
		for(int i = 0; i < nodelist.getSize(); i++) {
			if(name.equals(nodelist.getNode(i).getID()))
				return nodelist.getNode(i);
		}
		return new Node("",0,0);
	}
	
	public Node getNode(int index) {
		return nodelist.getNode(index);
	}
	
	public Link getLink(int index) {
		return linklist.getLink(index);
	}
	
	public Link getLinkByName(String name) {
		for(int i = 0; i < linklist.getSize(); i++) {
			if(name.equals(linklist.getLink(i).getID()))
				return linklist.getLink(i);
		}
		return new Link("");
	}
	
	public boolean checkExistanceOfConnectedNodes(String n1, String n2) {
		for(int i = 0; i < linklist.getSize(); i++) {
			if((n1 + " and " + n2).equals(linklist.getLink(i).getNodes()) || (n2 + " and " + n1).equals(linklist.getLink(i).getNodes()))
				return true;
		}
		return false;
	}
	
	public ArrayList<Point> getNodeCoords() {
		ArrayList<Point> temp = new ArrayList<Point>();
		for(int i = 0; i < nodelist.getSize(); i++) {
			temp.add(nodelist.getNode(i).getCoords());
		}
		
		return temp;
	}
	
	public ArrayList<Node> getActualNodes() {
		ArrayList<Node> temp = new ArrayList<Node>();
		for(int i = 0; i < nodelist.getSize(); i++) {
			temp.add(nodelist.getNode(i));
		}
		
		return temp;
	}
	
	public ArrayList<Link> getActualLinks() {
		ArrayList<Link> temp = new ArrayList<Link>();
		for(int i = 0; i < linklist.getSize(); i++) {
			temp.add(linklist.getLink(i));
		}
		
		return temp;
	}
	
	public ArrayList<Link> getAssociatedLinks() {
		ArrayList<Link> temp = new ArrayList<Link>();
		for(int i = 0; i < nodelist.getSize(); i++) {
			for(int j = 0; j < nodelist.getNode(i).getLinkNames().size(); j++) {
				temp.add(getLinkByName(nodelist.getNode(i).getLinkNames().get(j)));
			}
			
		}
		
		return temp;
	}
	
	public ArrayList<Point> getPairs() {
		ArrayList<Point> temp = new ArrayList<Point>();
		for(int i = 0; i < linklist.getSize(); i++) {
			temp.add(getNodeByName(linklist.getLink(i).getActualNodes().get(0)).getCoords());
			temp.add(getNodeByName(linklist.getLink(i).getActualNodes().get(1)).getCoords());
		}
		
		return temp;
	}
	
	public boolean tooClose(int x1, int y1, int x2, int y2, int d) {
		int xDifference = x1 - x2;
		int yDifference = y1 - y2;
		int distance = ((int) (Math.sqrt(xDifference * xDifference + yDifference * yDifference)));
		if(distance < d)
			return true;
		return false;
	}
	
	public int withinNode(int x, int y) {
		for(int i = 0; i < nodelist.getSize(); i++) {
			if(tooClose(x, y, nodelist.getNode(i).getXCoord(), nodelist.getNode(i).getYCoord(), 40) == true)
				return i;
		}
		return -1;
	}
	
	public int withinMSSpace(int x, int y) {
		for(int i = 0; i < linklist.getSize(); i++) {
			if(tooClose(x, y, linklist.getLink(i).getMSLoc().x, linklist.getLink(i).getMSLoc().y, 40) == true)
				return i;
		}
		return -1;
	}
	
	public boolean checkCoords(int x, int y) {
		for(int i = 0; i < nodelist.getSize(); i++) {
			if(tooClose(x, y, nodelist.getNode(i).getXCoord(), nodelist.getNode(i).getYCoord(), 150) == true)
				return true;
		}
		return false;
	}
	
	public void setSelectedNode(Node n, boolean bool) {
		n.setSelected(bool);
	}
	
	public void setSelectedLink(Link l, boolean bool) {
		l.setSelected(bool);
	}
	
	public String setLinkMS(Link l, String ms) {
		try {
			if(ms.equals("delete")) {
				removeLink(l);
			}
			int temp = Integer.parseInt(ms);
			l.setMS(temp);
		}
		catch(NumberFormatException nfe) {
			return "Please give an integer value!";
		}
		return "Success";
	}
	
	public void clearSelected() {
		for(int i = 0; i < nodelist.getSize(); i++) {
			nodelist.getNode(i).setSelected(false);
		}
	}
	
	public void removeLink(Link l) {
		
		getNodeByName(l.getActualNodes().get(0)).removeLink(l.getID());
		getNodeByName(l.getActualNodes().get(1)).removeLink(l.getID());
		linklist.removeLink(l);
	}
	
	public void removeNode(int i) {
		ArrayList<Link> temp = new ArrayList<Link>();
		
		for(int j = 0; j < nodelist.getNode(i).getLinkNames().size(); j++) {
			temp.add(getLinkByName(nodelist.getNode(i).getLinkNames().get(j)));
		}
		
		for(int j = 0; j < temp.size(); j++) {
			removeLink(temp.get(j));
		}
		
		nodelist.removeNode(i);
	}
		
	public boolean hasLinks(int i) {
		if(nodelist.getNode(i).getLinkNames().size() > 0)
			return true;
		
		return false;
	}
	
	// Save the data to disk.
	public boolean saveData(String filename) {
		
		try {
			file = new FileOutputStream(filename);
			output = new ObjectOutputStream(file);	
			output.writeObject(this.getNodeObjects());
			output.writeObject(this.getLinkObjects());
			file.close();
			return true;
		}
		catch(FileNotFoundException ex) {
			System.out.println("File could not be found on filesystem");
			return false;
		}
		catch(IOException ioe) {
			System.out.println("Exception while reading the file" + ioe);
			return false;
		}
	}
	
	// Load data from disk.
	public boolean loadData(String filename) {

		try {
			fileIn = new FileInputStream(filename);
			input = new ObjectInputStream(fileIn);	
			nodelist = (NodeList) input.readObject();
			linklist = (LinkList) input.readObject();
			clearSelected();
			fileIn.close();
			return true;
		}
		catch(FileNotFoundException ex) {
			System.out.println("File could not be found on filesystem");
			return false;
		}
		catch(IOException ioe) {
			System.out.println("Exception while reading the file" + ioe);
			return false;
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Class could not be found");
			return false;
		}
	}
	
}
