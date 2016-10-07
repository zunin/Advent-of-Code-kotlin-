package day_7;


public class BitWiseOperation {
    static int AND (int left, int right) {
        return left & right;
    }

    static int OR (int left, int right) {
        return left | right;
    }

    static int NOT (int number) {
        return ~ number;
    }

    static int RIGHT_SHIFT (int number, int shift) {
        return number >> shift;
    }

    static int LEFT_SHIFT (int number, int shift) {
        return number << shift;
    }
}
