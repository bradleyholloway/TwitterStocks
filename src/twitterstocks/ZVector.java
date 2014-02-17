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
    public ZVector(float[] normalData)
    {
        construct(normalData);
    }
    public ZVector(double[][] graphData)
    {
        float[] normalData = new float[graphData.length];
        for(int i = 0; i < normalData.length; i++)
        {
            normalData[i] = (float) graphData[i][1];
        }
        construct(normalData);
    }
    public ZVector(int[][] graphData)
    {
        float[] normalData = new float[graphData.length];
        for(int i = 0; i < normalData.length; i++)
        {
            normalData[i] = (float) graphData[i][1];
        }
    }
    private void construct(float[] normalData)
    {
        this.rawMean = Compare.mean(normalData);
        this.stDev = Compare.standardDev(normalData);
        this.zData = Compare.convertToZ(normalData);
    }
    public float[] getZData()
    {
        return zData;
    }
    public double getMean()
    {
        return rawMean;
    }
    public double getStDev()
    {
        return stDev;
    }
    public double[] getScaledData()
    {
        double[] temp = new double[zData.length];
        for(int i = 0; i < temp.length; i++)
        {
            temp[i] = zData[i] * stDev + rawMean;
        }
        return temp;
    }
    
}
