/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datalogger;


import static datalogger.DataLogger.CONN_STRING;
import static datalogger.DataLogger.PASSWORD;
import static datalogger.DataLogger.USERNAME;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpatsiliandra
 */
class NavDBWriter {
    static void logNavRecorder(String GPSTime, double BSP, double WSP, double HDG, double CMG) throws SQLException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(GPSTime);
        } catch (ParseException ex) {
            Logger.getLogger(NavDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement stmt = null;
        String sql = "INSERT INTO NavLog (Date_, BSP, WSP, HDG, CMG) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);) {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, dfs.format(date));
            stmt.setDouble(2, BSP);
            stmt.setDouble(3, WSP);
            stmt.setDouble(4, HDG);
            stmt.setDouble(5, CMG);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if(stmt != null) stmt.close();
        }
    }
}
