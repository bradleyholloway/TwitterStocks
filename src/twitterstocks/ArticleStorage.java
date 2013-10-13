/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Bradley Holloway and Roberto Dailey
 */
public class ArticleStorage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedFlavorException, IOException {
        Database.load();
        
        
        
        //System.out.print("Date (MMDDYYYY): ");
        //try (Scanner sn = new Scanner(System.in)) {
            
        //    Database.add(new Article((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), sn.nextInt()));
        //}
        
        //System.out.println(Database.getCountOfWordByDate("word", 10102013));
        //System.out.println(Database.getCountOfWordByDate("word", 10112013));
        //System.out.println(Database.getCountOfWordByDate("word", 10122013));
        
        for(int i = 0; i < Database.getCountOfWordGraph("word").length; i++)
        {
            System.out.println(Arrays.toString(Database.getCountOfWordGraph("word")[i]));
        }
    }
}
