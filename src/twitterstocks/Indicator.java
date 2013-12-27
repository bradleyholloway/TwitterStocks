package twitterstocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import sun.misc.Sort;

class Indicator {
    private ArrayList<double[]> points;
    private String name;
    
    public Indicator(String name) throws FileNotFoundException
    {
        this.name = name;
        points = new ArrayList<double[]>();
        File f = new File("indicators\\"+ name + ".txt");
        if(f.exists())
        {
            this.read();
        }
    }
    
    public void addPoint(double x, double y) throws FileNotFoundException
    {
        double[] point = new double[2];
        point[0] = x;
        point[1] = y;
        points.add(insertPoint(x), point);
        write();
    }
    private int insertPoint(double d)
    {
        int a = 0;
        while(a < points.size() && d > points.get(a)[0])
        {
            a++;
        }
        return a;
    }
    
    public double[][] getGraphData()
    {
        
        double[][] returns = new double[points.size()][2];
        for(int a = 0; a < points.size(); a++)
        {
            returns[a][0] = points.get(a)[0];
            returns[a][1] = points.get(a)[1];
        }
        return returns;
    }
    
    public String getName()
    {
        return name;
    }
    
    private void write() throws FileNotFoundException
    {
        PrintWriter out = new PrintWriter("indicators\\"+ name + ".txt");
        try {
            for (double[] p : points)
            {
                out.println(p[0] +","+p[1]);
            }
        } finally { out.close();}
    }
    
    private void read() throws FileNotFoundException
    {
        File file = new File("indicators\\"+ name + ".txt");
        Scanner fileIn = new Scanner(file);
        try {
            while (fileIn.hasNextLine()) {
                String p = fileIn.nextLine();
                double x = Double.parseDouble(p.substring(0, p.indexOf(',')));
                double y = Double.parseDouble(p.substring(p.indexOf(',')+1));
                addPoint(x, y);
            }
        }
        finally {fileIn.close();}
    }
    
}