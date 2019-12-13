package com.aast.systemprogramming.sicxe;

import java.util.ArrayList;

public class LiteralMemory {

    private static ArrayList<Literal> literalBuffer = new ArrayList<>();

    static void add(Literal literal) {
        if(!literalBuffer.contains(literal))
            literalBuffer.add(literal);
    }

    public static ArrayList<Literal> getLiterals() {
        return literalBuffer;
    }

    public static void clear() {
        literalBuffer.clear();
    }

}
