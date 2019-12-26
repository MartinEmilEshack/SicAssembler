package com.aast.systemprogramming.sicxe;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SicXEMatcher {
    public static final String fullLine = "^(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*$";
    public static final String fullLineWithLabel = "^(\\w*)\\s+(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*$";
    public static final String operandEqualValue = "^(=)(\\w)(')(\\w+)(')\\s*$";
    public static final String operandValue = "^(\\w)(')(\\w+)(')\\s*$";
    public static final String regularInstruction = "^(\\w+)\\s*$";
    public static final String extendedInstruction = "^(\\+)(\\w+)\\s*$";
    public static final String regularAddressing = "^(\\w+)\\s*$";
    public static final String indirectAddressing = "^(\\@)(\\w+)\\s*$";
    public static final String immediateAddressing = "^(\\#)(\\w+)\\s*$";
    public static final String indexAddressing = "^(\\w+)(,)([xX])*$";
    public static final String RegToRegAddressing = "^(\\w+)(,)(\\w*)*$";



    public static boolean CheckMatch(String line, String condition){
        Pattern cond = Pattern.compile(condition);
        Matcher m = cond.matcher(line);
        if(m.find())
            return true;
        return false;
    }
    public static ArrayList<String> getMatchGroups(String line, String condition){
        ArrayList<String> matches = new ArrayList<>();
        Pattern cond = Pattern.compile(condition);
        Matcher m = cond.matcher(line);
        m.matches();
        for(int i = 0; i < m.groupCount();i++ ){
            matches.add(m.group(i+1));
        }
        return matches;
    }
}
