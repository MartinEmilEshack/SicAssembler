package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format2 {

//    op r1,r2
//    op r1
//    op r1,n

    private int address = -1;
    private String label;
    private String operator;
    private Register r1;
    private Register r2;
    private int n = -1;

    public Format2(String instruction) {
        Matcher matcher = Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*$").matcher(instruction);
        if (matcher.find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
            r1 = Register.getReg(matcher.group(3));

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*,\\s*(\\w+)\\s*$")).find()) {
            int operand2;
            try {
                operand2 = Integer.parseInt(matcher.group(4));
            } catch (NumberFormatException NFE) {
                operand2 = -1;
            }

            label = matcher.group(1).equals("") ? null : matcher.group(1);
            operator = matcher.group(2);
            r1 = Register.getReg(matcher.group(3));
            if (operand2 > -1) n = operand2;
            else r2 = Register.getReg(matcher.group(4));

        } else throw new IllegalStateException();
    }

    public Format2(int address, String instruction) {
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

    public Register getR1() {
        return r1;
    }

    public Register getR2() {
        return r2;
    }

    public int getNumber() {
        return n;
    }

    public static boolean isIt(String instruction) {
//        System.out.println(instruction);
        Matcher matcher = Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*$").matcher(instruction);
        if (matcher.find()) {
            return !Directive.isIt(matcher.group(1)) &&
                    Register.isIt(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^\\w*\\s+(\\w+)\\s+(\\w+)\\s*,\\s*(\\w+)\\s*$")).find()) {

            int operand2;

            try {
                operand2 = Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException NFE) {
                operand2 = -1;
            }

            return !Directive.isIt(matcher.group(1)) &&
                    Register.isIt(matcher.group(2)) &&
                    (Register.isIt(matcher.group(3)) || operand2 >= 0);

        } else return false;
    }

    public static int getNextAddress(int locationCounter) {
        return locationCounter + 2;
    }

    @Override
    public String toString() {
        String numOrReg = (r2 != null) ? "," + r2 : (n != -1) ? "," + n : "";
        return address == -1 ?
                label == null ?
                        "\t\t" + operator + "\t" + r1 + numOrReg :
                        "\t" + label + "\t" + operator + "\t" + r1 + numOrReg :
                address + "\t" + label + "\t" + operator + "\t" + r1 + numOrReg;
    }

}
