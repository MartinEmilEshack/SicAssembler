package com.aast.systemprogramming.sicxe;

public enum Directive {

    START, BYTE, RESB, BASE, NOBASE, ORG, END, WORD, RESW, LTORG, EQU, USE;

    public static boolean isIt(String value) {
//        System.out.println(value);
        return value.equals(START.name()) ||
                value.equals(BYTE.name()) ||
                value.equals(RESB.name()) ||
                value.equals(BASE.name()) ||
                value.equals(NOBASE.name()) ||
                value.equals(ORG.name()) ||
                value.equals(END.name()) ||
                value.equals(WORD.name()) ||
                value.equals(RESW.name()) ||
                value.equals(LTORG.name()) ||
                value.equals(EQU.name()) ||
                value.equals(USE.name());
    }

    @Override
    public String toString() {
        return this.name();
    }

}
