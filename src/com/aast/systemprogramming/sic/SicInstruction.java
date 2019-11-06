package com.aast.systemprogramming.sic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SicInstruction {

    private int address;
    private String label;
    private String instruction;
    private String operand;
    private String operand2;

    private SicInstruction(String instruction,String operand){
        label = "";
        this.instruction = instruction;
        this.operand = operand;
        operand2 = "";
    }

    private SicInstruction(String label,String instruction,String operand){
        this.label = label;
        this.instruction = instruction;
        this.operand = operand;
        operand2 = "";
    }

    private SicInstruction(String instruction,String[] operand){
        label = "";
        this.instruction = instruction;
        this.operand = operand[0];
        operand2 = operand[1];
    }


    //////////////////////////////////////////////////////////////////////////////////////

    private SicInstruction(int address,String instruction,String operand){
        this.address = address;
        label = null;
        this.instruction = instruction;
        this.operand = operand;
        operand2 = null;
    }

    private SicInstruction(int address,String label,String instruction,String operand){
        this.address = address;
        this.label = label;
        this.instruction = instruction;
        this.operand = operand;
        operand2 = null;
    }

    private SicInstruction(int address,String instruction,String[] operand){
        this.address = address;
        label = null;
        this.instruction = instruction;
        this.operand = operand[0];
        operand2 = operand[1];
    }

    /////////////////////////////////////////////////////////////////////////////////////


    String getLabel() {
        return label;
    }

    String getInstruction() {
        return instruction;
    }

    String getOperand() {
        return operand;
    }

    String getOperand2() {
        return operand2;
    }

    static SicInstruction parseInstruction(String instructionLine){

//        String label_instruction_digit = "^(\\w*)\\s+(\\w+)\\s+(\\d+)\\s*$*";
//        String label_instruction_typeConstant = "^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$*";
//        String label_instruction_towOperands = "^(\\w*)\\s+(\\w+)\\s+(\\w+,\\w+)\\s*$*";
//        String label_instruction_label = "^(\\w*)\\s+(\\w+)\\s+(\\w*)\\s*$*";

        Pattern pattern = Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\d+)\\s*$*");
        Matcher match = pattern.matcher(instructionLine);

        if(match.find()) {
//            System.out.print("label_instruction_digit\t");
            if(match.group(1) != null)
                return new SicInstruction(match.group(1),match.group(2),match.group(3));
            else
                return new SicInstruction(match.group(2),match.group(3));

        }else if (match.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w'\\w+')\\s*$*")).find()){

//            System.out.print("label_instruction_typeConstant\t");
            if(match.group(1) != null)
                return new SicInstruction(match.group(1),match.group(2),match.group(3));
            else
                return new SicInstruction(match.group(2),match.group(3));

        }else if(match.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s+(\\w+),(\\w+)\\s*$*")).find()){

//            System.out.print("label_instruction_towOperands\t");
            String[] operands = {match.group(3),match.group(4)};
            return new SicInstruction(match.group(2),operands);

        } else if(match.usePattern(Pattern.compile("^(\\w*)\\s+(\\w+)\\s*(\\w*)\\s*$*")).find()){
//            System.out.print("label_instruction_label\t");
//            System.out.print(match.group(1));
//            System.out.print(match.group(2));
//            System.out.println(match.group(3));
            if(match.group(1) != null)
                return new SicInstruction(match.group(1),match.group(2),match.group(3));
            else
                return new SicInstruction(match.group(2),match.group(3));

        }else {

//            System.out.print("Comment: "+instructionLine+"\t");
            return new SicInstruction("","");

        }
    }

    boolean isStart(){
        return instruction.equals("START");
    }

    boolean forSymbol(){
        return instruction.equals("WORD") || instruction.equals("BYTE") || instruction.equals("RESW") || instruction.equals("RESB");
    }

    boolean isEnd(){
        return instruction.equals("END");
    }

    String toIntermediateFormat(){
        Matcher matcher = Pattern.compile("^\\s+$").matcher(label);
        if (matcher.find()) label="\t";
        matcher.reset(instruction);
        if (matcher.find()) instruction = "";
        matcher.reset(operand);
        if (matcher.find()) operand = "";
        matcher.reset(operand2);
        if (matcher.find())
            return label+"\t"+instruction+"\t"+operand+','+operand2;
        else
            return label+"\t"+instruction+"\t"+operand;
    }

    @Override
    public String toString() {
        return label+"\t"+instruction+"\t"+operand+','+operand2;
    }
}
