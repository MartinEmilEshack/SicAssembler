package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format1 {

    Format1(){

    }

    public static boolean isFormat1(String instruction){
        Matcher matcher = Pattern.compile("^\\s+(\\w+)\\s*$").matcher(instruction);
        return matcher.find() && !Directive.isIt(matcher.group(1));
    }

}
