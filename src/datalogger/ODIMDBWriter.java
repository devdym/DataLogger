package datalogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class ODIMDBWriter {

    static void LogRecorder(int[] rSRTensionValue_Array, int[] rSRCableLengthOut_Array, int[] rSRCableSpeed_Array,
            int[] rLRTensionValue_Array, int[] rLRCableLengthOut_Array, int[] rLRCableSpeed_Array,
            int[] rWTTensionValue_Array, int[] rWTCableLengthOut_Array, int[] rWTCableSpeed_Array, Connection con) {
        try {
            //record tension data
            Statement stmt = null;
            Statement stmt1 = null;
            //  try (Connection con = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD)) {
            System.out.println("rec");
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            for (int i = 0; i < rSRTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_Str_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + rSRTensionValue_Array[i] + ", " + rSRCableLengthOut_Array[i] + "," + rSRCableSpeed_Array[i] + ")");
                stmt1.executeUpdate("INSERT INTO ODIM_Str_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + rSRTensionValue_Array[i] + ", " + rSRCableLengthOut_Array[i] + "," + rSRCableSpeed_Array[i] + ")");
            
            }
            for (int i = 0; i < rLRTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_Leadin_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + rLRTensionValue_Array[i] + ", " + rLRCableLengthOut_Array[i] + "," + rLRCableSpeed_Array[i] + ")");
                stmt1.executeUpdate("INSERT INTO ODIM_Leadin_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + rLRTensionValue_Array[i] + ", " + rLRCableLengthOut_Array[i] + "," + rLRCableSpeed_Array[i] + ")");
            }
            for (int i = 0; i < rWTTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_WideTow_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + rWTTensionValue_Array[i] + ", " + rWTCableLengthOut_Array[i] + "," + rWTCableSpeed_Array[i] + ")");
                stmt1.executeUpdate("INSERT INTO ODIM_WideTow_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + rWTTensionValue_Array[i] + ", " + rWTCableLengthOut_Array[i] + "," + rWTCableSpeed_Array[i] + ")");
            }
//        } catch (SQLException e) {
//            System.err.println(e);
//        } finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(ODIMDBWriter.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        } catch (SQLException ex) {
            Logger.getLogger(ODIMDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
