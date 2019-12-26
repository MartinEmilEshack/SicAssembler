package com.aast.systemprogramming.sicxe;

public class InstToOpCode {
    public static String[] validInstructions = {"ADD", "AND","COMP","DIV","J","JEQ","JGT","JLT","JSUB","LDA","LDT","LDCH","LDL","LDX","MUL","OR","RD",
            "RSUB","STA","STCH","STL","STSW","STX","SUB","TD","TIX","WD","LDB"};
    public static String[] correspondingOPCode = {"18","40","28","24","3C","30","34","38","48","00","74","50","08","04","20","44","D8","4C","0C","54","14","E8","10","1C",
            "E0","2C","DC","68"};

    public static String getOPCode(String inst){
        String ret = "Invalid Opcode";

        for(int i = 0; i<validInstructions.length; i++){
            if(inst.equals(validInstructions[i])){
                ret = correspondingOPCode[i];
                break;
            }
        }

        return ret;
    }
}
