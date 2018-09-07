package datalogger;

import static datalogger.DataLogger.CONN_STRING;
import static datalogger.DataLogger.PASSWORD;
import static datalogger.DataLogger.USERNAME;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class SealMsgReader extends Thread {

    boolean flag = true, done = true, Forward;
    String msg, oneMsg = null, IP;
    int port;
    Connection conn ;
    
    @Override
    public void run() {
        try {
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("conn exe");
        } catch (SQLException ex) {
            Logger.getLogger(SealMsgReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (done) {
            try {
                Socket clientSocket = new Socket(IP, port);
                System.out.println("Connected\n");
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (flag) {
                    msg = inFromServer.readLine();
                    if (msg == null) {
                        flag = false;

                    } else {
                        oneMsg = oneMsg + "\n" + msg;
                        if (msg.contains("</ACQ_LOG>")) {
                            // System.out.println("onemsg:" + oneMsg);
                            if (oneMsg != null) {
                                if (oneMsg.startsWith("<")) {
                                    SealMsgDecoder.logReader(oneMsg, conn);
                                } else {
                                    SealMsgDecoder.logReader(oneMsg.substring(oneMsg.indexOf("<"), oneMsg.length()), conn);
                                }
                            }
                            oneMsg = null;
                        }
                        if (msg.contains("</STREAMER_HEAD_MEASURE>")) {
                            //System.out.println("onemsg:" + oneMsg);
                            if (oneMsg != null) {
                                if (oneMsg.startsWith("<")) {
                                    SealMsgDecoder.headMeasureReader(oneMsg, conn);
                                    //  SealMsgForwarder(oneMsg);
                                    if (Forward) {
                                        SealForward.send(oneMsg);
                                    }
                                } else {
                                    SealMsgDecoder.headMeasureReader(oneMsg.substring(oneMsg.indexOf("<"), oneMsg.length()), conn);
                                    // System.out.println("_________onemsg:" + oneMsg.substring(oneMsg.indexOf("<"), oneMsg.length()));
                                }
                            }
                            oneMsg = null;
                        }
                        if (msg.contains("</STREAMER_TAIL_MEASURE>")) {
                            //  System.out.println("onemsg:" + oneMsg);
                            if (oneMsg != null) {
                                if (oneMsg.startsWith("<")) {
                                    SealMsgDecoder.tailMeasureReader(oneMsg, conn);
//                                          //  SealMsgForwarder(oneMsg);
                                    if (Forward) {
                                        SealForward.send(oneMsg);
                                    }
                                } else {
                                    SealMsgDecoder.tailMeasureReader(oneMsg.substring(oneMsg.indexOf("<"), oneMsg.length()), conn);
                                }
                            }
                            oneMsg = null;
                        }
                    }
                }
                clientSocket.close();
                System.out.println("Disconnected\n");
            } catch (IOException ex) {
                Logger.getLogger(SealMsgReader.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("cab be msg");
                done = false;
                JOptionPane.showMessageDialog(null, "Cannot connect to the Seal 428 server", "Check", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public SealMsgReader(String IP_V, int port_V, boolean frw) {
        IP = IP_V;
        port = port_V;
        Forward = frw;
    }

    private void SealMsgForwarder(String oneMsg) {

    }

}
