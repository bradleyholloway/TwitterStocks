package twitterstocks;

public class RollingAverage {

    private boolean firstPass;
    private int size;
    private int currentIndex;
    private double[] values;

    public RollingAverage(int size) {
        this.firstPass = true;
        this.size = size;
        this.currentIndex = 0;
        this.values = new double[size];
    }

    public double get() {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        if (!firstPass) {
            return (sum / size);
        }
        return (sum / currentIndex);
    }

    public void update(double value) {
        round(value, 3);
        values[currentIndex] = value;
        currentIndex++;
        if (currentIndex == size) {
            currentIndex = 0;
            firstPass = false;
        }
    }

    private double round(double value, int places) {
        value = value * (Math.pow(10, places));
        value = Math.round(value);
        value = value / (Math.pow(10, places));
        return value;
    }
}