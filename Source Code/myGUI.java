import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*; 


/*	Menu Class. This class presents the GUI that the whole application is built upon.
 * It has the main call within this class. Panels are created upon the creation of
 * the class. These panels are shown/hidden as the user navigates through the gui. */

public class myGUI extends JFrame implements ActionListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8777498239802222451L;

	private static JFrame f = new JFrame("Distributed System Simulation"); //create Frame
	
	private static JMenuBar mb = new JMenuBar();
	private static Container mAddData = new JMenu("Add Data");
	private static Container mListData = new JMenu("List Data");
	private static Container mViewSystem = new JMenu("View System");
	private static Container mFile = new JMenu("File"); //Save or load data.
	private static Container mQuit = new JMenu("Quit");
	
	private static JMenuItem mItemAddNodes = new JMenuItem("Add Node");
	private static JMenuItem mItemAddLinks = new JMenuItem("Add Link");
	private static JMenuItem mItemListNodes = new JMenuItem("List Nodes");
	private static JMenuItem mItemListLinks = new JMenuItem("List Links");
	private static JMenuItem mItemViewGraph = new JMenuItem("View Graph");
	private static JMenuItem mItemRunSim = new JMenuItem("Run Simulation");
	private static JMenuItem mItemSaveData = new JMenuItem("Save Data"); //Save data.
	private static JMenuItem mItemLoadData = new JMenuItem("Load Data"); //Load data.
	private static JMenuItem mItemQuit = new JMenuItem("Quit");
	
	private static JTextField textField1 = new JTextField(20);
	private static JTextField textField2 = new JTextField(20);
	private static JTextField textField3 = new JTextField("NODE-0");
	private static JTextField textField4 = new JTextField("NODE-1");
	private static JTextField textXCoord = new JTextField("200");
	private static JTextField textYCoord = new JTextField("200");
	private static JTextField textFile = new JTextField(20);
	private static JTextField textFile2 = new JTextField(20);
	private static JTextField textMS = new JTextField(20);
	
	private static JPanel panel1 = new JPanel();
	private static JPanel panel2 = new JPanel();
	private static JPanel panel3 = new JPanel();
	private static JPanel panel4 = new JPanel();
	private static JPanel panel5 = new JPanel();
	private static JPanel panel6 = new JPanel();
	private static JPanel panel7 = new JPanel();
	private static JPanel panel8 = new JPanel();
	private static JPanel panel9 = new JPanel();
	private static JPanel panel10 = new JPanel();
	private static JPanel panel11 = new JPanel();
	private static JPanel panel12 = new JPanel();
	private static JPanel panel13 = new JPanel();
	private static JPanel panel14 = new JPanel();
	private static JPanel panel15 = new JPanel();
	private static JPanel panel16 = new JPanel();
	private static JPanel panel17 = new JPanel();
	private static JPanel panel18 = new JPanel();
	
	private static JButton addNode;
	private static JButton addLink;
	private static JButton saveData;
	private static JButton loadData;
	private static JButton cancelNode;
	private static JButton cancelLink;
	private static JButton cancelSave;
	private static JButton cancelLoad;
	private static JButton changeMS;
	private static JButton consensus;
	private static JButton leaderElection;
	
	
	private static JLabel nodeName; 
	private static JLabel linkName;
	private static JLabel nodePrint;
	private static JLabel linkPrint;
	private static JLabel dataPrint;
	private static JLabel failureNode;
	private static JLabel failureLink;
	private static JLabel failureSave;
	private static JLabel failureLoad;
	private static JLabel idReport;
	private static JLabel node1; 
	private static JLabel node2;
	private static JLabel xcoord; 
	private static JLabel ycoord;
	private static JLabel save;
	private static JLabel load;
	private static JLabel filename;
	private static JLabel filename2;
	private static JLabel newMS;
	
	
	private Random generator = new Random(System.currentTimeMillis());
	private String val1 = Integer.toString(generator.nextInt(1000) + 50);
	private String val2 = Integer.toString(generator.nextInt(600) + 50);
	
	
	private String name = "";
	private Controller con1 = Controller.instance();
	private static myGUI singleton;
	private PaintPanel p1 = new PaintPanel();
	private Color defaultColor;
	private MouseListener m1;
	private MouseListener m2;
	private MouseListener m3;
	private Node n1;
	private Link l1;
	private int index = -1;
	private int index2 = -1;
	private String algorithmType = "Leader Election";
	


	
	//Constructor. This sets up all of the frames/menus/panels and adds the listeners for
	//each item.
	private myGUI() {
		
		f.setJMenuBar(mb);
		defaultColor = f.getBackground();

		mAddData.add(mItemAddNodes);
		mAddData.add(mItemAddLinks);
		mListData.add(mItemListNodes);
		mListData.add(mItemListLinks);
		mViewSystem.add(mItemViewGraph);
		mViewSystem.add(mItemRunSim);
		mFile.add(mItemSaveData);
		mFile.add(mItemLoadData);
		mQuit.add(mItemQuit);
		
		mb.add(mAddData);       
		mb.add(mListData);
		mb.add(mViewSystem);
		mb.add(mFile);
		mb.add(mQuit);
		
		nodeName = new JLabel("Node Name: ");
		linkName = new JLabel("Link Name: ");
		filename = new JLabel("File Name: ");
		filename2 = new JLabel("File Name: ");
		nodePrint = new JLabel("Node Data: \n");
		linkPrint = new JLabel("Link Data: \n");
		failureNode = new JLabel("Node Not Successfully Added! \n");
		failureLink = new JLabel("Link Not Successfully Added! \n");
		failureSave = new JLabel("Data Not Saved! \n");
		failureLoad = new JLabel("Data Not Loaded! \n");
		idReport = new JLabel("");
		node1 = new JLabel("Node 1: \n");
		node2 = new JLabel("Node 2: \n");
		xcoord = new JLabel("X-coordinate: \n");
		ycoord = new JLabel("Y-coordinate: \n");
		save = new JLabel("Data Saved! \n");
		load = new JLabel("Data Loaded! \n");
		newMS = new JLabel("New MS: \n");
		newMS.setForeground(Color.white);
		
		
		
		addNode = new JButton("Add Node");
		addLink = new JButton("Add Link");
		saveData = new JButton("Save To File");
		loadData = new JButton("Load File");
		cancelNode = new JButton("Cancel Operation");
		cancelLink = new JButton("Cancel Operation");
		cancelSave = new JButton("Cancel Operation");
		cancelLoad = new JButton("Cancel Operation");
		changeMS = new JButton("Change MS");
		consensus = new JButton("Consensus");
		leaderElection = new JButton("Leader Election");
		
		
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(nodeName);
		panel1.add(textField1);
		panel1.add(xcoord);
		panel1.add(textXCoord);
		panel1.add(ycoord);
		panel1.add(textYCoord);
		panel2.add(addNode);
		panel2.add(cancelNode);
		
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		panel3.add(linkName);
		panel3.add(textField2);
		panel3.add(node1);
		panel3.add(textField3);
		panel3.add(node2);
		panel3.add(textField4);
		panel4.add(addLink);
		panel4.add(cancelLink);
		
		
		panel6.add(failureNode);
		panel7.add(failureLink);
		panel11.add(failureSave);
		panel13.add(failureLoad);
		
		panel8.add(save);
		panel9.add(load);
		
		panel10.setLayout(new BoxLayout(panel10, BoxLayout.PAGE_AXIS));
		panel10.add(filename);
		panel10.add(textFile);
		panel14.add(saveData);
		panel14.add(cancelSave);
		
		panel12.setLayout(new BoxLayout(panel12, BoxLayout.PAGE_AXIS));
		panel12.add(filename2);
		panel12.add(textFile2);
		panel15.add(loadData);
		panel15.add(cancelLoad);
		
		panel16.setLayout(new FlowLayout());
		panel16.add(newMS);
		panel16.add(textMS);
		panel16.add(changeMS);
		panel16.setBackground(Color.black);
		
		panel17.setLayout(new FlowLayout());
		
		panel18.add(consensus);
		panel18.add(leaderElection);
		panel18.setBackground(Color.black);
		
		textXCoord.setText(val1);
		textYCoord.setText(val2);
		

		f.getContentPane().setLayout(new BorderLayout());
		
		
		this.addListeners();
	}
	
	
	//Instance method for singleton purposes.
	public static myGUI instance() {

		if (singleton == null) {
			singleton = new myGUI();
		}
		
		return singleton;
	}
		

	public class closeWindow extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);         
		}
	}
	
	public class menuQuit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);         
		}
	}
	
	
	public class addNode implements ActionListener{
		public void actionPerformed(ActionEvent e){

			name = con1.addNode(textField1.getText(), textXCoord.getText(), textYCoord.getText());
			clearFields();
			clearPanels();
			idReport = new JLabel(name);
			panel6.add(idReport);
			f.add(panel6);
			panel6.setVisible(true);
		}
	}
	
	
	public class addNodeQuit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			clearFields();
			clearPanels();
			f.add(panel6);
			failureNode.setVisible(true);
			panel6.setVisible(true);
		}
	}
	
	
	public class menuAddNode implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			clearPanels();
			
			f.add(panel1, BorderLayout.NORTH);
			f.add(panel2, BorderLayout.SOUTH);
			
			panel1.setVisible(true);
			panel2.setVisible(true);
			f.setVisible(true);

		}
	}
	
	public class makeConsensus implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			algorithmType = "Consensus";
			rethrowGraphics();
			f.addMouseListener(m1 = new ClickListener());
		}
	}
	
	public class makeLeaderElection implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			algorithmType = "Leader Election";
			rethrowGraphics();
			f.addMouseListener(m1 = new ClickListener());
		}
	}
	
	public class menuListNodes implements ActionListener{
		public void actionPerformed(ActionEvent e){
				
			clearPanels();
			String temp = "";
			ArrayList<String> nodesTemp = con1.getNodes();
			
			
			panel5 = new JPanel();
			panel5.setLayout(new BoxLayout(panel5, BoxLayout.PAGE_AXIS));
			panel5.add(nodePrint);
			
			for(int i = 0; i < nodesTemp.size(); i++) {
				temp = nodesTemp.get(i);
				dataPrint = new JLabel(temp);
				panel5.add(dataPrint, BorderLayout.NORTH);
			}
			
			f.add(panel5, BorderLayout.NORTH);
			panel5.setVisible(true);
			f.setVisible(true);
		}
	}
	
	
	public class addLink implements ActionListener{
		public void actionPerformed(ActionEvent e){

			name = con1.addLink(textField2.getText(),textField3.getText(),textField4.getText());
			clearFields();
			clearPanels();
			idReport = new JLabel(name);
			panel7.add(idReport);
			f.add(panel7);
			panel7.setVisible(true);
		}
	}
	

	public class addLinkQuit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			clearFields();
			clearPanels();
			f.add(panel7);
			failureLink.setVisible(true);
			panel7.setVisible(true);
		}
	}
	
	
	public class menuAddLink implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			clearPanels();
			
			f.add(panel3, BorderLayout.NORTH);
			f.add(panel4, BorderLayout.SOUTH);
			
			panel3.setVisible(true);
			panel4.setVisible(true);
			f.setVisible(true);
			
			
		}
	}
	
	
	public class menuListLinks implements ActionListener{
		public void actionPerformed(ActionEvent e){
				
			clearPanels();
			String temp = "";
			ArrayList<String> linksTemp = con1.getLinks();
			
			
			panel5 = new JPanel();
			panel5.setLayout(new BoxLayout(panel5, BoxLayout.PAGE_AXIS));
			panel5.add(linkPrint);
			
			for(int i = 0; i < linksTemp.size(); i++) {
				temp = linksTemp.get(i);
				dataPrint = new JLabel(temp);
				panel5.add(dataPrint, BorderLayout.NORTH);
			}
			
			f.add(panel5, BorderLayout.NORTH);
			panel5.setVisible(true);
			f.setVisible(true);
		}
	}
	
	
	public class menuViewGraph implements ActionListener{
		public void actionPerformed(ActionEvent e){
			rethrowGraphics();
			f.addMouseListener(m1 = new ClickListener());
		}
	}
	
	
	public class menuRunSim implements ActionListener{
		public void actionPerformed(ActionEvent e){
			rethrowGraphics();
			new sim().execute();
		}
	}
	
	
	public class sim extends SwingWorker<String, Object> {
		
		public String doInBackground() {
			p1.runSim();
			return null;
	    }

	    protected void done() {
	    	
	    }
	}
	
	
	public class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent event){
			if(event.getButton() == MouseEvent.BUTTON1) {
				String x = Integer.toString(event.getPoint().x - 5);
				String y = Integer.toString(event.getPoint().y - 45);
				clearPanels();
				f.addMouseListener(m1 = new ClickListener());
				
				con1.addNode("", x, y);
				f.getContentPane().setBackground(Color.black);
				p1 = new PaintPanel(con1.getNodeObjects(), con1.getLinkObjects(), algorithmType);
				f.add(p1);
				f.add(panel18, BorderLayout.SOUTH);
				f.setVisible(true);
			}
			else if(event.getButton() == MouseEvent.BUTTON3) {
				int x = event.getPoint().x - 5;
				int y = event.getPoint().y - 45;
				clearPanels();
				
				
				index = con1.withinNode(x,y);
				if(index == -1) {
					index = con1.withinMSSpace(x,y);
					if(index == -1) {
						System.out.println("Nothing selected");
						f.addMouseListener(m1 = new ClickListener());
					}
					else {
						l1 = con1.getLink(index);
						System.out.println(l1.getID());
						con1.setSelectedLink(l1, true);
						f.getContentPane().setBackground(Color.black);
						p1 = new PaintPanel(con1.getNodeObjects(), con1.getLinkObjects(), algorithmType);
						f.add(panel16, BorderLayout.NORTH);
						f.setVisible(true);
					}
				}
				else {
					n1 = con1.getNode(index);
					System.out.println(n1.getID());
					con1.setSelectedNode(n1, true);
					f.addMouseListener(m2 = new ClickListener2());
				}
				
				
				f.getContentPane().setBackground(Color.black);
				p1 = new PaintPanel(con1.getNodeObjects(), con1.getLinkObjects(), algorithmType);
				f.add(p1);
				f.add(panel18, BorderLayout.SOUTH);
				f.setVisible(true);
			}
		}
	}
	
	public class ClickListener2 extends MouseAdapter {
		public void mousePressed(MouseEvent event){
			if(event.getButton() == MouseEvent.BUTTON3) {
				int x = event.getPoint().x - 5;
				int y = event.getPoint().y - 45;
				clearPanels();
				
				index2 = con1.withinNode(x,y);
				if(index2 == -1) {
					System.out.println("Nothing selected");
				}
				else if(index == index2) {
					con1.removeNode(index);
				}
				else {
					Node n2 = con1.getNode(index2);
					System.out.println(n2.getID());
					con1.addLink("", n1.getID(), n2.getID());
				}
				f.getContentPane().setBackground(Color.black);
				p1 = new PaintPanel(con1.getNodeObjects(), con1.getLinkObjects(), algorithmType);
				f.add(p1);
				f.add(panel18, BorderLayout.SOUTH);
				f.setVisible(true);
			}
			
			
			con1.setSelectedNode(n1, false);
			f.addMouseListener(m1);
			f.removeMouseListener(m2);
		}
	}
	
	
	public class changeMS implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			con1.setLinkMS(l1, textMS.getText());
			f.remove(panel16);
			f.remove(p1);
			clearPanels();
			con1.setSelectedLink(l1, false);
			
			f.getContentPane().setBackground(Color.black);
			p1 = new PaintPanel(con1.getNodeObjects(), con1.getLinkObjects(), algorithmType);
			f.addMouseListener(m1 = new ClickListener());
			f.add(p1);
			f.setVisible(true);
		}
	}
	
	public class save implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(con1.saveData(textFile.getText()) == true) {
				clearFields();
				clearPanels();
				f.add(panel8);
				save.setVisible(true);
				panel8.setVisible(true);
			}
			else {
				clearFields();
				clearPanels();
				f.add(panel11);
				failureSave.setVisible(true);
				panel11.setVisible(true);
			}
		}
	}
	
	public class saveQuit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			clearFields();
			clearPanels();
			f.add(panel11);
			failureSave.setVisible(true);
			panel11.setVisible(true);
		}
	}
	
	//This sequence is called upon the user clicking on the save data button
	//at the top of the menu.
	public class menuSaveData implements ActionListener{
		public void actionPerformed(ActionEvent e){
					
			clearPanels();
			
			f.add(panel10, BorderLayout.NORTH);
			f.add(panel14, BorderLayout.SOUTH);
			
			panel10.setVisible(true);
			panel14.setVisible(true);
			f.setVisible(true);
		}
	}
	
	
	public class load implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(con1.loadData(textFile2.getText()) == true) {
				clearFields();
				clearPanels();
				f.add(panel9);
				load.setVisible(true);
				panel9.setVisible(true);
			}
			else {
				clearFields();
				clearPanels();
				f.add(panel13);
				failureLoad.setVisible(true);
				panel13.setVisible(true);
			}
		}
	}
	
	public class loadQuit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			clearFields();
			clearPanels();
			f.add(panel13);
			failureLoad.setVisible(true);
			panel13.setVisible(true);
		}
	}
	
	//This sequence is called upon the user clicking on the load data button
	//at the top of the menu.
	public class menuLoadData implements ActionListener{
		public void actionPerformed(ActionEvent e){
					
			clearPanels();
			f.add(panel12, BorderLayout.NORTH);
			f.add(panel15, BorderLayout.SOUTH);
			
			panel12.setVisible(true);
			panel15.setVisible(true);
			f.setVisible(true);
		}
	}
	

	public void addListeners() {
		
		mItemAddNodes.addActionListener(new menuAddNode());
		mItemListNodes.addActionListener(new menuListNodes());
		mItemAddLinks.addActionListener(new menuAddLink());
		mItemListLinks.addActionListener(new menuListLinks());
		mItemViewGraph.addActionListener(new menuViewGraph());
		mItemRunSim.addActionListener(new menuRunSim());
		mItemSaveData.addActionListener(new menuSaveData());
		mItemLoadData.addActionListener(new menuLoadData());
		mItemQuit.addActionListener(new menuQuit());
		
		addNode.addActionListener(new addNode());
		cancelNode.addActionListener(new addNodeQuit());
		addLink.addActionListener(new addLink());
		cancelLink.addActionListener(new addLinkQuit());
		saveData.addActionListener(new save());
		cancelSave.addActionListener(new saveQuit());
		loadData.addActionListener(new load());
		cancelLoad.addActionListener(new loadQuit());
		changeMS.addActionListener(new changeMS());
		consensus.addActionListener(new makeConsensus());
		leaderElection.addActionListener(new makeLeaderElection());
		
		f.addWindowListener(new closeWindow());		
	}
	
	//Create the gui for the user.
	public void makeGui() {

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1500,800);
		f.setVisible(true);
	}
	
	//Sequence to clear the panels to quickly be rid of them as the user
	//navigates through the gui.
	public void clearPanels() {
		
		
		f.removeMouseListener(m1);
		f.removeMouseListener(m2);
		f.removeMouseListener(m3);
		panel1.setVisible(false);
		panel2.setVisible(false);
		panel3.setVisible(false);
		panel4.setVisible(false);
		panel5.setVisible(false);
		panel6.setVisible(false);
		panel7.setVisible(false);
		panel8.setVisible(false);
		panel9.setVisible(false);
		panel10.setVisible(false);
		panel11.setVisible(false);
		panel12.setVisible(false);
		panel13.setVisible(false);
		panel14.setVisible(false);
		panel15.setVisible(false);
		//panel16.setVisible(false);
		//panel17.remove(message);
		failureNode.setVisible(false);
		failureLink.setVisible(false);
		panel6.remove(idReport);
		panel7.remove(idReport);
		f.remove(panel5);
		f.remove(panel18);
		f.remove(p1);
		f.getContentPane().setBackground(defaultColor);
		f.repaint();
	}
	
	//Sequence to clear the fields so the user does not have to delete
	//the fields manually.
	public void clearFields() {
		val1 = Integer.toString(generator.nextInt(1000) + 50);
		val2 = Integer.toString(generator.nextInt(600) + 50);
		textField1.setText("");
		textField2.setText("");
		textXCoord.setText(val1);
		textYCoord.setText(val2);
	}
	
	public void rethrowGraphics() {
		clearPanels();
		f.getContentPane().setBackground(Color.black);
		p1 = new PaintPanel(con1.getNodeObjects(), con1.getLinkObjects(), algorithmType);
		f.add(p1);
		f.add(panel18, BorderLayout.SOUTH);
		f.setVisible(true);
	}
	
	//main
	public static void main(String args[]) {
        
		myGUI gui = myGUI.instance();
		gui.makeGui();
	}
	
	
	
	public void windowOpened(WindowEvent event) {}
	public void windowIconified(WindowEvent event) {}
	public void windowDeiconified(WindowEvent event) {}
	public void windowClosed(WindowEvent event) {}
	public void windowActivated(WindowEvent event) {}
	public void windowDeactivated(WindowEvent event) {}
	public void windowClosing(WindowEvent event) {}
	public void actionPerformed(ActionEvent event) {}
}
