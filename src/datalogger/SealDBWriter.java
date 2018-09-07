package datalogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class SealDBWriter {
    static void logRecorder(int sp, int file, int seq, int jday, int wDilay, int navMsgLength,
            int traceNb, int auxNb, int allTraceNb, int deadTrace, int recLength, int sampleRate,
            int sampleNb, int auxRecLength, int auxSampleRate, int auxSampleNb, int auxFilter,
            int traceFilter, int trace3dBFilter, int nbRecChSet, int ExtHeaderSize,
            String Line, String date, String time, String mode, String recType, String fileType,
            String testType, String ExtHeader, String SEGDerr, String traceSumm, String errTraces, Connection conn) throws SQLException {
        
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = new SimpleDateFormat("dd MMM yyyy").parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(NavDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Statement stmt = null;
//        try (Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD)) {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO RealTimeLog (Line_name, Sequence_Number, Shot_Point_Number, "
                    + "File_Number, T0_Date, T0_Time, Julian_Day, T0_Mode, Record_Type, File_Type, "
                    + "Type_of_Test, Water_Delay, Navigation_Message_Length, Total_Number_of_Traces, "
                    + "Number_of_Aux_Traces, Number_of_Seis_Traces, Number_of_Dead_Sies_Channels, "
                    + "Seal_Seis_Record_Length, Seal_Seis_Sample_Rate, Seal_Seis_Number_of_Samples, "
                    + "Seal_Aux_Record_Length, Seal_Aux_Sample_Rate, Seal_Aux_Number_of_Samples, "
                    + "Aux_Digital_Low_Cut_Filter, Seis_Digital_Low_Cut_Filter, Seis3dB_Compound_Low_Cut_Filter, "
                    + "Nb_Of_Recorded_Channel_Set, External_Header, External_Header_Size, SEGD_Disk_Write_Error, "
                    + "Trace_Summing_Description, Error_Traces) VALUES ('"
                    + Line + "', " + seq + ", " + sp + ", " + file + ", '" + dfs.format(d) + "', '" + time + "', " + jday
                    + ", '" + mode + "', '" + recType + "', '" + fileType + "', '" + testType + "', " + wDilay
                    + ", " + navMsgLength + ", " + traceNb + ", " + auxNb + ", " + allTraceNb + ", " + deadTrace
                    + ", " + recLength + ", " + sampleRate + ", " + sampleNb + ", " + auxRecLength
                    + ", " + auxSampleRate + ", " + auxSampleNb + ", " + auxFilter + ", " + traceFilter
                    + ", " + trace3dBFilter + ", " + nbRecChSet + ", '" + ExtHeader + "', " + ExtHeaderSize
                    + ", '" + SEGDerr + "', '" + traceSumm + "', '" + errTraces + "')");
//        } catch (SQLException e) {
//            System.err.println(e);
//        } finally {
//            if (stmt != null) stmt.close();
//        }
            if (stmt != null) stmt.close();
    }
    
    static void logHeadRecorder(int strNb, String date_h, int tension, int powerstat, int current, int voltage, Connection conn) throws SQLException {
        //System.out.println("ten: " + tension);
        PreparedStatement stmt = null;
        String sql = "INSERT INTO Seal_MetricLog (Date_, Streamer, Tension, Headbuoy_stat, Headbuoy_A, Headbuoy_V) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);) {
            System.out.println("rec");
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, date_h);
            stmt.setInt(2, strNb);
            stmt.setInt(3, tension);
            stmt.setInt(4, powerstat);
            stmt.setInt(5, current);
            stmt.setInt(6, voltage);
            stmt.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println(e);
//        } finally {
            if(stmt != null) stmt.close();
//        }
    }

    static void logTailRecorder(int strNb_t, String date_t, int powerstat_t, int current_t, int voltage_t, Connection conn) throws SQLException {
        String d = date_t.replace("T", " ");
        d = d.substring(0, d.lastIndexOf(":"));
        d = d + "%"; 
        PreparedStatement stmt = null;
        String sql = "UPDATE Seal_MetricLog set Tailbuoy_stat = ?, Tailbuoy_V = ?, Tailbuoy_A =? WHERE Date_ like ? and Streamer = ?";
//        try (Connection con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);) {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, powerstat_t);
            stmt.setInt(2, voltage_t);
            stmt.setInt(3, current_t);
            stmt.setString(4, d);
            stmt.setInt(5, strNb_t);
            stmt.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println(e);
//        } finally {
            if(stmt != null) stmt.close();
//        }
       // System.out.println("str:" + strNb_t + " d: " + d + " power: " + powerstat_t + " a: " + current_t + " v: " + voltage_t);
    }
}
