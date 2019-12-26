package com.aast.systemprogramming.sicxe;

public class hteFormatter {
    public static String formatLabel(String label){
        label = label.trim();
        String formattedLabel;
        if(label.length()>=6)
            formattedLabel = label.substring(0,6);
        else
            formattedLabel = label;

            while(formattedLabel.length() < 6){
            formattedLabel += "X";
        }

        return formattedLabel;
    }


    public static String formatOpcode(String opcode){
        opcode = opcode.trim();
        String formattedOpcode;
        if(opcode.length()>=6)
            formattedOpcode = opcode.substring(0,6);
        else
            formattedOpcode = opcode;
        while(formattedOpcode.length() < 6){
            formattedOpcode = "0" + formattedOpcode;
        }
        return formattedOpcode;
    }
    public static String formatOpcode4(String opcode){
        opcode = opcode.trim();
        String formattedOpcode;
        if(opcode.length()>=4)
            formattedOpcode = opcode.substring(0,4);
        else
            formattedOpcode = opcode;
        while(formattedOpcode.length() < 4){
            formattedOpcode = "0" + formattedOpcode;
        }
        return formattedOpcode;
    }
    public static String formatOpcode5(String opcode){
        opcode = opcode.trim();
        String formattedOpcode;
        if(opcode.length()>=5)
            formattedOpcode = opcode.substring(0,5);
        else
            formattedOpcode = opcode;
        while(formattedOpcode.length() < 5){
            formattedOpcode = "0" + formattedOpcode;
        }
        return formattedOpcode;
    }

    public static String formatOpcode2(String opcode){
        opcode = opcode.trim();
        String formattedOpcode;
        if(opcode.length()>=4)
            formattedOpcode = opcode.substring(0,2);
        else
            formattedOpcode = opcode;
        while(formattedOpcode.length() < 2){
            formattedOpcode = "0" + formattedOpcode;
        }
        return formattedOpcode;
    }
    public static String formatOpcode3(String opcode){
        opcode = opcode.trim();
        String formattedOpcode;
        if(opcode.length()>=3)
            formattedOpcode = opcode.substring(0,3);
        else
            formattedOpcode = opcode;
        while(formattedOpcode.length() < 3){
            formattedOpcode = "0" + formattedOpcode;
        }
        return formattedOpcode;
    }
}
