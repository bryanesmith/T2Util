/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.proteomecommons.T2DE.T2Interface.JobItemInterface;
import org.proteomecommons.T2DE.T2Interface.JobRunInterface;
import org.proteomecommons.t2util.utils.AssertionUtil;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class PeakListDescription {

    private final SpotDescription parent;
    private final JobItemInterface jobItemInterface;
    private final JobRunInterface jobRunInterface;
    // Lazy-loaded variables
    private List<PeakDescription> peakDescriptions = null, allPeakDescription = null;
    private String jobRunItemId = null, jobItemId = null;

    protected PeakListDescription(final JobItemInterface jobItemInterface, final JobRunInterface jobRunInterface, final SpotDescription parent) {
        AssertionUtil.assertNotNull("Cannot retrieve peaklist without jobItemInterface, should have checked. Program error.", jobItemInterface);
        AssertionUtil.assertNotNull("Cannot retrieve peaklist without jobRunInterface, should have checked. Program error.", jobRunInterface);
        AssertionUtil.assertNotNull("Parent node must always be non-null", parent);

        this.jobItemInterface = jobItemInterface;
        this.jobRunInterface = jobRunInterface;
        this.parent = parent;
    }

    /**
     * @return the parent
     */
    public SpotDescription getParent() {
        return parent;
    }

    /**
     *
     * @param peak
     * @return
     */
    protected boolean addPeakDescription(PeakDescription peak) {
        return getPeakDescriptions().add(peak);
    }

    public String getJobId() {
        if (jobItemId == null) {
            jobItemId = jobItemInterface.getJobId();
        }
        return jobItemId;
    }

    /**
     *
     * @return
     */
    public String getJobRunId() {
        if (jobRunItemId == null) {
            jobRunItemId = this.jobRunInterface.getJobRunId();
        }
        return jobRunItemId;
    }

    /**
     * 
     * @return
     */
    public List<PeakDescription> getPeakDescriptions() {

        // Note that peaks are generated and added in SpotDescription lazily, so all this method
        // needs to do is create the collection.
        if (peakDescriptions == null) {
            peakDescriptions = new LinkedList();
            allPeakDescription = peakDescriptions;
        }

        return peakDescriptions;
    }

    /**
     * <p>Can temporarily remove all peaks below a required absolute intensity.</p>
     * <p>To restore all peaks, invoke restoreAllPeakDescriptions</p>
     * @param requiredAbsIntensity
     */
    public void removePeakDescriptionsBelowAbsoluteThresholdIntensity(double requiredAbsIntensity) {
        List<PeakDescription> newPeakDescriptions = new LinkedList();

        for (PeakDescription peak : getPeakDescriptions()) {
            if (peak.getIntensity() >= requiredAbsIntensity) {
                newPeakDescriptions.add(peak);
            }
        }

        peakDescriptions = newPeakDescriptions;
    }

    /**
     * <p>Remove up to two subsequent peaks that are 1 m/z greater than a peak if they have 10% less intensity than preceding peak.</p>
     */
    public void removeIsolopes() {
        Set<PeakDescription> toRemove = new HashSet();
        
        List<PeakDescription> oldPeakDescriptions = getPeakDescriptions();

        for (int index = oldPeakDescriptions.size()-1; index >= 1; index--) {
            PeakDescription next = oldPeakDescriptions.get(index);
            PeakDescription prev = oldPeakDescriptions.get(index-1);

            // Is it almost exactly 1 m/z apart?
            if (isWithinDelta(prev.getCentroid() + 1.0, next.getCentroid(), 0.2)) {

                // Remove it if significantly smaller
                if (isLessThan(next.getIntensity(), prev.getIntensity(), 0.10)) {
//                    System.out.println("DEBUG> "+parent.getWellName()+" Removed "+next.getCentroid()+" m/z ["+next.getIntensity()+"] because of "+prev.getCentroid()+" m/z ["+prev.getIntensity()+"]");
                    toRemove.add(next);
                }

            }
        }

        List<PeakDescription> newPeakDescriptions = new LinkedList(getPeakDescriptions());
        newPeakDescriptions.removeAll(toRemove);

        peakDescriptions = newPeakDescriptions;
    }

    /**
     * <p>If peaks were remove, invoke this to reset to all peaks.
     */
    public void restoreAllPeakDescriptions() {
        peakDescriptions = allPeakDescription;
    }

    /**
     *
     * @param d1
     * @param d2
     * @param delta
     * @return
     */
    private boolean isWithinDelta(double d1, double d2, double delta) {
        return d1 + delta >= d2 && d1 - delta <= d2;
    }

    /**
     *
     * @param greater
     * @param less
     * @param requiredPercentageLess e.g., 0.10 if lesser value must be greater value - 10% of its value of less
     * @return
     */
    private boolean isLessThan(double less, double greater, double requiredPercentageLess) {
        return less <= greater - greater * requiredPercentageLess;
    }
    
    private int hashCode = -1;

    @Override()
    public int hashCode() {
        if (hashCode == -1) {
            hashCode = this.getJobRunId().hashCode();
        }
        return hashCode;
    }

    @Override()
    public boolean equals(Object o) {
        if (o instanceof PeakListDescription) {
            PeakListDescription other = (PeakListDescription)o;

            return other.getParent().getWellName().equals(this.getParent().getWellName()) && other.getJobRunId().equals(this.getJobRunId());
        }
        return false;
    }
}
