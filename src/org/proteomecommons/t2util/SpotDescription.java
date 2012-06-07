/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.proteomecommons.T2DE.T2Interface.DataBuilder;
import org.proteomecommons.T2DE.T2Interface.JobItemInterface;
import org.proteomecommons.T2DE.T2Interface.JobItemListInterface;
import org.proteomecommons.T2DE.T2Interface.JobRunInterface;
import org.proteomecommons.T2DE.T2Interface.JobRunItemInterface;
import org.proteomecommons.T2DE.T2Interface.PeakInterface;
import org.proteomecommons.T2DE.T2Interface.PeakListInterface;
import org.proteomecommons.T2DE.T2Interface.SpectrumInterface;
import org.proteomecommons.T2DE.T2Interface.SpotInfoInterface;
import org.proteomecommons.T2DE.T2Interface.SpotInterface;
import org.proteomecommons.t2util.utils.AssertionUtil;
import org.proteomecommons.t2util.utils.MALDIUtil;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class SpotDescription {

    private final SpotSetDescription parent;
    private final SpotInterface spotInterface;
    private final SpotInfoInterface spotInfoInterface;
    private final T2Instrument instrument;
    // Lazy-loaded variables
    private String wellName = null;
    private List<PeakListDescription> peakListDescriptions = null;
    private List<String> jobIds = null;

    /**
     * 
     * @param spotInterface
     * @param spotInfoInterface
     * @param parent
     * @param instrument
     */
    protected SpotDescription(final SpotInterface spotInterface, final SpotInfoInterface spotInfoInterface, final SpotSetDescription parent, final T2Instrument instrument) {
        AssertionUtil.assertNotNull("Cannot retrieve spot infromation without spotInterface, should have checked. Program error.", spotInterface);
        AssertionUtil.assertNotNull("Cannot retrieve spot infromation without spotInfoInterface, should have checked. Program error.", spotInfoInterface);
        AssertionUtil.assertNotNull("Parent node must always be non-null", parent);
        AssertionUtil.assertNotNull("Must be connected to an instrument.", instrument);

        this.instrument = instrument;

        this.spotInterface = spotInterface;
        this.spotInfoInterface = spotInfoInterface;
        this.parent = parent;

        // Assertion this is non-calibration data
        if (MALDIUtil.isCalibrationSpot(getWellName())) {
            AssertionUtil.fail("Attempting to make a spot for calibration data: " + wellName);
        }
    }

    /**
     * <p>A list of all job ids for this spot.</p>
     * @return
     */
    public List<String> getJobIds() {
        if (jobIds == null) {

            Set<String> jobIdsSet = new HashSet();

            for (PeakListDescription pld : getPeakListDescriptions()) {
                jobIdsSet.add(pld.getJobId());
            }

            jobIds = new ArrayList(jobIdsSet);
            Collections.sort(jobIds);
        }
        return jobIds;
    }

    /**
     *
     * @return
     */
    public List<PeakListDescription> getPeakListDescriptions() {

        if (peakListDescriptions == null) {

            peakListDescriptions = new LinkedList();

            JobItemListInterface jobItemListInterface = spotInterface.getJobItemList();

            // Tell the database to do something?
            this.instrument.dbInterface.getJobRunItem(jobItemListInterface);

            for (JobItemInterface jobItemInterface : jobItemListInterface.getJobItems()) {

                // Try to get peak list
                JobRunItemInterface jobRunItemInterface = jobItemInterface.getJobRunItem();

                // Why is this null sometimes?
                if (jobRunItemInterface == null) {
                    continue;
                }

                // Tell the database to do something?
                if (!DataBuilder.getInstance().getJobRunItemAcqTime(jobRunItemInterface)) {
                    if (!DataBuilder.getInstance().getJobRunItemProcTime(jobRunItemInterface)) {
                        DataBuilder.getInstance().getJobRunItemInterpTime(jobRunItemInterface);
                    }
                }

                // Tell the database to do something?
                JobRunInterface jobRunInterface = jobRunItemInterface.getJobRun();

                // Why is this null sometimes?
                if (jobRunInterface == null) {
                    continue;
                }

                String inputId = jobItemInterface.getInputId();

                // What does this mean?
                if (inputId == null || inputId.equals("")) {
                    this.instrument.dbInterface.getSpectrumFromJobRunItemId(jobRunItemInterface);
                } else {
                    this.instrument.dbInterface.getSpectrumFromJobRunItemInputId(inputId, jobRunItemInterface);
                }

                SpectrumInterface spectrumInterface = jobRunItemInterface.getSpectrum();

                // If not spectrum, skip
                if (spectrumInterface == null) {
                    continue;
                }

                this.instrument.dbInterface.getPeaks(jobRunItemInterface);

                PeakListInterface peakListInterface = spectrumInterface.getPeakList();

                // If no peak list, skip
                if (peakListInterface == null) {
                    continue;
                }

                // Create a description object for peak list and add it to the collection
                PeakListDescription peakListDescription = new PeakListDescription(jobItemInterface, jobRunInterface, this);
                peakListDescriptions.add(peakListDescription);
                
                // Add the peaks
//                for (PeakInterface peakInterface : peakListInterface.getPeaks()) {
                for (PeakInterface peakInterface : peakListInterface.getMonoisotopicPeaks()) {
                    float centroid = Float.parseFloat(peakInterface.getCentroid());
                    float intensity = Float.parseFloat(peakInterface.getPeakHeight());

                    PeakDescription peakDescription = new PeakDescription(centroid, intensity, peakListDescription);
                    peakListDescription.addPeakDescription(peakDescription);
                } // For all peaks
            } // For all jobs
        } // Lazy load
        
        return peakListDescriptions;
    }

    /**
     * 
     * @param jobId
     * @return
     */
    public List<PeakListDescription> getPeakListDescriptionsByJobId(String jobId) {
        List<PeakListDescription> plds = new LinkedList();

        for (PeakListDescription pld : this.getPeakListDescriptions()) {
            if (pld.getJobId().equals(jobId)) {
                plds.add(pld);
            }
        }

        return plds;
    }

    /**
     *
     * @return
     */
    public String getWellName() {
        if (this.wellName == null) {
            this.wellName = this.spotInfoInterface.getSpecialNameLocation();
        }
        return this.wellName;
    }

    /**
     *
     * @param spotOrder See MALDIUtil for static String variables with valid ordering. Will throw exception if invalid ordering offered.
     * @return
     */
    public int getFractionNumber(final String spotOrder) {
        return MALDIUtil.convertWellLabelToFractionNumber(getWellName(), spotOrder);
    }

    /**
     * @return the parent
     */
    public SpotSetDescription getParent() {
        return parent;
    }
}
