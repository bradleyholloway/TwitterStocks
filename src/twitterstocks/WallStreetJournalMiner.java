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
 * @author roberto
 */
public class WallStreetJournalMiner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         WebRobot robot = new WebRobot();
        try {
            Database.load();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewYorkTimesMiner.class.getName()).log(Level.SEVERE, null, ex);
        }
        robot.launchChrome();
        
        int year = 1985;
        int month, articles = 10; //Articles per day to look up, Remember perday, so compound by 365 per year, so keep fairly small
        for(year = 1985; year < 2013; year++)
        {
            month = 1;
            for(int day = 5; day <= 5; day++) //January
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day =5; day <=5; day++) //February
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //March
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //April
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //May
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //June
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //July
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //August
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //September
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //October
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //November
            {
                robot.mineWSJ(day, month, year, articles);
            } month++;
            for(int day = 5; day <= 5; day++) //December
            {
                robot.mineWSJ(day, month, year, articles);
            }            
        }
    }//main
}//wallStreetJournalMiner
