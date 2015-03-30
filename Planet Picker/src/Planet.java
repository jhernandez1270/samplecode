import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


//imports for XMl handling
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
// import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
// import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Element;

import java.io.File;
// import java.io.FileWriter;

// Written by Jesse Hernandez for the My Devices Page testing for Jawbone

public class Planet extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Planet(){
// **** ACTUAL WORK DONE **************		
		//Kill Updater First
		try {
			killUpdater();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create the UI for Planet Picker
		planetPickerUI(checkExistingAppSettings());	// current settings passed in from checkExistingAppSettings() and set in the UI

// *************************************

// ***** METHODS *********		
		
	}
	// ****** Kill Updater before editing AppSettings.xml ******
	public static void killUpdater() throws IOException{
		Runtime rt = Runtime.getRuntime();
		String[] cmd = {"/usr/bin/killall", "Jawbone Updater"};
		//  if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1){ 
		//     rt.exec("taskkill /F /IM JawboneUpdater.exe");
	//}else{
		     rt.exec(cmd);
		     System.out.println("killing Updater now");
	}
	
	// ****** Start Updater after editing AppSettings.xml ******
		public static void startUpdater() throws IOException{
			Runtime rt = Runtime.getRuntime();
			String[] cmd = {"/usr/bin/open", "/Applications/Jawbone Updater.app"};
			//  if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1){ 
			//     rt.exec("taskkill /F /IM JawboneUpdater.exe");
		//}else{
			     rt.exec(cmd);
			     System.out.println("Restarting Updater now");
		}

		
		// ****** Set AppSettings.xml PROD method ******
		public final void setAppSettings2Prod(String planetName){
			
			System.out.println("planet Name passed to setAppSettings2:" + planetName);
			//read in current AppSettings.xml file
			try {
		    	 
		    	File XmlFile = new File(System.getProperty("user.home"), "Library/Application Support/Jawbone Updater/AppSettings.xml");
		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(XmlFile);
		     
		    	//optional, but recommended
		    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		    	doc.getDocumentElement().normalize();
		        //Read the root element
		    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    	
		    	Node rootElement = doc.getFirstChild();
		    
		  
		    	NodeList currentBackendHostUrl = doc.getElementsByTagName("backendHostUrl");
		    	NodeList currentBackendHostUrl2 = doc.getElementsByTagName("backendHostUrl2");
		    	
		    	Node BEHUNode = currentBackendHostUrl.item(0);
		    	Node BEHUNode2 = currentBackendHostUrl2.item(0);
		    	
		    	System.out.println("Current Backend Host setting: " + currentBackendHostUrl);
		    	System.out.println("----------------------------");
		    	System.out.println("Setting Planet to: " + planetName); 
		    	
		    	System.out.println("Removing BackendHostUrls: " + BEHUNode + "," + BEHUNode2);
		    	
		    	rootElement.removeChild(BEHUNode);
		    	rootElement.removeChild(BEHUNode2);
		    	
		    	
		    	
/*	CHANGE THIS WHOLE BLOCK TO CHANGE TO PROD SETTINGS
*		   		Node BEHUNode = currentBackendHostUrl.item(0);
* //		   		Node BEHUNode2 = currentBackendHostUrl2.item(0);
*		   		if (BEHUNode == null){
*		   			//add backendHostUrl elements to AppSettings.xml file
*		   			Element backendHostUrl = doc.createElement("backendHostUrl");
*		   			Element backendHostUrl2 = doc.createElement("backendHostUrl2");
*		   			backendHostUrl.appendChild(doc.createTextNode("http://"+ planetName + ".jawbeta.com"));
*		   			backendHostUrl2.appendChild(doc.createTextNode("http://"+ planetName + ".jawbeta.com"));
*		   			rootElement.appendChild(backendHostUrl);
*		   			rootElement.appendChild(backendHostUrl2);
*		   		}else{
*
*		   			     	     
*	   		currentBackendHostUrl.item(0).setTextContent("http://"+ planetName + ".jawbeta.com");
*	   		currentBackendHostUrl2.item(0).setTextContent("http://"+ planetName + ".jawbeta.com");
*		   		}
*/
		    	
	   				// write the content into xml file
	   				TransformerFactory transformerFactory = TransformerFactory.newInstance();
	   				Transformer transformer = transformerFactory.newTransformer();
	   				DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(XmlFile);
	   				transformer.transform(source, result);
	   	 
	   				System.out.println("Write to AppSettings.xml Done!");
	   		
	   		
		    } catch (Exception e){
		    	e.printStackTrace();
		    	JOptionPane.showMessageDialog(null, "Please install Jawbone Updater to continue.");
		    	System.exit(0);
		    }
		

		}

	// ****** Set AppSettings.xml to QA Planets method ******
	public final void setAppSettings2QaPlanet(String planetName){
		if (planetName == "prod"){
			setAppSettings2Prod(planetName);
		}else{
		System.out.println("planet Name passed to setAppSettings2:" + planetName);
		//read in current AppSettings.xml file
		try {
	    	 
	    	File XmlFile = new File(System.getProperty("user.home"), "Library/Application Support/Jawbone Updater/AppSettings.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(XmlFile);
	     
	    	//optional, but recommended
	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    	doc.getDocumentElement().normalize();
	        //Read the root element
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    	
	    	Node rootElement = doc.getFirstChild();
	    	
	  
	    	NodeList currentBackendHostUrl = doc.getElementsByTagName("backendHostUrl");
	    	NodeList currentBackendHostUrl2 = doc.getElementsByTagName("backendHostUrl2");
	    	
	    	System.out.println("Current Backend Host setting: " + currentBackendHostUrl);
	    	System.out.println("----------------------------");
	    	System.out.println("Setting QA Planet to: " + planetName); 
	     
	   		Node BEHUNode = currentBackendHostUrl.item(0);
//	   		Node BEHUNode2 = currentBackendHostUrl2.item(0);
	   		if (BEHUNode == null){
	   			//add backendHostUrl elements to AppSettings.xml file
	   			Element backendHostUrl = doc.createElement("backendHostUrl");
	   			Element backendHostUrl2 = doc.createElement("backendHostUrl2");
	   			backendHostUrl.appendChild(doc.createTextNode("http://"+ planetName + ".jawbeta.com"));
	   			backendHostUrl2.appendChild(doc.createTextNode("http://"+ planetName + ".jawbeta.com"));
	   			rootElement.appendChild(backendHostUrl);
	   			rootElement.appendChild(backendHostUrl2);
	   		}else{

	   			     	     
   		currentBackendHostUrl.item(0).setTextContent("http://"+ planetName + ".jawbeta.com");
   		currentBackendHostUrl2.item(0).setTextContent("http://"+ planetName + ".jawbeta.com");
	   		}
	   		
   				// write the content into xml file
   				TransformerFactory transformerFactory = TransformerFactory.newInstance();
   				Transformer transformer = transformerFactory.newTransformer();
   				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(XmlFile);
   				transformer.transform(source, result);
   	 
   				System.out.println("Write to AppSettings.xml Done!");
   		
   		
	    } catch (Exception e){
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Please install Jawbone Updater to continue.");
	    	System.exit(0);
	    }
		}

	}
	
	//******** Set Dev Menu to True **************
	public final void devMenuTrue() {
		//read in AppSettings.xml file
		
	    try {
	    	 
	    	File XmlFile = new File(System.getProperty("user.home"), "Library/Application Support/Jawbone Updater/AppSettings.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(XmlFile);
	     
	    	//optional, but recommended
	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    	doc.getDocumentElement().normalize();
	        //Read the root element
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    	
	    	Node rootElement = doc.getFirstChild();
	  
	    	//copied section
	    	NodeList currentEnableDevMenu = doc.getElementsByTagName("enableDevMenu");
	    	
	    	System.out.println("----------------------------");
	    	 
	     
	   		Node BEHUNode = currentEnableDevMenu.item(0);
	   		System.out.println(BEHUNode);
	   		
	   		if(BEHUNode == null){
	   			// if Null addChild of enableDevMenu and set value to true
	   			Element enableDevMenu = doc.createElement("enableDevMenu");
	   			enableDevMenu.appendChild(doc.createTextNode("true"));
	   			rootElement.appendChild(enableDevMenu);
	   			
	   		}else{
	   			// else just set enableDevMenu value to true
	   			
	   			currentEnableDevMenu.item(0).setTextContent("true");
	   		}
	   		
	   	// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(XmlFile);
				transformer.transform(source, result);
	 
				System.out.println("Write to AppSettings.xml Done!");
	   		
	    } catch (Exception e){
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Please install Jawbone Updater to continue.");
	    	System.exit(0);
	    }
	
	}
	
	//******** Set Dev Menu to True **************
	public final void devMenuFalse() {
		//read in AppSettings.xml file
		
	    try {
	    	 
	    	File XmlFile = new File(System.getProperty("user.home"), "Library/Application Support/Jawbone Updater/AppSettings.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(XmlFile);
	     
	    	//optional, but recommended
	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    	doc.getDocumentElement().normalize();
	        //Read the root element
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    	
	    	Node rootElement = doc.getFirstChild();
	  
	    	//copied section
	    	NodeList currentEnableDevMenu = doc.getElementsByTagName("enableDevMenu");
	    	
	    	System.out.println("----------------------------");
	    	 
	     
	   		Node BEHUNode = currentEnableDevMenu.item(0);
	   		System.out.println(BEHUNode);
	   		
	   		if(BEHUNode == null){
	   			// if Null addChild of enableDevMenu and set value to true
	   			Element enableDevMenu = doc.createElement("enableDevMenu");
	   			enableDevMenu.appendChild(doc.createTextNode("false"));
	   			rootElement.appendChild(enableDevMenu);
	   			
	   		}else{
	   			// else just set enableDevMenu value to false
	   			
	   			currentEnableDevMenu.item(0).setTextContent("false");
	   		}
	   		
	   	// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(XmlFile);
				transformer.transform(source, result);
	 
				System.out.println("Write to AppSettings.xml Done!");
	   		
	    } catch (Exception e){
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Please install Jawbone Updater to continue.");
	    	System.exit(0);
	    }
	
	}
	
	
	
	// ****** Check existing AppSettings.xml method ******
	public final String checkExistingAppSettings() {
		//read in AppSettings.xml file
		String planetName = "";
	    try {
	    	 
	    	File XmlFile = new File(System.getProperty("user.home"), "Library/Application Support/Jawbone Updater/AppSettings.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(XmlFile);
	     
	    	//optional, but recommended
	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    	doc.getDocumentElement().normalize();
	        //Read the root element
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	  
	    	//copied section
	    	NodeList currentBackendHostUrl = doc.getElementsByTagName("backendHostUrl");
	  //  	NodeList currentBackendHostUrl2 = doc.getElementsByTagName("backendHostUrl2");
	    	
	    	System.out.println("----------------------------");
	    	 
	   // 	for (int temp = 0; temp < currentBackendHostUrl.getLength(); temp++) {
	     
	   		Node BEHUNode = currentBackendHostUrl.item(0);
	//   		Node BEHU2Node = currentBackendHostUrl2.item(0);
	   		System.out.println(BEHUNode);
	   		
	   		if(BEHUNode == null){
	   			planetName = "prod";
	   			System.out.println(planetName);
	   		}else{
	   		
	   		planetName = BEHUNode.getTextContent();
	   		//planetName = BEHUNode.getNodeValue();
	   		planetName = planetName.substring(planetName.indexOf('/') +2, planetName.indexOf('.'));
	   		System.out.println(planetName);
	   		}
	   		

	     
	   //		System.out.println("\nCurrent Element :" + BEHUNode.getNodeName());
	  //		System.out.println("\nCurrect Text :" + BEHUNode.getTextContent());
	  		
	  //		System.out.println("\nCurrent Element2 :" + BEHU2Node.getNodeName());
	  //		System.out.println("\nCurrect Text2 :" + BEHU2Node.getTextContent());
	     
	    		
	    //	}
	    	// Read backendHostUrl element
	  //test 	Node firstChild = doc.getFirstChild();
	  //test  	System.out.println(firstChild.getElegetTextContent());
	  //test  	NodeList currentBackendHostUrl = doc.getElementsByTagName("backendHostUrl");
	  //test 	Node backEndText = doc.getTextContent();
	  //test  	System.out.println("Current backendHostUrl: " + currentBackendHostUrl.);
	    	
	    } catch (Exception e){
	    	e.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Please install Jawbone Updater to continue.");
	    	System.exit(0);
	    }
		return planetName;
	}
	
	// ****** planetPickerUI method ******
	public final void planetPickerUI(final String planetName) {
		
		
		// create Panel
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		panel.setLayout(null);
		
		//create Quit button
		JButton quitButton = new JButton("Quit and Re-start Updater");
		quitButton.setBounds(400, 300, 200, 30);
		quitButton.addActionListener ( new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try {
					startUpdater();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		//add Quit button to Panel
		panel.add(quitButton);
		
		//Create Combo box
		String[] planetString = {"prod","saturn","jupiter","pluto","hoth"};
		
		final JComboBox planetList = new JComboBox<String>(planetString);
		planetList.setBounds(60, 200, 100, 25);
		planetList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Need to grab what index is selected in jComboBox and set that as planetName
				JComboBox cb = (JComboBox)e.getSource();
				String planetName = (String)cb.getSelectedItem();

				//change backendHostUrl in AppSettings.xml to selection made
				setAppSettings2QaPlanet(planetName);
			}
		});
		panel.add(planetList);
		
		//Create CUSTOM planet TextBox
		JLabel pre = new JLabel("http://www.");
		JLabel post = new JLabel(".com");
		final JTextField custPlanet = new JTextField(20);
		custPlanet.setBounds(60, 300, 150, 25);
		custPlanet.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String planetText = custPlanet.getText();
				String planetName = planetText;
				
			}
		});
		panel.add(custPlanet);
/* 		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(pre,BorderLayout.WEST);
		panel2.add(custPlanet, BorderLayout.CENTER);
		panel2.add(post, BorderLayout.EAST);
	*/
		
		
		//Create PROD and Planet radio buttons
		JRadioButton prod = new JRadioButton();
		prod.setText("PROD");
		prod.setBounds(50, 60, 75, 25);
		
		//When PROD is selected, disable ComboBox
		prod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				planetList.setEnabled(false);
				String planetName = "prod";
				setAppSettings2Prod(planetName);
			}
		});
		
		JRadioButton planets = new JRadioButton();
		planets.setText("QA Planets");
		planets.setBounds(50, 150, 100, 25);
		//When Planets is selected, enable ComboBox
		planets.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				planetList.setEnabled(true);
			}
		});
		
		//Create CUSTOM planet radio button
		JRadioButton customPlanet = new JRadioButton();
		customPlanet.setText("Custom Planet");
		customPlanet.setBounds(50, 250, 150, 25);
		//when CUSTOM planet selected, disable comboBox
		customPlanet.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				planetList.setEnabled(false);
			}
		});
		
		
		//group the radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(prod);
		group.add(planets);
		group.add(customPlanet);
		
		//add radio buttons to Panel
		panel.add(prod);
		panel.add(planets);
		panel.add(customPlanet);
		
		//set Dev Menu Enabled/Disable Toggle Button
		JToggleButton devMenu = new JToggleButton("Dev Menu Enable/Disable");
		  devMenu.setBounds(400, 60, 200, 30);
	      devMenu.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	            	tBtn.setText("Dev Menu is: Enabled");
	               System.out.println("Dev Menu Enabled");
	               devMenuTrue();
	            } else {
	            	tBtn.setText("Dev Menu is: Disabled");
	               System.out.println("Dev Menu Disabled");
	               devMenuFalse();
	            }
	         }
	      });
	      
	      //add Dev Menu Toggle to panel
	      panel.add(devMenu);
		
		//Set Panel size
		setTitle("Planet Picker");
		setSize(600,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		//Set Prod Radio button selected if planetName = "Prod"
		if (planetName == "prod"){
			prod.setSelected(true);
			//if planetName == prod set the jCombobox to prod
	//		planetList.setSelectedItem("prod");
			planetList.setEnabled(false);
		}else{   //Set QA Planet Radio button selected if planetName != "Prod"
			planets.setSelected(true);
			planetList.setEnabled(true);
			planetList.setSelectedItem(planetName);
		}
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a Planet instance
		Planet myPlanet = new Planet();
		myPlanet.setVisible(true);
	}

}
