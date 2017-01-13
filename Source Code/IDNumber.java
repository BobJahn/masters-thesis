import java.io.Serializable;

/*	IDNumber Class. This class exists to generate IDs for newly added nodes/links.  
 */

public class IDNumber implements Serializable{
	
	private static final long serialVersionUID = 3727961209354130688L;
	private int lastNodeID = 0;
	private int lastLinkID = 0;
	private int lastTaskID = 0;
	private static IDNumber singleton;
	
	
	// Instance method for singleton purposes.
	public static IDNumber instance() {
		if (singleton == null) {
			singleton = new IDNumber();
		}
		
		return singleton;
	}
	
	// Increment the lastID(to be equal to the newly created ID) and return this ID number.
	public int generateNodeID() {
		
		return lastNodeID++;
	}
	
	// Increment the lastID(to be equal to the newly created ID) and return this ID number.
	public int generateLinkID() {
		
		return lastLinkID++;
	}
	
	// Increment the lastID(to be equal to the newly created ID) and return this ID number.
	public int generateTaskID() {
		
		return lastTaskID++;
	}
	
	// Used to get around the saving to disk issue.
	public void setLastNodeID(int i) {
		lastNodeID = i;
	}
	
	// Used to get around the saving to disk issue.
	public void setLastLinkID(int i) {
		lastLinkID = i;
	}
	
	// Used to get around the saving to disk issue.
	public void setLastTaskID(int i) {
		lastTaskID = i;
	}
}
