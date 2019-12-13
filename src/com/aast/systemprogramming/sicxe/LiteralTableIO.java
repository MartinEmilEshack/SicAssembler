package com.aast.systemprogramming.sicxe;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LiteralTableIO {

    private BufferedWriter literalOutput;
    private File literalTableFile;

    LiteralTableIO(String path) {
        try {
            literalTableFile = new File(path);
            if (!literalTableFile.exists()) literalTableFile.createNewFile();
            literalOutput = new BufferedWriter(new FileWriter(literalTableFile));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void addLiteral(String literal, int address) {
        if (literal == null) return;
        try {
            if (!searchSymbol(literal)) {
                literalOutput.write(String.format("%04X", address) + "\t" + literal);
                literalOutput.newLine();
                literalOutput.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int getAddress(String literal) {
        try (BufferedReader literalInput = new BufferedReader(new FileReader(literalTableFile))) {
            String literalLine = literalInput.readLine();
            Matcher match = Pattern.compile("^(\\d+)\\s+(" + literal + ")\\s*$*").matcher(literalInput.readLine());
            while (literalLine != null) {
                if (match.find()) {
                    return Integer.parseInt(match.group(1), 16);
                } else {
                    literalLine = literalInput.readLine();
                    match.reset(literalLine);
                }
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private boolean searchSymbol(String literal) {
        try (BufferedReader literalInput = new BufferedReader(new FileReader(literalTableFile))) {
            String literalLine = literalInput.readLine();
            if (literalLine == null)
                return false;
            Matcher match = Pattern.compile("^.*(" + literal + ")").matcher(literalLine);
            while (literalLine != null) {
                match.reset(literalLine);
                if (match.find()) {
                    return true;
                } else
                    literalLine = literalInput.readLine();
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    void close() {
        try {
            if (literalOutput != null)
                literalOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
