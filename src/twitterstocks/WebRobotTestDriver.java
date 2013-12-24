/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bradley Holloway
 */
public class WebRobotTestDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Database.load();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebRobotTestDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        WebRobot tester = new WebRobot();
        tester.launchChrome();
        tester.mineNYTimes(24, 11, 1996);
        tester.mineNYTimes(25, 11, 1996);
        tester.mineNYTimes(26, 11, 1996);
    }
}
