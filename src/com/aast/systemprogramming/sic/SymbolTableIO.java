package com.aast.systemprogramming.sic;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolTableIO {

    private BufferedWriter symbolOutput;
    private BufferedReader symbolInput;

    public SymbolTableIO(String path) {
        try {
            symbolOutput = new BufferedWriter(new FileWriter(path));
            symbolInput = new BufferedReader(new FileReader(path));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void addSymbol(String label, int address) {
        try {
            if (searchSymbol(label))
                throw new IOException(label + " already exist");
            symbolOutput.write(label + "   \t" + Integer.toHexString(address));
            symbolOutput.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAddress(String label) {
        try {
            String symbolLine = symbolInput.readLine();
            Matcher match = Pattern.compile("^(" + label + ")\\s+(\\d+)\\s*$*").matcher(symbolInput.readLine());
            while (symbolLine != null){
                if (match.find()) {
                    return Integer.parseInt(match.group(2),16);
                } else {
                    symbolLine = symbolInput.readLine();
                    match.reset(symbolLine);
                }
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean searchSymbol(String label) {
        try {
            String symbolLine = symbolInput.readLine();
            if (symbolLine == null)
                return false;
            Matcher match = Pattern.compile("^(" + label + ").*").matcher(symbolLine);
            while (symbolLine != null){
                if (match.find()) {
                    return true;
                } else {
                    symbolLine = symbolInput.readLine();
                    match.reset(symbolLine);
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close(){
        try {
            if(symbolInput != null)
                symbolInput.close();
            if(symbolOutput != null)
                symbolOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
