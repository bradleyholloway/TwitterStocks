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
        int month, articles = 2; //Articles per day to look up, Remember perday, so compound by 365 per year, so keep fairly small
        for(year = 1981; year < 2013; year++)
        {
            month = 1;
            for(int day = 15; day <= 18; day++) //January
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day =15; day <=18; day++) //February
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //March
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //April
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //May
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //June
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //July
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //August
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //September
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //October
            {
                //robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //November
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 15; day <= 18; day++) //December
            {
                //robot.mineNYTimes(day, month, year, articles);
            }            
        }
    }//main
}//wallStreetJournalMiner
