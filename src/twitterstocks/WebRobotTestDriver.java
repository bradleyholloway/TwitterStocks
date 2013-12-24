/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.io.FileNotFoundException;
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
        WebRobot tester = new WebRobot();
        try {
            Database.load();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebRobotTestDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tester.launchChrome();
        tester.mineNYTimes(27, 11, 1996,3);
        //tester.mineNYTimes(25, 11, 1996);
        //tester.mineNYTimes(26, 11, 1996);
    }
}
