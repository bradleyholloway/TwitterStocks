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
public class NewYorkTimesMiner {

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
        robot.launchChrome(); //Make sure chrome is closed.
        
        int year = 1981;
        int month, articles = 5; //Articles per day to look up, Remember perday, so compound by 365 per year, so keep fairly small
        //for(int year = 1981; year < 2013; year++)
        //{
            month = 1;
            for(int day = 17; day <= 17; day++) //January
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day =17; day <=17; day++) //February
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //March
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //April
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //May
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //June
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //July
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //August
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //September
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //October
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //November
            {
                robot.mineNYTimes(day, month, year, articles);
            } month++;
            for(int day = 17; day <= 17; day++) //December
            {
                robot.mineNYTimes(day, month, year, articles);
            }            
        //}
        
        
        
        
    }
}
