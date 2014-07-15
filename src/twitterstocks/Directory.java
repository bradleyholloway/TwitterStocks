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
/**
 *
 * @author Roberto   <-- heckyeah
 */
public class Directory {
    
    
     static HashMap<Integer, ArrayList<Article>> articles = new HashMap<Integer, ArrayList<Article>>();
     static ArrayList<Integer> dates = new ArrayList<Integer>();
     
     public Directory ()
     {
         
     }
     
     public void addArticle(Article a)
     {
         if(articles.get(a.getDate())==null){
             articles.put(a.getDate(), new ArrayList<Article>());
         }
         
         if (!dates.contains(a.getDate()))
         {
             dates.add(a.getDate());
         }
         
         articles.get(a.getDate()).add(a);     
     }
     
     public void addWordCounts(HashMap<String, Integer> map)
     {
         for(Integer d : dates)
         {
             for(Article a : articles.get(d))
             {
                 a.addWordCounts(map);
             }
         }
     }
     
     public ArrayList<Article> getArticlesByDate(int date)
     {
         return articles.get(date);
     }
     
     public ArrayList<Integer> getDates()
     {
         return dates;
     }
     
     public void addArticle()
     {
         
     }
    
     public Article getArticle()
     {
         return null;
     }
}
