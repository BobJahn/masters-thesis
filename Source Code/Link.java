import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;


/* Link class. This class is responsible for representing a single link object.  
 */

public class Link implements Serializable{
	
	private static final long serialVersionUID = 3597154270978475135L;
	private ArrayList<String> nodes = new ArrayList<String>();
	private String id = "";
	private String sendNode = "";
	private String recNode = "";
	private int ms = 0;
	private int activeTime = 0;
	private Point msLoc = new Point();
	private boolean selected = false;
	private boolean active = false;
	
	
	// Constructor. Takes in an integer and creates a generic ID. Takes in two node names and sets them accordingly.
	Link(int val, String n1, String n2) {
		id = "LINK-" + val;
		nodes.add(n1);
		nodes.add(n2);
	}
	
	// Constructor. Also takes in a delay and sets it accordinly.
	Link(String name, String n1, String n2, int ms) {
		id = name;
		nodes.add(n1);
		nodes.add(n2);
		this.ms = ms;
		setSendNode(n1);
		setRecNode(n2);
	}
	
	Link(String name) {
		id = name;
		nodes.add("");
		nodes.add("");
	}
	
	
	public String getID() {
		return id;
	}
	
	// Returns the connected nodes in a pretty-print string.
	public String getNodes() {
		return nodes.get(0) + " and " + nodes.get(1);
	}
	
	public ArrayList<String> getActualNodes() {
		return nodes;
	}
	
	public String getMs() {
		return "" + ms;
	}
	
	public void setMS(int ms) {
		this.ms = ms;
	}
	
	public void setMSloc(int x, int y) {
		msLoc = new Point(x,y);
	}
	
	public Point getMSLoc() {
		return msLoc;
	}
	
	public void setSelected(boolean bool) {
		selected = bool;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setActive(boolean bool) {
		active = bool;
		activeTime = ms;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActiveTime(int activeTime) {
		this.activeTime = activeTime;
	}

	public int getActiveTime() {
		return activeTime;
	}
	
	// Reduce active time for simulation purposes. Set to inactive if time has been reduced to/past 0.
	public void reduceActiveTime() {
		activeTime = activeTime - 100;
		if(activeTime <= 0) {
			active = false;
		}
	}
	
	public void setSendNode(String sendNode) {
		if(!sendNode.equals("")) {
			if(!this.sendNode.equals(sendNode)) {
				this.recNode = this.sendNode;
				this.sendNode = sendNode;
			}
		}
	}

	public String getSendNode() {
		return sendNode;
	}

	public void setRecNode(String recNode) {
		this.recNode = recNode;
	}

	public String getRecNode() {
		return recNode;
	}
}
