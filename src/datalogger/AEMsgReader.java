/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Dym
 */
class AEMsgReader extends Thread{
    
    public boolean done = true;
    public int port;
    int i = 1;
    Connection con;
    @Override
    public void run(){
        try {
            con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(AEMsgReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[358];
            while (done) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String msg = javax.xml.bind.DatatypeConverter.printHexBinary(receivePacket.getData());
                AEMsgDecoder.logReader(msg, con);
            }
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public AEMsgReader(int AEPort) {
        port = AEPort;
    }
}
