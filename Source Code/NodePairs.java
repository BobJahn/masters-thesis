
public class NodePairs {

	private Node recNode;
	private Node sendNode;
	
	
	
	public NodePairs(Node sendNode, Node recNode) {
		this.setSendNode(sendNode);
		this.setRecNode(recNode);
	}


	public void setRecNode(Node recNode) {
		this.recNode = recNode;
	}

	public Node getRecNode() {
		return recNode;
	}

	public void setSendNode(Node sendNode) {
		this.sendNode = sendNode;
	}

	public Node getSendNode() {
		return sendNode;
	}
	
	public boolean hasTarget(String sender) {
		if(sendNode.getID().equals(sender)) {
			return true;
		}
		return false;
	}
}
