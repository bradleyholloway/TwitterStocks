package twitterstocks;

import java.util.ArrayList;

public class PriceLog 
{
    private ArrayList<Double> prices;
    private int index;
    
    public PriceLog()
    {
        index = -1;
        prices = new ArrayList<>();
    }
    
    public void put(double price)
    {
        index++;
        prices.add(price);
    }
    
    public double getPrice()
    {
        if(index != -1)
        {
            return prices.get(index);
        }
        return 0.0;
    }
    
    public double getDelta()
    {
        if(index >= 1)
        {
            return (prices.get(index) - prices.get(index - 1));
        }
        return 0.0;
    }
    
    public String toString()
    {
        if(index>= 1)
        {
            if(getDelta() >= 0)
            {
                return "$" + getPrice() + " +" + getDelta() + " "; 
            }
            return "$" + getPrice() + " " + getDelta() + " ";
        }
        return "$" + getPrice() + " ";
    }
}
