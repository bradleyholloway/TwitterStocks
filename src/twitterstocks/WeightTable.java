package twitterstocks;

import java.util.HashMap;

public class WeightTable {
    private HashMap<String, Double> data;
    
    public WeightTable()
    {
        data = new HashMap<String, Double>();
    }
    
    public void add(String word)
    {
        data.put(word, 0.0);
    }
    
    public void update(String word, double delta)
    {
        data.put(word, data.get(word) + delta);
    }
    
    public double get(String word)
    {
        return data.get(word);
    }
}
