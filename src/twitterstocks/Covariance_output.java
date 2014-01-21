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
        
        int numtests = 1000;// number of tests with random word combinations
        int numwords = 20;//number of words in a combination
        ArrayList<String> words = Database.words;
        Double [] correlations = new Double [numtests];
        String [] matchWord = new String [numtests];
        double maxR = 0.0;
        
        for(int x = 0; x <numtests; x++)
        {
            ArrayList<int [] []>countlists = new ArrayList <int [] []>() ;
            String countedWords = "";
            for(int y = 0; y<=(int)(Math.random()*numwords);y++)
            {
                String temp = words.get((int)(Math.random()*words.size()));
                countedWords = countedWords + " + " + temp;
                
                countlists.add(Database.getCountOfWordGraph(temp));
            }
            double currentR = (Compare.covariance(Database.getIndicatorGraph("GDPdx"), Database.combineCounts(countlists)));
            if(currentR > maxR)
            {
                maxR= currentR;
                 System.out.println("R: " + currentR + " test# " + x + countedWords);
            }    
        }
        
        /*
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
        
        
        
        for(int display = 0; display < correlations.length; display ++)
        {
            System.out.println(matchWord[display]+ " R: " + correlations[display]);   
        }
        * /
       
       
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
