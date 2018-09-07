package datalogger;


import java.io.StringReader;
import java.sql.Connection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SealMsgDecoder {
    //logReader
    static int  sp, file, seq, jday, wDilay, navMsgLength, traceNb, auxNb, 
                allTraceNb, deadTrace, recLength, sampleRate, sampleNb, 
                auxRecLength, auxSampleRate, auxSampleNb, auxFilter,
                traceFilter, trace3dBFilter, nbRecChSet, ExtHeaderSize;
    static String Line, date, time, mode, recType, fileType, testType, 
                  ExtHeader, SEGDerr, traceSumm,err_trace, Str_nb, Ch_nb, Anom;
    //headData
    static int strNb, tension, powerstat, current, voltage;
    static String date_h;         
    //tailData
    static int strNb_t, powerstat_t, current_t, voltage_t;
    static String date_t; 
    
    public static void logReader(String msg, Connection conn){

        try{     
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            
            InputSource inString = new InputSource();
//            System.out.println("msg: " + msg);
            inString.setCharacterStream(new StringReader(msg));
            Document doc = docBuilder.parse(inString);
             // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList tlabel = doc.getElementsByTagName("ACQ_LOG");
            
            Node label1 = tlabel.item(0);
            if (label1.getNodeType() == Node.ELEMENT_NODE){
                Element fstElemnt = (Element)label1;
                
                NodeList Line_Name = fstElemnt.getElementsByTagName("Line_Name").item(0).getChildNodes();
                Line = Line_Name.item(0).getNodeValue();
                                
                NodeList Sequence_Number = fstElemnt.getElementsByTagName("Sequence_Number").item(0).getChildNodes();
                seq = Integer.valueOf(Sequence_Number.item(0).getNodeValue());
 
                NodeList Shot_Point_Number = fstElemnt.getElementsByTagName("Shot_Point_Number").item(0).getChildNodes();
                sp = Integer.valueOf(Shot_Point_Number.item(0).getNodeValue());
                
                NodeList File_Number = fstElemnt.getElementsByTagName("File_Number").item(0).getChildNodes();
                file = Integer.valueOf(File_Number.item(0).getNodeValue());
                
                NodeList T0_Date = fstElemnt.getElementsByTagName("T0_Date").item(0).getChildNodes();
                date = T0_Date.item(0).getNodeValue();
                
                NodeList T0_Time = fstElemnt.getElementsByTagName("T0_Time").item(0).getChildNodes();
                time = T0_Time.item(0).getNodeValue();
                
                NodeList Julian_Day = fstElemnt.getElementsByTagName("Julian_Day").item(0).getChildNodes();
                jday = Integer.valueOf(Julian_Day.item(0).getNodeValue());
                
                NodeList T0_Mode = fstElemnt.getElementsByTagName("T0_Mode").item(0).getChildNodes();
                mode = T0_Mode.item(0).getNodeValue();
                
                NodeList Record_Type = fstElemnt.getElementsByTagName("Record_Type").item(0).getChildNodes();
                recType = Record_Type.item(0).getNodeValue();
                
                NodeList File_Type = fstElemnt.getElementsByTagName("File_Type").item(0).getChildNodes();
                fileType = File_Type.item(0).getNodeValue();
                
                NodeList Type_of_Test = fstElemnt.getElementsByTagName("Type_of_Test").item(0).getChildNodes();
                testType = Type_of_Test.item(0).getNodeValue();
                               
                NodeList Water_Delay = fstElemnt.getElementsByTagName("Water_Delay").item(0).getChildNodes();
                wDilay = Integer.valueOf(Water_Delay.item(0).getNodeValue());
                
                NodeList Navigation_Message_Length = fstElemnt.getElementsByTagName("Navigation_Message_Length").item(0).getChildNodes();
                navMsgLength = Integer.valueOf(Navigation_Message_Length.item(0).getNodeValue());
                
                NodeList Total_Number_of_Traces = fstElemnt.getElementsByTagName("Total_Number_of_Traces").item(0).getChildNodes();
                allTraceNb = Integer.valueOf(Total_Number_of_Traces.item(0).getNodeValue());
                
                NodeList Number_of_Aux_Traces = fstElemnt.getElementsByTagName("Number_of_Aux_Traces").item(0).getChildNodes();
                auxNb = Integer.valueOf(Number_of_Aux_Traces.item(0).getNodeValue());
                
                NodeList Number_of_Seis_Traces = fstElemnt.getElementsByTagName("Number_of_Seis_Traces").item(0).getChildNodes();
                traceNb = Integer.valueOf(Number_of_Seis_Traces.item(0).getNodeValue());
                
                NodeList Number_of_Dead_Seis_Channels = fstElemnt.getElementsByTagName("Number_of_Dead_Seis_Channels").item(0).getChildNodes();
                deadTrace = Integer.valueOf(Number_of_Dead_Seis_Channels.item(0).getNodeValue());
                
                NodeList Seal_Seis_Record_Length = fstElemnt.getElementsByTagName("Seal_Seis_Record_Length").item(0).getChildNodes();
                recLength = Integer.valueOf(Seal_Seis_Record_Length.item(0).getNodeValue());
 
                NodeList Seal_Seis_Sample_Rate = fstElemnt.getElementsByTagName("Seal_Seis_Sample_Rate").item(0).getChildNodes();
                sampleRate = Integer.valueOf(Seal_Seis_Sample_Rate.item(0).getNodeValue());
                
                NodeList Seal_Seis_Number_of_Samples = fstElemnt.getElementsByTagName("Seal_Seis_Number_of_Samples").item(0).getChildNodes();
                sampleNb = Integer.valueOf(Seal_Seis_Number_of_Samples.item(0).getNodeValue());
                
                NodeList Seal_Aux_Record_Length = fstElemnt.getElementsByTagName("Seal_Aux_Record_Length").item(0).getChildNodes();
                auxRecLength = Integer.valueOf(Seal_Aux_Record_Length.item(0).getNodeValue());
                
                NodeList Seal_Aux_Sample_Rate = fstElemnt.getElementsByTagName("Seal_Aux_Sample_Rate").item(0).getChildNodes();
                auxSampleRate = Integer.valueOf(Seal_Aux_Sample_Rate.item(0).getNodeValue());
                
                NodeList Seal_Aux_Number_of_Samples = fstElemnt.getElementsByTagName("Seal_Aux_Number_of_Samples").item(0).getChildNodes();
                auxSampleNb = Integer.valueOf(Seal_Aux_Number_of_Samples.item(0).getNodeValue());
                
                NodeList Aux_Digital_Low_Cut_Filter = fstElemnt.getElementsByTagName("Aux_Digital_Low_Cut_Filter").item(0).getChildNodes();
                auxFilter = Integer.valueOf(Aux_Digital_Low_Cut_Filter.item(0).getNodeValue());
                
                NodeList Seis_Digital_Low_Cut_Filter = fstElemnt.getElementsByTagName("Seis_Digital_Low_Cut_Filter").item(0).getChildNodes();
                traceFilter = Integer.valueOf(Seis_Digital_Low_Cut_Filter.item(0).getNodeValue());
                
                NodeList Seis3dB_Compound_Low_Cut_Filter = fstElemnt.getElementsByTagName("Seis3dB_Compound_Low_Cut_Filter").item(0).getChildNodes();
                trace3dBFilter = Integer.valueOf(Seis3dB_Compound_Low_Cut_Filter.item(0).getNodeValue());
                
                NodeList Nb_Of_Recorded_Channel_Set = fstElemnt.getElementsByTagName("Nb_Of_Recorded_Channel_Set").item(0).getChildNodes();
                nbRecChSet = Integer.valueOf(Nb_Of_Recorded_Channel_Set.item(0).getNodeValue());
                
                NodeList External_Header = fstElemnt.getElementsByTagName("External_Header");
                if(fstElemnt.getElementsByTagName("External_Header").getLength() > 0){
                    Node header = External_Header.item(0);
                    Node ch = header.getFirstChild();
                    if(ch == null){
                        ExtHeader = null;
                    }
                    else {
                        ExtHeader = ch.getNodeValue();
                        if(ExtHeader.contains("'")){
                           ExtHeader = ExtHeader.replaceAll("'", ".");
                            System.out.println("----edited----");
                        }
                    }
                }
                
                NodeList External_Header_Size = fstElemnt.getElementsByTagName("External_Header_Size").item(0).getChildNodes();
                ExtHeaderSize = Integer.valueOf(External_Header_Size.item(0).getNodeValue());
                
                NodeList SEGD_Disk_Write_Error = fstElemnt.getElementsByTagName("SEGD_Disk_Write_Error").item(0).getChildNodes();
                SEGDerr = SEGD_Disk_Write_Error.item(0).getNodeValue();
                
                NodeList Trace_Summing_Description = fstElemnt.getElementsByTagName("Trace_Summing_Description");
                if(fstElemnt.getElementsByTagName("Trace_Summing_Description").getLength() > 0){
                    Node summ = Trace_Summing_Description.item(0);
                    Node ch = summ.getFirstChild();
                    if(ch == null){
                        traceSumm = null;
                    }
                    else {
                        traceSumm = ch.getNodeValue();
                    }
                }
           
                NodeList List_of_Error_Traces = fstElemnt.getElementsByTagName("Error_Trace");
                //System.out.println("err: " + List_of_Error_Traces.getLength());
                if(List_of_Error_Traces.getLength()<1){
                   
                }else{
                    int i = List_of_Error_Traces.getLength();
                    err_trace = "";
                    while(i!=0){
                    NodeList Streamer_nb = fstElemnt.getElementsByTagName("Streamer_Nb").item(0).getChildNodes();
                    Str_nb = Streamer_nb.item(0).getNodeValue();
                    
                    NodeList Channel_nb = fstElemnt.getElementsByTagName("Channel_Nb").item(0).getChildNodes();
                    Ch_nb = Channel_nb.item(0).getNodeValue();
                    
                    NodeList Anomaly = fstElemnt.getElementsByTagName("Anomaly").item(0).getChildNodes();
                    Anom = Anomaly.item(0).getNodeValue();
                    
                   // System.out.println("str_nb "+Str_nb +" ch_nb "+ Ch_nb +" anom "+ Anom);
                    err_trace = err_trace + Str_nb +"-"+ Ch_nb + "-"+Anom+"/";
                   i--;
                }
                }
                SealDBWriter.logRecorder(sp, file, seq, jday, wDilay, navMsgLength, traceNb, auxNb, allTraceNb, deadTrace, 
               recLength, sampleRate, sampleNb, auxRecLength, auxSampleRate, auxSampleNb, auxFilter,
               traceFilter, trace3dBFilter, nbRecChSet, ExtHeaderSize, Line, date, time, mode, recType, fileType, testType, 
               ExtHeader, SEGDerr, traceSumm, err_trace, conn);
            }
            
            }catch (SAXParseException err) {
                System.out.println ("** Parsing error aqc" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
                System.out.println(" " + err.getMessage ());
            }catch (SAXException e) {
                Exception x = e.getException ();
       }catch (Throwable t) { }       
}
    
    public static void headMeasureReader(String hData, Connection conn){
        try{     
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            
            InputSource inString = new InputSource();
            //System.out.println("hdata:" + hData);
            inString.setCharacterStream(new StringReader(hData));
            Document doc = docBuilder.parse(inString);
             // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList tlabel = doc.getElementsByTagName("SEAL");
            
            Node label1 = tlabel.item(0);
            if (label1.getNodeType() == Node.ELEMENT_NODE){
                Element fstElemnt = (Element)label1;
                
                NodeList Streamer_Nb = fstElemnt.getElementsByTagName("Streamer_Nb").item(0).getChildNodes();
                strNb = Integer.valueOf(Streamer_Nb.item(0).getNodeValue());
              //  System.out.println("str: " + strNb);
                
                NodeList Date = fstElemnt.getElementsByTagName("Date").item(0).getChildNodes();
                date_h = Date.item(0).getNodeValue();
              //  System.out.println("date: " + date_h);
                NodeList Tension = fstElemnt.getElementsByTagName("Tension").item(0).getChildNodes();
                tension = Integer.valueOf(Tension.item(0).getNodeValue());
              //  System.out.println("ten: " + tension);
                powerstat = 0;
                
                if(fstElemnt.getElementsByTagName("PowerOn").getLength() != 0){
                    NodeList PowerOn = fstElemnt.getElementsByTagName("PowerOn").item(0).getChildNodes();
                    if(PowerOn != null){
                       // System.out.println(PowerOn.item(0).getNodeValue());
                        if(PowerOn.item(0).getNodeValue().contains("true")){
                            powerstat = 1;
                        }else {
                            powerstat = 0;
                        }
                       // System.out.println("power: " + powerstat);
                    }
                }    
                current = 0;
                if(fstElemnt.getElementsByTagName("Current").getLength() != 0){
                    NodeList Current = fstElemnt.getElementsByTagName("Current").item(0).getChildNodes();
                    if(Current != null){    
                        String t = (Current.item(0).getNodeValue());
                        current = Integer.valueOf(t.substring(0, t.indexOf(".")));
                      //  System.out.println("cueL" + t + ": "+ current);
                    }
                }
                voltage = 0; 
                if(fstElemnt.getElementsByTagName("OutputVoltage").getLength() != 0){
                    NodeList OutputVoltage = fstElemnt.getElementsByTagName("OutputVoltage").item(0).getChildNodes();
                    if(OutputVoltage != null){
                        String t = OutputVoltage.item(0).getNodeValue();
                        voltage = Integer.valueOf(t.substring(0, t.indexOf(".")));
                     //   System.out.println("vol: " + voltage);
                    }    
                }
                SealDBWriter.logHeadRecorder(strNb, date_h, tension, powerstat, current, voltage, conn);
            }
            }catch (SAXParseException err) {
                System.out.println ("** Parsing error seal" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
                System.out.println(" " + err.getMessage ());
            }catch (SAXException e) {
                Exception x = e.getException ();
       }catch (Throwable t) { }       
    }
    
    public static void tailMeasureReader(String tData, Connection conn){
         try{     
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            
            InputSource inString = new InputSource();
           
            inString.setCharacterStream(new StringReader(tData));
            Document doc = docBuilder.parse(inString);
             // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList tlabel = doc.getElementsByTagName("SEAL");
            
            Node label1 = tlabel.item(0);
            if (label1.getNodeType() == Node.ELEMENT_NODE){
                Element fstElemnt = (Element)label1;
                
                NodeList Streamer_Nb = fstElemnt.getElementsByTagName("Streamer_Nb").item(0).getChildNodes();
                //System.out.println(Streamer_Nb.item(0).getNodeValue());
                strNb_t = Integer.valueOf(Streamer_Nb.item(0).getNodeValue());
               
                NodeList Date = fstElemnt.getElementsByTagName("Date").item(0).getChildNodes();
              //  System.out.println(Date.item(0).getNodeValue());
                date_t = Date.item(0).getNodeValue();
                
                NodeList PowerOn = fstElemnt.getElementsByTagName("PowerOn").item(0).getChildNodes();
                if(PowerOn.item(0).getNodeValue().contains("true")){
                    powerstat_t = 1;
                }else {
                    powerstat_t = 0;
                }
                
                NodeList Current = fstElemnt.getElementsByTagName("Current").item(0).getChildNodes();
                current_t = Integer.valueOf(Current.item(0).getNodeValue());
                NodeList OutputVoltage = fstElemnt.getElementsByTagName("OutputVoltage").item(0).getChildNodes();
                String te = OutputVoltage.item(0).getNodeValue();
                voltage_t = Integer.valueOf(te.substring(0, te.indexOf(".")));
                
                SealDBWriter.logTailRecorder(strNb_t, date_t, powerstat_t, current_t, voltage_t, conn);
                }
            }catch (SAXParseException err) {
                System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
                System.out.println(" " + err.getMessage ());
            }catch (SAXException e) {
                Exception x = e.getException ();
       }catch (Throwable t) { }    
    }
}

