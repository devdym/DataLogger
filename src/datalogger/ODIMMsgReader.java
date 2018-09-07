package datalogger;

import static datalogger.DataLogger.CONN_STRING;
import static datalogger.DataLogger.PASSWORD;
import static datalogger.DataLogger.USERNAME;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class ODIMMsgReader extends Thread{
    
    public boolean done = true;
    public int port;
    int i = 1;
    Connection con;
    @Override
    public void run(){
        try {
            con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ODIMMsgReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[202];
            while (done) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String msg = javax.xml.bind.DatatypeConverter.printHexBinary(receivePacket.getData());
// EVERY four mesg
//                if(i==4){
//                    ODIMMsgDecoder.logReader(msg, con);
//                    i=1;
//                   // System.out.println("record");
//                } else {
//                    i++;
//                } 
                //every msg
                System.out.println("msg:" + msg);
                ODIMMsgDecoder.logReader(msg, con);
            }
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public ODIMMsgReader(int ODIMPort){
        port = ODIMPort;
    }
}
