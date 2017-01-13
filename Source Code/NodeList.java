
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class NodeList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8459405496887315821L;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private static NodeList singleton;
	private Random generator = new Random(System.currentTimeMillis());
	ArrayList<NodePairs> nodePairs = new ArrayList<NodePairs>();
	ArrayList<Task> tasks = new ArrayList<Task>();
	private String algorithmType = "";
	
	//Instance method for singleton purposes.
	public static NodeList instance() {
		if(singleton == null) 
			singleton = new NodeList();
				
		
		return singleton;
	}
	
	
	public boolean addNode(Node n) {
		return nodes.add(n);
	}
	
	public Node getNode(int index) {
		return nodes.get(index);
	}
	
	public int getSize() {
		return nodes.size();
	}
	
	public void removeNode(int i) {
		nodes.get(i).removeLinks();
		nodes.remove(i);
	}
	
	public boolean anyNodesActive() {
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).isActive() == true)
				return true;
		}
		
		return false;
	}
	
	public void assignActiveTimes() {
		int time;
		for(int i = 0; i < nodes.size(); i++) {
			time = generator.nextInt(5000) + 1000;
			if(nodes.get(i).isActive()) {
				nodes.get(i).setActiveTime(time);
			}
		}
	}
	
	public Node getNodeByName(String name) {
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).getID().equals(name)) {
				return nodes.get(i);
			}
		}
		return null;
	}
	
	public void setAllSpecs(int addedTime, int staticTime, int sendChance, String algorithmType) {
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).setSpecs(addedTime, staticTime, sendChance);
		}
		
		this.algorithmType = algorithmType;
	}
	
	public void setConnectedNodes(LinkList links) {
		
		for(int j = 0; j < nodes.size(); j++) {
			
			ArrayList<String> attachedNodes = new ArrayList<String>();
			ArrayList<String> attachedLinks = getNode(j).getLinkNames();
			ArrayList<Link> attachedLinkObjects = new ArrayList<Link>();
			
			for(int i = 0; i < links.getSize(); i++) {
				if(linkExistance(links.getLink(i), attachedLinks))
					attachedLinkObjects.add(links.getLink(i));
			}
		
		
			for(int i = 0; i < attachedLinkObjects.size(); i++) {
				attachedNodes.add(attachedLinkObjects.get(i).getSendNode());
				attachedNodes.add(attachedLinkObjects.get(i).getRecNode());
			}
		
			while(attachedNodes.remove(getNode(j).getID())) {}
		
			
			getNode(j).setAttachedNodes(attachedNodes);
		}
	}
	
	public void showNodeConnections() {
		for(int j = 0; j < nodes.size(); j++) {
			System.out.print("Node " + j + " has the following attached nodes: ");
			for(int i = 0; i < nodes.get(j).getAttachedNodes().size(); i++) {
				if(i != nodes.get(j).getAttachedNodes().size() - 1) {
					System.out.print(nodes.get(j).getAttachedNodes().get(i) + ", ");
				}
				else {
					System.out.print(nodes.get(j).getAttachedNodes().get(i) + "\n");
				}
			}
		}
		System.out.println("\n_______________________________\n");
	}
	
	public static boolean linkExistance(Link l, ArrayList<String> attachedLinks) {
		for(int i = 0; i < attachedLinks.size(); i++) {
			if(l.getID().equals(attachedLinks.get(i))) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setTargets() {
		generator = new Random(System.currentTimeMillis());
		
		for(int i = 0; i < nodes.size(); i++) {
			int k = 0;
			boolean temp = false;
			for(int j = 0; j < nodePairs.size(); j++) {
				if(nodePairs.get(j).hasTarget(nodes.get(i).getID())) {
					k = j;
					temp = true;
				}
			}
			
			if(temp == true) {
				nodes.get(i).setTargetNode(nodePairs.get(k).getRecNode().getID());
			}
			else {
				nodes.get(i).setTargetNode(nodes.get(i).getID());
			}
			
			System.out.println(nodes.get(i).getID() + " is sending to " + nodes.get(i).getTargetNode());
		}
		
	}
	
	public int addCommands(String algorithmType) {
		
		if(algorithmType.equals("Leader Election")) {
			for(int i = 1; i < nodes.size(); i++) {
				for(int j = 1; j < nodes.size(); j++) {
					if(i != j) {
						nodePairs.add(new NodePairs(getNode(i), getNode(j)));
					}
				}
			}
		}
		else if(algorithmType.equals("Consensus")) {
			for(int i = 1; i < nodes.size(); i++) {
				for(int j = 1; j < nodes.size(); j++) {
					if(i != j) {
						nodePairs.add(new NodePairs(getNode(i), getNode(j)));
					}
				}
			}
		}
		
		setActiveNodes();
		return nodePairs.size();
	}
	
	public void setActiveNodes() {
		for(int i = 0; i < nodePairs.size(); i++) {
			nodePairs.get(i).getSendNode().setActiveInitial();
		}
	}
	
	public void addCommand(String start, String end) {
		nodePairs.add(new NodePairs(getNodeByName(start), getNodeByName(end)));
	}
	
	public ArrayList<Task> setPath(LinkList links) {
        Graph graph = new Graph();
        for(int i = 0; i < links.getSize(); i++) {
        	graph.addEdge(links.getLink(i).getActualNodes().get(0), links.getLink(i).getActualNodes().get(1));
        	graph.addEdge(links.getLink(i).getActualNodes().get(1), links.getLink(i).getActualNodes().get(0));
        }
        ArrayList<Task> tasks = new ArrayList<Task>();
        
        for(int i = 0; i < nodePairs.size(); i++) {
        	
        	LinkedList<String> visited = new LinkedList<String>();
        	visited.add(nodePairs.get(i).getSendNode().getID());
        	Search s = new Search();
        	s.setTarget(nodePairs.get(i).getRecNode().getID());
        	if(nodePairs.get(i).getSendNode().getID().equals(nodePairs.get(i).getRecNode().getID())) {
        		s.setPath(nodePairs.get(i).getRecNode().getID());
        	}
        	else {
        		s.breadthFirst(graph, visited);
        	}
        	
        	int UID = nodePairs.get(i).getSendNode().getUID();
        	Task t = new Task(s.getPath(), false, nodePairs.get(i).getSendNode().getID(), UID, "");
        	//nodePairs.get(i).getSendNode().setPath(s.getPath());
        	nodePairs.get(i).getSendNode().addTask(t);
        	nodePairs.get(i).getSendNode().setPath(nodePairs.get(i).getSendNode().getCurrentTask().getPath());
        	tasks.add(t);
        }
        
        return tasks;
	}
	
	public Task addNewCommandAndSetPath(LinkList links, Task t) {
		t.nextNode();
		
		
		
		if(t.getStartNode().equals(t.getTargetNode()) && t.getFinalWork() == false) {
			getNodeByName(t.getStartNode()).setReceived(getNodeByName(t.getStartNode()).getReceived() + 1);
			getNodeByName(t.getStartNode()).addToUIDNames(t.getOriginalStartNode());
			getNodeByName(t.getStartNode()).addToUIDValues(t.getUID());
			if(algorithmType.equals("Leader Election")) {
				if(getNodeByName(t.getStartNode()).findLeader(nodes.size() - 1) == true) {
					t.setFinalWork(true);
					getNodeByName(t.getStartNode()).setFinalWork(true);
					t.setTargetNode(nodes.get(0).getID());
					t.setOriginalStartNode(t.getStartNode());
					System.out.println(getNodeByName(t.getStartNode()).getID() + " believes that " + getNodeByName(t.getStartNode()).getLeader() + " is the leader.");
				}
				else {
					return null;
				}
			}
			else if(algorithmType.equals("Consensus")) {
				if(getNodeByName(t.getStartNode()).findConsensusValue(nodes.size() - 1) == true) {
					t.setFinalWork(true);
					getNodeByName(t.getStartNode()).setFinalWork(true);
					t.setTargetNode(nodes.get(0).getID());
					t.setOriginalStartNode(t.getStartNode());
					System.out.println(getNodeByName(t.getStartNode()).getID() + " believes that " + getNodeByName(t.getStartNode()).getConsensusValue() + " is the value.");
				}
				else {
					return null;
				}
			}
			
			
		}
		else if(t.getStartNode().equals(t.getTargetNode()) && t.getFinalWork() == true) {
			getNodeByName(t.getOriginalStartNode()).setReported(true);
			//nodes.get(0).addToFinalWork();
			//nodes.get(0).addToReported();
			return null;
		}
		
		addCommand(t.getStartNode(),t.getTargetNode());
		
		Graph graph = new Graph();
        for(int i = 0; i < links.getSize(); i++) {
        	graph.addEdge(links.getLink(i).getActualNodes().get(0), links.getLink(i).getActualNodes().get(1));
        	graph.addEdge(links.getLink(i).getActualNodes().get(1), links.getLink(i).getActualNodes().get(0));
        }
        
        LinkedList<String> visited = new LinkedList<String>();
    	visited.add(nodePairs.get(nodePairs.size()-1).getSendNode().getID());
    	Search s = new Search();
    	s.setTarget(nodePairs.get(nodePairs.size()-1).getRecNode().getID());
    	if(t.getStartNode().equals(t.getTargetNode())) {
    		s.setPath(t.getStartNode());
    	}
    	else {
    		s.breadthFirst(graph, visited);
    	}
    	
    	    	
    	if(nodePairs.get(nodePairs.size()-1).getSendNode().taskSize() == 0)
    		nodePairs.get(nodePairs.size()-1).getSendNode().setTargetNode(t.getTargetNode());
    	
    	
  	
    	//int UID = nodePairs.get(nodePairs.size()-1).getSendNode().getUID();
    	t = new Task(s.getPath(), t.getFinalWork(), t.getOriginalStartNode(), t.getUID(), t.getMessage());
    	if(!t.getStartNode().equals(t.getTargetNode()))
    		nodePairs.get(nodePairs.size()-1).getSendNode().addTask(t);
    	
    	
    	if(nodePairs.get(nodePairs.size()-1).getSendNode().taskSize() == 1 && !t.getStartNode().equals(t.getTargetNode())) {
    		nodePairs.get(nodePairs.size()-1).getSendNode().setPath(s.getPath());
    	}
    	if(nodePairs.get(nodePairs.size()-1).getSendNode().isActive() == false) {
    		nodePairs.get(nodePairs.size()-1).getSendNode().setActive(true);
    	}
    	
    	nodePairs.get(nodePairs.size()-1).getSendNode().printPaths();
    	
    	return t;
        
	}
	
	public void printPaths() {
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).printPaths();
		}
	}
	
	public int howManyNodesReported() {
		int reportedCount = 0;
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).hasNodeReported() == true) {
				reportedCount++;
			}
		}
		return reportedCount;
	}
	
	public void resetNodeData() {
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).resetNodeData();
		}
		nodePairs = new ArrayList<NodePairs>();
		tasks = new ArrayList<Task>();
	}
}
