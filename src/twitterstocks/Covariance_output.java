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
        for(int x = 0; x <10; x++)
        {
           // System.out.println(words.get(x)+ " R: " +Compare.covariance(Database.getIndicatorGraph("gdp by quarter"), Database.getCountOfWordGraph(words.get(x))));
        }
        /*for(int x = 0; x <400; x++)
        {
            int count = (Database.getCountOfWordGraph("money") [x][1]+Database.getCountOfWordGraph("stock") [x][1]+Database.getCountOfWordGraph("investment") [x][1]+Database.getCountOfWordGraph("growth") [x][1]+Database.getCountOfWordGraph("buisness") [x][1])/10;
            System.out.println("");
            for(int y =0; y<count;y++)
            System.out.print("I");git 
        }*/
        
        Database.ArticleFrequency();
        //test cause everthyings skrewed up
        
    }
}
