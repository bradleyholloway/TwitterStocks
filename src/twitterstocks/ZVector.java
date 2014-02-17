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
    public float[] getScaledData()
    {
        float[] temp = new float[zData.length];
        for(int i = 0; i < temp.length; i++)
        {
            temp[i] = zData[i] * (float)stDev + (float)rawMean;
        }
        return temp;
    }
    public ZVector divideBy(ZVector divisor)
    {
        float[] numerator = this.getScaledData();
        float[] denomonator = divisor.getScaledData();
        return new ZVector(divide(numerator, denomonator));
    }
    
    private float[] divide(float[] numerator, float[] denomonator)
    {
        float[] result = new float[numerator.length];
        for(int i = 0; i < numerator.length; i++)
        {
            result[i] = numerator[i] / denomonator[i];
        }
        return result;
    }
    
}
