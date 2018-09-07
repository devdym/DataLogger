/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dym
 */
class AEDBWriter {
    static void LogRecorder(int[] rSRTensionValue_Array, int[] rSRCableLengthOut_Array, int[] rSRCableSpeed_Array,
            int[] rLRTensionValue_Array, int[] rLRCableLengthOut_Array, int[] rLRCableSpeed_Array,
            int[] rWTTensionValue_Array, int[] rWTCableLengthOut_Array, int[] rWTCableSpeed_Array, 
            int[] rUmbTensionValue_Array, int[] rUmbCableLengthOut_Array, int[] rUmbCableSpeed_Array,
            Connection con) {
        try {
            //record tension data
            Statement stmt = null;
            stmt = con.createStatement();
            for (int i = 0; i < rSRTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_Str_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rSRTensionValue_Array[i]*10) + ", " + rSRCableLengthOut_Array[i] + "," + rSRCableSpeed_Array[i] + ")");
                stmt.executeUpdate("INSERT INTO ODIM_Str_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rSRTensionValue_Array[i]*10) + ", " + rSRCableLengthOut_Array[i] + "," + rSRCableSpeed_Array[i] + ")");
            }
            for (int i = 0; i < rLRTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_Leadin_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rLRTensionValue_Array[i]*10) + ", " + rLRCableLengthOut_Array[i] + "," + rLRCableSpeed_Array[i] + ")");
                stmt.executeUpdate("INSERT INTO ODIM_Leadin_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rLRTensionValue_Array[i]*10) + ", " + rLRCableLengthOut_Array[i] + "," + rLRCableSpeed_Array[i] + ")");
            }
            for (int i = 0; i < rWTTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_WideTow_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rWTTensionValue_Array[i]*10) + ", " + rWTCableLengthOut_Array[i] + "," + rWTCableSpeed_Array[i] + ")");
                stmt.executeUpdate("INSERT INTO ODIM_WideTow_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rWTTensionValue_Array[i]*10) + ", " + rWTCableLengthOut_Array[i] + "," + rWTCableSpeed_Array[i] + ")");
            }
            for (int i = 0; i < rUmbTensionValue_Array.length; i++) {
                stmt.executeUpdate("INSERT INTO ODIM_Umb_winch(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rUmbTensionValue_Array[i]*10) + ", " + rUmbCableLengthOut_Array[i] + "," + rUmbCableSpeed_Array[i] + ")");
                stmt.executeUpdate("INSERT INTO ODIM_Umb_winch_cycle(Date_, Winch_number, TensionValue, CableLengthOut, CableSpeed) "
                        + "VALUES (sysdate(3), "+ (i+1) + ", " + (rUmbTensionValue_Array[i]*10) + ", " + rUmbCableLengthOut_Array[i] + "," + rUmbCableSpeed_Array[i] + ")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ODIMDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
