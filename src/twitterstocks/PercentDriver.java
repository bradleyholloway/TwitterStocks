/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

/**
 *
 * @author Bradley Holloway
 */
public class PercentDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database.load();
        double[][] indicatorGraph = Database.getIndicatorGraph(Database.indicators.get(2));
        double[][] wordPercent = Database.getPercentOfWordGraph(" the ");
        System.out.println("Got Percents and Indicator.");
        double[][] result = Compare.allignWordPercent(indicatorGraph, wordPercent);
        double[][] iResult = Compare.allignIndicatorPercent(indicatorGraph, wordPercent);
        System.out.println("Done.");
        float[] vectorW = Compare.convertToVectorZ(result);
        float[] vectorI = Compare.convertToVectorZ(iResult);
        System.out.println("Done.");
        Grapher.createGraph(vectorW,vectorI, "Test Percents");
        Grapher.createGraph(vectorW, "Test PercentsW");
        Grapher.createGraph(vectorI, "Test PercentsI");
        System.out.println("Done.");
    }
}
