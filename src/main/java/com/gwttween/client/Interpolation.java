package com.gwttween.client;

public enum Interpolation {
    Linear {
        public double val(double[] a, double v) {

            int m = a.length - 1;
            double f = m * v;
            int i = (int) Math.floor(f);

            if (v < 0) return Utils.calcLinear(a[0], a[1], f);
            if (v > 1) return Utils.calcLinear(a[m], a[m - 1], m - f);

            return Utils.calcLinear(a[i], a[i + 1 > m ? m : i + 1], f - i);

        }
    },
    Bezier {
        @Override
        double val(double[] a, double v) {
            int b = 0, n = a.length - 1;

            for (int i = 0; i <= n; i++) {
                b += Math.pow(1 - v, n - i) * Math.pow(v, i) * a[i] * Utils.calcBernstein(n, i);
            }

            return b;
        }
    },
    CatmullRom {
        @Override
        double val(double[] a, double v) {
            int m = a.length - 1;
            double f = m * v;
            int i = (int) Math.floor(f);

            if (a[0] == a[m]) {

                if (v < 0) i = (int) Math.floor(f = m * (1 + v));

                return Utils.calcCatmullRom(a[(i - 1 + m) % m], a[i], a[(i + 1) % m], a[(i + 2) % m], f - i);

            } else {

                if (v < 0) return a[0] - (Utils.calcCatmullRom(a[0], a[0], a[1], a[1], -f) - a[0]);
                if (v > 1) return a[m] - (Utils.calcCatmullRom(a[m], a[m], a[m - 1], a[m - 1], f - m) - a[m]);

                return Utils.calcCatmullRom(a[i != 0 ? i - 1 : 0], a[i], a[m < i + 1 ? m : i + 1], a[m < i + 2 ? m : i + 2], f - i);

            }
        }
    };


    abstract double val(double[] a, double v);
}


