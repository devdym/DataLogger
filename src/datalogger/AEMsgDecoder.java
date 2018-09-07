/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datalogger;

import java.sql.Connection;

/**
 *
 * @author Dym
 */
class AEMsgDecoder {
public static void logReader(String msg, Connection con) {
        int index = 0;
        String  dHeaderMessageID,
                dHeaderYear, dHeaderMonth, dHeaderDay, dHeaderHour, dHeaderMin,dHeaderSec, dHeaderMil;        
        
        int     rSRTensionValue, rSRCableLengthOut, rSRCableSpeed, rWTTensionValue,
                WTCableLengthOut, rWTCableSpeed;

        dHeaderMessageID = msg.substring(index, index = index + 4);
        dHeaderYear = msg.substring(index, index = index + 4);
        dHeaderMonth = msg.substring(index, index = index + 4);
        dHeaderDay = msg.substring(index, index = index + 4);
        dHeaderHour = msg.substring(index, index = index + 4);
        dHeaderMin = msg.substring(index, index = index + 4);
        dHeaderSec = msg.substring(index, index = index + 4);
        dHeaderMil = msg.substring(index, index = index + 8);

        int StreamersNB = 16, WTNB = 2, leadNB = 2, UmbNB = 8;
                
        int[] rSRTensionValue_Array = new int[StreamersNB];
        int[] rSRCableLengthOut_Array = new int[StreamersNB];
        int[] rSRCableSpeed_Array = new int[StreamersNB];
        
        int[] rLRTensionValue_Array = new int[leadNB];
        int[] rLRCableLengthOut_Array = new int[leadNB];
        int[] rLRCableSpeed_Array = new int[leadNB];
        
        int[] rWTTensionValue_Array = new int[WTNB];
        int[] rWTCableLengthOut_Array = new int[WTNB];
        int[] rWTCableSpeed_Array = new int[WTNB];
        
        int[] rUmbTensionValue_Array = new int[UmbNB];
        int[] rUmbCableLengthOut_Array = new int[UmbNB];
        int[] rUmbCableSpeed_Array = new int[UmbNB];
        
        for (int i = 0; i < WTNB; i++) { //wide tow
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            rWTTensionValue_Array[i] =  Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rWTCableLengthOut_Array[i] =  Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rWTCableSpeed_Array[i] =  Math.round(f); 
        }
        for (int i = 0; i < leadNB; i++) { //Lead-in winches
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            rLRTensionValue_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rLRCableLengthOut_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rLRCableSpeed_Array[i] = Math.round(f);
        }
        for (int i = 0; i < StreamersNB; i++) { //winches
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            rSRTensionValue_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rSRCableLengthOut_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rSRCableSpeed_Array[i] = Math.round(f);
        }
        for (int i = 0; i < UmbNB; i++) { //umbilical
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            rUmbTensionValue_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rUmbCableLengthOut_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            rUmbCableSpeed_Array[i] = Math.round(f);
        }
            AEDBWriter.LogRecorder( rSRTensionValue_Array, rSRCableLengthOut_Array, rSRCableSpeed_Array,
                                    rLRTensionValue_Array, rLRCableLengthOut_Array, rLRCableSpeed_Array,
                                    rWTTensionValue_Array, rWTCableLengthOut_Array, rWTCableSpeed_Array, 
                                    rUmbTensionValue_Array, rUmbCableLengthOut_Array, rUmbCableSpeed_Array,
                                    con);
    }    
}
