package com.aast.systemprogramming.sic;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

//    static HashMap<String,Integer> dataReserving = new HashMap<>();

    public static void main(String[] args){

//        String label_instruction_digit = "^(\\w*)\\s+(\\w+)\\s+(\\d+)\\s*$*";
//        String label_instruction_typeConstant = "^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$*";
//        String label_instruction_towOperands = "^(\\w*)\\s+(\\w+)\\s+(\\w+,\\w+)\\s*$*";
//        String label_instruction_label = "^(\\w*)\\s+(\\w+)\\s+(\\w*)\\s*$*";
//
//        Pattern pattern = Pattern.compile(label_instruction_digit);
//        Matcher match = pattern.matcher("\tSTCH\tBUFFER,X\n");
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

//        dataReserving.put("WORD",3);
//        dataReserving.put("BYTE",1);
//        dataReserving.put("RESW",0);
//        dataReserving.put("RESB",0);

        firstPass();

//        secondPass();

    }

    private static void firstPass(){

        int locCounter = 0;
        SicFileIO sicFileIO = new SicFileIO("E:\\AAST\\Term7\\Microprocessors\\Lecture1Code.asm");
        SymbolTableIO symbolTableIO = new SymbolTableIO("E:\\AAST\\Term7\\Microprocessors\\SymbolTable.txt");

        SicInstruction sicInstruction = sicFileIO.getSicInstruction();
        if(sicInstruction.isStart())
            locCounter = Integer.parseInt(sicInstruction.getOperand(),16);
        sicFileIO.writeSicIntermediate(locCounter,sicInstruction);
        sicInstruction = sicFileIO.getSicInstruction();

//        System.out.println(Integer.toHexString(locCounter));

        while (!sicInstruction.isEnd()){
//            System.out.println(sicInstruction);
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
//                System.out.println(Integer.toHexString(locCounter));
                sicFileIO.writeSicIntermediate(locCounter,sicInstruction);
                locCounter+=3;
            }
            sicInstruction = sicFileIO.getSicInstruction();
            if(sicInstruction == null) break;
        }
//        System.out.println(sicInstruction);
        sicFileIO.closeReader();
        symbolTableIO.close();
    }

    static private void secondPass(){

        String label_instruction_digit = "^(\\w*)\\s+(\\w+)\\s+(\\d+)\\s*$*";
        String label_instruction_typeConstant = "^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$*";
        String label_instruction_towOperands = "^(\\w*)\\s+(\\w+)\\s+(\\w+),(\\w+)\\s*$*";
        String label_instruction_label = "^(\\w*)\\s+(\\w+)\\s*(\\w*)\\s*$*";

        SicFileIO sicFileIO = new SicFileIO("E:\\AAST\\Term7\\Microprocessors\\Lecture1Code.asm");

        Pattern pattern = Pattern.compile(label_instruction_digit);
        Matcher match = pattern.matcher(sicFileIO.getString());
//        System.out.println(sicFileIO.getString());
        if(match.find()) {
            System.out.println("label_instruction_digit");
            System.out.println(match.group(1));
            System.out.println(match.group(2));
            System.out.println(match.group(3));
        }else if (match.usePattern(Pattern.compile(label_instruction_typeConstant)).find()){
            System.out.println("label_instruction_typeConstant");
            System.out.println(match.group(1));
            System.out.println(match.group(2));
            System.out.println(match.group(3));
        }else if(match.usePattern(Pattern.compile(label_instruction_towOperands)).find()){
            System.out.println("label_instruction_towOperands");
            System.out.println(match.group(1));
            System.out.println(match.group(2));
            System.out.println(match.group(3));
        } else {
            if(match.usePattern(Pattern.compile(label_instruction_label)).find()) {
                System.out.println("label_instruction_label");
                System.out.println(match.group(1));
                System.out.println(match.group(2));
                System.out.println(match.group(3));
            }
        }

    }

}
