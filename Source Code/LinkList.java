
import java.io.Serializable;
import java.util.ArrayList;


public class LinkList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2683650833202152990L;
	private ArrayList<Link> links = new ArrayList<Link>();
	private static LinkList singleton;
	
	//Instance method for singleton purposes.
	public static LinkList instance() {
		if(singleton == null) 
			singleton = new LinkList();
				
		
		return singleton;
	}
	
	
	public boolean addLink(Link n) {
		return links.add(n);
	}
	
	public Link getLink(int index) {
		return links.get(index);
	}
	
	public int getSize() {
		return links.size();
	}
	
	public void removeLink(Link l) {
		links.remove(l);
	}
	
	public boolean anyLinksActive() {
		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).isActive() == true)
				return true;
		}
		
		return false;
	}
	
	public Link getLinkByName(String name) {
		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).getID().equals(name)) {
				return links.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Link> getActiveLinks() {
		ArrayList<Link> temp = new ArrayList<Link>();
		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).isActive()) {
				temp.add(links.get(i));
			}
		}
		return temp;
	}
	
	public Link getLinkByNodes(String n1, String n2) {
		for(int i = 0; i < links.size(); i++) {
			if(n1.equals(links.get(i).getSendNode())) {
				if(n2.equals(links.get(i).getRecNode())) {
					return links.get(i);
				}
			}
			else if(n2.equals(links.get(i).getSendNode())) {
				if(n1.equals(links.get(i).getRecNode())) {
					return links.get(i);
				}
			}
		}
		return new Link("");
	}
}