import java.util.ArrayList;


public class Task {

	
	private ArrayList<String> path = new ArrayList<String>();
	private String startNode;
	private String originalStartNode;
	private String targetNode;
	private String message = "";
	private boolean finalWork;
	private int index = 0;
	private int id = 0;
	private int UID = 0;
	private IDNumber ID = IDNumber.instance();



	public Task(ArrayList<String> path, boolean finalWork, String originalStartNode, int UID, String message) {
		this.setPath(path);
		if(path.size() > 0) {
			setStartNode(path.get(0));
			
			setTargetNode(path.get(path.size()-1));
		}
		this.originalStartNode = originalStartNode;
		this.setFinalWork(finalWork);
		id = ID.generateTaskID();
		this.UID = UID;
		this.message = message;
	}


	public void setPath(ArrayList<String> path) {
		this.path = path;
	}

	public ArrayList<String> getPath() {
		return path;
	}

	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}
	
	public void setOriginalStartNode(String startNode) {
		this.originalStartNode = startNode;
	}

	public String getStartNode() {
		return startNode;
	}
	
	public String getOriginalStartNode() {
		return originalStartNode;
	}
	
	public String getNextNode() {
		if(path.size() > 0)
			return path.get(1);
		else
			return null;
	}

	public void setTargetNode(String targetNode) {
		this.targetNode = targetNode;
	}

	public String getTargetNode() {
		return targetNode;
	}
	
	public boolean isCorrectTask(String sender) {
		if(sender.equals(startNode)) {
			return true;
		}
		return false;
	}
	
	public void nextNode() {
		if(path.size() > 0) {
			path.remove(0);
			setStartNode(path.get(0));
		}
	}
	
	public void returnToStart() {
		setStartNode(path.get(0));
		setStartNode(originalStartNode);
	}


	public void setFinalWork(boolean finalWork) {
		this.finalWork = finalWork;
	}


	public boolean getFinalWork() {
		return finalWork;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


	public int getUID() {
		return UID;
	}
}




