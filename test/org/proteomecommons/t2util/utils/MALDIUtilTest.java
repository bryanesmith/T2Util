/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util.utils;

import junit.framework.TestCase;

/**
 *
 * @author besmit
 */
/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class MALDIUtilTest extends TestCase {

    public MALDIUtilTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testIsCalibrationSpot() {
        assertTrue(MALDIUtil.isCalibrationSpot("CAL1"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL2"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL3"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL4"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL5"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL6"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL7"));
        assertTrue(MALDIUtil.isCalibrationSpot("CAL8"));
        assertFalse(MALDIUtil.isCalibrationSpot("A01"));
        assertFalse(MALDIUtil.isCalibrationSpot("A02"));
        assertFalse(MALDIUtil.isCalibrationSpot("A04"));
        assertFalse(MALDIUtil.isCalibrationSpot("B04"));
        assertFalse(MALDIUtil.isCalibrationSpot("B05"));
        assertFalse(MALDIUtil.isCalibrationSpot("C04"));
        assertFalse(MALDIUtil.isCalibrationSpot("AF10"));
    }

    /**
     * Test of convertWellLabelToFractionNumber method, of class MALDIUtil.
     */
    public void testConvertWellLabelToFractionNumberSequential48() {
        int fraction = MALDIUtil.convertWellLabelToFractionNumber("A01", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 1, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A02", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 2, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A04", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 4, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B04", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 52, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B05", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 53, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("C04", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 100, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("D48", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 192, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("E01", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 1, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("E02", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 2, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("E04", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 4, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("X48", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 192, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("AC01", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 1, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("AF48", MALDIUtil.SEQUENTIAL_ORDER);
        assertEquals("Expecting certain fraction id for well name", 192, fraction);

    }

    public void testConvertWellLabelToFractionNumberSnake192() {
        int fraction = MALDIUtil.convertWellLabelToFractionNumber("A01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 1, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A03", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 2, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A05", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 3, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A07", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 4, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A09", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 5, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A11", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 6, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A13", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 7, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A15", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 8, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A17", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 9, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A19", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 10, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A21", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 11, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A23", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 12, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A24", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 13, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A22", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 14, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A20", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 15, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A18", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 16, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A16", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 17, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A14", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 18, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A12", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 19, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A10", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 20, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A08", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 21, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A06", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 22, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A04", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 23, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("A02", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 24, fraction);

        int offset = 24;

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 1 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B03", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 2 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B05", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 3 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B07", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 4 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B09", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 5 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B11", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 6 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B13", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 7 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B15", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 8 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B17", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 9 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B19", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 10 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B21", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 11 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B23", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 12 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B24", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 13 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B22", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 14 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B20", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 15 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B18", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 16 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B16", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 17 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B14", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 18 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B12", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 19 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B10", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 20 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B08", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 21 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B06", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 22 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B04", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 23 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("B02", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 24 + offset, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("C01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 49, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("D01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 73, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("E01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 97, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("F01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 121, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("G01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 145, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("H01", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 169, fraction);

        fraction = MALDIUtil.convertWellLabelToFractionNumber("H02", MALDIUtil.SNAKE_192_ORDER);
        assertEquals("Expecting certain fraction id for well name", 192, fraction);
    }
}
