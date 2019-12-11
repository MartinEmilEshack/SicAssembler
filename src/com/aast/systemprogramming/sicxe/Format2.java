package com.aast.systemprogramming.sicxe;

import javax.xml.namespace.QName;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format2 {

    public static boolean isIt(String instruction){
        System.out.println(instruction);
        Matcher matcher = Pattern.compile("^(\\w*:?)\\s+(\\w+)\\s+(\\w+)\\s*,\\s*(\\w+)\\s*$").matcher(instruction);
        return matcher.find() &&
                !Directive.isIt(matcher.group(2)) &&
                Register.isIt(matcher.group(3)) &&
                Register.isIt(matcher.group(4));
    }

}
