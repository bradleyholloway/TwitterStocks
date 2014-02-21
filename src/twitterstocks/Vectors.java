package twitterstocks;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bradley Holloway
 */
public class Vectors {
    public static ZVector getVectorCombined(HashMap<String, ZVector> map, ArrayList<String> keys, ZVector goal, double epsilon, WordWeightTable wt)
    {
        float[] combined = new float[goal.zData.length];
        ArrayList<String> keysInEpsilon = new ArrayList<String>();
        for (String key : keys)
        {
            double angle = Math.acos(Math.abs(Compare.dotProduct(goal.getZData(), map.get(key).getZData())));
            if(angle < epsilon)
            {
                keysInEpsilon.add(key);
            }
        }
        for (String key : keysInEpsilon)
        {
            //vectory pairing within keys in epsilon to goal using even weight???
        }
        
        return new ZVector(combined);
    }
}
