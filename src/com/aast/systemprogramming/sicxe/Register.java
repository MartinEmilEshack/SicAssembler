package com.aast.systemprogramming.sicxe;

public enum Register {

    A, X, L, B, S, T, F, PC, SW;

    public int getValue() {
        switch (this) {
            case PC:
            case SW:
                return ordinal() + 1;
            default:
                return ordinal();
        }
    }

    public static Register getReg(int value) {
        switch (value) {
            case 0:
                return A;
            case 1:
                return X;
            case 2:
                return L;
            case 3:
                return B;
            case 4:
                return S;
            case 5:
                return T;
            case 6:
                return F;
            case 8:
                return PC;
            case 9:
                return SW;
            default:
                return null;
        }
    }

    public static Register getReg(String value) {
        switch (value) {
            case "A":
                return A;
            case "X":
                return X;
            case "L":
                return L;
            case "B":
                return B;
            case "S":
                return S;
            case "T":
                return T;
            case "F":
                return F;
            case "PC":
                return PC;
            case "SW":
                return SW;
            default:
                return null;
        }
    }

    public static boolean isIt(String register) {
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
