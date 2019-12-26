package com.aast.systemprogramming.sicxe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


public class hteRecordIO {

    public ArrayList<String> calcLocCounters = new ArrayList<String>();
    public ArrayList<String> LocCounters = new ArrayList<String>();
    public ArrayList<String> instructions = new ArrayList<String>();
    public ArrayList<String> operands = new ArrayList<String>();
    public int totalLineLength=1;
    public int startAddr;
    public int endAddr;
    public int progLen;
    private String output = "";
    public String progName;
    public void readIntermidiateFile(String path){
        try {
            Scanner scanner = new Scanner(new File(path));
            if(scanner.hasNextLine()){
                String firstLine = scanner.nextLine();
                if(SicXEMatcher.CheckMatch(firstLine, SicXEMatcher.fullLineWithLabel)){
                    ArrayList<String> firstLineInfo = SicXEMatcher.getMatchGroups(firstLine, SicXEMatcher.fullLineWithLabel);
                    if(firstLineInfo.get(2).equals("START")){
                        progName = firstLineInfo.get(1);
                        startAddr = Integer.parseInt(firstLineInfo.get(3), 16);
                    }


                }
            }
            while (scanner.hasNextLine()) {
                totalLineLength++;
                String line = scanner.nextLine();
                String[] split =  line.split("\\s+");
                calcLocCounters.add(split[0]);
                if(split.length == 3 &&
                        !split[1].equals("EQU") &&
                        !split[1].equals("END") &&
                        !split[1].equals("RESW") &&
                        !split[1].equals("BASE")){
                    LocCounters.add(split[0]);
                    instructions.add(split[1]);
                    operands.add(split[2]);
                }
                else if(split.length == 4 &&
                        !split[2].equals("EQU") &&
                        !split[2].equals("END") &&
                        !split[2].equals("RESW") &&
                        !split[2].equals("BASE")){
                    LocCounters.add(split[0]);
                    instructions.add(split[2]);
                    operands.add(split[3]);
                }
            }

            endAddr = Integer.parseInt(this.calcLocCounters.get(this.calcLocCounters.size()-1), 16);
            progLen = endAddr - startAddr;
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String matchFromLiteralTable(String path,String literal){
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split =  line.split("\\s");
                if(split[1].equals(literal)){
                    return split[0];
                }
            }

            scanner.close();
            return "Error";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Error";
        }
    }
    public static String matchFromSymbolTable(String path,String literal){
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split =  line.split("\\s");
                if(split[1].equals(literal)){
                    return split[0];
                }
            }

            scanner.close();
            return "Error";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Error";
        }
    }
    public String getStartAddr() {
        return hteFormatter.formatOpcode(String.valueOf(startAddr));
    }

    public String getEndAddr() {
        return hteFormatter.formatOpcode(String.valueOf(endAddr));
    }

    public String getProgLen() {
        return hteFormatter.formatOpcode(String.valueOf(progLen));
    }

    public String getProgName() {
        return hteFormatter.formatLabel(progName);
    }
    public String addToOutput(String val) throws FileNotFoundException {
        output+=val;
        writeToHTERecord("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\HTERecord.txt");
        return output;
    }
    public void writeToHTERecord(String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        writer.println(output);
        writer.close();
    }
}
