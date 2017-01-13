import java.util.*;
import java.awt.*;
import java.io.Serializable;


/* Node class. This class is responsible for representing a single node object.  
 */

public class Node implements Serializable {
	
	private static final long serialVersionUID = 1548577894454033130L;
	private ArrayList<String> links = new ArrayList<String>();
	private ArrayList<String> attachedNodes = new ArrayList<String>();
	private ArrayList<String> path = new ArrayList<String>();
	private ArrayList<Task> taskQueue = new ArrayList<Task>();
	private ArrayList<Integer> LEValues = new ArrayList<Integer>();
	private ArrayList<String> LENames = new ArrayList<String>();
	private String id = "";
	private String targetNode;
	private String leader;
	private Point coords;
	private boolean selected = false;
	private boolean active = false;
	private boolean reported = false;
	private boolean finalWorkStatus = false;
	private boolean sustainedInactivity = true;
	private int finalWork = 0;
	private int activeTime = 0;
	private int addedTime = 0;
	private int staticTime = 0;
	private int sendChance = 100;
	private int received = 0;
	private int UID = 0;
	private int reportedCount = 0;
	private Random generator = new Random(System.currentTimeMillis());
	
	// Constructor. Takes in a name and the graphical (x,y) coordinates and sets them accordingly.
	Node(String name, int x, int y) {
		id = name;
		coords = new Point(x,y);
		setUID(generator.nextInt(10000));
		LEValues.add(UID);
		LENames.add(id);
	}
	
	
	public String getID() {
		return id;
	}
	
	public void addLink(String name) {
		links.add(name);
	}
	
	public void removeLink(String l) {
		links.remove(l);
	}
	
	public void removeLinks() {
		for(int i = 0; i < links.size(); i++) {
			links.remove(i);
		}
	}
	
	// Get the attached links in a single string.
	public String getLinks() {
		String temp = "";
		if(links.size() == 1) {
			temp += links.get(0);
		}
		else {
			for(int i = 0; i < links.size(); i++) {
				if(i != 0)
					temp += ", ";
				temp += links.get(i);
			}
		}
		return temp;
	}
	
	// Get the individual link names in individual strings. Return an array of these strings.
	public ArrayList<String> getLinkNames() {
		return links;
	}
	
	public Point getCoords() {
		return coords;
	}
	
	public int getXCoord() {
		return coords.x;
	}
	
	public int getYCoord() {
		return coords.y;
	}
	
	public void setSelected(boolean bool) {
		selected = bool;
		if(selected) 
			active = false;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public void refreshActive() {
		if(activeTime <= 0) {
			active = false;
		}
	}
	
	// Delay sending on a given link by temporarily increasing the active time remaining.
	public void delaySending() {
		activeTime += 100;
		active = true;
	}
	
	// Retrieve the next task if there are still tasks to be performed.
	public void getNextTask() {
		if(active == false && taskQueue.size() > 0) {
			setActive(true);
			setPath(taskQueue.get(0).getPath());
			if(path.size() > 0)
				setTargetNode(taskQueue.get(0).getTargetNode());
		}
	}
	
	// Set a node as active.
	public void setActive(boolean bool) {
		if(active == true && bool == true) {
			activeTime += generator.nextInt(addedTime) + staticTime;
		}
		else if(active == false && bool == true){
			active = bool;
			activeTime = generator.nextInt(addedTime) + staticTime;
		}
		else if(active == true && bool == false) {
			active = bool;
			activeTime = 0;
		}
		if(path.size() > 0){
			targetNode = path.get(path.size()-1);
		}
	}
	
	public void setActiveInitial() {
		activeTime = generator.nextInt(addedTime) + staticTime;
		active = true;
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
	
	public void reduceActiveTime() {
		activeTime = activeTime - 100;
		if(activeTime <= 0) {
			activeTime = 0;
		}
	}

	public void setSendChance(int sendChance) {
		this.sendChance = sendChance;
	}

	public int getSendChance() {
		return sendChance;
	}
	
	public ArrayList<String> sendOnLinks(LinkList links) {
		ArrayList<String> recLinks = new ArrayList<String>();
		recLinks.add(links.getLinkByNodes(path.get(0), path.get(1)).getID());
		return recLinks;
	}
	
	public void setSustainedInactivity(boolean sustainedInactivity) {
		this.sustainedInactivity = sustainedInactivity;
	}

	public boolean getSustainedInactivity() {
		return sustainedInactivity;
	}
	
	public void setSpecs(int addedTime, int staticTime, int sendChance) {
		this.addedTime = addedTime;
		this.staticTime = staticTime;
		this.sendChance = sendChance;
		if(active == true) {
			this.activeTime = generator.nextInt(addedTime) + staticTime;
		}
	}

	public void setAttachedNodes(ArrayList<String> attachedNodes) {
		this.attachedNodes = attachedNodes;
	}

	public ArrayList<String> getAttachedNodes() {
		return attachedNodes;
	}

	public void setTargetNode(String targetNode) {
		this.targetNode = targetNode;
	}

	public String getTargetNode() {
		return targetNode;
	}
	
	public void addToPath(String n1) {
		path.add(n1);
	}
	
	public void setPath(ArrayList<String> path) {
		this.path = path;
	}
	
	public void printPath(int index) {
		ArrayList<String> tempPath = taskQueue.get(index).getPath();
		System.out.print("For the " + index + "th task at node " + getID() + ". The path from " + getID() + " to " + taskQueue.get(index).getTargetNode() + " consists of: ");
		for(int i = 0; i < tempPath.size(); i++) {
			if(i == tempPath.size() - 1) {
				System.out.print(tempPath.get(i));
			}
			else {
				System.out.print(tempPath.get(i) + ", ");
			}
			
		}
		System.out.print("\n");
	}
	
	public void printPaths() {
		for(int i = 0; i < taskQueue.size(); i++) {
			printPath(i);
		}
	}
	
	public void removeNodeFromAttached(int i) {
		attachedNodes.remove(i);
	}
	
	public void addTask(Task t) {
		taskQueue.add(t);
	}
	
	public Task getCurrentTask() {
		return taskQueue.get(0);
	}
	
	public void removeTask(int i) {
		taskQueue.remove(i);
	}
	
	public boolean queueEmpty() {
		if(taskQueue.size() == 0)
			return true;
		return false;
	}
	
	public void printQueue() {
		for(int i = 0; i < taskQueue.size(); i++) {
			for(int j = 0; j < taskQueue.get(i).getPath().size(); j++) {
				System.out.println(taskQueue.get(i).getPath().get(j) + " ");
			}
			System.out.println("\n\n");
		}
	}
	
	public int taskSize() {
		return taskQueue.size();
	}
	
	public void addToFinalWork() {
		finalWork++;
	}
	
	public void addToReported() {
		reportedCount++;
	}
	
	public int finalWorkSize() {
		return finalWork;
	}

	public boolean finalWorkComplete() {
		return finalWorkStatus;
	}
	
	public void setFinalWork(boolean status) {
		finalWorkStatus = status;
	}
	
	public void setReported() {
		reported = true;
	}
	
	public boolean hasNodeReported() {
		return reported;
	}


	public void setReceived(int received) {
		this.received = received;
	}


	public int getReceived() {
		return received;
	}


	public void setUID(int UID) {
		this.UID = UID;
	}


	public int getUID() {
		return UID;
	}
	
	public void addToLEValues(int val) {
		LEValues.add(val);
	}
	
	public void addToLENames(String name) {
		LENames.add(name);
	}
	
	public boolean findLeader(int nodeAmount) {
		if(LEValues.size() >= nodeAmount) {
			int max = LEValues.get(0);
			int index = 0;
		
			for(int i = 1; i < LEValues.size(); i++) {
				if(LEValues.get(i) > max) {
					max = LEValues.get(i);
					index = i;
				}
			}
		
			setLeader(LENames.get(index));
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getLeader() {
		return leader;
	}
	
	public void setLeader(String leader) {
		this.leader = leader;
	}


	public void setReportedCount(int reportedCount) {
		this.reportedCount = reportedCount;
	}


	public int getReportedCount() {
		return reportedCount;
	}
}
