package com.aast.systemprogramming.sicxe;

public enum Register {

    A,X,L,B,S,T,F,PC,SW;

    public int getValue() {
        switch (this){
            case PC:
            case SW: return ordinal()+1;
            default: return ordinal();
        }
    }

    public static String name(int value){
        switch (value){
            case 0: return A.name();
            case 1: return X.name();
            case 2: return L.name();
            case 3: return B.name();
            case 4: return S.name();
            case 5: return T.name();
            case 6: return F.name();
            case 8: return PC.name();
            case 9: return SW.name();
            default: return null;
        }
    }

    public static boolean isIt(String register){
        return register.equals(A.name()) ||
                register.equals(X.name()) ||
                register.equals(L.name()) ||
                register.equals(B.name()) ||
                register.equals(S.name()) ||
                register.equals(T.name()) ||
                register.equals(F.name()) ||
                register.equals(PC.name()) ||
                register.equals(SW.name());
    }

    @Override
    public String toString() {
        return this.name();
    }

}
