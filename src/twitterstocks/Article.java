package twitterstocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class Article {

    private String content;
    private String name;
    private String fileName;
    private int date;
    public static int numFiles = 0;

    public Article(String content, int date) throws FileNotFoundException {
        this.content = replace(content, '\n', " ");
        this.fileName = "articles\\Article" + numFiles;
        this.name = this.fileName;
        this.date = date;
        writeToFile();
        numFiles++;
    }

    public Article(int fileNum) throws FileNotFoundException {
        readFromFile("articles\\Article"+fileNum);
        this.fileName = "articles\\Article"+fileNum;
        this.name = this.fileName;
        
    }
    
    public int getCount(String word)
    {
        int count = 0;
        String temp = this.content;
        while(temp.indexOf(word)!=-1) {
            count++;
            temp = temp.substring(0,temp.indexOf(word)) + temp.substring(temp.indexOf(word) + word.length());
        }
        
        return count;
    }
    
    public int getWordCount()
    {
        String space = " ";
        int count = 0;
        String temp = this.content;
        while(temp.indexOf(space)!=-1) {
            count++;
            temp = temp.substring(0,temp.indexOf(space)) + temp.substring(temp.indexOf(space) + space.length());
        }
        
        return count;
    }
    
    public int getNum()
    {
        return Integer.parseInt(fileName.substring(16));
    }
    public int getDate()
    {
        return date;
    }

    private void readFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName+".txt");
        Scanner fileIn = new Scanner(file);
        try {
            String d = fileIn.nextLine();
            this.date = Integer.parseInt(d);
            while (fileIn.hasNextLine()) {
                this.content = fileIn.nextLine();
            }
        }
        finally {
            fileIn.close();
        }
    }

    private void writeToFile() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName + ".txt");
        try {
            out.println(date);
            out.println(content);
        }
        finally { out.close();}
    }
    
    private String replace(String original, char find, String replacement)
    {
        String temp = original;
        while(temp.indexOf(find)!=-1) {
            temp = temp.substring(0,temp.indexOf(find)) + replacement + temp.substring(temp.indexOf(find)+1);
        }
        
        return temp;
    }
    
    @Override
    public String toString()
    {
        return getNum()+"";
    }
}