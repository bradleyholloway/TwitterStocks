package twitterstocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Indicator {
    private ArrayList<double[]> points;
    private String name;
    
    public Indicator(String name) throws FileNotFoundException
    {
        this.name = name;
        points = new ArrayList<>();
        File f = new File("indicators\\"+ name + ".txt");
        if(f.exists())
        {
            this.read();
        }
    }
    
    public void addPoint(double x, double y)
    {
        double[] point = new double[2];
        point[0] = x;
        point[1] = y;
        points.add(point);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void write() throws FileNotFoundException
    {
        try (PrintWriter out = new PrintWriter("indicators\\"+ name + ".txt")) {
            for (double[] p : points)
            {
                out.println(p[0] +","+p[1]);
            }
        }
    }
    
    private void read() throws FileNotFoundException
    {
        File file = new File("indicators\\"+ name + ".txt");
        try (Scanner fileIn = new Scanner(file)) {
            while (fileIn.hasNextLine()) {
                String p = fileIn.nextLine();
                double x = Double.parseDouble(p.substring(0, p.indexOf(',')));
                double y = Double.parseDouble(p.substring(p.indexOf(',')+1));
                addPoint(x, y);
            }
        }
    }
    
}