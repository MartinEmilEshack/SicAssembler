package com.aast.systemprogramming.sicxe;

import java.io.*;

public class SicXeFileIO {

    private BufferedReader instructionFile;
    private BufferedWriter intermediateFile;
    private ModifiedSymbolTableIO symbolTableIO;
    private LiteralTableIO literalTableIO;

    public SicXeFileIO(String path) {
        try {
            File instructionFile = new File(path);
            this.instructionFile = new BufferedReader(new FileReader(path));
            intermediateFile = new BufferedWriter(new FileWriter(instructionFile.getParentFile().getPath() + "\\Intermediate.txt"));
            symbolTableIO = new ModifiedSymbolTableIO(instructionFile.getParentFile().getPath() + "\\SymbolTable.txt");
            literalTableIO = new LiteralTableIO(instructionFile.getParentFile().getPath() + "\\LiteralTable.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString() {
        try {
            return instructionFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeIntermediate(int address, String instruction) {
        try {
            intermediateFile.write(String.format("%04X", address) + instruction);
            intermediateFile.newLine();
            intermediateFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSymbol(String label, int address) {
        symbolTableIO.addSymbol(label, address);
    }

    public void writeLiteral(String literal, int address) {
        literalTableIO.addLiteral(literal, address);
    }

    public String getSicXeInstruction() {
        try {
            return instructionFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getSymbolAddress(String label) {
        return symbolTableIO.getAddress(label);
    }

    public int getLiteralAddress(String literal) {
        return literalTableIO.getAddress(literal);
    }

    public void closeReader() {
        try {
            if (instructionFile != null)
                instructionFile.close();
            if (intermediateFile != null)
                intermediateFile.close();
            if (symbolTableIO != null)
                symbolTableIO.close();
            if (literalTableIO != null)
                literalTableIO.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
