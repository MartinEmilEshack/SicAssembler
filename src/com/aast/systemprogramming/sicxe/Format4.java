package com.aast.systemprogramming.sicxe;

public class Format4 {

    public static boolean isIt(String instruction){

        int plus = instruction.indexOf('+');
        return (plus >= 0) && Format3.isIt((new StringBuilder(instruction)).deleteCharAt(plus).toString());

    }

}
