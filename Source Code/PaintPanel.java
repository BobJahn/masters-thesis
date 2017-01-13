import java.awt.*;
import java.util.ArrayList;


/* Paint Panel class. This is the class responsible for maintaining a graphical display of 
 * the distributed system. It is also responsible for continually redrawing the display at
 * each step of the simulation.
 */

public class PaintPanel extends Component {

	private static final long serialVersionUID = -1119287681958673825L;
	private NodeList nodes;
	private LinkList links;
	private int nodeIndex = 0;
	private int linkIndex = 0;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private boolean started = false;
	private int originalCommands = 0;
	private int timerStart = 0;
	private int timerEnd = 0;
	private int timeElapsed = 0;
	public static final int DIAMETER = 80;
	
	
	// Generic constructor.
	public PaintPanel() {
		
	}
	
	// Advance constructor. Instantiates the class with the current nodes and links.
	public PaintPanel(NodeList nodes, LinkList links) {
		this.nodes = nodes;
		this.links = links;
	}
	
	
	// Chief paint method. Called repeatedly during simulation runs.
	public void paint(Graphics g) {
	    super.paint(g);
	    
	    // Instructions for creation of nodes/links.
	    g.setColor(Color.white);
	    g.drawString("Left click to place nodes. Right click a pair of nodes to create link between them. \n", 0, 10);
	    g.drawString("Right click an ms value to change the value. Enter 'delete' in the ms field to remove the link. \n", 0, 20);
	    g.drawString("Double right click a node to delete it. All attached links will be destroyed. \n", 0, 30);
	    g.drawString("Please ensure that all nodes are connected within the system. \n", 0, 40);
	    
	    
	    // Continually redraw a clock during simulations.
	    g.setFont(new Font("Times New Roman", Font.BOLD, 32));
	    g.setColor(Color.yellow);
	    g.drawString("Clock \n", 1000, 30);
	    g.drawLine(980, 35, 1100, 35);
	    String time = Integer.toString(timeElapsed);
	    
	    if(time.length() > 3) {
	    	String seconds = time.substring(0,time.length()-3);
	    	String milis = time.substring(time.length()-3);
	    	g.drawString(seconds + ": " + milis, 1000, 60);
	    }
	    else {
	    	String seconds = "0";
	    	String milis = Integer.toString(timeElapsed/100);
	    	g.drawString(seconds + ": " + milis + "00", 1000, 60);
	    }
	    
	    
	    
	    
	    // Keep a textual list of nodes and highlight them as they complete their final work.
	    g.setFont(new Font("Times New Roman", Font.BOLD, 20));
	    g.setColor(Color.white);
	    g.drawString("Nodes and Chosen Leader \n", 920, 400);
	    g.drawLine(920, 405, 1150, 405);
	    for(int i = 0; i < nodes.getSize(); i++) {
	    	if(nodes.getNode(i).finalWorkComplete() == true) {
	    		g.setColor(Color.green);
	    		g.drawString(nodes.getNode(i).getID(), 920, 402 + 20*(i+1));
	    		g.drawString(" ---> ", 920 + 75, 402 + 20*(i+1));
	    		g.drawString(nodes.getNode(i).getLeader(), 920 + 120, 402 + 20*(i+1));
	    	}
	    	else {
	    		g.setColor(Color.white);
	    		g.drawString(nodes.getNode(i).getID(), 920, 402 + 20*(i+1));
	    	}
	    	
	    }
	    
	    
	    // Keep a textual list of nodes and highlight them as they report back to the start node.
	    g.setFont(new Font("Times New Roman", Font.BOLD, 20));
	    g.setColor(Color.white);
	    g.drawString("Reported Nodes \n", 1220, 400);
	    g.drawLine(1220, 405, 1350, 405);
	    for(int i = 0; i < nodes.getSize(); i++) {
	    	if(nodes.getNode(i).hasNodeReported() == true) {
	    		g.setColor(Color.green);
	    		g.drawString(nodes.getNode(i).getID(), 1220, 402 + 20*(i+1));
	    	}
	    	else {
	    		g.setColor(Color.white);
	    		g.drawString(nodes.getNode(i).getID(), 1220, 402 + 20*(i+1));
	    	}
	    }
	    
	    if(started == true) {
	    	g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		    g.setColor(Color.white);
		    g.drawString("Tasks \n", 100, 600);
		    g.drawLine(80, 605, 170, 605);
		    for(int i = 0; i < tasks.size(); i++) {
		    	for(int j = 0; j < tasks.get(i).getPath().size(); j++) {
		    		g.drawString(tasks.get(i).getPath().get(j), 90 + 120*(j), 602 + 20*(i+1));
		    		if(j < tasks.get(i).getPath().size() - 1) {
		    			g.drawString(" ---> ", 90 + 120*(j) + 75, 602 + 20*(i+1));
		    		}
		    	}
		    	
		    }
		    
		    int finalWorkCount = 0;
		    int reportedCount = 0;
		    for(int i = 0; i < nodes.getSize(); i++) {
		    	if(nodes.getNode(i).finalWorkComplete() == true) {
		    		finalWorkCount++;
		    	}
		    	if(nodes.getNode(i).hasNodeReported() == true) {
		    		reportedCount++;
		    	}
		    }
		    
		    if(finalWorkCount == nodes.getSize()-1 && timerStart == 0) {
		    	timerStart = timeElapsed;
		    }
		    
		    if(reportedCount == nodes.getSize()-1 && timerEnd == 0) {
		    	timerEnd  = timeElapsed;
		    }
		    
		    g.setFont(new Font("Times New Roman", Font.BOLD, 32));
		    g.setColor(Color.yellow);
		    g.drawString("Finish Time \n", 1200, 30);
		    g.drawLine(1180, 35, 1400, 35);
		    
		    if(timerStart != 0 && timerEnd != 0) {
		    	g.setFont(new Font("Times New Roman", Font.BOLD, 32));
			    g.setColor(Color.yellow);
		    	int finishTime = timerEnd - timerStart;
		    	
		    	String timeDisplay = Integer.toString(finishTime);
			    
			    if(timeDisplay.length() > 3) {
			    	String seconds = timeDisplay.substring(0,timeDisplay.length()-3);
			    	String milis = timeDisplay.substring(timeDisplay.length()-3);
			    	g.drawString(seconds + ": " + milis, 1200, 60);
			    }
			    else {
			    	String seconds = "0";
			    	String milis = Integer.toString(timeElapsed/100);
			    	g.drawString(seconds + ": " + milis + "00", 1200, 60);
			    }
		    }
	    }
	    
	    // Redraw all links at each step.
	    g.setFont(new Font("Times New Roman", Font.PLAIN, 16));
	    g.setColor(Color.white);
	    
	    for(int i = 0; i < links.getSize(); i++) {
	    	int x1 = nodes.getNodeByName(links.getLink(i).getSendNode()).getXCoord();
	    	int x2 = nodes.getNodeByName(links.getLink(i).getRecNode()).getXCoord();
	    	int xOffset = Math.min(x1,x2);
	    	int y1 = nodes.getNodeByName(links.getLink(i).getSendNode()).getYCoord();
	    	int y2 = nodes.getNodeByName(links.getLink(i).getRecNode()).getYCoord();
	    	int yOffset = Math.min(y1,y2);
	    	g.drawLine(x1, y1, x2, y2);
	    	
	    	if(links.getLink(i).getSelected() == true) {
	    		g.setColor(Color.yellow);
	    		g.drawLine(x1, y1, x2, y2);
	    	}
	    	
	    	if(links.getLink(i).isActive() == true) {
	    		g.setColor(Color.green);
	    		if(linkIndex == 0) {
	    			g.drawLine(x1, y1, x1 + (int)(x2-x1)/3, y1 + (int)(y2-y1)/3);
	    		}
	    		else if(linkIndex == 1) {
	    			g.drawLine(x1, y1, x1 + (int)2*(x2-x1)/3, y1 + (int)2*(y2-y1)/3);
	    		}
	    		else {
	    			g.drawLine(x1, y1, x2, y2);
	    		}
	    	}
	    	
	    	links.getLink(i).setMSloc(xOffset + (int) Math.abs((x1 - x2) / 2), yOffset + (int) Math.abs((y1 - y2) / 2));
	    	g.drawString(links.getLink(i).getMs() + " ms", links.getLink(i).getMSLoc().x, links.getLink(i).getMSLoc().y);
	    	g.setColor(Color.white);
	    }
	    
	    
	    // Redraw all nodes at each step.
	    g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
	    g.setColor(Color.gray);
	    for(int i = 0; i < nodes.getSize(); i++) {
	    	g.fillOval(nodes.getNode(i).getCoords().x - DIAMETER/2, nodes.getNode(i).getCoords().y - DIAMETER/2, DIAMETER, DIAMETER);
	    	
	    	if(nodes.getNode(i).getSelected() == true) {
	    		g.setColor(Color.yellow);
	    		g.drawOval(nodes.getNode(i).getCoords().x - DIAMETER/2, nodes.getNode(i).getCoords().y - DIAMETER/2, DIAMETER, DIAMETER);
	    	}
	    	else if(nodes.getNode(i).isActive() == true) {
	    		if(nodeIndex != 4) {
	    			g.setColor(Color.green);
	    			g.drawOval(nodes.getNode(i).getCoords().x - DIAMETER/2 - nodeIndex*5, nodes.getNode(i).getCoords().y - DIAMETER/2 - nodeIndex*5, DIAMETER + nodeIndex*10, DIAMETER + nodeIndex*10);
	    		}
	    		else {
	    			g.setColor(Color.black);
	    			for(int j = 1; j < 5; j++) {
	    				g.drawOval(nodes.getNode(i).getCoords().x - DIAMETER/2 - j*5, nodes.getNode(i).getCoords().y - DIAMETER/2 - j*5, DIAMETER + j*10, DIAMETER + j*10);
	    			}
	    		}
	    	}
	    	
	    	g.setColor(Color.white);
	    	g.drawString(nodes.getNode(i).getID(), nodes.getNode(i).getCoords().x - DIAMETER/4, nodes.getNode(i).getCoords().y);
	    	if(i != 0) {
	    		g.drawString(nodes.getNode(i).getUID()+"", nodes.getNode(i).getCoords().x - DIAMETER/4, nodes.getNode(i).getCoords().y + 20);
	    	}
	    	g.setColor(Color.gray);
	    }
	}
	
	
	// Main logic method. Responsible for running simulation and retaining/updating logic at each step.
	public void runSim() {
		
		// Prepare to run simulation
		started = true;
		nodes.setAllSpecs(1000, 1000, 100);
		originalCommands = nodes.addCommandsLE();
		nodes.setConnectedNodes(links);
		nodes.showNodeConnections();
		nodes.setTargets();
		tasks = nodes.setPath(links);
		nodes.printPaths();
		
		// Run until the final work has been recorded for each node used in the computation.
		while(nodes.howManyNodesReported() < nodes.getSize()-1) {
			
			try {
				
				// Gather the currently active links.
				ArrayList<Link> activeLinks = links.getActiveLinks();
				for(int i = 0; i < activeLinks.size(); i++) {
					// Reduce the active time remaining for the given link.
					activeLinks.get(i).reduceActiveTime();
					
					// If the link has turned inactive from an active state, pass the current task onto the receiving node.
					if(!activeLinks.get(i).isActive()) {
						Task t = getTaskByNodes(activeLinks.get(i).getSendNode(), activeLinks.get(i).getRecNode());
						if(t.getStartNode().equals(t.getTargetNode()))
							nodes.addNewCommandAndSetPath(links, t);
						else {
							int k = getIndexByNodes(activeLinks.get(i).getSendNode(), activeLinks.get(i).getRecNode());
							tasks.remove(k);
							Task temp = nodes.addNewCommandAndSetPath(links, t);
							if(temp != null)
								tasks.add(temp);
						}
					}
				}
				
				// Search through each node in the current graph.
				for(int i = 0; i < nodes.getSize(); i++) {
					
					ArrayList<String> targets = new ArrayList<String>();
					String sendNode = "";
					
					// Flag the current node so we know it was inactive before reducing the active time.
					if(nodes.getNode(i).isActive()) {
						nodes.getNode(i).setSustainedInactivity(false);
					}
					
					// Reduce the remaining active time of the current node.
					nodes.getNode(i).refreshActive();
					nodes.getNode(i).reduceActiveTime();
					
					// If the node has gone from active to inactive during this single pass, gather the target links
					// on which to send the current task.
					if(!nodes.getNode(i).isActive() && !nodes.getNode(i).getSustainedInactivity()) {
						if(!nodes.getNode(i).getTargetNode().equals(nodes.getNode(i).getID())) {
							
							targets = nodes.getNode(i).sendOnLinks(links);
							if(targets.size() > 0) {
								sendNode = nodes.getNode(i).getID();
							}
							if(links.getLinkByName(targets.get(0)).isActive()) {
								nodes.getNodeByName(sendNode).delaySending();
								targets.remove(0);
							}
							else {
							
								if(nodes.getNode(i).queueEmpty() == false) {
									nodes.getNode(i).removeTask(0);
								}
								
								if(nodes.getNode(i).taskSize() > 0) {
									nodes.getNode(i).getNextTask();
								}
								else {
									nodes.getNode(i).setSustainedInactivity(true);
								}
							}
						}
						else {
							Task t = getTaskByNodes(nodes.getNode(i).getID(), nodes.getNode(i).getID());
							nodes.addNewCommandAndSetPath(links, t);
						}
					}
					
					// Having gathered the target links previously(if any), make those target links active.
					for(int j = 0; j < targets.size(); j++) {
						
						links.getLinkByName(targets.get(j)).setActive(true);
						links.getLinkByName(targets.get(j)).setSendNode(sendNode);
					}

					System.out.println(nodes.getNode(i).getID() + ": " + nodes.getNode(i).getActiveTime() + "\n");
				}
				
				System.out.println("\n_______________________________\n");
				
				// Repaint each 1/10th of a second.
				repaint();
				Thread.sleep(100);
				timeElapsed = timeElapsed + 100;
				
				// Increment indexes for drawing purposes for the repainting portion.
				if(nodeIndex < 3) {
					nodeIndex++;
				}
				else {
					nodeIndex = 0;
				}
				
				if(linkIndex < 2) {
					linkIndex++;
				}
				else {
					linkIndex = 0;
				}
				
			} 
			catch(InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	
	
	// Private method that returns a task based on the sending/receiving nodes.
	private Task getTaskByNodes(String sender, String receiver) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getStartNode().equals(sender) && tasks.get(i).getNextNode().equals(receiver)) {
				return tasks.get(i);
			}
		}
		return null;
	}
	
	
	// Private method that returns a index based on the sending/receiving nodes.
	private int getIndexByNodes(String sender, String receiver) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getStartNode().equals(sender) && tasks.get(i).getNextNode().equals(receiver)) {
				return i;
			}
		}
		return -1;
	}
}
