package twitterstocks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

class Database {

    public static HashMap<Integer, ArrayList<Article>> articles = new HashMap<Integer, ArrayList<Article>>();
    public static ArrayList<Integer> files = new ArrayList<Integer>();
    public static ArrayList<Integer> dates = new ArrayList<Integer>();
    public static ArrayList<String> directories = new ArrayList<String>();
    public static HashMap<String, HashMap<Integer, ArrayList<Article>>> directoriesArticles = new HashMap<String, HashMap<Integer, ArrayList<Article>>>();
    public static HashMap<String, ArrayList<Integer>> directoriesDates = new HashMap<String, ArrayList<Integer>>();
    public static ArrayList<Indicator> indicators = new ArrayList<Indicator>();
    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<String> RevWords = new ArrayList<String>();

    public static void add(Article a) throws FileNotFoundException {
        String fileName = a.getFileName();
        if (fileName.substring(0, fileName.indexOf("Article")).equals("articles\\")) {
            addNoDirectory(a);
        } else {
            add(a, fileName.substring(0, fileName.indexOf("\\")));
        }

    }

    private static void add(Article a, String directory) throws FileNotFoundException {
        if (directoriesArticles.get(directory) == null) {
            directories.add(directory);
            directoriesArticles.put(directory, new HashMap<Integer, ArrayList<Article>>());
        }
        if (directoriesArticles.get(directory).get(a.getDate()) == null) {
            directoriesArticles.get(directory).put(a.getDate(), new ArrayList<Article>());
            if (directoriesDates.get(directory) == null) {
                directoriesDates.put(directory, new ArrayList<Integer>());
            }
            directoriesDates.get(directory).add(a.getDate());
        }
        directoriesArticles.get(directory).get(a.getDate()).add(a);

        Collections.sort(directoriesDates.get(directory));

    }

    private static void addNoDirectory(Article a) throws FileNotFoundException {
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

    public static ZVector getTotalWordCountAlligned(double[][] indicatorData) {
        ArrayList<int[]> wordCounts = new ArrayList<int[]>();
        for (int date : dates) {
            int[] point = new int[2];
            point[0] = date;
            point[1] = getWordCountByDate(date);
            wordCounts.add(point);
        }
        int[][] wordData = new int[wordCounts.size()][2];
        for (int i = 0; i < wordData.length; i++) {
            wordData[i][0] = wordCounts.get(i)[0];
            wordData[i][1] = wordCounts.get(i)[1];
        }
        int[][] allignedCount = Compare.allignWord(indicatorData, wordData);
        return new ZVector(allignedCount);

    }

    public static ZVector getTotalWordCountAlligned(double[][] indicatorData, String dir) {
        ArrayList<int[]> wordCounts = new ArrayList<int[]>();
        for (int date : dates) {
            int[] point = {date, getWordCountByDate(date, dir)};
            wordCounts.add(point);
        }
        int[][] wordData = new int[wordCounts.size()][2];
        for (int i = 0; i < wordData.length; i++) {
            wordData[i] = wordCounts.get(i);
        }
        int[][] allignedCount = Compare.allignWord(indicatorData, wordData);
        return new ZVector(allignedCount);

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
            // Begin new Loading Process
            File articlesFolder = new File("articles");

            File[] dirs = articlesFolder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return !(file.getName().contains("Article"));
                }
            });
            for (File dir : dirs) {
                if (!directories.contains(dir.getName())) {
                    directories.add(dir.getName());
                }
                if (directoriesArticles.get(dir.getName()) == null) {
                    directoriesArticles.put(dir.getName(), new HashMap<Integer, ArrayList<Article>>());
                }
                if (directoriesDates.get(dir.getName()) == null) {
                    directoriesDates.put(dir.getName(), new ArrayList<Integer>());
                }
                if (Article.numFilesMap.get(dir.getName()) == null) {
                    Article.numFilesMap.put(dir.getName(), 0);
                }
                for (File art : dir.listFiles()) {
                    int articleNum = Integer.parseInt(art.getName().substring(art.getName().indexOf("e") + 1, art.getName().length() - 4));
                    if (articleNum >= Article.numFilesMap.get(dir.getName())) {
                        Article.numFilesMap.put(dir.getName(), articleNum + 1);
                    }
                    Article a = new Article(articleNum, dir.getName());
                    if (directoriesArticles.get(dir.getName()).get(a.getDate()) == null) {
                        directoriesArticles.get(dir.getName()).put(a.getDate(), new ArrayList<Article>());
                    }
                    directoriesArticles.get(dir.getName()).get(a.getDate()).add(a);
                    if (!directoriesDates.get(dir.getName()).contains(a.getDate())) {
                        directoriesDates.get(dir.getName()).add(a.getDate());
                    }
                }

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

    public static HashMap<String, ZVector> getGSONMap(String indicatorName) {
        if (RevWords.size() == 0) {
            loadRevWords();
        }
        System.out.print("Loading GSON Data...\t");
        Gson g = new Gson();
        HashMap<String, ZVector> data = new HashMap<String, ZVector>();
        File file;
        Scanner fileIn;
        try {
            file = new File("gson\\" + indicatorName + "\\IDATES.txt");
            fileIn = new Scanner(file);
            data.put("IDATES", g.fromJson(fileIn.nextLine(), ZVector.class));

            //System.out.println("Success "+word);
        } catch (Exception ex) {
            System.out.print("dates failed,\t");
        }

        for (String word : RevWords) {
            try {
                file = new File("gson\\" + indicatorName + "\\" + removeSpaces(word) + ".txt");
                fileIn = new Scanner(file);
                data.put(word, g.fromJson(fileIn.nextLine(), ZVector.class));

                //System.out.println("Success "+word);
            } catch (Exception ex) {
                //System.out.println(ex.getMessage());
            }
        }
        System.out.println("Done.");
        return data;
    }

    public static ZVector getGSONIndicator(String indicatorName) {
        System.out.print("Loading GSON Ind...\t");
        Gson g = new Gson();
        ZVector returns = null;
        File file;
        Scanner fileIn;
        try {
            file = new File("gson\\" + indicatorName + "\\" + indicatorName + ".txt");
            fileIn = new Scanner(file);
            returns = g.fromJson(fileIn.nextLine(), ZVector.class);
            //System.out.println("Success "+word);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Done.");
        return returns;
    }

    public static HashMap<String, ZVector> getGSONPerMap(String indicatorName) {
        if (RevWords.size() == 0) {
            loadRevWords();
        }
        System.out.print("Loading GSON Data...\t");
        Gson g = new Gson();
        HashMap<String, ZVector> data = new HashMap<String, ZVector>();
        File file;
        Scanner fileIn;
        try {
            file = new File("gson\\" + indicatorName + "\\IDATES.txt");
            fileIn = new Scanner(file);
            data.put("IDATES", g.fromJson(fileIn.nextLine(), ZVector.class));
            //System.out.println("Success "+word);
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
        try {
            file = new File("gson\\" + indicatorName + "\\TWORDS.txt");
            fileIn = new Scanner(file);
            data.put("TWORDS", g.fromJson(fileIn.nextLine(), ZVector.class));
            //System.out.println("Success "+word);
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
        for (String word : RevWords) {
            try {
                file = new File("gson\\" + indicatorName + "\\" + removeSpaces(word) + ".txt");
                fileIn = new Scanner(file);
                ZVector value = g.fromJson(fileIn.nextLine(), ZVector.class).divideBy(data.get("TWORDS"));
                data.put(word, value);
                //System.out.println("Success "+word);
            } catch (Exception ex) {
                //System.out.println(ex.getMessage());
            }
        }
        System.out.println("Done.");
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
        for (String dir : directories) {
            for (Integer d : directoriesDates.get(dir)) {
                for (Article a : directoriesArticles.get(dir).get(d)) {
                    a.addWordCounts(wordCount);
                }
            }
        }
        return wordCount;
    }

    private static HashMap<String, Integer> getWordCountsMap(String dir) {
        HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
        for (String word : words) {
            wordCount.put(word, new Integer(0));
        }
        for (Integer d : directoriesDates.get(dir)) {
            for (Article a : directoriesArticles.get(dir).get(d)) {
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
                //System.out.println("Removed " + words.get(index) + " at index: " + index);
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

        HashMap<String, ZVector> wordVectors = new HashMap<String, ZVector>();
        for (Indicator indicator : indicators) {
            file = new File("gson\\" + indicator.getName());
            file.mkdirs();
            file.mkdir();
            double[][] indicatorData = Database.getIndicatorGraph(indicator);
            try {
                File f = new File("gson\\" + indicator.getName() + "\\" + indicator.getName() + ".txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorVector(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote Indicator");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                File f = new File("gson\\" + indicator.getName() + "\\IDATES.txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorDatesVector(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote IDATES");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                File f = new File("gson\\" + indicator.getName() + "\\TWORDS.txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getTotalWordCountAlligned(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote Total Words");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Placeing Words in " + indicator.getName() + "'s HashMap...\t");
            int index = 0;
            double prevPercent = 0, percent;
            for (String word : RevWords) {
                percent = (double) index * 10 / RevWords.size();
                if (Math.floor(prevPercent) != Math.floor(percent)) {
                    System.out.print(".");
                }
                wordVectors.put(word, Database.getWordVector(indicatorData, word));
                //System.out.println(index + " Inserting: " + word);
                index++;
                prevPercent = percent;
            }
            System.out.println("Writing from HashMap");
            index = 0;
            prevPercent = 0;
            for (String word : RevWords) {
                percent = (double) index * 10 / RevWords.size();
                if (Math.floor(percent) != Math.floor(prevPercent)) {
                    System.out.print(".");
                }
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
                index++;
                prevPercent = percent;
            }
            System.out.println(indicator.getName() + "'s arrays written.");
        }

        System.out.println("DONE.");
    }
    public static void writeGSON(Indicator indicator) {
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
                //System.out.println("Removed " + words.get(index) + " at index: " + index);
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

        HashMap<String, ZVector> wordVectors = new HashMap<String, ZVector>();
            file = new File("gson\\" + indicator.getName());
            file.mkdirs();
            file.mkdir();
            double[][] indicatorData = Database.getIndicatorGraph(indicator);
            try {
                File f = new File("gson\\" + indicator.getName() + "\\" + indicator.getName() + ".txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorVector(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote Indicator");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                File f = new File("gson\\" + indicator.getName() + "\\IDATES.txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorDatesVector(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote IDATES");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                File f = new File("gson\\" + indicator.getName() + "\\TWORDS.txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getTotalWordCountAlligned(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote Total Words");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Placeing Words in " + indicator.getName() + "'s HashMap...\t");
            int index = 0;
            double prevPercent = 0, percent;
            for (String word : RevWords) {
                percent = (double) index * 10 / RevWords.size();
                if (Math.floor(prevPercent) != Math.floor(percent)) {
                    System.out.print(".");
                }
                wordVectors.put(word, Database.getWordVector(indicatorData, word));
                //System.out.println(index + " Inserting: " + word);
                index++;
                prevPercent = percent;
            }
            System.out.println("Writing from HashMap");
            index = 0;
            prevPercent = 0;
            for (String word : RevWords) {
                percent = (double) index * 10 / RevWords.size();
                if (Math.floor(percent) != Math.floor(prevPercent)) {
                    System.out.print(".");
                }
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
                index++;
                prevPercent = percent;
            }
            System.out.println(indicator.getName() + "'s arrays written.");

        System.out.println("DONE.");
    }

    public static void writeGSON(String dir) {
        Gson g = new Gson();
        Database.load();
        RevWords = new ArrayList<String>();

        HashMap<String, Integer> wordcounts = getWordCountsMap(dir);
        for (int index = 0; index < words.size(); index++) {
            double count = wordcounts.get(words.get(index));
            //double count = Compare.sum(getCountOfWordGraph(words.get(index)));
            //System.out.println("index: " + index + " " + words.get(index) + " wordcount: " + count);
            if (count > 10) {
                RevWords.add(words.get(index));
            } else {
                //System.out.println("Removed " + words.get(index) + " at index: " + index);
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

        HashMap<String, ZVector> wordVectors = new HashMap<String, ZVector>();
        for (Indicator indicator : indicators) {
            file = new File("gson\\" + indicator.getName());
            file.mkdirs();
            file.mkdir();
            double[][] indicatorData = Database.getIndicatorGraph(indicator);
            try {
                File f = new File("gson\\" + indicator.getName() + "\\" + indicator.getName() + ".txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorVector(indicatorData, dir)));
                } finally {
                    fout.close();
                    System.out.println("Wrote Indicator");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                File f = new File("gson\\" + indicator.getName() + "\\IDATES.txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getIndicatorDatesVector(indicatorData)));
                } finally {
                    fout.close();
                    System.out.println("Wrote IDATES");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                File f = new File("gson\\" + indicator.getName() + "\\TWORDS.txt");
                PrintWriter fout = new PrintWriter(f);
                try {
                    fout.print(g.toJson(getTotalWordCountAlligned(indicatorData, dir)));
                } finally {
                    fout.close();
                    System.out.println("Wrote TWORDS");
                }
                //System.out.println("Done.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Placeing Words in " + indicator.getName() + "'s HashMap...\t");
            //int index = 0;
            for (String word : RevWords) {
                wordVectors.put(word, Database.getWordVector(indicatorData, word, dir));
                //System.out.println(index + " Inserting: " + word);
                //index++;
            }
            System.out.println("Writing from HashMap.");
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
        ArrayList<Article> returns = articles.get(date);
        for (String dir : directories) {
            returns = merge(returns, directoriesArticles.get(dir).get(date));
        }
        return returns;
    }

    private static ArrayList<Article> merge(ArrayList<Article> listA, ArrayList<Article> listB) {
        ArrayList<Article> returns = new ArrayList<Article>();
        if (!(listA == null)) {
            for (Article a : listA) {
                returns.add(a);
            }
        }
        if (!(listB == null)) {
            for (Article a : listB) {
                returns.add(a);
            }
        }
        return returns;
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

    public static int getCountOfWordByDate(String word, int date, String dir) {
        int count = 0;
        for (Article a : directoriesArticles.get(dir).get(date)) {
            count += a.getCount(word);

            //count += a.getCount(word)*10000/(a.getWordCount()+1);
            //returns word count adjusted for document length
        }
        return count;
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

    public static int getWordCountByDate(int date, String dir) {
        int count = 0;
        for (Article a : directoriesArticles.get(dir).get(date)) {
            count += a.getWordCount();
            //count += a.getCount(word)*10000/(a.getWordCount()+1);
            //returns word count adjusted for document length
        }
        return count;
    }

    public static ZVector getWordVector(double[][] indicator, String word) {
        //double[][] indicatorT = Compare.getIndicatorMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int)indicator[0][0]-1, (int)indicator[indicator.length - 1][0]+1));
        int[][] wordT = Compare.allignWord(indicator, getCountOfWordGraph(word));
        //float[] indicatorZ = Compare.convertToVectorZ(indicatorT);
        ZVector wordZ = new ZVector(wordT);
        return wordZ;
    }

    public static ZVector getWordVector(double[][] indicator, String word, String dir) {
        //double[][] indicatorT = Compare.getIndicatorMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int)indicator[0][0]-1, (int)indicator[indicator.length - 1][0]+1));
        int[][] wordT = Compare.allignWord(indicator, getCountOfWordGraph(word, dir));
        //float[] indicatorZ = Compare.convertToVectorZ(indicatorT);
        ZVector wordZ = new ZVector(wordT);
        return wordZ;
    }

    public static ZVector getIndicatorVector(double[][] indicator) {
        double[][] indicatorT = Compare.allignIndicator(indicator, getCountOfWordGraph("allignment"));
        //double[][] wordT = Compare.allignIndicatorPercent(indicator, getPercentOfWordGraph(word));
        ZVector indicatorZ = new ZVector(indicatorT);
        //float[] wordZ = Compare.convertToVectorZ(wordT);
        return indicatorZ;
    }

    public static ZVector getIndicatorDatesVector(double[][] indicator) {
        double[][] indicatorT = Compare.allignIndicator(indicator, getCountOfWordGraph("allignment"));
        //double[][] wordT = Compare.allignIndicatorPercent(indicator, getPercentOfWordGraph(word));
        float[] indicatorZ = Compare.convertToDates(indicatorT);
        //float[] wordZ = Compare.convertToVectorZ(wordT);
        return new ZVector(indicatorZ);
    }

    public static ZVector getIndicatorVector(double[][] indicator, String dir) {
        double[][] indicatorT = Compare.allignIndicator(indicator, getCountOfWordGraph("allign", (int) indicator[0][0] - 1, (int) indicator[indicator.length - 1][0] + 1, dir));
        //int[][] wordT = Compare.getWordMatchIndicatorWord(indicator, getCountOfWordGraph(word, (int)indicator[0][0]-1, (int)indicator[indicator.length - 1][0]+1));
        ZVector indicatorZ = new ZVector(indicatorT);
        //float[] wordZ = Compare.convertToVectorZ(wordT);
        return indicatorZ;
    }

    public static void ArticleFrequency() {
        ArrayList<Integer> datesInRange = new ArrayList<Integer>();
        for (int date : combinedDates()) {
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

    public static int[][] getCountOfWordGraph(String word) {
        return getCountOfWordGraph(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int[][] getCountOfWordGraph(String word, String dir) {
        return getCountOfWordGraph(word, Integer.MIN_VALUE, Integer.MAX_VALUE, dir);
    }

    public static int[][] getCountOfWordGraph(String word, int start, int end) {
        ArrayList<Integer> datesInRange = new ArrayList<Integer>();
        for (int date : combinedDates()) {
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

    public static int[][] getCountOfWordGraph(String word, int start, int end, String dir) {
        ArrayList<Integer> datesInRange = new ArrayList<Integer>();
        for (int date : directoriesDates.get(dir)) {
            if (date >= start && date <= end) {
                datesInRange.add(date);
            }
        }
        int[][] graphPoints = new int[datesInRange.size()][2];
        for (int index = 0; index < datesInRange.size(); index++) {
            graphPoints[index][0] = datesInRange.get(index);
            graphPoints[index][1] = getCountOfWordByDate(word, datesInRange.get(index), dir);
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

    public static ArrayList<Integer> combinedDates() {
        ArrayList<Integer> combined = new ArrayList<Integer>();
        for (int date : dates) {
            combined.add(date);
        }
        for (String dir : directories) {
            for (Integer dirDate : directoriesDates.get(dir)) {
                if (!combined.contains(dirDate)) {
                    combined.add(dirDate);
                }
            }
        }
        Collections.sort(combined);
        return combined;

    }
}