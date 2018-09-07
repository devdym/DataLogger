/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datalogger;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author dpatsiliandra
 */
class NavMsgDecoder {
    public static String GPSTime;
    public static double BSP, WSP, HDG, CMG;
    static void logReader(String msg)  {
       
        try{     
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            
            InputSource inString = new InputSource();
           // System.out.println("nav msg:" + msg);
            inString.setCharacterStream(new StringReader(msg));
            Document doc = docBuilder.parse(inString);
             // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList tlabel = doc.getElementsByTagName("data");
            
            Node label1 = tlabel.item(0);
            if (label1.getNodeType() == Node.ELEMENT_NODE){
                Element fstElemnt = (Element)label1;
                
                NodeList gpstime = fstElemnt.getElementsByTagName("gpstime").item(0).getChildNodes();
                GPSTime = gpstime.item(0).getNodeValue();
               
                NodeList bsp = fstElemnt.getElementsByTagName("bsp").item(0).getChildNodes();
                BSP = Double.valueOf(bsp.item(0).getNodeValue());
                
                NodeList wsp = fstElemnt.getElementsByTagName("wsp").item(0).getChildNodes();
                WSP = Double.valueOf(wsp.item(0).getNodeValue());
                
                NodeList hdg = fstElemnt.getElementsByTagName("hdg").item(0).getChildNodes();
                HDG = Double.valueOf(hdg.item(0).getNodeValue());
                
                NodeList cmg = fstElemnt.getElementsByTagName("cmg").item(0).getChildNodes();
                CMG = Double.valueOf(cmg.item(0).getNodeValue());
            }
            }catch (SAXParseException err) {
                System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
                System.out.println(" " + err.getMessage ());
            }catch (SAXException e) {
                Exception x = e.getException ();
        }catch (Throwable t) { } 
        try {
            NavDBWriter.logNavRecorder(GPSTime, BSP, WSP, HDG, CMG);
            // System.out.println("dec msg: " + GPSTime + " " + BSP + " " + WSP + " " + HDG + " " + CMG );    
        } catch (SQLException ex) {
            Logger.getLogger(NavMsgDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
