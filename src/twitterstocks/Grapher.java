/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Bradley Holloway
 */
public class Grapher {
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    public static int BORDER = 200;
    
    public static void createGraph(double[][] dataWord, double[][] dataIndicator, String fileOut)
    {
        BufferedImage render = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = render.getGraphics();
        
        //Get basic scale.
        double xMinWord, xMaxWord, yMinWord, yMaxWord;
        xMinWord = dataWord[0][0];
        xMaxWord = dataWord[dataWord.length - 1][0];
        yMinWord = yMaxWord = dataWord[0][1];
        for(int a = 1; a < dataWord.length; a++)
        {
            if(dataWord[a][1] < yMinWord)
            {
                yMinWord = dataWord[a][1];
            } else if (dataWord[a][1] > yMaxWord)
            {
                yMaxWord = dataWord[a][1];
            }
        }
        double xMinIndicator, xMaxIndicator, yMinIndicator, yMaxIndicator;
        xMinIndicator = dataIndicator[0][0];
        xMaxIndicator = dataIndicator[dataIndicator.length - 1][0];
        yMinIndicator = yMaxIndicator = dataIndicator[0][1];
        for(int a = 1; a < dataIndicator.length; a++)
        {
            if(dataIndicator[a][1] < yMinIndicator)
            {
                yMinIndicator = dataIndicator[a][1];
            } else if (dataIndicator[a][1] > yMaxIndicator)
            {
                yMaxIndicator = dataIndicator[a][1];
            }
        }
        //Begin Drawing
        
        g.setColor(Color.black);
        g.drawRect(BORDER, 1, WIDTH, HEIGHT - BORDER);
       
        g.setColor(Color.red);//Draw Indicator Line
        for (int a = 1; a < dataIndicator.length; a ++)
        {
            drawLine(g, dataIndicator[a-1], dataIndicator[a], xMinIndicator, xMaxIndicator, yMinIndicator, yMaxIndicator);
        }
        
        g.setColor(Color.blue);//Draw WordCount Line
        for (int a = 1; a < dataWord.length; a++)
        {
            drawLine(g, dataWord[a-1], dataWord[a], xMinWord, xMaxWord, yMinWord, yMaxWord);
        }
        
        //-------------Need To Create and Draw Scales within BORDER---------------------
        
        try {
            File outputfile = new File(fileOut + ".png");
            ImageIO.write(render, "png", outputfile);
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        
    }
    private static void drawLine(Graphics g, double[] pointA, double[] pointB, double xMin, double xMax, double yMin, double yMax)
    {
        int[] tempPointA = convertPoint(pointA, xMin, xMax, yMin, yMax);
        int[] tempPointB = convertPoint(pointA, xMin, xMax, yMin, yMax);
        g.drawLine(tempPointA[0], tempPointA[1], tempPointB[0], tempPointB[1]);
    }
    
    
    private static int[] convertPoint(double[] point, double xMin, double xMax, double yMin, double yMax)
    {
        int[] ret = new int[2];
        ret[0] = (int)((point[0]-xMin)/(xMax - xMin + 1) * (WIDTH - BORDER) + BORDER);
        ret[1] = (int)((yMax - point[1])/(yMax - yMin + 1) * (HEIGHT - BORDER));
        return new int[2];
    }
    
}
