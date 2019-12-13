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

    public static class Literal {

        public String label;
        public int size;

        Literal(String label, int size) {
            this.label = label;
            this.size = size;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Literal literal = (Literal) obj;
            return label.equals(literal.label) &&
                    size == literal.size;
        }

        @Override
        public int hashCode() {
            return size;
        }

    }

}
