package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format2 {

//    op r1,r2
//    op r1
//    op r1,n

    public static boolean isIt(String instruction){
//        System.out.println(instruction);
        Matcher matcher = Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*$").matcher(instruction);
        if(matcher.find()){
            return !Directive.isIt(matcher.group(1)) &&
                    Register.isIt(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*,\\s*(\\w+)\\s*$")).find()){

            int operand2;

            try {
                operand2 = Integer.parseInt(matcher.group(3));
            }catch (NumberFormatException NFE){
                operand2 = -1;
            }

            return !Directive.isIt(matcher.group(1)) &&
                    Register.isIt(matcher.group(2)) &&
                    (Register.isIt(matcher.group(3)) || operand2 >= 0);

        } else return false;
    }

}
