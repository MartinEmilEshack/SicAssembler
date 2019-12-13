package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format3 {

    public static String RSUB = "RSUB";

//    op m
//    op @m
//    op #const
//    op m,X

    private int address = -1;
    private String label;
    private String operator;
    private String memory;

    private int ni;
    private int xbpe; // 12 - 10 - 8 - 4 - 2 - 0

    public Format3(String instruction) {
        int index = instruction.indexOf('@');
        if (index > -1) {
            instruction = (new StringBuilder(instruction)).deleteCharAt(index).toString();
            ni = 2;
        } else {
            index = instruction.indexOf('#');
            if (index > -1) {
                instruction = (new StringBuilder(instruction)).deleteCharAt(index).toString();
                ni = 1;
            } else
                ni = 3;
        }

        Matcher matcher = Pattern.compile("^(\\w*)\\s+RSUB\\s*$").matcher(instruction);
        if (matcher.find()) {
            //RSUB
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = "RSUB";
            xbpe = 0;

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*$")).find()
                && !instruction.contains("RSUB")) {
            //op m
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
            memory = matcher.group(3);
            xbpe = 2;

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$")).find()) {
            //op X'f2' or op C'dhbc'
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
            memory = matcher.group(3);
            xbpe = 2;

        } else if (matcher.usePattern(Pattern.compile("(^\\w*)\\s+(\\w+)\\s+(=\\w'\\w+')\\s*$")).find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
            memory = matcher.group(3);
            xbpe = 2;
            LiteralMemory.add(new LiteralMemory.Literal(
                    memory, memory.startsWith("=X'") ?
                    (memory.length() - 4) % 2 == 0 ?
                    (memory.length() - 4) / 2 : (memory.length() - 4) / 2 + 1 : memory.length() - 4));

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*,\\s*X\\s*$")).find()
                && !instruction.contains("RSUB")) {
            //op m,X
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
            memory = matcher.group(3);
            xbpe = 10;

        } else throw new IllegalArgumentException();
    }

    public Format3(int address, String instruction) {
        this(instruction);
        this.address = address;
    }

    public static boolean isIt(String instruction) {
//        System.out.println(instruction);
//        if (instruction.contains("RSUB")) return true;

        int index = instruction.indexOf('@');
        if (index > -1) instruction = (new StringBuilder(instruction)).deleteCharAt(index).toString();

        index = instruction.indexOf('#');
        if (index > -1) instruction = (new StringBuilder(instruction)).deleteCharAt(index).toString();

        Matcher matcher = Pattern.compile("^\\w*\\s+RSUB\\s*$").matcher(instruction);
        if (matcher.find()) return true;
        else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*$")).find() &&
                !instruction.contains("RSUB")) {
            return !Directive.isIt(matcher.group(1)) &&
                    !Register.isIt(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w'\\w+')\\s*$")).find() &&
                !instruction.contains("RSUB")) {
            return !Directive.isIt(matcher.group(1)) &&
                    !Register.isIt(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(=\\w'\\w+')\\s*$")).find()
                && !instruction.contains("RSUB")) {
            return !Directive.isIt(matcher.group(1)) &&
                    !Register.isIt(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*,\\s*X\\s*$")).find() &&
                !instruction.contains("RSUB")) {
            return !Directive.isIt(matcher.group(1)) &&
                    !Register.isIt(matcher.group(2));

        } else return false;
    }

    public static int getNextAddress(int locationCounter) {
        return locationCounter + 3;
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

    public String getMem() {
        return memory;
    }

    public int getNi() {
        return ni;
    }

    public boolean isIndexed() {
        return xbpe >= 10;
    }

    @Override
    public String toString() {

        String mem = (memory != null) ? memory : "";
        mem += (xbpe >= 10) ? ",X" : "";
        mem = (ni == 2) ? "@" + mem : (ni == 1) ? "#" + mem : mem;

        return address == -1 ?
                label == null ?
                        "\t\t" + operator + "\t" + mem :
                        "\t" + label + "\t" + operator + "\t" + mem :
                address + "\t" + label + "\t" + operator + "\t" + mem;

    }

}
