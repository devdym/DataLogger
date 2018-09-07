/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpatsiliandra
 */
class NavMsgReader extends Thread {

    public boolean done = true;
    String URL;

    @Override
    public void run() {
        while (done) {
            try {
                StringBuilder msg = new StringBuilder();
                URL navURL = new URL(URL);
                URLConnection yc = navURL.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (!inputLine.contentEquals("<pre>")) {
                        msg.append(inputLine);
                    }
                 //   System.out.println(inputLine);
                }
                in.close();
                
                NavMsgDecoder.logReader(msg.toString());
                Thread.sleep(1000*60);
            } catch (MalformedURLException ex) {
                Logger.getLogger(NavMsgReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NavMsgReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(NavMsgReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    NavMsgReader(String rURL) {
        URL = rURL;
    }
}
