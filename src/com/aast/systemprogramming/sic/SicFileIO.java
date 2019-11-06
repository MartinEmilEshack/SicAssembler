package com.aast.systemprogramming.sic;

import java.io.*;

public class SicFileIO {

    private BufferedReader inputFile;
    private BufferedWriter intermediateFile;

    SicFileIO(String path){
        try {
            File file = new File(path);
            inputFile = new BufferedReader(new FileReader(path));
            intermediateFile = new BufferedWriter(new FileWriter(file.getParentFile().getPath()+"\\intermediate.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    SicInstruction getSicInstruction() {
        try {
            String instruction = inputFile.readLine();
            if (instruction != null)
                return SicInstruction.parseInstruction(instruction);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    String getString(){
        try {
            return inputFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void writeSicIntermediate(int address,SicInstruction instruction){
        try {
            intermediateFile.write(Integer.toHexString(address)+"\t"+instruction.toIntermediateFormat());
            intermediateFile.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeReader(){
        try {
            if (inputFile != null)
                inputFile.close();
            if (intermediateFile != null)
                intermediateFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
