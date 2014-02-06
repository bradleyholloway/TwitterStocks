package twitterstocks;

import java.io.FileNotFoundException;
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
        for(String word : RevWords)
        {
            wordVectors.put(word, Database.getWordVector(indicatorData, word));
        }//Populates wordVectors with the vectors for a given indicator, using alignment tecnique.
        /*
         * The way to iterate though this is to use a foreach with revwords.
         * 
         */
        double minimumDistance = Double.MAX_VALUE;
        double tempDistance = 0.0;
        String bestWord = "";
        for (String word : RevWords)
        {
            tempDistance = Compare.distanceBetweenScaled(Database.getIndicatorVector(indicatorData, word), wordVectors.get(word));
            if(tempDistance < minimumDistance)
            {
                minimumDistance = tempDistance;
                bestWord = word;
            }
        }
        
        
    }
}
