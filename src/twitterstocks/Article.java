package twitterstocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

class Article {

    private String content;
    private String fileName;
    private int date;
    public static int numFiles = 0;
    public static HashMap<String, Integer> numFilesMap = new HashMap<String, Integer>();

    public Article(String content, int date) throws FileNotFoundException {
        this.content = replace(content, '\n', " ");
        this.fileName = "articles\\Article" + numFiles;
        this.date = date;
        writeToFile();
        numFiles++;
    }
    public Article(String content, int date, String directory) throws FileNotFoundException {
        if(!numFilesMap.containsKey(directory))
        {
            numFilesMap.put(directory,0);
        }
        
        this.content = replace(content, '\n', " ");
        this.fileName = "articles\\"+directory + "\\Article" + numFilesMap.get(directory);
        this.date = date;
        writeToFile();
        numFilesMap.put(directory, numFilesMap.get(directory)+1);
    }
    public Article(int fileNum, String directory) throws FileNotFoundException
    {
        readFromFile("articles\\"+directory + "\\Article" + fileNum);
        this.fileName = "articles\\"+directory + "\\Article" + fileNum;
    }

    public Article(int fileNum) throws FileNotFoundException {
        readFromFile("articles\\Article" + fileNum);
        this.fileName = "articles\\Article" + fileNum;

    }

    public int getCount(String word) {
        int count = 0;
        String temp = this.content.toLowerCase();
        word = word.toLowerCase();
        while (temp.indexOf(word) != -1) {
            count++;
            temp = temp.substring(0, temp.indexOf(word)) + temp.substring(temp.indexOf(word) + word.length());
        }

        return count;
    }

    public int getWordCount() {
        String space = " ";
        int count = 0;
        String temp = this.content;
        while (temp.indexOf(space) != -1) {
            count++;
            temp = temp.substring(0, temp.indexOf(space)) + temp.substring(temp.indexOf(space) + space.length());
        }

        return count;
    }

    public int getNum() {
        return Integer.parseInt(fileName.substring(16));
    }

    public int getDate() {
        return date;
    }

    private void readFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName + ".txt");
        Scanner fileIn = new Scanner(file);
        try {
            String d = fileIn.nextLine();
            this.date = Integer.parseInt(d);
            while (fileIn.hasNextLine()) {
                this.content = fileIn.nextLine();
            }
        } finally {
            fileIn.close();
        }
    }

    private void writeToFile() throws FileNotFoundException {
        File f = new File(fileName+".txt");
        f.getParentFile().mkdirs();
        PrintWriter out = new PrintWriter(fileName + ".txt");
        try {
            out.println(date);
            out.println(content);
        } finally {
            out.close();
        }
    }

    public void addWordCounts(HashMap<String, Integer> map) {
        String space = " ";
        
        String temp = this.content;
        while (temp.indexOf(space) != -1) {
            //System.out.println(" " + temp.substring(0, temp.indexOf(space)).toLowerCase()+" ");
            if (map.get(" " + temp.substring(0, temp.indexOf(space)).toLowerCase()+" ") != null) {
                map.put(" " + temp.substring(0, temp.indexOf(space)).toLowerCase()+" ", map.get(" " + temp.substring(0, temp.indexOf(space)).toLowerCase()+" ") + 1);
            }
            temp = temp.substring(temp.indexOf(space) + space.length());
        }
    }
    public double getPercentUse(String word)
    {
        return (double)getCount(word)/getWordCount();
    }

    private String replace(String original, char find, String replacement) {
        String temp = original;
        while (temp.indexOf(find) != -1) {
            temp = temp.substring(0, temp.indexOf(find)) + replacement + temp.substring(temp.indexOf(find) + 1);
        }

        return temp;
    }
    public String getFileName()
    {
        return fileName;
    }

    @Override
    public String toString() {
        return getNum() + "";
    }
}