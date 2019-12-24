package com.aast.systemprogramming.sicxe;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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

    void addSymbol(int address, String label) {
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

    void addSymbol(int address, String label, String value) {
        if (label == null) return;
        try {
            if (searchSymbol(label))
                throw new IOException(label + " already exist");

            int intValue;
            Matcher match = Pattern.compile("^X'(\\w+)'").matcher(value);
            if (match.find())
                intValue = Integer.parseInt(match.group(1), 16);
            else if (value.equals("*"))
                intValue = address;
            else if (value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")) {
                for (String oldLabel : value.split("\\s*[^a-zA-Z]+\\s*"))
                    value = value.replaceAll(oldLabel, Integer.toString(getValue(oldLabel)));

                ScriptEngine javaScript = (new ScriptEngineManager()).getEngineByName("JavaScript");
                intValue = (int) javaScript.eval(value);

            } else
                intValue = Integer.parseInt(value);

            symbolOutput.write(String.format("%04X", address) + "\t" + label + "\t" + intValue);
            symbolOutput.newLine();
            symbolOutput.flush();

        } catch (ScriptException | IOException se) {
            se.printStackTrace();
        }
    }

    int getAddress(String label) {
        try (BufferedReader symbolInput = new BufferedReader(new FileReader(symbolTableFile))) {
            String symbolLine = symbolInput.readLine();
            if (symbolLine == null) throw new IllegalStateException();
            Matcher match = Pattern.compile("^(\\d+)\\s+" + label + "\\s*$").matcher(symbolLine);
            while (symbolLine != null) {
                if (match.find()) {
                    return Integer.parseInt(match.group(1), 16);
                } else {
                    symbolLine = symbolInput.readLine();
                    if (symbolLine != null) match.reset(symbolLine);
                }
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    int getValue(String label) {
        try (BufferedReader symbolInput = new BufferedReader(new FileReader(symbolTableFile))) {
            String symbolLine = symbolInput.readLine();
            if (symbolLine == null) throw new IllegalStateException();
            Matcher match = Pattern.compile("^\\d+\\s+" + label + "\\s+(\\d+)\\s*$").matcher(symbolLine);
            while (symbolLine != null) {
                if (match.find())
                    return Integer.parseInt(match.group(1));
                else if (match.usePattern(Pattern.compile("^\\d+\\s+" + label + "\\s+X'(\\w+)'\\s*$")).find())
                    return Integer.parseInt(match.group(1), 16);
                else {
                    symbolLine = symbolInput.readLine();
                    match.usePattern(Pattern.compile("^\\d+\\s+" + label + "\\s+(\\d+)\\s*$"));
                    if (symbolLine != null) match.reset(symbolLine);
                }
            }
            throw new IllegalStateException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
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
