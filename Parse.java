import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Parse {
	//Directory of Layout Folder in terra-operator-android-new
	final static String myDirectory = "/Users/aakash/Desktop/XMLParsing/terra-operator-android-new/MyApplication/app/src/main/res/layout"; 
	
	//Directory of where to output csv file. Make sure name of csv file is also in this path
	final static String outputPath = "/Users/aakash/Documents/Aakash's Files/Java/Parse/test.csv";
		
	final static String notFoundText = "NOTFOUND"; //change to empty string if you want
	
	static BufferedWriter writer;
	
	static String ID = "";
    static String text = "";    
    
    public static void main(String[] args) throws IOException {
    	    	    	
        writer = Files.newBufferedWriter(Paths.get(outputPath));
        
        writer.append("File Name, android:id, android:text\n"); //titles for columns for csv file
       
       	File dir = new File(myDirectory);
    	
    	File[] directoryListing = dir.listFiles(); //lists all xml files inside layout folder
    	
    	for (File childFile : directoryListing) { //looping through all xml files
    		findIDAndText(childFile);
    	}
    	
    	writer.flush(); //flush and close file writer
    	writer.close();
    } 
    
    
    public static void findIDAndText(File xmlFile) throws IOException{
    	
        
        String fileName = xmlFile.getName();
           
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            
            NodeList nodeList = doc.getElementsByTagName("*"); //looks for every single node in the file
                        
            for (int i = 0; i < nodeList.getLength(); i++) { //loops through every node in the xml file
                Element node = (Element) nodeList.item(i);
                
                if (node.getNodeName().equals("TextView")) { //check if node is called "<TextView"
                	// get a map containing the attributes of this node 
                    NamedNodeMap attributes = node.getAttributes();
             
                    // get the number of nodes in this map
                    int numAttrs = attributes.getLength();
                    
                    boolean idFound = false;
                    boolean textFound = false;
                    
                    for (int j = 0; j < numAttrs; j++) { //loops through all the attributes of the text view node
                    	
                        Attr attr = (Attr) attributes.item(j);
                        
                        if (attr.getNodeName() == "android:id") { 
                        	idFound = true;
                        	ID = attr.getNodeValue();
                        	
                        } else if (attr.getNodeName() == "android:text") {
                        	textFound = true;
                        	text = attr.getNodeValue();
                        	if (text.contains(",")) {
                        		text = "\"" + text + "\"";
                        	}
                    		text = text.replaceAll(",", "~");
                        }    
                    }
                    //done looping through all attributes
                    
                    //handling cases where android:id or android:text is not found
            		if (!idFound) {
            			ID = notFoundText;
            		}
            		if (!textFound) {
            			text = notFoundText;
            		}   
            		System.out.println(fileName + ", " + ID + ", " + text); //print to the console
            		
            		writer.append(fileName + ", " + ID); //write to the csv file
            		writer.append(", "); 
            		writer.write(text);
            		writer.append("\n");
            		
                }
                
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    	
    }
}