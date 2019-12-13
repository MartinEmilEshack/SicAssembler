package com.aast.systemprogramming.sicxe;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ModifiedSymbolTableIO {

    private BufferedWriter symbolOutput;
    private File symbolTableFile;

    ModifiedSymbolTableIO(String path) {
        try {
            symbolTableFile = new File(path);
            if (!symbolTableFile.exists()) symbolTableFile.createNewFile();
            symbolOutput = new BufferedWriter(new FileWriter(symbolTableFile));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void addSymbol(String label, int address) {
        if (label == null) return;
        try {
            if (searchSymbol(label))
                throw new IOException(label + " already exist");
            symbolOutput.write(String.format("%04X", address) + "\t" + label);
            symbolOutput.newLine();
            symbolOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int getAddress(String label) {
        try (BufferedReader symbolInput = new BufferedReader(new FileReader(symbolTableFile))) {
            String symbolLine = symbolInput.readLine();
            Matcher match = Pattern.compile("^(\\d+)\\s+(" + label + ")\\s*$*").matcher(symbolInput.readLine());
            while (symbolLine != null) {
                if (match.find()) {
                    return Integer.parseInt(match.group(1), 16);
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

    private boolean searchSymbol(String label) {
        try (BufferedReader symbolInput = new BufferedReader(new FileReader(symbolTableFile))) {
            String symbolLine = symbolInput.readLine();
            if (symbolLine == null)
                return false;
            Matcher match = Pattern.compile("^.*(" + label + ")").matcher(symbolLine);
            while (symbolLine != null) {
                match.reset(symbolLine);
                if (match.find()) {
                    return true;
                } else {
                    symbolLine = symbolInput.readLine();
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    void close() {
        try {
            if (symbolOutput != null)
                symbolOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
