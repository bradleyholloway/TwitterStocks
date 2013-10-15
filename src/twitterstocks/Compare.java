package twitterstocks;

class Compare {
    
    public static double squaredSumError(double[] data1, double[] data2)
    {
        double r = 0;
        for(int a = 0; a < data1.length && a < data2.length; a++)
        {
            r += ( data1[a]-data2[a] ) * ( data1[a]-data2[a] );
        }
        
        return r;
    }
    
}