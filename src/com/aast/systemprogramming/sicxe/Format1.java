package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format1 {

    Format1(){

    }

    public static boolean isIt(String instruction){
        Matcher matcher = Pattern.compile("^\\w*\\s+(\\w+)\\s*$").matcher(instruction);
        return matcher.find() &&
                !Directive.isIt(matcher.group(1)) &&
                !matcher.group(1).equals(Format3.RSUB);
    }

}
