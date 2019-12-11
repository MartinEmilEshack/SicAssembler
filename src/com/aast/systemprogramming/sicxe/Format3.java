package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format3 {

    public static String RSUB = "RSUB";

//    op m
//    op @m
//    op #const
//    op m,X

    public static boolean isIt(String instruction){
//        System.out.println(instruction);
        if (instruction.contains("RSUB")) return true;

        int index = instruction.indexOf('@');
        if(index > -1) instruction = (new StringBuilder(instruction)).deleteCharAt(index).toString();

        index = instruction.indexOf('#');
        if(index > -1) instruction = (new StringBuilder(instruction)).deleteCharAt(index).toString();

        Matcher matcher = Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*$").matcher(instruction);
        if (matcher.find()){
            return !Directive.isIt(matcher.group(1)) &&
                    !Register.isIt(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*,\\s*(\\w+)\\s*$")).find()){
            return !Directive.isIt(matcher.group(1)) &&
                    !Register.isIt(matcher.group(2)) &&
                    matcher.group(3).equals(Register.X.toString());

        } else return false;
    }

}
