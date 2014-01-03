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
    public static double covariance(double[][] indicator, double[][] word)
    {
        double xMean = mean(indicator);
        double yMean = mean(word, indicator.length);
        double coV = 0;
        for (int a = 0; a < indicator.length; a++)
        {
            double[] indicatorPoint = indicator[a];
            double timeOut = (a != indicator.length - 1) ? indicator[a+1][0] : Double.MAX_VALUE;
            coV += (indicatorPoint[1]-xMean)*(sum(word, indicatorPoint[0], timeOut)-yMean);
        }
        coV /= indicator.length;
        return coV;
    }
    private static double mean(double[][] data)
    {
        double sum = 0, count = 0;
        for (double[] point : data)
        {
            sum += point[1];
            count++;
        }
        return sum/count;
    }
    private static double mean(double[][] data, double count)
    {
        double sum = 0;
        for (double[] point : data)
        {
            sum += point[1];
            count++;
        }
        return sum/count;
    }
    private static double sum(double[][] word, double timeIn, double timeOut)
    {
        double sum = 0;
        for (double[] wordCount : word)
        {
            if(wordCount[0] >= timeIn && wordCount[0] < timeOut)
            {
                sum += wordCount[1];
            }
        }
        return sum;
    }
    
}