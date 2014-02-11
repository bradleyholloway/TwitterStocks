package twitterstocks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Database {

    public static HashMap<Integer, ArrayList<Article>> articles = new HashMap<Integer, ArrayList<Article>>();
    public static ArrayList<Integer> files = new ArrayList<Integer>();
    public static ArrayList<Integer> dates = new ArrayList<Integer>();
    public static ArrayList<Indicator> indicators = new ArrayList<Indicator>();
    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<String> RevWords = new ArrayList<String>();

    public static void add(Article a) throws FileNotFoundException {
        if (articles.get(a.getDate()) == null) {
            articles.put(a.getDate(), new ArrayList<Article>());
            dates.add(a.getDate());
        }
        articles.get(a.getDate()).add(a);
        files.add(a.getNum());
        String content;
        File file = new File("Articles.txt");
        Scanner fileIn = new Scanner(file);
        try {
            content = fileIn.nextLine();
        } finally {
            fileIn.close();
        }

        PrintWriter out = new PrintWriter(file);
        try {
            out.print(content);
            out.print(a.getNum() + ",");
        } finally {
            out.close();
        }

        Collections.sort(dates);
        Collections.sort(files);
    }

    public static void addE(Article a) throws FileNotFoundException {
        if (articles.get(a.getDate()) == null) {
            articles.put(a.getDate(), new ArrayList<Article>());
            dates.add(a.getDate());
        }
        articles.get(a.getDate()).add(a);
        files.add(a.getNum());
        Collections.sort(dates);
        Collections.sort(files);
    }

    public static void add(Indicator i) throws FileNotFoundException {
        indicators.add(i);
        String names;
        File file = new File("Indicators.txt");
        Scanner fileIn = new Scanner(file);
        try {
            names = fileIn.nextLine();

        } finally {
            fileIn.close();
        }
        PrintWriter out = new PrintWriter(file);
        try {
            out.print(names);
            out.print(i.getName() + ",");
        } finally {
            out.close();
        }
    }

    public static void load() {
        System.out.println("Loading Database...");
        loadIndicators();
        loadWords();
        loadRevWords();
        loadArticles();
        System.out.println("Database Loading Done.");
    }

    public static void loadArticles() {
        System.out.print("Loading Articles...\t");
        try {
            File file = new File("Articles.txt");
            Scanner fileIn = new Scanner(file);
            String content;
            try {
                content = fileIn.nextLine();
            } finally {
                fileIn.close();
            }

            PrintWriter out = new PrintWriter(file);
            try {
                out.print(content);
            } finally {
                out.close();
            }

            while (content.length() > 0) {
                content = parseArticles(content);
            }
            int max = -1;
            for (Integer temp : files) {
                if (temp > max) {
                    max = temp;
                }
            }
            Article.numFiles = max + 1;

            ArrayList<Integer> tempFiles = new ArrayList<Integer>();

            for (int f : files) {
                tempFiles.add(f);
            }

            for (int f : tempFiles) {
                addE(new Article(f));
            }
            System.out.println("Done.");
        } catch (FileNotFoundException ex) {
            System.out.println("Articles Failed: " + ex.getMessage());
        }
    }

    public static void loadIndicators() {
        System.out.print("Loading Indicators...\t");
        try {
            String content;
            File file = new File("Indicators.txt");
            Scanner fileIn = new Scanner(file);
            try {
                content = fileIn.nextLine();
            } finally {
                fileIn.close();
            }
            while (content.length() > 0) {
                content = parseIndicators(content);
            }
            System.out.println("Done.");
        } catch (FileNotFoundException ex) {
            System.out.println("Indicators Failed: " + ex.getMessage());
        }
    }

    public static void loadWords() {
        System.out.print("Loading Words...\t");
        try {
            File file = new File("10000_words.txt");
            Scanner fileIn = new Scanner(file);
            String content;
            try {
                content = fileIn.nextLine();
            } finally {
                fileIn.close();
            }
            while (content.length() > 0) {
                content = parseWords(content);
            }
            System.out.println("Done.");
        } catch (FileNotFoundException ex) {
            System.out.println("Words Failed: " + ex.getMessage());
        }
    }

    public static void loadRevWords() {
        System.out.print("Loading RevWords...\t");
        Gson g = new Gson();
        try {
            File f = new File("gson\\REVWORDS.txt");
            Scanner fileIn = new Scanner(f);
            Type alType = new TypeToken<ArrayList<String>>() {
            }.getType();
            RevWords = g.fromJson(fileIn.nextLine(), alType);
            System.out.println("Done.");
        } catch (FileNotFoundException ex) {
            System.out.println("RevWords Failed: " + ex.getMessage());
        }
    }

    public static HashMap<String, float[]> getGSONMap(String indicatorName) {
        if(RevWords.size() == 0)
        {
            loadRevWords();
        }
        System.out.print("Loading GSON Data...\t");
        Gson g = new Gson();
        HashMap<String, float[]> data = new HashMap<String, float[]>();
        File file;
        Scanner fileIn;
        for (String word : RevWords) {
            try {
                file = new File("gson\\" + indicatorName + "\\" + removeSpaces(word) + ".txt");
                fileIn = new Scanner(file);
                data.put(word, g.fromJson(fileIn.nextLine(), float[].class));
                //System.out.println("Success "+word);
            } catch (FileNotFoundException ex) {
                //System.out.println(ex.getMessage());
            }
        }
        System.out.println("Done.");
        return data;
    }

    public static float[] getGSONIndicator(String indicatorName) {
        System.out.print("Loading GSON Ind...\t");
        Gson g = new Gson();
        float[] returns = null;
        File file;
        Scanner fileIn;
        try {
            file = new File("gson\\" + indicatorName + "\\" + indicatorName + ".txt");
            fileIn = new Scanner(file);
            returns = g.fromJson(fileIn.nextLine(), float[].class);
            //System.out.println("Success "+word);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Done.");
        return returns;
    }
    public static float[] getGSONIndicatorPer(String indicatorName) {
        Gson g = new Gson();
        float[] returns = null;
        File file;
        Scanner fileIn;
        try {
            file = new File("gsonper\\" + indicatorName + "\\" + indicatorName + ".txt");
            fileIn = new Scanner(file);
            returns = g.fromJson(fileIn.nextLine(), float[].class);
            //System.out.println("Success "+word);
        } catch (FileNotFoundException ex) {
            //System.out.println(ex.getMessage());
        }
        return returns;
    }

    public static HashMap<String, float[]> getGSONPerMap(String indicatorName) {
        Gson g = new Gson();
        HashMap<String, float[]> data = new HashMap<String, float[]>();
        File file;
        Scanner fileIn;
        for (String word : RevWords) {
            try {
                file = new File("gsonper\\" + indicatorName + "\\" + removeSpaces(word) + ".txt");
                fileIn = new Scanner(file);
                data.put(word, g.fromJson(fileIn.nextLine(), float[].class));
                //System.out.println("Success "+word);
            } catch (FileNotFoundException ex) {
                //System.out.println(ex.getMessage());
            }
        }
        return data;
    }

    private static HashMap<String, Integer> getWordCountsMap() {
        HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
        for (String word : words) {
            wordCount.put(word, new Integer(0));
        }
        for (Integer d : dates) {
            for (Article a : articles.get(d)) {
                a.addWordCounts(wordCount);
            }
        }
        return wordCount;
    }

    public static void writeGSON() {
        Gson g = new Gson();
        Database.load();
        RevWords = new ArrayList<String>();

        HashMap<String, Integer> wordcounts = getWordCountsMap();
        for (int index = 0; index < words.size(); index++) {
            double count = wordcounts.get(words.get(index));
            //double count = Compare.sum(getCountOfWordGraph(words.get(index)));
            //System.out.println("index: " + index + " " + words.get(index) + " wordcount: " + count);
            if (count > 10) {
                RevWords.add(words.get(index));
            } else {
                System.out.println("Removed " + words.get(index) + " at index: " + index);
            }
        }//takes out all the NAN's
        File file = new File("gson\\REVWORDS.txt");
        PrintWriter out;
        try {
            out = new PrintWriter(file);
            Type alType = new TypeToken<ArrayList<String>>() {
            }.getType();
            out.println(g.toJson(RevWords, alType));
            out.close();
            System.out.println("Wrote RevWords");
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(StepTwoGSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("removed null words");

        HashMap<String, float[]> wordVectors = new HashMap<String, float[]>();
        for (Indicator indicator : indicators) {
            file = new File("gson\\" + indicator.getName());
            file.mkdirs();
            double[][] indicatorData = Database.getIndicatorGraph(indicator);
            try {
                File f = new File("gson\\" + indicator.getName() + "\\" + indicator.getName() + ".txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorVector(indicatorData)));
                } finally {
                    fout.close();
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Placeing Words in " + indicator.getName() + "'s HashMap...\t");
            //int index = 0;
            for (String word : RevWords) {
                wordVectors.put(word, Database.getWordVector(indicatorData, word));
                //System.out.println(index + " Inserting: " + word);
                //index++;
            }

            for (String word : RevWords) {
                //System.out.print("Writing " + word + " ...\t");
                try {
                    File f = new File("gson\\" + indicator.getName() + "\\" + removeSpaces(word) + ".txt");
                    PrintWriter fout = new PrintWriter(f);
                    try {
                        fout.print(g.toJson(wordVectors.get(word)));
                    } finally {
                        fout.close();
                    }
                    //System.out.println("Done.");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println(indicator.getName() + "'s arrays written.");
        }

        System.out.println("DONE.");
    }

    public static void writeGSONper() {
        Gson g = new Gson();
        Database.load();
        RevWords = new ArrayList<String>();

        HashMap<String, Integer> wordcounts = getWordCountsMap();
        for (int index = 0; index < words.size(); index++) {
            double count = wordcounts.get(words.get(index));
            //double count = Compare.sum(getCountOfWordGraph(words.get(index)));
            //System.out.println("index: " + index + " " + words.get(index) + " wordcount: " + count);
            if (count > 10) {
                RevWords.add(words.get(index));
            } else {
                System.out.println("Removed " + words.get(index) + " at index: " + index);
            }
        }//takes out all the NAN's
        File file = new File("gson\\REVWORDS.txt");
        PrintWriter out;
        try {
            out = new PrintWriter(file);
            Type alType = new TypeToken<ArrayList<String>>() {
            }.getType();
            out.println(g.toJson(RevWords, alType));
            out.close();
            System.out.println("Wrote RevWords");
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(StepTwoGSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("removed null words");

        HashMap<String, float[]> wordVectors = new HashMap<String, float[]>();
        for (Indicator indicator : indicators) {
            file = new File("gsonper\\" + indicator.getName());
            file.mkdirs();
            double[][] indicatorData = Database.getIndicatorGraph(indicator);
            try {
                File f = new File("gsonper\\" + indicator.getName() + "\\" + indicator.getName() + ".txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorVector(indicatorData)));
                } finally {
                    fout.close();
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Placeing Words in " + indicator.getName() + "'s HashMap...\t");
            //int index = 0;
            for (String word : RevWords) {
                wordVectors.put(word, Database.getWordPercentVector(indicatorData, word));
                //System.out.println(index + " Inserting: " + word);
                //index++;
            }

            for (String word : RevWords) {
                //System.out.print("Writing " + word + " ...\t");
                try {
                    File f = new File("gsonper\\" + indicator.getName() + "\\" + removeSpaces(word) + ".txt");
                    PrintWriter fout = new PrintWriter(f);
                    try {
                        fout.print(g.toJson(wordVectors.get(word)));
                    } finally {
                        fout.close();
                    }
                    //System.out.println("Done.");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println(indicator.getName() + "'s arrays written.");
        }

        System.out.println("DONE.");
    }

    public static String removeSpaces(String s) {
        return remove(s, " ");
    }

    private static String remove(String s, String r) {
        while (s.indexOf(r) != -1) {
            s = s.substring(0, s.indexOf(r)) + s.substring(s.indexOf(r) + r.length());
        }
        return s;
    }

    public static boolean updateIndicator(String name, double x, double y) throws FileNotFoundException {
        for (Indicator i : indicators) {
            if (i.getName().equals(name)) {
                i.addPoint(x, y);
                return true;
            }
        }
        return false;
    }

    private static String parseArticles(String content) {
        files.add(Integer.parseInt(content.substring(0, content.indexOf(','))));
        return content.substring(content.indexOf(',') + 1);
    }

    private static String parseIndicators(String content) throws FileNotFoundException {
        indicators.add(new Indicator(content.substring(0, content.indexOf(','))));
        return content.substring(content.indexOf(',') + 1);
    }

    private static String parseWords(String content) throws FileNotFoundException {
        words.add((content.substring(0, content.indexOf(','))));
        return content.substring(content.indexOf(',') + 1);
    }

    private static ArrayList<Article> getByDate(int date) {
        return articles.get(date);
    }

    public static int getCountOfWordByDate(String word, int date) {
        int count = 0;
        for (Article a : getByDate(date)) {
            count += a.getCount(word);

            //count += a.getCount(word)*10000/(a.getWordCount()+1);
            //returns word count adjusted for document length
        }
        return count;
    }

    public static double getPercentOfWordByDate(String word, int date) {
        double count = 0;
        int totalCount = 0;
        for (Article a : getByDate(date)) {
            count += a.getCount(word);
            totalCount += a.getWordCount();
            //count += a.getCount(word)*10000/(a.getWordCount()+1);
            //returns word count adjusted for document length
        }
        return (totalCount > 0) ? (count / totalCount) : 0.0;
    }

    public static int getWordCountByDate(int date) {
        int count = 0;
        for (Article a : getByDate(date)) {
            count += a.getWordCount();
            //count += a.getCount(word)*10000/(a.getWordCount()+1);
            //returns word count adjusted for document length
        }
        return count;
    }

    public static float[] getWordVector(double[][] indicator, String word) {
        //double[][] indicatorT = Compare.getIndicatorMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int)indicator[0][0]-1, (int)indicator[indicator.length - 1][0]+1));
        int[][] wordT = Compare.allignWord(indicator, getCountOfWordGraph(word));
        //float[] indicatorZ = Compare.convertToVectorZ(indicatorT);
        float[] wordZ = Compare.convertToVectorZ(wordT);
        return wordZ;
    }

    public static float[] getWordPercentVector(double[][] indicator, String word) {
        //double[][] indicatorT = Compare.getIndicatorMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int)indicator[0][0]-1, (int)indicator[indicator.length - 1][0]+1));
        double[][] wordT = Compare.allignIndicatorPercent(indicator, getPercentOfWordGraph(word));
        //float[] indicatorZ = Compare.convertToVectorZ(indicatorT);
        float[] wordZ = Compare.convertToVectorZ(wordT);
        return wordZ;
    }

    public static float[] getIndicatorVector(double[][] indicator) {
        double[][] indicatorT = Compare.allignIndicatorPercent(indicator, getPercentOfWordGraph("allignment"));
        //double[][] wordT = Compare.allignIndicatorPercent(indicator, getPercentOfWordGraph(word));
        float[] indicatorZ = Compare.convertToVectorZ(indicatorT);
        //float[] wordZ = Compare.convertToVectorZ(wordT);
        return indicatorZ;
    }

    public static float[] getScaledWordVector(double[][] indicator, String word, double scale) {
        float[] returns = getWordVector(indicator, word);
        for (int i = 0; i < returns.length; i++) {
            returns[i] = returns[i] * (float) scale;
        }
        return returns;
    }

    public static float[] getIndicatorVector(double[][] indicator, String word) {
        double[][] indicatorT = Compare.getIndicatorMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int) indicator[0][0] - 1, (int) indicator[indicator.length - 1][0] + 1));
        //int[][] wordT = Compare.getWordMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int)indicator[0][0]-1, (int)indicator[indicator.length - 1][0]+1));
        float[] indicatorZ = Compare.convertToVectorZ(indicatorT);
        //float[] wordZ = Compare.convertToVectorZ(wordT);
        return indicatorZ;
    }

    public static void ArticleFrequency() {
        ArrayList<Integer> datesInRange = new ArrayList<Integer>();
        for (int date : dates) {
            if (date >= Integer.MIN_VALUE && date <= Integer.MAX_VALUE) {
                datesInRange.add(date);
            }
        }
        int ticker = 1;
        for (int index = 1; index < datesInRange.size(); index++) {
            if ((datesInRange.get(index) / 10000) == (datesInRange.get(index - 1) / 10000)) {
                ticker += articles.get(datesInRange.get(index)).size();
            } else {
                System.out.print(datesInRange.get(index - 1) + ": ");
                for (int l = 0; l < ticker; l++) {
                    System.out.print("l");
                }
                System.out.println("");
                ticker = 1;
            }
        }
    }

    public static double[][] getPercentOfWordGraph(String word) {
        return getPercentOfWordGraph(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int[][] getCountOfWordGraph(String word) {
        return getCountOfWordGraph(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static double[][] getPercentOfWordGraph(String word, int start, int end) {
        ArrayList<Integer> datesInRange = new ArrayList<Integer>();
        for (int date : dates) {
            if (date >= start && date <= end) {
                datesInRange.add(date);
            }
        }
        double[][] graphPoints = new double[datesInRange.size()][3];
        for (int index = 0; index < datesInRange.size(); index++) {
            graphPoints[index][0] = datesInRange.get(index);
            graphPoints[index][1] = getPercentOfWordByDate(word, datesInRange.get(index));
            graphPoints[index][2] = getWordCountByDate(datesInRange.get(index));
        }
        return graphPoints;
    }

    public static int[][] getCountOfWordGraph(String word, int start, int end) {
        ArrayList<Integer> datesInRange = new ArrayList<Integer>();
        for (int date : dates) {
            if (date >= start && date <= end) {
                datesInRange.add(date);
            }
        }
        int[][] graphPoints = new int[datesInRange.size()][2];
        for (int index = 0; index < datesInRange.size(); index++) {
            graphPoints[index][0] = datesInRange.get(index);
            graphPoints[index][1] = getCountOfWordByDate(word, datesInRange.get(index));
        }
        return graphPoints;
    }

    public static double[][] getIndicatorGraph(String name) {
        Indicator i = null;
        for (Indicator temp : indicators) {
            if (temp.getName().equals(name)) {
                i = temp;
            }
        }
        if (i == null) {
            return null;
        }
        return i.getGraphData();
    }

    public static double[][] getIndicatorGraph(String name, int start, int end) {
        Indicator i = null;
        for (Indicator temp : indicators) {
            if (temp.getName().equals(name)) {
                i = temp;
            }
        }
        if (i == null) {
            return null;
        }
        double[][] full = i.getGraphData();
        int begin = 0;
        while (begin < full.length && full[begin][0] < start) {
            begin++;
        }
        int stop = full.length - 1;
        while (stop >= begin && full[stop][0] > end) {
            stop--;
        }
        if (stop <= begin) {
            return null;
        }

        double[][] filtered = new double[stop - begin + 1][2];
        for (int a = begin; a <= stop; a++) {
            filtered[a - begin][0] = full[a][0];
            filtered[a - begin][1] = full[a][1];
        }
        return filtered;
    }

    public static double[][] getIndicatorGraph(Indicator i) {
        return getIndicatorGraph(i.getName());
    }

    public static int[][] combineCounts(ArrayList<int[][]> wordcounts) {
        int[][] combined = new int[wordcounts.get(0).length][2];
        for (int index = 0; index < wordcounts.get(0).length; index++) {
            for (int[][] count : wordcounts) {
                combined[index][1] += count[index][1];
            }

            combined[index][0] = wordcounts.get(0)[index][0];
        }

        //for(int [] point :combined)
        //    System.out.println(point [1]);

        return combined;
    }

    public static int[][] subtractCounts(int[][] Pwords, int[][] Nwords) {
        int[][] combined = new int[Pwords.length][2];
        for (int index = 0; index < Pwords.length; index++) {

            combined[index][1] = Pwords[index][1] - Nwords[index][1];
            combined[index][0] = Pwords[index][0];
        }

        //for(int [] point :combined)
        //    System.out.println(point [1]);

        return combined;
    }
}