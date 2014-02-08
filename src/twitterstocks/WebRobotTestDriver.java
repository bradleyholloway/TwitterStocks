/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

//MAKE SURE CHROME IS NOT ALREADY RUNNING ON YOUR COMPUTER!
/**
 *
 * @author Bradley Holloway
 */
public class WebRobotTestDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //WebRobot tester = new WebRobot();
        Database.load();

        //for (int[] point : Database.getCountOfWordGraph("test"))
        //{
        //    System.out.println(point[0]+" "+point[1]);
        //    
        //}

        //tester.launchChrome();
        //tester.mineNYTimes(27, 11, 1996,3);
        //tester.mineNYTimes(25, 11, 1996);
        //tester.mineNYTimes(26, 11, 1996);

        /*
         Database.add(new Indicator("GDPTest"));
        
         String gdp = tester.getClipboard();
         while(gdp.length() > 5)
         {
         int year = Integer.parseInt(gdp.substring(7, gdp.indexOf(",",7)));
         gdp = gdp.substring(12);
         double value = Double.parseDouble(gdp.substring(0,gdp.indexOf(",")));
         gdp = gdp.substring(gdp.indexOf("\n")+1);
         System.out.println(year + " " + value + " " + gdp);
         try {
         Database.updateIndicator("GDPTest", year, value);
         } catch (FileNotFoundException ex) {
         Logger.getLogger(WebRobotTestDriver.class.getName()).log(Level.SEVERE, null, ex);
         }
         } */


    }
}
