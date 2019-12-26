package com.aast.systemprogramming.sicxe;

import java.util.ArrayList;

public class mRecord {
    public static ArrayList<String> modificationRecordBuffer = new ArrayList<>();
    public static String createModificationRecord(String pc){
        pc = hteFormatter.formatOpcode(Integer.toHexString((Integer.parseInt(pc,16) + 1)));
        String mod = "M . ";
        mod+=pc +" . 00000";
        mod+="5";
        modificationRecordBuffer.add(mod);
        return mod;
    }
    public static String dumpModificationRecord(){
        String modRec="";
        for(int i = 0; i< modificationRecordBuffer.size(); i++){
            modRec+=modificationRecordBuffer.get(i) + "\n";
        }
        return modRec;
    }
}
