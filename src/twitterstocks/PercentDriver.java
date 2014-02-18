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
        
        int iterations = 5; // Number of iterations to corrilate
        int prediction = 4; //Number of datapoints to predict correlation
        int analysis = 16; //number of data points to coorilate to
        int increment = 8; //Increment in irrelevant data points
        boolean percentBased = true; //Toggles word count vs percent usage
        
        for (Indicator i : Database.indicators) {
        //    VectorPairing.dotProductWeighting(i, iterations, false, true);
        //}
        //Indicator i = Database.indicators.get(1);
            
            VectorPairing.dotProductWeightingLimitedSequence(i, percentBased, false, iterations, increment, prediction, analysis);
            //for (double percent = 0.05; percent <= 1.03; percent += .05) {
            //    System.out.println(percent);
            //    VectorPairing.dotProductWeightingLimitedRegression(i, false, false, percent);
            //}
        }

    }

    public static float[] copy(float[] data) {
        float[] returns = new float[data.length];
        System.arraycopy(data, 0, returns, 0, data.length);
        return returns;
    }
}
