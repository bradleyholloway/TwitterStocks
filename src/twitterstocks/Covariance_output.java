/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roberto
 */


public class Covariance_output {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Database.load();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Covariance_output.class.getName()).log(Level.SEVERE, null, ex);
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
        
        for(int indicator = 1; indicator < Database.indicators.size();indicator++)
        {
            System.out.println(Database.indicators.get(indicator).getName());
        
        int numtests = 100000;// number of tests with random word combinations
        int numwords = 20;//number of words in a combination
        numtests = RevWords.size();
        Double [] correlations = new Double [numtests];
        String [] matchWord = new String [numtests];
        double maxR = 0.0;
        
        for(int x = 0; x <numtests; x++)
        {
            ArrayList<int [] []>countlists = new ArrayList <int [] []>() ;
            for(int y = 0; y<1;y++)
            {
                String temp = RevWords.get(x);
                matchWord[x] = temp;
                countlists.add(Database.getCountOfWordGraph(temp));
            }
            correlations [x] = (Compare.covariance(Database.getIndicatorGraph(Database.indicators.get(indicator)), Database.combineCounts(countlists)));         
        }

        
        for(int outer = 0; outer < numtests;outer++)
        {
            for(int inner = outer+1; inner <numtests; inner++)
            {
                if(correlations [outer] < correlations[inner])
                {
                    double temp = correlations[outer];
                    String tempS= (matchWord[outer]);
                    correlations[outer] = correlations[inner];
                    matchWord[outer]=(matchWord[inner]);
                    correlations[inner]=temp;
                    matchWord[inner]=(tempS);
                }
            }
        }
        
        
        
        //for(int display = 0; display < correlations.length; display ++)
        //{
           // System.out.println(matchWord[display]+ " R: " + correlations[display]);   
        //} 
        
        
        String [] wordsPos = new String [50];
        
        for(int matchindex = 0; matchindex < wordsPos.length; matchindex++ )
        {
            wordsPos [matchindex] = matchWord[matchindex];
        }
        
        String [] wordsNeg = new String [50];
        for(int matchindex = matchWord.length-1; matchindex > (matchWord.length-1) - (wordsNeg.length); matchindex-- )
        {
            wordsNeg [RevWords.size()-1-matchindex] = matchWord[matchindex];
        }
        
        
        numtests=400000;
        for(int x = 0; x <numtests; x++)
        {
            ArrayList<int [] []>countlists = new ArrayList <int [] []>() ;
            String countedWords = "";
            for(int y = 0; y<=(int)(Math.random()*numwords);y++)
            {
                String tempP = wordsPos[((int)(Math.random()*wordsPos.length))];
                countedWords = countedWords + " + " + tempP;
                String tempN = wordsNeg[((int)(Math.random()*wordsPos.length))];
                countedWords = countedWords + " + " + tempN;
                countlists.add(Database.subtractCounts(Database.getCountOfWordGraph(tempP), Database.getCountOfWordGraph(tempN)));
            }
            double currentR = (Compare.covariance(Database.getIndicatorGraph(Database.indicators.get(indicator)), Database.combineCounts(countlists)));
            
            
            if(Math.abs(currentR) > Math.abs(maxR))
            {
                maxR= currentR;
                 System.out.println("R: " + currentR + " test# " + x + countedWords);
                 Grapher.createGraph(Database.combineCounts(countlists), Database.getIndicatorGraph(Database.indicators.get(indicator)), Database.indicators.get(indicator).getName(), true);
            }    
        }//numtests = 50000
        }//indicator itorator
        /*
        int index = 0;
        for(Integer date : Database.dates)
        {
            int count = (Database.getCountOfWordGraph("money") [index][1]+Database.getCountOfWordGraph("stock") [index][1]+Database.getCountOfWordGraph("investment") [index][1]+Database.getCountOfWordGraph("growth") [index][1]+Database.getCountOfWordGraph("buisness") [index][1]);
            System.out.println(count); 
            index++;
        }
        */
        
        //Database.ArticleFrequency();
        /* 
        double [][] test = Database.getIndicatorGraph("GDPdx");
        
        for(int t = 0; t<test.length; t++)
        {
            System.out.print(test [t] [0] + ": ");
            System.out.println(test [t] [1]);
        }
        */
    }//main
}//covariance output
