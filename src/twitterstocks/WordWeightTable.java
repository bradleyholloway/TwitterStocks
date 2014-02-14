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
public class WordWeightTable {

    public ArrayList<String> words;
    public HashMap<String, Double> weights;

    public WordWeightTable() {
        words = new ArrayList<String>();
        weights = new HashMap<String, Double>();
    }

    public void add(String word, double weight) {
        if(!containsNANInfinity(weight))
        {
        if (!words.contains(word)) {
            words.add(word);
            weights.put(word, weight);
        } else {
            weights.put(word, weights.get(word) + weight);
        }
        }
    }

    private static boolean containsNANInfinity(double f) {
        if (("" + f).equals("" + Double.NaN) || ("" + f).equals(Double.NEGATIVE_INFINITY) || (f + "").equals(Double.POSITIVE_INFINITY)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String data = "";
        for (String w : words) {
            data += w + ":" + weights.get(w) + "\n";
            if (("" + weights.get(w)).equals("" + Double.NaN)) {

                System.out.println("NAN EXCEPTION");
            }
        }
        return data;
    }
}
