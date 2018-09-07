package datalogger;

import java.sql.Connection;

class ODIMMsgDecoder {
    
    public static void logReader(String msg, Connection con) {

        
        int index = 0;
        
        String  dHeaderMessageID,
                dHeaderNumberOfRetryes,
                dHederWantReceipt,
                dHeaderNumberOfByteFollowing,
                dHeaderDestinationObject,
                dHeaderOrigonObject,
                dHeaderCommand,
                dHeaderNumberOfByteUserData,
                iMessageCounter;
        
        int     rSRTensionValue,
                rSRCableLengthOut,
                rSRCableSpeed,
                rWTTensionValue,
                WTCableLengthOut,
                rWTCableSpeed;

        dHeaderMessageID = msg.substring(index, index = index + 8);
        dHeaderNumberOfRetryes = msg.substring(index, index = index + 8);
        dHederWantReceipt = msg.substring(index, index = index + 8);
        //System.out.println("headerWantReceipt: " + dHederWantReceipt);
        dHeaderNumberOfByteFollowing = msg.substring(index, index = index + 8);
        dHeaderDestinationObject = msg.substring(index, index = index + 8);
        dHeaderOrigonObject = msg.substring(index, index = index + 8);
        dHeaderCommand = msg.substring(index, index = index + 8);
        dHeaderNumberOfByteUserData = msg.substring(index, index = index + 8);
        iMessageCounter = msg.substring(index, index = index + 4);

        String w, s, wt;
        int winchesNB, StreamersNB, WTNB, leadNB;
        w = dHederWantReceipt.substring(0, 2);
        s = dHederWantReceipt.substring(2, 4);
        wt = dHederWantReceipt.substring(4, 8);
                
        winchesNB = Integer.parseInt(w, 16);
        StreamersNB = Integer.parseInt(s, 16);
        WTNB = Integer.parseInt(wt, 16);
        leadNB = winchesNB - StreamersNB - WTNB;
        
        int[] rSRTensionValue_Array = new int[StreamersNB];
        int[] rSRCableLengthOut_Array = new int[StreamersNB];
        int[] rSRCableSpeed_Array = new int[StreamersNB];
        
        int[] rLRTensionValue_Array = new int[leadNB];
        int[] rLRCableLengthOut_Array = new int[leadNB];
        int[] rLRCableSpeed_Array = new int[leadNB];
        
        int[] rWTTensionValue_Array = new int[WTNB];
        int[] rWTCableLengthOut_Array = new int[WTNB];
        int[] rWTCableSpeed_Array = new int[WTNB];
        
        for (int i = 0; i < StreamersNB; i++) { //winches
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rSRTensionValue_Array"+i+":"+f);
            rSRTensionValue_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rSRCableLengthOut_Array"+i+":"+f);
            rSRCableLengthOut_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rSRCableSpeed_Array"+i+":"+f);
            rSRCableSpeed_Array[i] = Math.round(f);
        }
        for (int i = 0; i < leadNB; i++) { //Lead-in winches
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rSRTensionValue_Array"+i+":"+f);
            rLRTensionValue_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rSRCableLengthOut_Array"+i+":"+f);
            rLRCableLengthOut_Array[i] = Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rSRCableSpeed_Array"+i+":"+f);
            rLRCableSpeed_Array[i] = Math.round(f);
        }
        
        for (int i = 0; i < WTNB; i++) { //wide tow
            String myString = msg.substring(index, index = index + 8);
            Long in = Long.parseLong(myString, 16);
            Float f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rWTTensionValue_Array"+i+":"+f);
            rWTTensionValue_Array[i] =  Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            //System.out.println("WTCableLengthOut_Array"+i+":"+f);
            rWTCableLengthOut_Array[i] =  Math.round(f);
            
            myString = msg.substring(index, index = index + 8);
            in = Long.parseLong(myString, 16);
            f = Float.intBitsToFloat(in.intValue());
            //System.out.println("rWTCableSpeed_Array"+i+":"+f);
            rWTCableSpeed_Array[i] =  Math.round(f);      
        }
            ODIMDBWriter.LogRecorder(rSRTensionValue_Array, rSRCableLengthOut_Array, rSRCableSpeed_Array,
                                     rLRTensionValue_Array, rLRCableLengthOut_Array, rLRCableSpeed_Array,
                                     rWTTensionValue_Array, rWTCableLengthOut_Array, rWTCableSpeed_Array, con);
    }
}
