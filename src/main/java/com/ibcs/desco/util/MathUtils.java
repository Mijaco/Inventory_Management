package com.ibcs.desco.util;

/**
 * Created by Mohammed Hossain Doula on 4/27/2016.
 */
public class MathUtils {

    public static float findPercentage(float scaleValue, float measureValue){
        return 100*(measureValue/scaleValue);
    }

    public static boolean isOdd(int number){
        return ((number&1) == 1);
    }

    public static boolean isEven(int number){
        return number%2 == 0;
    }

    public static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= num/2; ++i)
            if (num % i == 0) return false;
        return true;
    }

    public static int nextPrime(int num) {
        if (num < 2) return 2;
        for (int i = num + 1; ; ++i)
            if (isPrime(i)) return i;
    }

    public static double sum(double... xs) {
        double sum = 0.0;
        for(int i = 0; i < xs.length; ++i)
            sum += xs[i];
        return sum;
    }

    public static double minimum(double... xs) {
        if (xs.length == 0) return Double.NaN;
        double min = xs[0];
        for (int i = 1; i < xs.length; ++i)
            if (xs[i] < min) min = xs[i];
        return min;
    }

    public static double maximum(double... xs) {
        if (xs.length == 0) return Double.NaN;
        double max = xs[0];
        for (int i = 1; i < xs.length; ++i)
            if (xs[i] > max) max = xs[i];
        return max;
    }

    /**
     * Returns the maximum value of an element in xs.  If any of the
     * values are {@code Double.NaN}, or if the input array is empty,
     * the result is {@code Double.NaN}.
     *
     * @param xs Array in which to find maximum.
     * @return Maximum value in array.
     */
    public static double max(double... xs) {
        if (xs.length == 0)
            return Double.NaN;
        double max = xs[0];
        for (int i = 1; i < xs.length; ++i)
            max = Math.max(max,xs[i]);
        return max;
    }

    /**
     * Returns the maximum value of an element in the specified array.
     *
     * @param xs Array in which to find maximum.
     * @return Maximum value in the array.
     * @throws ArrayIndexOutOfBoundsException If the specified array does
     * not contai at least one element.
     */
    public static int max(int... xs) {
        int max = xs[0];
        for (int i = 1; i < xs.length; ++i)
            if (xs[i] > max)
                max = xs[i];
        return max;
    }

    /**
     * Returns the sum of the specified integer array.  Note that
     * there is no check for overflow.  If the array is of length 0,
     * the sum is defined to be 0.
     *
     * @param xs Array of integers to sum.
     * @return Sum of the array.
     */
    public static int sum(int... xs) {
        int sum = 0;
        for (int i = 0; i < xs.length; ++i)
            sum += xs[i];
        return sum;
    }
}
