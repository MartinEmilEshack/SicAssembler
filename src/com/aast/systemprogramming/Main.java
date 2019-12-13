package com.aast.systemprogramming;

import com.aast.systemprogramming.sic.SicFileIO;
import com.aast.systemprogramming.sic.SicInstruction;
import com.aast.systemprogramming.sic.SymbolTableIO;
import com.aast.systemprogramming.sicxe.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        firstPassSICXE();

    }

    private static void firstPassSICXE() {

        SicXeFileIO instructionFile = new SicXeFileIO("E:\\AAST\\Term7\\System Programming\\Project\\SicAssembler\\src\\com\\aast\\systemprogramming\\SIC program\\SicXeSection.asm");

        int locationCounter = 0;
        String instruction = instructionFile.getSicXeInstruction();

        while (true) {
            if (Format1.isIt(instruction)) {
                Format1 instructionF1 = new Format1(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF1.toString());
                instructionFile.writeSymbol(instructionF1.getLabel(), locationCounter);
                locationCounter = Format1.getNextAddress(locationCounter);

            } else if (Format2.isIt(instruction)) {
                Format2 instructionF2 = new Format2(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF2.toString());
                instructionFile.writeSymbol(instructionF2.getLabel(), locationCounter);
                locationCounter = Format2.getNextAddress(locationCounter);

            } else if (Format3.isIt(instruction)) {
                Format3 instructionF3 = new Format3(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF3.toString());
                instructionFile.writeSymbol(instructionF3.getLabel(), locationCounter);
                locationCounter = Format3.getNextAddress(locationCounter);

            } else if (Format4.isIt(instruction)) {
                Format4 instructionF4 = new Format4(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF4.toString());
                instructionFile.writeSymbol(instructionF4.getLabel(), locationCounter);
                locationCounter = Format4.getNextAddress(locationCounter);

            } else {
                DirectiveFormatter directive = new DirectiveFormatter(instruction);
                instructionFile.writeIntermediate(locationCounter, directive.toString());

                if (directive.getDirective().equals(Directive.LTORG)) {
                    for (Literal literal : LiteralMemory.getLiterals()) {
                        locationCounter += literal.size;
                        instructionFile.writeLiteral(literal.label, locationCounter - literal.size);
                    }
                    LiteralMemory.clear();
                }

                instructionFile.writeSymbol(directive.getLabel(), locationCounter);
                locationCounter = directive.getNextAddress(locationCounter);

                if (directive.getDirective().equals(Directive.END)) {
                    for (Literal literal : LiteralMemory.getLiterals()) {
                        locationCounter += literal.size;
                        instructionFile.writeLiteral(literal.label, locationCounter - literal.size);
                    }
                    LiteralMemory.clear();
                    break;
                }

            }

            instruction = instructionFile.getSicXeInstruction();

        }

        instructionFile.closeReader();

    }

    private static void firstPassSIC() {

        int locCounter = 0;
        SicFileIO sicFileIO = new SicFileIO("E:\\AAST\\Term7\\Microprocessors\\Lecture1Code.asm");
        SymbolTableIO symbolTableIO = new SymbolTableIO("E:\\AAST\\Term7\\Microprocessors\\SymbolTable.txt");

        SicInstruction sicInstruction = sicFileIO.getSicInstruction();
        if (sicInstruction.isStart())
            locCounter = Integer.parseInt(sicInstruction.getOperand(), 16);
        sicFileIO.writeSicIntermediate(locCounter, sicInstruction);
        sicInstruction = sicFileIO.getSicInstruction();

        while (!sicInstruction.isEnd()) {

            if (sicInstruction.forSymbol() && !symbolTableIO.searchSymbol(sicInstruction.getLabel())) {
                symbolTableIO.addSymbol(sicInstruction.getLabel(), locCounter);

                if (sicInstruction.getInstruction().equals("BYTE")) {

                    Matcher matcher = Pattern.compile("^X'\\w+'$").matcher(sicInstruction.getOperand());
                    if (matcher.find()) locCounter += 1;

                    matcher.usePattern(Pattern.compile("^C'(\\w+)'$"));
                    if (matcher.find()) locCounter += matcher.group(1).length();

                } else if (sicInstruction.getInstruction().equals("WORD"))
                    locCounter += 3;

                else if (sicInstruction.getInstruction().equals("RESW"))
                    locCounter += Integer.parseInt(sicInstruction.getOperand()) * 3;

                else
                    locCounter += Integer.parseInt(sicInstruction.getOperand());

            } else {
                sicFileIO.writeSicIntermediate(locCounter, sicInstruction);
                locCounter += 3;
            }
            sicInstruction = sicFileIO.getSicInstruction();
            if (sicInstruction == null) break;
        }
        sicFileIO.closeReader();
        symbolTableIO.close();
    }

    static private void secondPassSIC() {

    }

    //        String label_instruction_digit = "^(\\w*)\\s+(\\w+)\\s+(\\d+)\\s*$*";
//        String label_instruction_typeConstant = "^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$*";
//        String label_instruction_towOperands = "^(\\w*)\\s+(\\w+)\\s+(\\w+),(\\w+)\\s*$*";
//        String label_instruction_label = "^(\\w*)\\s+(\\w+)\\s*(\\w*)\\s*$*";
//
//        SicFileIO sicFileIO = new SicFileIO("E:\\AAST\\Term7\\Microprocessors\\Lecture1Code.asm");
//
//        Pattern pattern = Pattern.compile(label_instruction_digit);
//        Matcher match = pattern.matcher(sicFileIO.getString());
////        System.out.println(sicFileIO.getString());
//        if(match.find()) {
//            System.out.println("label_instruction_digit");
//            System.out.println(match.group(1));
//            System.out.println(match.group(2));
//            System.out.println(match.group(3));
//        }else if (match.usePattern(Pattern.compile(label_instruction_typeConstant)).find()){
//            System.out.println("label_instruction_typeConstant");
//            System.out.println(match.group(1));
//            System.out.println(match.group(2));
//            System.out.println(match.group(3));
//        }else if(match.usePattern(Pattern.compile(label_instruction_towOperands)).find()){
//            System.out.println("label_instruction_towOperands");
//            System.out.println(match.group(1));
//            System.out.println(match.group(2));
//            System.out.println(match.group(3));
//        } else {
//            if(match.usePattern(Pattern.compile(label_instruction_label)).find()) {
//                System.out.println("label_instruction_label");
//                System.out.println(match.group(1));
//                System.out.println(match.group(2));
//                System.out.println(match.group(3));
//            }
//        }

}
