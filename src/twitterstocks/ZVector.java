/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

/**
 *
 * @author Bradley Holloway
 */
public class ZVector {
    public float[] zData;
    public double rawMean;
    public double stDev;
    
    public ZVector(float[] zData, double rawMean, double stDev) {
        this.zData = zData;
        this.rawMean = rawMean;
        this.stDev = stDev;
    }
    
}
