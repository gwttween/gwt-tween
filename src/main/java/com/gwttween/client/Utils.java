package com.gwttween.client;


/**
 * Taken from Tween.js  TWEEN.Interpolation.Utils
 */
public class Utils {

    public static double calcLinear(double p0, double p1, double t) {

        return (p1 - p0) * t + p0;

    }

    public static double calcBernstein(int n, int i) {
        return calcFactorial(n) / calcFactorial(i) / calcFactorial(n - i);

    }

    public static double calcFactorial(int n) {
        int s = 1;
        for (int i = n; i > 1; i--) s *= i;
        //TODO: add cache for s value
        return s;

    }

    public static double calcCatmullRom(double p0, double p1, double p2, double p3, double t) {

        double v0 = (p2 - p0) * 0.5, v1 = (p3 - p1) * 0.5, t2 = t * t, t3 = t * t2;
        return (2 * p1 - 2 * p2 + v0 + v1) * t3 + (-3 * p1 + 3 * p2 - 2 * v0 - v1) * t2 + v0 * t + p1;
    }

}
