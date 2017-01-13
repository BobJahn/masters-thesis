import java.util.ArrayList;
import java.util.LinkedList;

public class Search {

    
    private String target = "";
    private ArrayList<String> path = new ArrayList<String>();
    private ArrayList<LinkedList<String>> paths = new ArrayList<LinkedList<String>>(); 
    private boolean flag = false;

    public void breadthFirst(Graph graph, LinkedList<String> visited) {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());

        // examine adjacent nodes
        for (String node : nodes) {
            if(visited.contains(node)) {
                continue;
            }
            if(node.equals(target)) {
                visited.add(node);
                if(flag == false) {
                	setPath2(visited);
                	flag = true;
                }
                if(visited.size() < path.size()) {
                	setPath2(visited);
                }
                visited.removeLast();
                break;
            }
        }
        // in breadth-first, recursion needs to come after visiting adjacent nodes
        for (String node : nodes) {
            if (visited.contains(node) || node.equals(target)) {
                continue;
            }
            visited.addLast(node);
            breadthFirst(graph, visited);
            visited.removeLast();
        }
        
    }

    public void setPath2(LinkedList<String> visited) {
    	path = new ArrayList<String>();
    	for(String node : visited) {
        	path.add(node);
        }
    }
    
    public void setPath(String str) {
    	ArrayList<String> temp = new ArrayList<String>();
    	temp.add(str);
    	this.path = temp;
    }
    
    private void shortenPath() {
    	int index = 0;
    	for(int i = 1; i < path.size(); i++) {
    		if(path.get(i).equals(target)) {
    			index = i;
    			break;
    		}
    	}
    	while(path.size() > index + 1) {
    		path.remove(path.size()-1);
    	}
    }
    
    private void removeCycles() {
    	int size = path.size();
    	for(int i = 0; i < size; i++) {
    		for(int j = 0; j < i; j++) {
    			if(path.get(i).equals(path.get(j))) {
    				int temp = i - j;
    				while(temp >= 0) {
    					path.remove(i-temp);
    					temp--;
    				}
    				size -= i-j;
    				i = j;
    				break;
    			}
    		}
    	}
    }
    
    public void setTarget(String targetNode) {
    	this.target = targetNode;
    }
    
    public ArrayList<String> getPath() {
    	return path;
    }
}