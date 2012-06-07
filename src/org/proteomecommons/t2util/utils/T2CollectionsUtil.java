/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.proteomecommons.t2util.PeakListDescription;
import org.proteomecommons.t2util.SpotDescription;
import org.proteomecommons.t2util.SpotSetDescription;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class T2CollectionsUtil {

    public static List<SpotSetDescription> sortSpotSetsByName(Set<SpotSetDescription> spotSets) {
        List<SpotSetDescription> spotSetsList = new ArrayList(spotSets);

        Comparator spotSetComparator = new Comparator() {

            public int compare(Object t1, Object t2) {
                if (t1 instanceof SpotSetDescription && t2 instanceof SpotSetDescription) {
                    SpotSetDescription s1 = (SpotSetDescription) t1;
                    SpotSetDescription s2 = (SpotSetDescription) t2;

                    return s1.getName().compareToIgnoreCase(s2.getName());
                }
                throw new RuntimeException("Expecting two SpotSetDescription objects, instead found " + t1.getClass().getSimpleName() + " and " + t2.getClass().getSimpleName() + ".");
            }
        };

        Collections.sort(spotSetsList, spotSetComparator);

        return spotSetsList;
    }

    /**
     *
     * @param spots
     * @param spotOrder See MALDIUtil for static String variables with valid ordering. Will throw exception if invalid ordering offered.
     * @return
     */
    public static List<SpotDescription> sortSpotsByFractionNumber(Set<SpotDescription> spots, final String spotOrder) {
        List<SpotDescription> list = new ArrayList(spots);
        sortSpotsByFractionNumber(list, spotOrder);
        return list;
    }

    /**
     *
     * @param spotOrder See MALDIUtil for static String variables with valid ordering. Will throw exception if invalid ordering offered.
     */
    public static void sortSpotsByFractionNumber(List<SpotDescription> sortedSelectedSpots, final String spotOrder) {
        Comparator spotComparator = new Comparator() {

            public int compare(Object t1, Object t2) {
                if (t1 instanceof SpotDescription && t2 instanceof SpotDescription) {
                    SpotDescription s1 = (SpotDescription) t1;
                    SpotDescription s2 = (SpotDescription) t2;

                    if (s1.getFractionNumber(spotOrder) < s2.getFractionNumber(spotOrder)) {
                        return -1;
                    } else if (s1.getFractionNumber(spotOrder) > s2.getFractionNumber(spotOrder)) {
                        return +1;
                    }

                    return 0;
                }
                throw new RuntimeException("Expecting two SpotDescription objects, instead found " + t1.getClass().getSimpleName() + " and " + t2.getClass().getSimpleName() + ".");
            }
        };

        Collections.sort(sortedSelectedSpots, spotComparator);
    }

    /**
     * <p>Search a spot for all job runs for job with associate peak lists and return the newest, if found.</p>
     * @param spotDescription
     * @param jobId
     * @return The newest peak list (i.e., large job run id) found for job id, or null if not found.
     */
    public static PeakListDescription getNewestPeakListForSpotSetByJobId(SpotDescription spotDescription, String jobId) {
        PeakListDescription newestPKL = null;
        for (PeakListDescription pld : spotDescription.getPeakListDescriptionsByJobId(jobId)) {
            if (newestPKL == null || Long.parseLong(newestPKL.getJobRunId()) < Long.parseLong(pld.getJobRunId())) {
                newestPKL = pld;
            }
        }
        return newestPKL;
    }
}
