package twitterstocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

class Database {
    public static HashMap<Integer, ArrayList<Article>> articles = new HashMap<>();
    public static ArrayList<Integer> files = new ArrayList<>();
    public static ArrayList<Integer> dates = new ArrayList<>();
    public static ArrayList<Indicator> indicators = new ArrayList<>();
    
    public static void add(Article a) throws FileNotFoundException
    {
        if(articles.get(a.getDate())==null)
        {
            articles.put(a.getDate(), new ArrayList<Article>());
            dates.add(a.getDate());
        }
        articles.get(a.getDate()).add(a);
        files.add(a.getNum());
        String content;
        File file = new File("Articles.txt");
        try (Scanner fileIn = new Scanner(file)) {
            content = fileIn.nextLine();
            
        }
        try (PrintWriter out = new PrintWriter("Articles.txt")) {
            out.print(content);
            out.print(a.getNum()+",");
        }
        Collections.sort(dates);
        Collections.sort(files);
    }
    public static void add(Indicator i) throws FileNotFoundException
    {
        indicators.add(i);
        String names;
        File file = new File("Indicators.txt");
        try (Scanner fileIn = new Scanner(file)) {
            names = fileIn.nextLine();
            
        }
        try (PrintWriter out = new PrintWriter("Indicators.txt")) {
            out.print(names);
            out.print(i.getName()+",");
        }
    }
    public static void load() throws FileNotFoundException
    {
        String content;
        File file = new File("Indicators.txt");
        try (Scanner fileIn = new Scanner(file)) {
            content = fileIn.nextLine();
        }
        while(content.length() > 0)
        {
            content = parseIndicators(content);
        }
        
        file = new File("Articles.txt");
        try (Scanner fileIn = new Scanner(file)) {
            content = fileIn.nextLine();
        }
        try (PrintWriter out = new PrintWriter("Articles.txt")) {
            out.println();
        }
        
        while(content.length() > 0)
        {
            content = parseArticles(content);
        }
        int max = -1;
        for( Integer temp : files)
        {
            if(temp > max)
            {
                max = temp;
            }
        }
        Article.numFiles = max + 1;
        
        ArrayList<Integer> tempFiles = new ArrayList<>();
        
        for( int f : files)
        {
            tempFiles.add(f);
        }
        
        for( int f : tempFiles)
        {
            add(new Article(f));
        }
    }
    public static boolean updateIndicator(String name, double x, double y)
    {
        for (Indicator i : indicators)
        {
            if(i.getName().equals(name))
            {
                i.addPoint(x, y);
                return true;
            }
        }
        return false;
    }
       
    private static String parseArticles(String content)
    {
        files.add(Integer.parseInt(content.substring(0,content.indexOf(','))));
        return content.substring(content.indexOf(',')+1);
    }
    private static String parseIndicators(String content) throws FileNotFoundException
    {
        indicators.add(new Indicator(content.substring(0,content.indexOf(','))));
        return content.substring(content.indexOf(',')+1);
    }
    
    private static ArrayList<Article> getByDate(int date)
    {
        return articles.get(date);
    }
    
    public static int getCountOfWordByDate(String word, int date)
    {
        int count = 0;
        for(Article a : getByDate(date))
        {
            count += a.getCount(word);
        }
        return count;
    }
    
    public static int[][] getCountOfWordGraph(String word)
    {
        return getCountOfWordGraph(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public static int[][] getCountOfWordGraph(String word, int start, int end)
    {
        ArrayList<Integer> datesInRange = new ArrayList<>();
        for (int date : dates)
        {
            if(date >= start && date <= end)
            {
                datesInRange.add(date);
            }
        }
        int[][] graphPoints = new int[datesInRange.size()][2];
        for(int index = 0; index < datesInRange.size(); index ++)
        {
            graphPoints[index][0] = datesInRange.get(index);
            graphPoints[index][1] = getCountOfWordByDate(word, datesInRange.get(index));
        }
        return graphPoints;
    }
}