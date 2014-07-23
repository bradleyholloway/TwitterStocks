/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

/**
 *
 * @author roberto
 */
public class GoogleFinanceMiner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         WebRobot robot = new WebRobot();
        Database.load();
        robot.launchChrome();
        robot.mineGoogleFinance();
    }
}
                                                                                                                                                                                                                            
