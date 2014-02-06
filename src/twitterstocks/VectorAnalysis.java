package twitterstocks;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        
        
    }
}
