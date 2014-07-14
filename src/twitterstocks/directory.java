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
public class directory {
    
    
     public static HashMap<Integer, ArrayList<Article>> articles = new HashMap<Integer, ArrayList<Article>>();
     public static HashMap<String, ArrayList<Integer>> directoriesDates = new HashMap<String, ArrayList<Integer>>();
    
}
