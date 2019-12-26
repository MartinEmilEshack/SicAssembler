package com.aast.systemprogramming;

import com.aast.systemprogramming.sic.SicFileIO;
import com.aast.systemprogramming.sic.SicInstruction;
import com.aast.systemprogramming.sic.SymbolTableIO;
import com.aast.systemprogramming.sicxe.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        firstPassSICXE();
//        secondPassSICXE();
//        System.out.println(SicXEMatcher.getMatchGroups("0943 label LDA approx", SicXEMatcher.fullLineWithLabel));
//            System.out.println(tRecord.getFinalOpcode("LDT", "#300","0006"));
//        System.out.println(mRecord.createModificationRecord("000c"));
        secondPassSICXE();
    }

    private static void firstPassSICXE() {

        SicXeFileIO instructionFile = new SicXeFileIO("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SicXeSection.asm");

        int locationCounter = 0;
        String instruction = instructionFile.getSicXeInstruction();

        while (true) {
            if (Format1.isIt(instruction)) {
                Format1 instructionF1 = new Format1(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF1.toString());
                instructionFile.writeSymbol(locationCounter, instructionF1.getLabel());
                locationCounter = Format1.getNextAddress(locationCounter);

            } else if (Format2.isIt(instruction)) {
                Format2 instructionF2 = new Format2(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF2.toString());
                instructionFile.writeSymbol(locationCounter, instructionF2.getLabel());
                locationCounter = Format2.getNextAddress(locationCounter);

            } else if (Format3.isIt(instruction)) {
                Format3 instructionF3 = new Format3(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF3.toString());
                instructionFile.writeSymbol(locationCounter, instructionF3.getLabel());
                locationCounter = Format3.getNextAddress(locationCounter);

            } else if (Format4.isIt(instruction)) {
                Format4 instructionF4 = new Format4(instruction);
                instructionFile.writeIntermediate(locationCounter, instructionF4.toString());
                instructionFile.writeSymbol(locationCounter, instructionF4.getLabel());
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

                if (directive.getDirective().equals(Directive.EQU))
                    instructionFile.writeSymbol(locationCounter, directive.getLabel(), directive.getOperand());
                else
                    instructionFile.writeSymbol(locationCounter, directive.getLabel());

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

    private static void secondPassSICXE() {
        hteRecordIO hterecordIO = new hteRecordIO();
        String hteRec = "";
        hterecordIO.readIntermidiateFile("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\Intermediate.txt");
        System.out.println(hRecord.GetHRecord(hterecordIO.getProgName(), hterecordIO.getStartAddr() , hterecordIO.getProgLen()).toUpperCase().trim());
//        System.out.println(hterecordIO.totalLineLength);
//        System.out.println(hterecordIO.calcLocCounters);
//        System.out.println(hterecordIO.LocCounters);
//        System.out.println(hterecordIO.instructions);
//        System.out.println(hterecordIO.operands);
        ArrayList<String>[] codeData = new ArrayList[4];
        codeData[0] = hterecordIO.instructions;
        codeData[1] = hterecordIO.operands;
        codeData[2] = hterecordIO.LocCounters;
        codeData[3] = hterecordIO.calcLocCounters;
        System.out.println(tRecord.getAllTRecords(codeData).toUpperCase().trim());
        System.out.println(mRecord.dumpModificationRecord().toUpperCase().trim());
        System.out.println(eRecord.GetERecord(hterecordIO.getStartAddr()).toUpperCase().trim());
//        hteRecordIO.writeToHTERecord("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program");
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
