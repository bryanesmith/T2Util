/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util.utils;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class AssertionUtil {

    private static final String ASSERTION_PREFIX = "ASSERTION FAILED. ";

    /**
     * <p>Assert that an object is not null. If it is, fail with a standard message
     * @param failureMessage An optional message about why the variable is needed or useful debugging information
     * @param o The object to assert is not null, or throw an excepton otherwise.
     */
    public static void assertNotNull(String failureMessage, Object o) {
        final String msg = ASSERTION_PREFIX +"Object was null, but shouldn't be";
        if (o == null) {
            if (failureMessage != null) {
                fail(msg + ": " + failureMessage);
            } else {
                fail(msg);
            }
        }
    }

    /**
     * <p>An assertion has failed, so throw an exception.</p>
     * @param failureMessage Required message for failed assertion.
     */
    public static void fail(String failureMessage) {
        throw new RuntimeException(failureMessage);
    }
}
