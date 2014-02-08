/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bradley Holloway
 */
public class StepTwoGSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Gson g = new Gson();
        try {
            Database.load();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VectorAnalysis.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> words = Database.words;
        ArrayList<String> RevWords = new ArrayList<String>();

        for (int index = 0; index < words.size(); index++) {
            double count = Compare.sum(Database.getCountOfWordGraph(words.get(index)));
            System.out.println("index: " + index + " " + words.get(index) + " wordcount: " + count);
            if (count > 10) {
                RevWords.add(words.get(index));
            }
        }//takes out all the NAN's
        System.out.println("removed null words");

        HashMap<String, float[]> wordVectors = new HashMap<String, float[]>();
        double[][] indicatorData = Database.getIndicatorGraph(Database.indicators.get(0));
        System.out.println("Placeing Words in HashMap...\t");
        int index = 0;
        for (String word : RevWords) {
            wordVectors.put(word, Database.getWordVector(indicatorData, word));
            System.out.println(index + " Inserting: " + word);
            index++;
        }
        
        for (String word : RevWords) {
            System.out.print("Writing "+word+" ...\t");
            try {
                File file = new File("gson\\"+word+".txt");
                PrintWriter out = new PrintWriter(file);
                try {
                    out.print(g.toJson(wordVectors.get(word)));
                } finally {
                    out.close();
                }
                System.out.println("Done.");
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("DONE.");
    }
}
