/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class MALDIUtil {



    /**
     *
     */
    public static final String SEQUENTIAL_ORDER = "Sequential (192 spots, row length 48)",
            /**
             *
             */
            SNAKE_192_ORDER = "Snake (192 spots, row length 24)";

    /**
     * All possible orderings. If add new orderings above, add to this array
     */
    public static final String[] ALL_ORDERINGS = {
            SEQUENTIAL_ORDER,SNAKE_192_ORDER
    };

    private static final String[] calibrationSpots = {
        "CAL1", "CAL2", "CAL3", "CAL4",
        "CAL5", "CAL6", "CAL7", "CAL8",
        "CAL9"
    };

    public static boolean isCalibrationSpot(String wellName) {

        wellName = removeSpaces(wellName);

        for (String calSpot : calibrationSpots) {
            if (calSpot.equals(wellName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>This assumes 192 fractions per run.</p>
     * @param wellName
     * @return
     */
    public static int convertWellLabelToFractionNumber(String wellName, String ordering) {

        wellName = removeSpaces(wellName);

        if (ordering.equals(SEQUENTIAL_ORDER)) {
            return convertWellLabelToFractionNumberSequential192(wellName);
        } else if (ordering.equals(SNAKE_192_ORDER)) {
            return convertWellLabelToFractionNumberSnake192(wellName);
        } else {
            throw new RuntimeException("Unrecognized ordering: " + ordering);
        }
    }

    /**
     *
     * @param wellName
     * @return
     */
    private static int convertWellLabelToFractionNumberSnake192(String wellName) {

        if (isCalibrationSpot(wellName)) {
            throw new RuntimeException(wellName + " is a calibration spot and cannot be mapped to a fraction number.");
        }

        return convertLetterToRowValueSnake192(wellName) + convertNumberToColumnValueSnake192(wellName);
    }

    /**
     *
     * @param wellName
     * @return
     */
    private static int convertLetterToRowValueSnake192(String wellName) {
        int rowLen = 24;
        final String prefix = wellName.substring(0, 1);

        if (prefix.equals("A")) {
            return rowLen * 0;
        } else if (prefix.equals("B")) {
            return rowLen * 1;
        } else if (prefix.equals("C")) {
            return rowLen * 2;
        } else if (prefix.equals("D")) {
            return rowLen * 3;
        } else if (prefix.equals("E")) {
            return rowLen * 4;
        } else if (prefix.equals("F")) {
            return rowLen * 5;
        } else if (prefix.equals("G")) {
            return rowLen * 6;
        } else if (prefix.equals("H")) {
            return rowLen * 7;
        } else {
            throw new RuntimeException("Expecting well name (" + wellName + ") to start with A-H, instead: " + prefix);
        }
    }

    /**
     *
     * @param wellName
     * @return
     */
    private static int convertNumberToColumnValueSnake192(String wellName) {

        try {
            int rawNum = Integer.parseInt(wellName.substring(1));

            if (rawNum < 1 || rawNum > 24) {
                throw new RuntimeException("Expecting number in well name (" + wellName + ") to be value between 1-24 (inclusive), but instead found: " + rawNum);
            }

            switch (rawNum) {
                case 1:
                    return 1;
                case 3:
                    return 2;
                case 5:
                    return 3;
                case 7:
                    return 4;
                case 9:
                    return 5;
                case 11:
                    return 6;
                case 13:
                    return 7;
                case 15:
                    return 8;
                case 17:
                    return 9;
                case 19:
                    return 10;
                case 21:
                    return 11;
                case 23:
                    return 12;
                case 24:
                    return 13;
                case 22:
                    return 14;
                case 20:
                    return 15;
                case 18:
                    return 16;
                case 16:
                    return 17;
                case 14:
                    return 18;
                case 12:
                    return 19;
                case 10:
                    return 20;
                case 8:
                    return 21;
                case 6:
                    return 22;
                case 4:
                    return 23;
                case 2:
                    return 24;
                default:
                    throw new RuntimeException("Unexpected value: " + rawNum);
            }

        } catch (NumberFormatException nfe) {
            throw new RuntimeException("Expecting number in well name (" + wellName + ") to be value between 1-24 (inclusive). The following error message was received: " + nfe.getMessage());
        }
    }

    /**
     *
     * @param wellName
     * @return
     */
    private static int convertWellLabelToFractionNumberSequential192(String wellName) {

        int rowLen = 48;

        if (isCalibrationSpot(wellName)) {
            throw new RuntimeException(wellName + " is a calibration spot and cannot be mapped to a fraction number.");
        }

        for (Row row : getRowsSequential192()) {
            if (wellName.startsWith(row.prefix)) {
                String wellNumberStr = wellName.substring(row.prefix.length());
                int wellNumber = -1;

                try {
                    wellNumber = Integer.parseInt(wellNumberStr);
                } catch (NumberFormatException nfe) {
                    System.err.println("NumberFormatException for well name<" + wellName + ">: " + nfe.getMessage());
                    throw nfe;
                }

                // Modulus because the fraction number is for a run, limited to 192 wells
                int value = ((rowLen * row.rowNumber) + wellNumber) % 192;

                // But 0 actually represents 192
                if (value == 0) {
                    value = 192;
                }

                if (value < 1 || value > 192) {
                    throw new RuntimeException("Expected 1 <= fraction number <= 192, instead: " + value);
                }

                return value;
            }
        }

        throw new RuntimeException("Cannot convert special name location to fraction: " + wellName);
    }

    private static String removeSpaces(String str) {
        return str.replaceAll("\\s+", "");
    }
    private static Row[] rows = null;

    /**
     *
     */
    private static Row[] getRowsSequential192() {

        // Lazily create rows
        if (rows == null) {

            int totalRowCount = 32;

            List<String> prefixes = new ArrayList(totalRowCount);

            prefixes.add("AF");
            prefixes.add("AE");
            prefixes.add("AD");
            prefixes.add("AC");
            prefixes.add("AB");
            prefixes.add("AA");

            for (char c = 'Z'; c >= 'A'; c--) {
                String nextChar = String.valueOf(c);
                prefixes.add(nextChar);
            }

            rows = new Row[32];
            for (int listIndex = 0; listIndex < totalRowCount; listIndex++) {
                int rowNumber = totalRowCount - (listIndex + 1);
                rows[listIndex] = new Row(prefixes.get(listIndex), rowNumber);
            }
        }
        return rows;
    }

    /**
     *
     */
    static class Row {

        final String prefix;
        final int rowNumber;

        Row(String prefix, int value) {
            this.prefix = prefix;
            this.rowNumber = value;
        }

        @Override()
        public String toString() {
            return "prefix: " + this.prefix + ", row number: " + this.rowNumber;
        }
    }
}
