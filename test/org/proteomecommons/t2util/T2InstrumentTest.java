/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.proteomecommons.t2util.utils.MALDIUtil;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class T2InstrumentTest extends TestCase {

    public T2InstrumentTest(String testName) {
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

    /**
     * <p>Should run quickly since not accessing spot information.</p>
     * @throws Exception
     */
    public void testGetSpotSetDescriptionsSimple() throws Exception {
        printTestName("testGetSpotSetDescriptionsSimple");
        T2Instrument instrument = null;
        try {
            instrument = getTestInstrument();

            for (SpotSetDescription ssd : instrument.getSpotDescriptions()) {
                System.out.println(ssd.getId() + ": " + ssd.getName());
            }

        } finally {
            safeClose(instrument);
        }
    }

    /**
     * <p>Should take much longer than testGetSpotSetDescriptionsSimple().</p>
     * @throws Exception
     */
    public void testGetSpotSetDescriptionsWithSpotCounts() throws Exception {
        printTestName("testGetSpotSetDescriptionsWithSpotCounts");
        final int limit = 2;
        T2Instrument instrument = null;
        try {
            instrument = getTestInstrument();

            List<SpotSetDescription> shuffledSpotSetDescriptions = new ArrayList(instrument.getSpotDescriptions());
            Collections.shuffle(shuffledSpotSetDescriptions);

            int spotSetCount = 0;
            final int totalSpotSetCount = instrument.getSpotDescriptions().size();

            SPOT_SETS:
            for (SpotSetDescription ssd : shuffledSpotSetDescriptions) {
                spotSetCount++;
                System.out.println("[" + spotSetCount + "/" + totalSpotSetCount + "] " + ssd.getId() + ": " + ssd.getName() + " [spots: " + ssd.size() + "]");
                ssd.close();

                if (spotSetCount >= limit) {
                    System.out.println("... reached limit of " + limit + ", stopping.");
                    break SPOT_SETS;
                }
            }

        } finally {
            safeClose(instrument);
        }
    }

    /**
     * <p>Print up to ___ peak lists from ___ spot sets and bail.</p>
     * @throws Exception
     */
    public void testPrintSomePeakListsFromSomeSpotSets() throws Exception {
        printTestName("testPrintSomePeakListsFromSomeSpotSets");
        T2Instrument instrument = null;
        try {
            instrument = getTestInstrument();

            List<SpotSetDescription> shuffledSpotSetDescriptions = new ArrayList(instrument.getSpotDescriptions());
            Collections.shuffle(shuffledSpotSetDescriptions);

            int spotSets = 0;
            final int spotSetsLimit = 2;

            SPOT_SETS:
            for (SpotSetDescription ssd : shuffledSpotSetDescriptions) {
                System.out.println(ssd.getId() + ": " + ssd.getName() + " [spots: " + ssd.size() + "]");

                int peakLists = 0;
                final int peakListsLimit = 2;

                SPOTS:
                for (SpotDescription sd : ssd.getSpotDescriptions()) {

                    PEAK_LISTS:
                    for (PeakListDescription pkd : sd.getPeakListDescriptions()) {

                        System.out.println("* Peak list [job id: " + pkd.getJobId() + ", job run id: " + pkd.getJobRunId() + ", peaks: " + pkd.getPeakDescriptions().size() + "]");

                        for (PeakDescription pd : pkd.getPeakDescriptions()) {
                            System.out.println("    - " + pd.getCentroid() + " m/z, " + pd.getIntensity() + " intensity");
                        }

                        peakLists++;

                        if (peakLists >= peakListsLimit) {
                            break SPOTS;
                        }
                    }
                }

                ssd.close();

                spotSets++;
                if (spotSets >= spotSetsLimit) {
                    break SPOT_SETS;
                }
            }

        } finally {
            safeClose(instrument);
        }
    }

    public void testGetJobIds() throws Exception {
        printTestName("testGetJobIds");
        T2Instrument instrument = null;
        try {
            instrument = getTestInstrument();

            int limit = 2;
            int count = 0;

            List<SpotSetDescription> shuffedSpotSets = new ArrayList(instrument.getSpotDescriptions());
            Collections.shuffle(shuffedSpotSets);

            for (SpotSetDescription ssd : shuffedSpotSets) {
                System.out.println(ssd.getId() + ": " + ssd.getName()+" [job ids: "+ssd.getJobIds().size()+"]");
                
                for (String jobId : ssd.getJobIds()) {
                    System.out.println("    - "+jobId);
                }

                count++;
                if (count >= limit) {
                    System.out.println("... reached limit of "+limit+", stopping.");
                    break;
                }
            }

        } finally {
            safeClose(instrument);
        }
    }

    /**
     *
     * @param name
     */
    private static void printTestName(String name) {
        System.out.println();
        System.out.println("--- " + name + " ---");
        System.out.println();
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    private T2Instrument getTestInstrument() throws Exception {
        return T2Instrument.connect(T2Instrument.DEFAULT_IP, T2Instrument.DEFAULT_PORT);
    }

    /**
     *
     * @param instrument
     */
    private static void safeClose(T2Instrument instrument) {
        try {
            instrument.close();
        } catch (Exception e) {/* */

        }
    }
}
