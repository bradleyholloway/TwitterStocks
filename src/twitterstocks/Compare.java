package twitterstocks;

class Compare {

    public static double squaredSumError(double[] data1, double[] data2) {
        double r = 0;
        for (int a = 0; a < data1.length && a < data2.length; a++) {
            r += (data1[a] - data2[a]) * (data1[a] - data2[a]);
        }

        return r;
    }

    public static float[] convertToVectorZ(double[][] data) {
        float[] returns = new float[data.length];
        double mean = mean(data);
        double stDev = standardDev(data);
        for (int a = 0; a < data.length; a++) {
            returns[a] = (float) ((data[a][1] - mean) / stDev);
        }
        return returns;
    }
    public static float[] convertToDates(double[][] data) {
        float[] returns = new float[data.length];
        for (int a = 0; a < data.length; a++) {
            returns[a] = (float)data[a][0];
        }
        return returns;
    }

    public static float[] convertToVectorZ(int[][] data) {
        float[] returns = new float[data.length];
        double mean = mean(data);
        double stDev = standardDev(data);
        for (int a = 0; a < data.length; a++) {
            returns[a] = (float) ((data[a][1] - mean) / stDev);
        }
        return returns;
    }

    public static double[][] getResid(double[][] indicator, double[][] word) {
        double[][] difference = new double[indicator.length][2];
        for (int i = 0; i < indicator.length; i++) {
            difference[i][0] = indicator[i][0];
            difference[i][1] = indicator[i][1] - word[i][1];
        }
        return difference;
    }

    public static double[][] getResid(double[][] indicator, int[][] word) {
        double[][] difference = new double[indicator.length][2];
        for (int i = 0; i < indicator.length; i++) {
            difference[i][0] = indicator[i][0];
            difference[i][1] = indicator[i][1] - word[i][1];
        }
        return difference;
    }

    public static double covariance(double[][] indicator, int[][] word) {
        /*//int indicatorerror = 31;//used for testing Constructiondx
         //indicatorerror = 298;//- used for testing GDPdx
         int init=0;
         while (indicator [init][0]+indicatorerror< word [0][0])
         {
         init++;
         }
        
         int end = indicator.length-1;
         while (indicator [end][0]+indicatorerror> word[word.length-1][0])
         {
         end--;
         }
         */
        double[][] adjIndicator = getIndicatorMatchIndicatorWord(indicator, word);
        int[][] adjWord = getWordMatchIndicatorWord(indicator, word);

        /*
         for(int index = 0; index <adjIndicator.length;index ++)
         {
         adjIndicator [index][0]= indicator [index+init][0];
         adjIndicator [index][1]= indicator [index+init][1];
         adjWord [index] [0] = (int) indicator [index+init][0];
         adjWord [index] [1] = (int) sum(word, adjWord [index] [0]-1,adjWord [index] [0]+298);
         }
         */

        double xMean = mean(adjIndicator);
        double yMean = mean(adjWord);
        double coV = 0;
        for (int a = 0; a < adjIndicator.length; a++) {
            double[] indicatorPoint = adjIndicator[a];
            int[] wordPoint = adjWord[a];
            coV += (indicatorPoint[1] - xMean) * (wordPoint[1] - yMean);
        }
        coV /= adjIndicator.length;
        coV /= (standardDev(adjIndicator) * standardDev(adjWord));
        return coV;
    }

    public static double covariance(float[] indicator, float[] word) {
        double xMean = mean(indicator);
        double yMean = mean(word);
        double coV = 0;
        for (int a = 0; a < indicator.length; a++) {
            float indicatorPoint = indicator[a];
            float wordPoint = word[a];
            coV += (indicatorPoint - xMean) * (wordPoint - yMean);
        }
        coV /= indicator.length;
        coV /= (standardDev(indicator) * standardDev(word));
        return coV;
    }

    public static double[][] getIndicatorMatchIndicatorWord(double[][] indicator, int[][] word) {
        int indicatorShift = 0;
        //int indicatorShift = 31;//used for testing Constructiondx
        //int indicatorShift = 298;//- used for testing GDPdx
        int init = 0;
        while (indicator[init][0] + indicatorShift < word[0][0]) {
            init++;
        }

        int end = indicator.length - 1;
        while (indicator[end][0] + indicatorShift > word[word.length - 1][0]) {
            end--;
        }

        double[][] adjIndicator = new double[(end + 1) - init][2];
        int[][] adjWord = new int[(end + 1) - init][2];
        for (int index = 0; index < adjIndicator.length; index++) {
            adjIndicator[index][0] = indicator[index + init][0];
            adjIndicator[index][1] = indicator[index + init][1];
            adjWord[index][0] = (int) indicator[index + init][0];
            adjWord[index][1] = (int) sum(word, adjWord[index][0] - 1, adjWord[index][0] + 298);
        }

        //for(int a = 0; a < adjIndicator.length; a++)
        //{
        //    System.out.print("date-in: " + adjIndicator [a] [0]);
        //    System.out.print(" value: " + adjIndicator [a] [1]);
        //    System.out.print("date-wd: " + adjWord [a] [0]);
        //    System.out.println(" : " + adjWord [a] [1]);
        //}

        return adjIndicator;
    }

    public static int[][] getWordMatchIndicatorWord(double[][] indicator, int[][] word) {
        int indicatorShift = 0;
        //int indicatorShift = 31;//used for testing Constructiondx
        //indicatorShift = 298;//- used for testing GDPdx
        int init = 0;
        while (indicator[init][0] + indicatorShift < word[0][0]) {
            init++;
        }

        int end = indicator.length - 1;
        while (indicator[end][0] + indicatorShift > word[word.length - 1][0]) {
            end--;
        }

        double[][] adjIndicator = new double[(end + 1) - init][2];
        int[][] adjWord = new int[(end + 1) - init][2];
        for (int index = 0; index < adjIndicator.length; index++) {
            adjIndicator[index][0] = indicator[index + init][0];
            adjIndicator[index][1] = indicator[index + init][1];
            adjWord[index][0] = (int) indicator[index + init][0];
            adjWord[index][1] = (int) sum(word, adjWord[index][0] - 1, adjWord[index][0] + 298);
        }

        //for(int a = 0; a < adjIndicator.length; a++)
        //{
        //    System.out.print("date-in: " + adjIndicator [a] [0]);
        //    System.out.print(" value: " + adjIndicator [a] [1]);
        //    System.out.print("date-wd: " + adjWord [a] [0]);
        //    System.out.println(" : " + adjWord [a] [1]);
        //}

        return adjWord;
    }

    public static int[][] allignWord(double[][] indicator, int[][] word) {
        int indicatorStart = (int) indicator[0][0];
        int indicatorEnd = (int) indicator[indicator.length-1][0];
        int wordStart = word[0][0];
        int wordEnd = word[word.length-1][0];
        int indicatorShift = 0, indicatorCut = indicator.length-1, wordShift = 0, wordCut = word.length-1;
        if (indicatorStart > wordStart) {
            while (wordShift != word.length && indicatorStart > word[wordShift][0]) {
                wordShift++;
            }
        } else {
            while (indicatorShift != word.length && wordStart > indicator[indicatorShift][0]) {
                indicatorShift++;
            }
        }
        if (indicatorEnd > wordEnd) {
            while (indicatorCut > indicatorShift && wordEnd < indicator[indicatorCut][0]) {
                indicatorCut--;
            }
        } else {
            while (wordCut > wordShift && indicatorEnd < word[wordCut][0]) {
                wordCut--;
            }
        }

        int newLength = 0, tempI = indicatorShift, tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {

            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (word[tempW][0] <= indicatorDate) {
                used = true;
                tempW++;
            }
            if (used) {
                newLength++;
            }

            tempI++;
        }
        int[][] adjWord = new int[newLength+1][2];
        newLength = 0;
        tempI = indicatorShift;
        tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {
            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (word[tempW][0] <= indicatorDate) {
                adjWord[newLength][1] += word[tempW][1];
                used = true;
                tempW++;
            }
            if (used) {
                adjWord[newLength][0] = (int)indicator[tempI][0];
                newLength++;
                
            }
            tempI++;
        }
        adjWord[newLength][0] = (int)indicator[tempI][0];
        adjWord[newLength][1] = word[tempW][1];
        return adjWord;
    }
    public static double[][] allignWordPercent(double[][] indicator, double[][] wordPercent) {
        int indicatorStart = (int) indicator[0][0];
        int indicatorEnd = (int) indicator[indicator.length-1][0];
        int wordStart = (int)wordPercent[0][0];
        int wordEnd = (int)wordPercent[wordPercent.length-1][0];
        int indicatorShift = 0, indicatorCut = indicator.length-1, wordShift = 0, wordCut = wordPercent.length-1;
        if (indicatorStart > wordStart) {
            while (wordShift != wordPercent.length-1 && indicatorStart > wordPercent[wordShift][0]) {
                wordShift++;
            }
        } else {
            while (indicatorShift != wordPercent.length-1 && wordStart > indicator[indicatorShift][0]) {
                indicatorShift++;
            }
        }
        if (indicatorEnd > wordEnd) {
            while (indicatorCut > indicatorShift && wordEnd < indicator[indicatorCut][0]) {
                indicatorCut--;
            }
        } else {
            while (wordCut > wordShift && indicatorEnd < wordPercent[wordCut][0]) {
                wordCut--;
            }
        }

        int newLength = 0, tempI = indicatorShift, tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {

            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (wordPercent[tempW][0] <= indicatorDate) {
                used = true;
                tempW++;
            }
            if (used) {
                newLength++;
            }

            tempI++;
        }
        double[][] adjWord = new double[newLength+1][2];
        newLength = 0;
        tempI = indicatorShift;
        tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {
            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            double totalCount = 0;
            while (wordPercent[tempW][0] <= indicatorDate) {
                
                totalCount += wordPercent[tempW][2];
                adjWord[newLength][1] += wordPercent[tempW][1] * wordPercent[tempW][2];
                used = true;
                tempW++;
            }
            if (used) {
                adjWord[newLength][1] /= totalCount;
                adjWord[newLength][0] = (int)indicator[tempI][0];
                newLength++;
                
            }
            tempI++;
        }
        adjWord[newLength][0] = (int)indicator[tempI][0];
        adjWord[newLength][1] = wordPercent[tempW][1];
        return adjWord;
    }
    public static double[][] allignIndicatorPercent(double[][] indicator, double[][] wordPercent) {
        int indicatorStart = (int) indicator[0][0];
        int indicatorEnd = (int) indicator[indicator.length-1][0];
        int wordStart = (int)wordPercent[0][0];
        int wordEnd = (int)wordPercent[wordPercent.length-1][0];
        int indicatorShift = 0, indicatorCut = indicator.length-1, wordShift = 0, wordCut = wordPercent.length-1;
        if (indicatorStart > wordStart) {
            while (wordShift != wordPercent.length-1 && indicatorStart > wordPercent[wordShift][0]) {
                wordShift++;
            }
        } else {
            while (indicatorShift != wordPercent.length-1 && wordStart > indicator[indicatorShift][0]) {
                indicatorShift++;
            }
        }
        if (indicatorEnd > wordEnd) {
            while (indicatorCut > indicatorShift && wordEnd < indicator[indicatorCut][0]) {
                indicatorCut--;
            }
        } else {
            while (wordCut > wordShift && indicatorEnd < wordPercent[wordCut][0]) {
                wordCut--;
            }
        }

        int newLength = 0, tempI = indicatorShift, tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {

            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (wordPercent[tempW][0] <= indicatorDate) {
                used = true;
                tempW++;
            }
            if (used) {
                newLength++;
            }

            tempI++;
        }
        double[][] adjIndicator = new double[newLength+1][2];
        newLength = 0;
        tempI = indicatorShift;
        tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {
            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (wordPercent[tempW][0] <= indicatorDate) {
                used = true;
                tempW++;
            }
            if (used) {
                adjIndicator[newLength][1] = indicator[tempI][1];
                adjIndicator[newLength][0] = (int)indicator[tempI][0];
                newLength++;
                
            }
            tempI++;
        }
        adjIndicator[newLength][0] = (int)indicator[tempI][0];
        adjIndicator[newLength][1] = indicator[tempI][1];
        return adjIndicator;
    }
    public static double[][] allignIndicator(double[][] indicator, int[][] word) {
        int indicatorStart = (int) indicator[0][0];
        int indicatorEnd = (int) indicator[indicator.length-1][0];
        int wordStart = word[0][0];
        int wordEnd = word[word.length-1][0];
        int indicatorShift = 0, indicatorCut = indicator.length-1, wordShift = 0, wordCut = word.length-1;
        if (indicatorStart > wordStart) {
            while (wordShift != word.length && indicatorStart > word[wordShift][0]) {
                wordShift++;
            }
        } else {
            while (indicatorShift != word.length && wordStart > indicator[indicatorShift][0]) {
                indicatorShift++;
            }
        }
        
        if (indicatorEnd > wordEnd) {
            while (indicatorCut > indicatorShift && wordEnd < indicator[indicatorCut][0]) {
                indicatorCut--;
            }
        } else {
            while (wordCut > wordShift && indicatorEnd < word[wordCut][0]) {
                wordCut--;
            }
        }

        int newLength = 0, tempI = indicatorShift, tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {

            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (word[tempW][0] <= indicatorDate) {
                used = true;
                tempW++;
            }
            if (used) {
                newLength++;
            }

            tempI++;
        }
        double[][] adjIndicator = new double[newLength+1][2];
        newLength = 0;
        tempI = indicatorShift;
        tempW = wordShift;
        while (tempI < indicatorCut && tempW < wordCut) {
            int indicatorDate = (int) indicator[tempI][0];
            boolean used = false;
            while (word[tempW][0] <= indicatorDate) {
                used = true;
                tempW++;
            }
            if (used) {
                adjIndicator[newLength][0] = (int)indicator[tempI][0];
                adjIndicator[newLength][1] = indicator[tempI][1];
                newLength++;
                
            }
            tempI++;
        }
        adjIndicator[newLength][0] = (int)indicator[tempI][0];
        adjIndicator[newLength][1] = indicator[tempI][1];
        return adjIndicator;
    }
    

    public static double[][] convertToZ(double[][] data) {
        double mean = mean(data);
        double stDev = standardDev(data);
        for (int a = 0; a < data.length; a++) {
            data[a][1] = (data[a][1] - mean) / stDev;
        }
        return data;
    }

    public static double[][] convertToZ(int[][] data) {
        double[][] tempData = new double[data.length][2];
        double mean = mean(data);
        double stDev = standardDev(data);
        for (int a = 0; a < data.length; a++) {
            tempData[a][0] = data[a][0];
            tempData[a][1] = (((double) data[a][1] - mean) / stDev);
        }
        return tempData;
    }

    private static double mean(double[][] data) {
        double sum = 0, count = 0;
        for (double[] point : data) {
            sum += point[1];
            count++;
        }
        return sum / count;
    }

    private static double mean(float[] data) {
        double sum = 0, count = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
            count++;
        }
        return sum / count;
    }

    private static double mean(int[][] data) {
        double sum = 0, count = 0;
        for (int[] point : data) {
            sum += point[1];
            count++;
        }
        return sum / count;
    }

    private static double mean(int[][] data, double count) {
        double sum = 0;
        for (int[] point : data) {
            sum += point[1];
        }
        return sum / count;
    }

    private static double sum(int[][] word, double timeIn, double timeOut) {
        double sum = 0;
        for (int[] wordCount : word) {
            if (wordCount[0] >= timeIn && wordCount[0] < timeOut) {
                sum += wordCount[1];
            }
        }
        return sum;
    }

    static double sum(int[][] word) {
        double sum = 0;
        for (int[] wordCount : word) {
            sum += wordCount[1];
        }
        return sum;
    }

    private static double standardDev(double[][] data) {
        double xMean = mean(data);
        double var = 0;
        for (double[] point : data) {
            var += (point[1] - xMean) * (point[1] - xMean);
        }
        var /= data.length - 1;
        return Math.sqrt(var);
    }

    private static double standardDev(float[] data) {
        double xMean = mean(data);
        double var = 0;
        for (float point : data) {
            var += (point - xMean) * (point - xMean);
        }
        var /= data.length - 1;
        return Math.sqrt(var);
    }

    private static double standardDev(int[][] data) {
        double xMean = mean(data);
        double var = 0;
        for (int[] point : data) {
            var += (point[1] - xMean) * (point[1] - xMean);
        }
        var /= data.length - 1;
        return Math.sqrt(var);
    }

    public static double distanceBetweenScaled(float[] indicator, float[] word) {
        if(Math.abs(correctScale(indicator, word)) > 20)
        {
            System.out.println("Suspicious:....");
        }
        float[] word2 = multiply(word, correctScale(indicator, word));
        return distance(indicator, word2);
    }

    public static double distance(float[] indicator, float[] word) {
        float[] difference = new float[indicator.length];
        for (int i = 0; i < indicator.length; i++) {
            difference[i] = indicator[i] - word[i];
        }
        return length(difference);
    }

    public static double length(float[] vector) {
        double length = 0.0;
        for (float f : vector) {
            length += (f * f);
        }
        return Math.sqrt(length);
    }

    public static float[] scalar(float[] data, double endLength) {
        double scale = getScale(data, endLength);
//        System.out.print("Raw Scale:"+scale + " ");
        float[] newData = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            newData[i] = (float) ((double) data[i] * scale);
        }
        return newData;
    }

    public static double correctScale(float[] goal, float[] data) {
        double endLength = length(goal);
        double startLength = length(data);
        double tempScale = (endLength / startLength) * dotProduct(goal, data);
        return tempScale;
    }

    public static float[] add(float[] data, float[] data2) {
        float[] returns = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            returns[i] = data[i] + data2[i];
        }
        return returns;
    }

    public static float[] multiply(float[] data, double scale) {
        float[] returns = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            returns[i] = data[i] * (float) scale;
        }
        return returns;
    }

    public static double dotProduct(float[] data1, float[] data2) {
        double sum = 0.0;
        for (int i = 0; i < data1.length; i++) {
            sum += data1[i] * data2[i];
        }
        //if ((""+(sum / (length(data1) * length(data2)))).equals(""+Double.NaN))
        //{
        //    System.out.println("NAN");
        //    if((length(data1) * length(data2)) == 0)
        //    {
        //        System.out.println("Length of 0");
        //    }
        //}
        return sum / (length(data1) * length(data2));
    }

    public static double getScale(float[] data, double endLength) {
        double length = length(data);
        return endLength / length;
    }

    public static double getScale(float[] data, float[] data2) {
        return getScale(data, length(data2));
    }

    public static float[] getDifference(float[] goal, float[] approximation) {
        float[] difference = new float[goal.length];
        for (int i = 0; i < difference.length; i++) {
            difference[i] = goal[i] - approximation[i];
        }
        return difference;
    }
}