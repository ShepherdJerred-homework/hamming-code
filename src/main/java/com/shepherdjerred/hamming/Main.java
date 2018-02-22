package com.shepherdjerred.hamming;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    private static String[] input = new String[]{
            "100100010010", "000110000110", "011111001001", "100111111000", "010111001000",
            "010011011110", "010111010111", "000101000000", "100111000101", "110111110011",
            "110111110010", "110111001111", "110111100010", "110011100011", "010101000000",
            "010111001001", "110011100111", "010101000000", "010011000110", "010011100101",
            "110011011110", "010001010001", "100101010010", "100101101100", "010101000000",
            "110110010100", "110101110010", "010101011110", "010101000100", "010110010010",
            "110111010001", "000011010010", "100111000101", "110111110011", "010101011110"
    };

    private static String[] smallInput = new String[]{
            "100010010000"
    };

    public static void main(String[] args) {
        for (String s : input) {
//            System.out.println("Working on string: " + s);
            decoode(s);
        }
    }

    private static void decoode(String s) {
        int sLength = s.length();
        Collection<Integer> parityBits = new ArrayList<>();

        for (int i = 1; i < sLength; i *= 2) {
            parityBits.add(i);
        }

//        System.out.println("Parity bits: " + parityBits.toString());

        char[] sChars = s.toCharArray();
        Collection<Integer> invalidParityBitIndexesPlusOne = new ArrayList<>();

        parityBits.forEach(parityBitIndexPlusOne -> {
//            System.out.println("\n\n");
//            System.out.println("Working on parity bit " + parityBitIndexPlusOne);

            char charParityBit = sChars[parityBitIndexPlusOne - 1];

            // 0 = even = false
            // 1 = odd = true

//            System.out.println("Char parity bit: " + charParityBit);

            boolean parityBit = charParityBit == '1';

//            System.out.println("Char parity bit bool: " + parityBit);

            int numberOfOnes = 0;
            int numberOfIndexesSkip = parityBitIndexPlusOne - 1;
            int numberOfIndexesToContinue = 1;

            for (int i = 0; i < sLength; i++) {
//                System.out.println("Working on index " + i);

                if (numberOfIndexesSkip > 0) {
                    numberOfIndexesSkip -= 1;
                    if (numberOfIndexesSkip == 0) {
                        numberOfIndexesToContinue = parityBitIndexPlusOne;
//                        System.out.println("Done skipping on index: " + (i + 1));
                    }
//                    System.out.println("Skipping index " + (i + 1));
                    continue;
                }

                if (numberOfIndexesToContinue > 0) {
                    if (sChars[i] == '1') {
                        if (!parityBits.contains(i + 1)) {
                            numberOfOnes += 1;
//                            System.out.println("Incrementing ones at index " + (i + 1) + " with bit " + parityBitIndexPlusOne);
                        } else {
//                            System.out.println("Not incrementing parity bit at index " + (i + 1));
                        }
                    }
                    numberOfIndexesToContinue -= 1;
                    if (numberOfIndexesToContinue == 0) {
                        numberOfIndexesSkip = parityBitIndexPlusOne;
//                        System.out.println("Done continuing on index: " + (i + 1));
                    }
//                    System.out.println("Checking index " + (i + 1));
                }

            }

            // 0 = even = false
            // 1 = odd = true
            boolean actualParity = numberOfOnes % 2 != 0;

//            System.out.println("Number of ones for bit " + parityBitIndexPlusOne + ": " + numberOfOnes);
//            System.out.println("Expected parity for bit " + parityBitIndexPlusOne + ": " + parityBit);
//            System.out.println("Actual parity: " + parityBitIndexPlusOne + ": " + actualParity);

            if (parityBit != actualParity) {
//                System.out.println("Invalid parity bit: " + parityBitIndexPlusOne);
                invalidParityBitIndexesPlusOne.add(parityBitIndexPlusOne);
            }

//            System.out.println("\n\n");
        });

//        System.out.println("Invalid parity bits: " + invalidParityBitIndexesPlusOne);

        int invalidBitIndexPlusOne = 0;
        for (Integer invalidCheckBitIndexPlusOne : invalidParityBitIndexesPlusOne) {
            invalidBitIndexPlusOne += invalidCheckBitIndexPlusOne;
        }

//        System.out.println("Invalid bit location: " + (invalidBitIndexPlusOne - 1));

        if (invalidBitIndexPlusOne != 0) {
            char c = sChars[invalidBitIndexPlusOne - 1];

            if (c == '0') {
                sChars[invalidBitIndexPlusOne - 1] = '1';
            } else {
                sChars[invalidBitIndexPlusOne - 1] = '0';
            }
        }

//        System.out.println("Corrected String: " + Arrays.toString(sChars));

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < sLength; i++) {
            if (!parityBits.contains(i + 1)) {
                sb.append(sChars[i]);
            }
        }
//        System.out.println("String without parity bits: " + sb.toString());

        int v = Integer.parseInt(sb.toString(), 2);
//        System.out.println("Integer value of string without parity bits: " + v);

//        System.out.println("Char value: "  + (char) v);
        System.out.print((char) v);

    }

}
