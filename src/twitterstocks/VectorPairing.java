/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bradley Holloway
 */
public class VectorPairing {

    public static void dotProductWeighting(Indicator indicator, int iterations, boolean percentBased, boolean iterationGraphing) {
        System.out.println("\nBeginning Vector Analysis on " + indicator.getName() + ".");
        WordWeightTable wt = new WordWeightTable();
        //Example of How to lod in Data using the GSON loaders (REDO WHEN INDICATOR CHANGES)
        HashMap<String, ZVector> ZVectors = (percentBased) ? Database.getGSONPerMap(indicator.getName()) : Database.getGSONMap(indicator.getName());
        ZVector indicatorPerData = Database.getGSONIndicator(indicator.getName());
        //double[][] indicatorData = Database.getIndicatorGraph(indicator);
        //float[] indicatorPerData = Database.getIndicatorVector(indicatorData);

        //Example of How to load in the Percent GSON data

        //HashMap<String, float[]> percentZVectors = Database.getGSONPerMap(indicator.getName());
        //float[] indicatorPerData = Database.getGSONIndicatorPer(indicator.getName());
        float[] tempData;
        float[] goal = copy(indicatorPerData);
        final float[] finalGoal = copy(goal);
        final float[] dates = ZVectors.get("IDATES");
        float[] total = new float[goal.length];
        String bestWord = "";
        double tempDistance;

        for (int iteration = 0; iteration < iterations; iteration++) {
            double maxDistance = Double.MIN_VALUE;
            //System.out.println("Searching for closest vector... Iteration: " + iteration);
            for (String word : Database.RevWords) {
                if (ZVectors.get(word) == null) {
                } else {
                    tempData = copy(ZVectors.get(word));
                    if (ZVectors.get(word) != null) {
                        tempDistance = Math.abs(Compare.dotProduct(goal, tempData));
                        if (tempDistance > maxDistance) {
                            maxDistance = tempDistance;
                            bestWord = word;
                            //System.out.println(word+maxDistance);
                            //System.out.println("New Best: " + bestWord);
                        }
                    }
                }

            } //System.out.println("Done.");
            tempData = copy(ZVectors.get(bestWord));
            //System.out.println(bestWord + ", Weighted at: " + Compare.correctScale(goal, tempData));
            if (!containsNANInfinity(tempData)) {
                wt.add(bestWord, Compare.correctScale(goal, tempData));
                if (iterationGraphing) {
                    Grapher.createGraph(total, finalGoal, "iterations\\total\\" + indicator.getName() + "\\iteration" + iteration,dates);
                    Grapher.createGraph(Compare.multiply(tempData, Compare.correctScale(goal, tempData)), goal, "iterations\\temporary\\" + indicator.getName() + "\\iteration" + iteration,dates);
                }
                total = Compare.add(total, Compare.multiply(tempData, Compare.correctScale(goal, tempData)));
                goal = Compare.getDifference(finalGoal, total);
                //Grapher.createGraph(total, finalGoal, "Vector" + bestWord + "CTG" + iteration);
            }
        }
        Grapher.createGraph(total, finalGoal, "Vector" + indicator.getName() + "Approximation",dates);
        System.out.println("Results for " + indicator.getName() + " for " + iterations + " iterations.");
        System.out.println(wt);
    }
    public static void dotProductWeightingLimitedSequence(Indicator indicator, boolean percentBased, boolean iterationGraphing, double percentStart, double percentEnd, double increment, int predictionCorrelation)
    {
        System.out.println("\nBeginning Vector Analysis on " + indicator.getName() + ".");
        for(double percent = percentStart; percent <= percentEnd; percent = round(percent+increment, 4))
        {
            dotProductWeightingLimitedRegression(indicator, percentBased, iterationGraphing, percent, predictionCorrelation);
        }
    }

    public static void dotProductWeightingLimitedRegression(Indicator indicator, boolean percentBased, boolean iterationGraphing, double percentAnalyzed, int predictionCorrelation) {
        int iterations = 0;
        
        WordWeightTable wt = new WordWeightTable();
        //Example of How to lod in Data using the GSON loaders (REDO WHEN INDICATOR CHANGES)
        HashMap<String, float[]> ZVectors = (percentBased) ? Database.getGSONPerMap(indicator.getName()) : Database.getGSONMap(indicator.getName());
        float[] indicatorPerData = Database.getGSONIndicator(indicator.getName());
        //double[][] indicatorData = Database.getIndicatorGraph(indicator);
        //float[] indicatorPerData = Database.getIndicatorVector(indicatorData);

        //Example of How to load in the Percent GSON data

        //HashMap<String, float[]> percentZVectors = Database.getGSONPerMap(indicator.getName());
        //float[] indicatorPerData = Database.getGSONIndicatorPer(indicator.getName());
        float[] tempData;
        float[] goal = copy(indicatorPerData);
        float[] tempGoal;
        final float[] finalGoal = getPercent(copy(goal), percentAnalyzed);
        final float[] dates = ZVectors.get("IDATES");
        iterations = finalGoal.length * 1 / 4 - 2;
        final float[] finalDisplay = copy(goal);
        float[] total = new float[goal.length];

        String bestWord = "";
        double tempDistance;

        for (int iteration = 0; iteration < iterations; iteration++) {
            double maxDistance = Double.MIN_VALUE;
            tempGoal = getPercent(goal, percentAnalyzed);
            //System.out.println("Searching for closest vector... Iteration: " + iteration);
            for (String word : Database.RevWords) {
                if (ZVectors.get(word) == null) {
                } else {
                    tempData = getPercent(copy(ZVectors.get(word)), percentAnalyzed);
                    if (ZVectors.get(word) != null) {
                        tempDistance = Math.abs(Compare.dotProduct(tempGoal, tempData));
                        if (tempDistance > maxDistance) {
                            maxDistance = tempDistance;
                            bestWord = word;
                            //System.out.println(word+maxDistance);
                            //System.out.println("New Best: " + bestWord);
                        }
                    }
                }

            } //System.out.println("Done.");
            tempData = getPercent(copy(ZVectors.get(bestWord)), percentAnalyzed);
            if (!containsNANInfinity(Compare.correctScale(tempGoal, tempData))) {

                //System.out.println(bestWord + ", Weighted at: " + Compare.correctScale(tempGoal, tempData));
                if (!containsNANInfinity(tempData)) {
                    wt.add(bestWord, Compare.correctScale(tempGoal, tempData));
                    if (iterationGraphing) {
                        Grapher.createGraph(total, finalDisplay, "percents\\iterations\\total\\" + indicator.getName() + "\\iteration" + iteration,dates);
                        Grapher.createGraph(Compare.multiply(tempData, Compare.correctScale(tempGoal, tempData)), tempGoal, "percents\\iterations\\temporary\\" + indicator.getName() + "\\iteration" + iteration,dates);
                    }
                    total = Compare.add(total, Compare.multiply(ZVectors.get(bestWord), Compare.correctScale(tempGoal, tempData)));
                    goal = Compare.getDifference(finalGoal, total);
                    if (iterationGraphing) {
                        Grapher.createGraph(goal, "percents\\iterations\\temporary\\" + indicator.getName() + "\\iterationR" + iteration,dates);
                    }
                    //Grapher.createGraph(total, finalGoal, "Vector" + bestWord + "CTG" + iteration);
                }
            }
        }
        Grapher.createGraph(total, finalDisplay, "percents\\" + indicator.getName() + "\\" + round(percentAnalyzed * 100, 2) + "%", percentAnalyzed,predictionCorrelation,dates);
        System.out.println("Results for " + indicator.getName() + " for " + iterations + " iterations and " + (round(percentAnalyzed * 100, 2)) + "%.");
        System.out.println(wt);
    }

    private static boolean containsNANInfinity(float[] tempData) {
        for (float f : tempData) {
            if (("" + f).equals("" + Float.NaN) || ("" + f).equals(Float.NEGATIVE_INFINITY) || (f + "").equals(Float.POSITIVE_INFINITY)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsNANInfinity(double tempData) {
        if (("" + tempData).equals("" + Double.NaN) || ("" + tempData).equals(Double.NEGATIVE_INFINITY) || (tempData + "").equals(Double.POSITIVE_INFINITY)) {
            return true;
        }
        return false;
    }

    private static float[] copy(float[] data) {
        float[] returns = new float[data.length];
        System.arraycopy(data, 0, returns, 0, data.length);
        return returns;
    }

    private static float[] getPercent(float[] data, double percent) {
        percent = Math.sqrt(percent);
        float[] newData = new float[(int) (Math.ceil((double) data.length * percent))];
        for (int i = 0; i < newData.length; i++) {
            newData[i] = data[i];
        }
        return newData;
    }

    private static double round(double number, int digits) {
        long temp = Math.round(number * Math.pow(10, digits));
        return (double) temp / (Math.pow(10, digits));
    }
}
