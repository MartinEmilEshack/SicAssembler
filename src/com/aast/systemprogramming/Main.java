package com.aast.systemprogramming;

import com.aast.systemprogramming.sic.SicFileIO;
import com.aast.systemprogramming.sic.SicInstruction;
import com.aast.systemprogramming.sic.SymbolTableIO;
import com.aast.systemprogramming.sicxe.Format1;
import com.aast.systemprogramming.sicxe.Format2;
import com.aast.systemprogramming.sicxe.Format3;
import com.aast.systemprogramming.sicxe.Format4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args){

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

        firstPassSICXE();

//        firstPassSIC();
//        secondPassSIC();

    }

    private static void firstPassSIC(){

        int locCounter = 0;
        SicFileIO sicFileIO = new SicFileIO("E:\\AAST\\Term7\\Microprocessors\\Lecture1Code.asm");
        SymbolTableIO symbolTableIO = new SymbolTableIO("E:\\AAST\\Term7\\Microprocessors\\SymbolTable.txt");

        SicInstruction sicInstruction = sicFileIO.getSicInstruction();
        if(sicInstruction.isStart())
            locCounter = Integer.parseInt(sicInstruction.getOperand(),16);
        sicFileIO.writeSicIntermediate(locCounter,sicInstruction);
        sicInstruction = sicFileIO.getSicInstruction();

        while (!sicInstruction.isEnd()){

            if(sicInstruction.forSymbol() && !symbolTableIO.searchSymbol(sicInstruction.getLabel())) {
                symbolTableIO.addSymbol(sicInstruction.getLabel(), locCounter);

                if (sicInstruction.getInstruction().equals("BYTE")) {

                    Matcher matcher = Pattern.compile("^X'\\w+'$").matcher(sicInstruction.getOperand());
                    if (matcher.find()) locCounter += 1;

                    matcher.usePattern(Pattern.compile("^C'(\\w+)'$"));
                    if (matcher.find()) locCounter += matcher.group(1).length();

                } else if (sicInstruction.getInstruction().equals("WORD"))
                    locCounter += 3;

                else if(sicInstruction.getInstruction().equals("RESW"))
                    locCounter += Integer.parseInt(sicInstruction.getOperand())*3;

                else
                    locCounter += Integer.parseInt(sicInstruction.getOperand());

            }else{
                sicFileIO.writeSicIntermediate(locCounter,sicInstruction);
                locCounter+=3;
            }
            sicInstruction = sicFileIO.getSicInstruction();
            if(sicInstruction == null) break;
        }
        sicFileIO.closeReader();
        symbolTableIO.close();
    }

    static private void secondPassSIC(){

    }

    private static void firstPassSICXE(){

        String testInstruction = "LABEL3\tRSB A0";

        if (Format1.isIt(testInstruction))
            System.out.println("Format 1 Instruction");
        else if (Format2.isIt(testInstruction))
            System.out.println("Format 2 Instruction");
        else if (Format3.isIt(testInstruction))
            System.out.println("Format 3 Instruction");
        else if (Format4.isIt(testInstruction))
            System.out.println("Format 4 Instruction");
        else
            System.out.println("Directive");

    }

}
