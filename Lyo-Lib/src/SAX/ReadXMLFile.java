package SAX;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê9ÔÂ28ÈÕ       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class ReadXMLFile {
	
	public static void main(String[] arg){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler() {
				boolean bfname = false;
				boolean blname = false;
				boolean bnname = false;
				boolean bsalary = false;
				
				public void startElement(String uri, String localName,String qName,
						Attributes atts){
					if (qName.equalsIgnoreCase("FIRSTNAME")) {
						bfname = true;
					}
			 
					if (qName.equalsIgnoreCase("LASTNAME")) {
						blname = true;
					}
			 
					if (qName.equalsIgnoreCase("NICKNAME")) {
						bnname = true;
					}
			 
					if (qName.equalsIgnoreCase("SALARY")) {
						bsalary = true;
					}
				}
				
				public void characters(char ch[], int start, int length) throws SAXException {
					 
					if (bfname) {
						System.out.println("First Name : " + new String(ch, start, length));
						bfname = false;
					}
			 
					if (blname) {
						System.out.println("Last Name : " + new String(ch, start, length));
						blname = false;
					}
			 
					if (bnname) {
						System.out.println("Nick Name : " + new String(ch, start, length));
						bnname = false;
					}
			 
					if (bsalary) {
						System.out.println("Salary : " + new String(ch, start, length));
						bsalary = false;
					}
			 
				}
				
			};
			
				parser.parse("D:\\eclipse\\workspace\\lyo\\Lyo-Lib\\src\\SAX\\Company.xml", handler);
				
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
 