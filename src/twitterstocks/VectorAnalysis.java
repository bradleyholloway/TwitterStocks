package twitterstocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bradley Holloway
 */
public class VectorAnalysis {

    public static void main(String[] args) {
        /*
         try {
         Database.load();
         } catch (FileNotFoundException ex) {
         Logger.getLogger(VectorAnalysis.class.getName()).log(Level.SEVERE, null, ex);
         }
         ArrayList<String> words = Database.words;
         ArrayList <String> RevWords = new ArrayList <String> ();
        
         for(int index = 0; index < words.size(); index ++)
         {
         double count = Compare.sum(Database.getCountOfWordGraph(words.get(index)));
         System.out.println("index: " + index +" " + words.get(index) + " wordcount: " + count);
         if(count>10)
         {
         RevWords.add(words.get(index));
         }    
         }//takes out all the NAN's
         System.out.println("removed null words");
        
         HashMap<String, float[]> wordVectors = new HashMap<String, float[]>();
         double[][] indicatorData = Database.getIndicatorGraph(Database.indicators.get(0));
         System.out.println("Placeing Words in HashMap...\t");
         for(String word : RevWords)
         {
         wordVectors.put(word, Database.getWordVector(indicatorData, word));
         System.out.println("Inserting: " + word);
         } //System.out.println("Done.");
         //Populates wordVectors with the vectors for a given indicator, using alignment tecnique.
         /*
         * The way to iterate though this is to use a foreach with revwords.
         * 
         */
        Database.load();
        
        //for (Indicator indicator : Database.indicators) {
        Indicator indicator = Database.indicators.get(2);
        
            HashMap<String, float[]> wordVectors = Database.getGSONMap(indicator.getName());
            double[][] indicatorData = Database.getIndicatorGraph(indicator);
            float[] goal = Database.getIndicatorVector(indicatorData, "allignment");
            float[] finalGoal = Database.getIndicatorVector(indicatorData, "allignment");
            float[] total = new float[goal.length];
            String bestWord = "";
            for (int iteration = 0; iteration < 10000; iteration++) {
                double minimumDistance = Double.MAX_VALUE;
                double tempDistance = 0.0;
                System.out.println("Searching for closest vector... Iteration: " + iteration);
                for (String word : Database.words) {
                    if (wordVectors.get(word) != null) {
                        tempDistance = Compare.distanceBetweenScaled(goal, wordVectors.get(word));
                        if (tempDistance < minimumDistance) {
                            minimumDistance = tempDistance;
                            bestWord = word;
                            //System.out.println("New Best: " + bestWord);
                        }
                    }
                } //System.out.println("Done.");
                //System.out.println(bestWord + ", Weighted at: " + Compare.correctScale(goal, wordVectors.get(bestWord)));
                total = Compare.add(total, Compare.multiply(wordVectors.get(bestWord), Compare.correctScale(goal, wordVectors.get(bestWord))));
                goal = Compare.getDifference(finalGoal, total);
                //Grapher.createGraph(total, finalGoal, "Vector" + bestWord + "CTG" + iteration);
            }
            Grapher.createGraph(total, finalGoal, "Vector" + bestWord + "CTG" + 10000);
        //}

    }
}
