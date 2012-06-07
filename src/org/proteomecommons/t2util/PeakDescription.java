/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class PeakDescription implements Comparable {

    private final float centroid, intensity;
    private final PeakListDescription parent;

    /**
     * <p>Public so can use in tests in other software.</p>
     * @param centroid
     * @param intensity
     * @param parent
     */
    public PeakDescription(final float centroid, final float intensity, final PeakListDescription parent) {
//        AssertionUtil.assertNotNull("Parent node must always be non-null", parent);

        this.centroid = centroid;
        this.intensity = intensity;
        this.parent = parent;
    }

    /**
     * @return the centroid
     */
    public float getCentroid() {
        return centroid;
    }

    /**
     * @return the intensity
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * @return the parent
     */
    public PeakListDescription getParent() {
        return parent;
    }
    private int hashCode = -1;

    @Override()
    public int hashCode() {

        if (hashCode == -1) {
            hashCode = String.valueOf(this.getCentroid()).hashCode();
        }
        return hashCode;
    }

    @Override()
    public boolean equals(Object o) {
        if (o instanceof PeakDescription) {
            PeakDescription other = (PeakDescription) o;

            boolean isSameVals = other.getCentroid() == this.getCentroid() && other.getIntensity() == this.getIntensity();
            boolean isSameParents = (other.parent == null && this.parent == null) || (other.parent != null && this.parent != null && other.parent.equals(this.parent));

            return isSameVals && isSameParents;
        }
        return false;
    }

    /**
     *
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        if (o instanceof PeakDescription) {
            PeakDescription other = (PeakDescription) o;

            if (this.centroid < other.centroid) {
                return -1;
            } else if (this.centroid > other.centroid) {
                return 1;
            } else {
                return 0;
            }
        }
        throw new RuntimeException("Argument must be of type PeakDescription");
    }
}
