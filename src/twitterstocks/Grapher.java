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
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

/**
 *
 * @author Bradley Holloway
 */
public class Grapher {

    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    public static int BORDER = 200;

    public static void createGraph(int[][] dataWordi, double[][] dataIndicatori, String fileOut, boolean zSpace) {
        BufferedImage render = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = render.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.black);
        g.drawRect(BORDER, 0, WIDTH - BORDER, HEIGHT - BORDER);
        g.setColor(Color.lightGray);
        g.fillRect(BORDER + 1, 1, WIDTH - BORDER - 2, HEIGHT - BORDER - 2);



        int[][] dataWord = Compare.getWordMatchIndicatorWord(dataIndicatori, dataWordi);
        double[][] dataIndicator = Compare.getIndicatorMatchIndicatorWord(dataIndicatori, dataWordi);

        if (!zSpace) {

            //Get basic scale.
            double xMinWord, xMaxWord, yMinWord, yMaxWord;
            xMinWord = dataWord[0][0];
            xMaxWord = dataWord[dataWord.length - 1][0];
            yMinWord = yMaxWord = dataWord[0][1];
            for (int a = 1; a < dataWord.length; a++) {
                if (dataWord[a][1] < yMinWord) {
                    yMinWord = dataWord[a][1];
                } else if (dataWord[a][1] > yMaxWord) {
                    yMaxWord = dataWord[a][1];
                }
            }
            double xMinIndicator, xMaxIndicator, yMinIndicator, yMaxIndicator;
            xMinIndicator = dataIndicator[0][0];
            xMaxIndicator = dataIndicator[dataIndicator.length - 1][0];
            yMinIndicator = yMaxIndicator = dataIndicator[0][1];
            for (int a = 1; a < dataIndicator.length; a++) {
                if (dataIndicator[a][1] < yMinIndicator) {
                    yMinIndicator = dataIndicator[a][1];
                } else if (dataIndicator[a][1] > yMaxIndicator) {
                    yMaxIndicator = dataIndicator[a][1];
                }
            }
            //Begin Drawing


            g.setColor(Color.red);//Draw Indicator Line


            for (int a = 1; a < dataIndicator.length; a++) {
                drawLine(g, dataIndicator[a - 1], dataIndicator[a], a - 1, dataIndicator.length, yMinIndicator, yMaxIndicator);
            }

            g.setColor(Color.blue);//Draw WordCount Line
            for (int a = 1; a < dataWord.length; a++) {
                drawLine(g, dataWord[a - 1], dataWord[a], a - 1, dataWord.length, yMinWord, yMaxWord);
            }

            //-------------Need To Create and Draw Scales within BORDER---------------------
            int numSubdivisions = 25;
            for (int i = 0; i < numSubdivisions; i++) {
                g.setColor(Color.blue);
                g.drawString("" + ((double) Math.round((((double) (numSubdivisions - i) / numSubdivisions * (yMaxWord - yMinWord) + yMinWord) * 100) / 100)), 5, (int) (((double) HEIGHT - BORDER) * i / numSubdivisions));
                g.setColor(Color.red);
                g.drawString("" + ((double) Math.round((((double) (numSubdivisions - i) / numSubdivisions * (yMaxIndicator - yMinIndicator) + yMinIndicator * 100)) / 100)), BORDER / 2 + 5, (int) (((double) HEIGHT - BORDER) * i / numSubdivisions));
            }
            g.setColor(Color.black);
            DecimalFormat date = new DecimalFormat("XXXX/XX/XX");
            int dateSubdivisions = 25;
            for (int i = 0; i < dateSubdivisions; i++) {
//            g.drawString(""+date.format((int)((double)i/dateSubdivisions*(xMaxWord-xMinWord)+xMinWord)), (int)((double)i/dateSubdivisions*(WIDTH-BORDER)+BORDER),(HEIGHT-BORDER)+5);
            }
        } else {
            double[][] zDataWord = Compare.convertToZ(dataWord);
            double[][] zDataIndicator = Compare.convertToZ(dataIndicator);
            //Get basic scale.
            double xMinWord, xMaxWord, yMinWord, yMaxWord;
            xMinWord = zDataWord[0][0];
            xMaxWord = zDataWord[dataWord.length - 1][0];
            yMinWord = yMaxWord = zDataWord[0][1];
            for (int a = 1; a < zDataWord.length; a++) {
                if (zDataWord[a][1] < yMinWord) {
                    yMinWord = zDataWord[a][1];
                } else if (zDataWord[a][1] > yMaxWord) {
                    yMaxWord = zDataWord[a][1];
                }
            }
            double xMinIndicator, xMaxIndicator, yMinIndicator, yMaxIndicator;
            xMinIndicator = zDataIndicator[0][0];
            xMaxIndicator = zDataIndicator[zDataIndicator.length - 1][0];
            yMinIndicator = yMaxIndicator = zDataIndicator[0][1];
            for (int a = 1; a < zDataIndicator.length; a++) {
                if (zDataIndicator[a][1] < yMinIndicator) {
                    yMinIndicator = zDataIndicator[a][1];
                } else if (zDataIndicator[a][1] > yMaxIndicator) {
                    yMaxIndicator = zDataIndicator[a][1];
                }
            }
            double yMin = Math.min(yMinIndicator, yMinWord);
            double yMax = Math.max(yMaxIndicator, yMaxWord);
            //Begin Drawing
            g.setColor(Color.darkGray);
            for(int i = (int)Math.ceil(yMin); i<= (int)Math.floor(yMax); i++)
            {
                g.drawLine(BORDER, (int)((double)(yMax-i)/(yMax-yMin)*(HEIGHT-BORDER)), WIDTH, (int)((double)(yMax-i)/(yMax-yMin)*(HEIGHT-BORDER)));
            }

            g.setColor(Color.red);//Draw Indicator Line
            for (int a = 1; a < zDataIndicator.length; a++) {
                drawLine(g, zDataIndicator[a - 1], zDataIndicator[a], a - 1, zDataIndicator.length, yMin, yMax);
            }

            g.setColor(Color.blue);//Draw WordCount Line
            for (int a = 1; a < dataWord.length; a++) {
                drawLine(g, zDataWord[a - 1], zDataWord[a], a - 1, zDataWord.length, yMin, yMax);
            }

            //-------------Need To Create and Draw Scales within BORDER---------------------
            int numSubdivisions = 25;
            for (int i = 0; i < numSubdivisions; i++) {
                g.setColor(Color.black);
                g.drawString("" + ((double) Math.round((((double) (numSubdivisions - i) / numSubdivisions * (yMax - yMin) + yMin) * 100)) / 100), BORDER / 2 - 15, (int) (((double) HEIGHT - BORDER) * i / numSubdivisions));

            }
            g.setColor(Color.black);
            DecimalFormat date = new DecimalFormat("XXXX/XX/XX");
            int dateSubdivisions = 25;
            for (int i = 0; i < dateSubdivisions; i++) {
//            g.drawString(""+date.format((int)((double)i/dateSubdivisions*(xMaxWord-xMinWord)+xMinWord)), (int)((double)i/dateSubdivisions*(WIDTH-BORDER)+BORDER),(HEIGHT-BORDER)+5);
            }

        }
        g.setColor(Color.black);
        g.drawString(Compare.covariance(dataIndicator, dataWord)+"", BORDER+50, 50);

        try {
            File outputfile = new File(fileOut + ".png");
            ImageIO.write(render, "png", outputfile);
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }

    }

    private static void drawLine(Graphics g, double[] pointA, double[] pointB, double xNum, double xMax, double yMin, double yMax) {
        int[] tempPointA = convertPoint(pointA, xNum, xMax, yMin, yMax);
        int[] tempPointB = convertPoint(pointB, xNum + 1, xMax, yMin, yMax);
        g.drawLine(tempPointA[0], tempPointA[1], tempPointB[0], tempPointB[1]);
    }

    private static void drawLine(Graphics g, int[] pointA, int[] pointB, double xNum, double xMax, double yMin, double yMax) {
        int[] tempPointA = convertPoint(pointA, xNum, xMax, yMin, yMax);
        int[] tempPointB = convertPoint(pointB, xNum + 1, xMax, yMin, yMax);
        g.drawLine(tempPointA[0], tempPointA[1], tempPointB[0], tempPointB[1]);
    }

    private static int[] convertPoint(int[] point, double xMin, double xMax, double yMin, double yMax) {
        int[] ret = new int[2];
        ret[0] = (int) ((double) (xMin / xMax) * (WIDTH - BORDER) + BORDER);
        ret[1] = (int) ((double) (yMax - point[1]) / (yMax - yMin + 1) * (HEIGHT - BORDER));
        return ret;
    }

    private static int[] convertPoint(double[] point, double xMin, double xMax, double yMin, double yMax) {
        int[] ret = new int[2];
        ret[0] = (int) (((double) xMin / xMax) * (WIDTH - BORDER) + BORDER);
        ret[1] = (int) ((yMax - point[1]) / (yMax - yMin + 1) * (HEIGHT - BORDER));
        return ret;
    }
}
