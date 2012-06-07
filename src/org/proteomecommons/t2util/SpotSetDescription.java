/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.proteomecommons.T2DE.T2Interface.JobItemInterface;
import org.proteomecommons.T2DE.T2Interface.JobItemListInterface;
import org.proteomecommons.T2DE.T2Interface.SpotInfoInterface;
import org.proteomecommons.T2DE.T2Interface.SpotInterface;
import org.proteomecommons.T2DE.T2Interface.SpotSetInterface;
import org.proteomecommons.t2util.utils.AssertionUtil;
import org.proteomecommons.t2util.utils.MALDIUtil;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class SpotSetDescription {

    private final T2Instrument instrument;
    private final String id, name;
    // Lazy-loaded variables
    private SpotSetInterface spotSetInterface = null;
    private Set<SpotDescription> spotDescriptions = null;
    private List<String> jobIds = null;

    protected SpotSetDescription(final String id, final String name, final T2Instrument instrument) {
        AssertionUtil.assertNotNull("Must specify id for spot set.", id);
        AssertionUtil.assertNotNull("Must specify name for spot set.", name);
        AssertionUtil.assertNotNull("Must be connected to an instrument.", instrument);

        this.id = id;
        this.name = name;
        this.instrument = instrument;
    }

    /**
     * <p>Clean up resources used by spot set. Only necessary if accessed spot descriptions and you wish to free up resources (e.g., memory).</p>
     * <p>Note that once closed, the spot set can be re-opened by simply calling getter methods for spot descriptions. This method simply allows garbage collection of child nodes.</p>
     */
    public void close() {
        if (spotDescriptions != null) {

            this.spotSetInterface = null;

            // Allow garbage collection of children
            spotDescriptions.clear();
            spotDescriptions = null;
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    protected SpotSetInterface getSpotSetInterface() {
        if (spotSetInterface == null) {
            spotSetInterface = instrument.dbInterface.getSpotSet(getId(), true);

            // Prep spot set for something?
            instrument.dbInterface.initSpotSet(spotSetInterface, true);
        }
        return spotSetInterface;
    }

    /**
     * <p>List of all job identifiers for a spot set. Note that not all spots might have been processed by job identifiers.</p>
     * @return
     */
    public List<String> getJobIds() {

        if (jobIds == null) {
            Set<String> jobIdsSet = new HashSet();

//            for (SpotDescription spot : getSpotDescriptions()) {
//                jobIdsSet.addAll(spot.getJobIds());
//            }

            for (JobItemListInterface jobItemListInterface : getSpotSetInterface().getAllJobItemLists()) {
                for (JobItemInterface jobItemInterface : jobItemListInterface.getJobItems()) {
                    jobIdsSet.add(jobItemInterface.getJobId());
                }
            }

            jobIds = new ArrayList(jobIdsSet);
            Collections.sort(jobIds);
        }

        return jobIds;
    }

    /**
     * 
     * @return Number of spots in spot set.
     */
    public int size() {
        return getSpotDescriptions().size();
    }

    /**
     * <p>Get all spots in spot set that were part of a specified job.</p>
     * @param jobId
     * @return
     */
    public Set<SpotDescription> getSpotDescriptionsByJobId(String jobId) {
        Set<SpotDescription> spots = new HashSet();

        for (SpotDescription spot : getSpotDescriptions()) {
            if (spot.getJobIds().contains(jobId)) {
                spots.add(spot);
            }
        }

        return spots;
    }

    /**
     * <p>Get all spots in spot set.</p>
     * @return the spotDescriptions
     */
    public Set<SpotDescription> getSpotDescriptions() {
        if (spotDescriptions == null) {
            spotDescriptions = new HashSet();

            SpotSetInterface ssi = getSpotSetInterface();

            for (String spotId : ssi.getSpotIds()) {

                SpotInterface spotInterface = ssi.getSpot(spotId);
                SpotInfoInterface spotInfoInterface = spotInterface.getSpotInfo();

                // Skip all calibration data
                if (MALDIUtil.isCalibrationSpot(spotInfoInterface.getSpecialNameLocation())) {
                    continue;
                }

                SpotDescription spot = new SpotDescription(spotInterface, spotInfoInterface, this, instrument);
                spotDescriptions.add(spot);
            }
        }
        return spotDescriptions;
    }
}
