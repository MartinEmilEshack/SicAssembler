package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format1 {

    private int address = -1;
    private String label;
    private String operator;

    public Format1(String instruction) {
        Matcher matcher = Pattern.compile("^(\\w*)\\s+(\\w+)\\s*$").matcher(instruction);
        if (matcher.find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
        } else throw new IllegalStateException();
    }

    public Format1(int address, String instruction) {
        this(instruction);
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public String getLabel() {
        return label;
    }

    public String getOperator() {
        return operator;
    }

    public static boolean isIt(String instruction) {
        Matcher matcher = Pattern.compile("^\\w*\\s+(\\w+)\\s*$").matcher(instruction);
        return matcher.find() &&
                !Directive.isIt(matcher.group(1)) &&
                !matcher.group(1).equals(Format3.RSUB);
    }

    public static int getNextAddress(int locationCounter) {
        return locationCounter + 1;
    }

    @Override
    public String toString() {
        return address == -1 ?
                label == null ?
                        "\t\t" + operator :
                        "\t" + label + "\t" + operator :
                address + "\t" + label + "\t" + operator;
    }

}
