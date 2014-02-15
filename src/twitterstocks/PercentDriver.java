/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.util.HashMap;

/**
 *
 * @author Bradley Holloway
 */
public class PercentDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Database.loadIndicators();
        //int iterations = 20
        //for(Indicator i : Database.indicators)
        //{
        //    VectorPairing.dotProductWeighting(i, iterations, false, true);
        //}
        Indicator i = Database.indicators.get(1);
        
        for(double percent = 0.05; percent <= 1.03; percent +=.05)
        {
            System.out.println(percent);
            VectorPairing.dotProductWeightingLimitedRegression(i, false, false, percent);
        }
        //}
        
    }

    public static float[] copy(float[] data) {
        float[] returns = new float[data.length];
        System.arraycopy(data, 0, returns, 0, data.length);
        return returns;
    }
}
