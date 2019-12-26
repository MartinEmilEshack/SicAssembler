package com.aast.systemprogramming.sicxe;

import java.math.BigInteger;
import java.util.ArrayList;

import static com.aast.systemprogramming.sicxe.hteFormatter.formatOpcode2;

public class tRecord {
    public static String getAllTRecords(ArrayList<String>[] codeData){
        String allTRecords="";
        boolean calculateNow = false;
        boolean firstTimeUwu = true;
        int startIndex = 0;
        int endIndex = 0;
        int arr1Index = 0;
        int arr2Index = 0;
        for (int i = 0; i < codeData[2].size(); i++){
            if(arr1Index < (codeData[2].size() - 1)) {
                while (codeData[2].get(arr1Index).compareTo(codeData[3].get(arr2Index)) == 0 && arr1Index < codeData[2].size()) {
                    arr1Index++;
                    arr2Index++;
                }
            }
            if(codeData[2].get(arr1Index).compareTo(codeData[3].get(arr2Index)) != 0){
                calculateNow = true;
                endIndex = arr1Index;

                while (codeData[2].get(arr1Index).compareTo(codeData[3].get(arr2Index)) != 0)
                {
                    arr2Index++;
                }
            }
            if(calculateNow == true){
                if(!firstTimeUwu)
                    startIndex++;
                else
                    firstTimeUwu = false;
                String length = Integer.toHexString(Integer.parseInt(codeData[2].get(endIndex),16)-Integer.parseInt(codeData[2].get(startIndex),16));

                allTRecords+=prepareCurrTRecord(hteFormatter.formatOpcode(codeData[2].get(startIndex)),hteFormatter.formatOpcode2(length),codeData,startIndex,endIndex);
                calculateNow = false;
                startIndex = endIndex;
            }
        }
        return allTRecords;
    }
    private static String prepareCurrTRecord(String tStartAddr, String tLength ,ArrayList<String>[] codeData, int startIndex, int endIndex){
        String initTRec = "T . " + tStartAddr + " . " + tLength;
        while (startIndex<=endIndex){
            initTRec += " . " + getFinalOpcode(codeData[0].get(startIndex), codeData[1].get(startIndex), codeData[2].get(startIndex));
            startIndex++;
        }

        return initTRec + "\n";
    }
    public static String getFinalOpcode(String instruction, String operand, String pc){
        String instructionOpcode;
        String BinaryInstructionOpcode;
        String BinaryOperand;
        String BinaryOpcode;
        String HexOperand;
        String HexInstructionOpcode;
        String finalOpcode;
        String n="1",i="1",x,b,p = "1",e;
        x=b=e="0";
        if(instruction.equals("BYTE") || instruction.equals("WORD"))
        {
            finalOpcode = SicXEMatcher.getMatchGroups(operand, SicXEMatcher.operandValue).get(2);
            return finalOpcode;
        }
        //get instruction, and determine if it's extended or not.
        if(SicXEMatcher.CheckMatch(instruction,SicXEMatcher.regularInstruction))
        {
            instructionOpcode = InstToOpCode.getOPCode(instruction);
        }
        else if(SicXEMatcher.CheckMatch(instruction,SicXEMatcher.extendedInstruction))
        {
            ArrayList<String> match = SicXEMatcher.getMatchGroups(instruction,SicXEMatcher.extendedInstruction);
            instructionOpcode = InstToOpCode.getOPCode(match.get(1));
            e = "1";
            mRecord.createModificationRecord(pc);
            pc = "0";
        }
        else
            return "Error. Invalid instruction";
        //get the value of n, i, and x.
        if(instruction.equals("RSUB")){
            x=b=p=e="0";
            operand = "000";
        }
        if(SicXEMatcher.CheckMatch(operand, SicXEMatcher.regularAddressing) && !instruction.equals("RSUB")){
            if(!hteRecordIO.matchFromSymbolTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SymbolTable.txt", operand).equals("Error")){
                operand = hteRecordIO.matchFromSymbolTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SymbolTable.txt", operand);
            }

            int opMinuspc  =Integer.parseInt(operand,16)-Integer.parseInt(pc,16);

            if(opMinuspc >= -2047 && opMinuspc <= 2047){
                b="0";
                p="1";
            }
            else if(opMinuspc > 2047 && opMinuspc < 4096){
                b="1";
                p="0";
            }
            else{
                b="1";
                p="1";
            }
            if(!(p.equals("0") && b.equals("0"))) {
                operand = Integer.toHexString(opMinuspc);
                if(operand.length()>3)
                    operand = operand.substring(operand.length()-3,operand.length());
                else
                    operand = hteFormatter.formatOpcode3(operand);
            }


        }
        if(SicXEMatcher.CheckMatch(operand, SicXEMatcher.operandEqualValue) && !instruction.equals("RSUB")) {
            n="1";
            i = "1";
            p = "1";
            b= "0";
            if(!hteRecordIO.matchFromLiteralTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\LiteralTable.txt", operand).equals("Error")){
                operand = hteRecordIO.matchFromLiteralTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\LiteralTable.txt", operand);
            }
            int opMinuspc  =Integer.parseInt(operand,16)-Integer.parseInt(pc,16);

            if(opMinuspc >= -2047 && opMinuspc <= 2047){
                b="0";
                p="1";
            }
            else if(opMinuspc > 2047 && opMinuspc < 4096){
                b="1";
                p="0";
            }
            else{
                b="1";
                p="1";
            }
            if(!(p.equals("0") && b.equals("0"))) {
                operand = Integer.toHexString(opMinuspc);
                if(operand.length()>3)
                    operand = operand.substring(operand.length()-3,operand.length());
                else
                    operand = hteFormatter.formatOpcode3(operand);
            }
        }
        if(SicXEMatcher.CheckMatch(operand, SicXEMatcher.immediateAddressing) && !instruction.equals("RSUB")) {
            n="0";
            p = "0";
            b= "0";
            ArrayList<String> match = SicXEMatcher.getMatchGroups(operand,SicXEMatcher.immediateAddressing);
            operand = match.get(1);

            if(!hteRecordIO.matchFromSymbolTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SymbolTable.txt", operand).equals("Error")){
                operand = hteRecordIO.matchFromSymbolTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SymbolTable.txt", operand);
            }


        }
        if(SicXEMatcher.CheckMatch(operand, SicXEMatcher.indirectAddressing) && !instruction.equals("RSUB")) {
            i="0";
            ArrayList<String> match = SicXEMatcher.getMatchGroups(operand,SicXEMatcher.indirectAddressing);
            operand = match.get(1);
            operand = Integer.toHexString(Integer.parseInt(operand,16)-Integer.parseInt(pc,16));

            int opMinuspc  =Integer.parseInt(operand,16)-Integer.parseInt(pc,16);

            if(opMinuspc >= -2047 && opMinuspc <= 2047){
                b="0";
                p="1";
            }
            else if(opMinuspc > 2047 && opMinuspc < 4096){
                b="1";
                p="0";
            }
            else{
                b="1";
                p="1";
            }

            if(!(p.equals("0") && b.equals("0"))) {
                operand = Integer.toHexString(opMinuspc);
                if(operand.length()>3)
                    operand = operand.substring(operand.length()-3,operand.length());
                else
                    operand = hteFormatter.formatOpcode3(operand);
            }
        }
        if(SicXEMatcher.CheckMatch(operand, SicXEMatcher.indexAddressing) && !instruction.equals("RSUB")) {
            x="1";
            ArrayList<String> match = SicXEMatcher.getMatchGroups(operand,SicXEMatcher.indexAddressing);
//            System.out.println("HEREEEEEE: " + match.get(0));
            if(!hteRecordIO.matchFromSymbolTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SymbolTable.txt", match.get(0)).equals("Error"))
                operand = hteRecordIO.matchFromSymbolTable("C:\\Users\\Abdu\\Desktop\\SicAssembler-master\\src\\com\\aast\\systemprogramming\\SIC program\\SymbolTable.txt", match.get(0));
            else
            operand = match.get(0);

            operand = Integer.toHexString(Integer.parseInt(operand,16)-Integer.parseInt(pc,16));

//            int opMinuspc  =Integer.parseInt(operand,16)-Integer.parseInt(pc,16);
//
//            if(opMinuspc >= -2047 && opMinuspc <= 2047){
//                b="0";
//                p="1";
//            }
//            else if(opMinuspc > 2047 && opMinuspc < 4096){
//                b="1";
//                p="0";
//            }
//            else{
//                b="1";
//                p="1";
//            }
//            if(!(p.equals("0") && b.equals("0"))) {
////                operand = Integer.toHexString(opMinuspc);
////                if(operand.length()>3)
////                    operand = operand.substring(operand.length()-3,operand.length());
////                else
////                    operand = hteFormatter.formatOpcode3(operand);
////            }

        }





        try {
            BinaryInstructionOpcode = hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(instructionOpcode.substring(0,1),16)))
                    +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(instructionOpcode.substring(1,2),16)));
            BinaryInstructionOpcode = hteFormatter.formatOpcode(BinaryInstructionOpcode);

            String xbpe = x + b + p + e;
            if(e.equals("0")){
                operand = hteFormatter.formatOpcode4(operand);
                BinaryOperand = hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(0,1),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(1,2),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(2,3),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(3,4),16)));
                HexOperand = DoWhatTheLanguageIsSupposedToFuckingDo(BinaryOperand.substring(0,4),xbpe) +
                        hteFormatter.formatOpcode3(Integer.toHexString(Integer.parseInt(BinaryOperand.substring(4,16),2)));
            }
            else
            {
                xbpe = x + b + "0" + e;
                operand = hteFormatter.formatOpcode5(operand);
                BinaryOperand = hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(0,1),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(1,2),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(2,3),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(3,4),16)))
                        +hteFormatter.formatOpcode4(Integer.toBinaryString(Integer.parseInt(operand.substring(4,5),16)));

                HexOperand = DoWhatTheLanguageIsSupposedToFuckingDo(BinaryOperand.substring(0,4),xbpe) +
                        hteFormatter.formatOpcode4(Integer.toHexString(Integer.parseInt(BinaryOperand.substring(4,20),2)));
            }


            HexInstructionOpcode = BinaryInstructionOpcode  + n + i;
            HexInstructionOpcode = hteFormatter.formatOpcode2(Integer.toHexString(Integer.parseInt(HexInstructionOpcode,2)));
            finalOpcode = HexInstructionOpcode + HexOperand;
//            finalOpcode = hteFormatter.formatOpcode(finalOpcode);
            return finalOpcode;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return "Error";
    }

    private static String DoWhatTheLanguageIsSupposedToFuckingDo(String bin1, String bin2){
        String theFuckingString = "";
        for(int i = 0; i< (bin1.length());i++){
            theFuckingString += Integer.parseInt(bin1.substring(i,i+1),2) + Integer.parseInt(bin2.substring(i,i+1),2);
        }
        return Integer.toHexString(Integer.parseInt(theFuckingString,2));
    }
}
