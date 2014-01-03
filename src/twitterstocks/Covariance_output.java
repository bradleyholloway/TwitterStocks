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
        for(String word : words)
        {
            System.out.println(Compare.covariance(Database.getIndicatorGraph("gdp by quarter"), Database.getCountOfWordGraph(word)));
        }
    }
}
