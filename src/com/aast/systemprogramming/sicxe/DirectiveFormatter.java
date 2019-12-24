package com.aast.systemprogramming.sicxe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectiveFormatter {

    private int address = -1;
    private String label;
    private Directive directive;
    private String operand;

    public DirectiveFormatter(String instruction) {
        Matcher matcher = Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$").matcher(instruction);
        if (matcher.find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            directive = Directive.valueOf(matcher.group(2));
            operand = matcher.group(3);

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\d+)\\s*$")).find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            directive = Directive.valueOf(matcher.group(2));
            operand = matcher.group(3);

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w+)\\s*$")).find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            directive = Directive.valueOf(matcher.group(2));
            operand = matcher.group(3);

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s*$")).find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            directive = Directive.valueOf(matcher.group(2));

        } else if (matcher.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+")).find()) {
            label = matcher.group(1).equals("") ? null : matcher.group(1);
            directive = Directive.valueOf(matcher.group(2));
            String rest = instruction;
            rest = rest.replace(matcher.group(0), "");
            if (rest.contains("+") ||
                    rest.contains("-") ||
                    rest.contains("*") ||
                    rest.contains("/"))
                operand = rest;
            else
                throw new IllegalStateException();
        } else throw new IllegalStateException();
    }

    public DirectiveFormatter(int address, String instruction) {
        this(instruction);
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public String getLabel() {
        return label;
    }

    public Directive getDirective() {
        return directive;
    }

    public String getOperand() {
        return operand;
    }

    public int getNextAddress(int locationCounter) {
        if (operand == null) return locationCounter;
        else if (operand.startsWith("X'"))
            return (locationCounter + operand.length() - 3) % 2 == 0 ?
                    (locationCounter + operand.length() - 3) / 2 : (locationCounter + operand.length() - 2) / 2;
        else if (operand.startsWith("C'"))
            return locationCounter + operand.length() - 3;
        else try {
                if (directive == null) return locationCounter;
                return directive.equals(Directive.RESB) ? locationCounter + Integer.parseInt(operand) :
                        directive.equals(Directive.RESW) ? locationCounter + Integer.parseInt(operand) * 3 : locationCounter;
            } catch (NumberFormatException NFE) {
                return locationCounter;
            }
    }

    @Override
    public String toString() {

        String operandString = operand == null ? "" : operand;
        String directiveName = directive == null ? "" : directive.toString();

        return address == -1 ?
                label == null ?
                        "\t\t" + directiveName + "\t" + operandString :
                        "\t" + label + "\t" + directiveName + "\t" + operandString :
                address + "\t" + label + "\t" + directiveName + "\t" + operandString;
    }

}
