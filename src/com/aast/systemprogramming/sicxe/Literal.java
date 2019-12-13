package com.aast.systemprogramming.sicxe;

public class Literal {

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
