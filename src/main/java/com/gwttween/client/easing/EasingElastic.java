package com.gwttween.client.easing;

public enum EasingElastic implements Easing {
    In {
        public double calc(double v) {
            double s, a = 0.1, p = 0.4;
            if (v == 0) return 0;
            if (v == 1) return 1;
            if (a < 1) {
                a = 1;
                s = p / 4;
            } else s = p * Math.asin(1 / a) / (2 * Math.PI);
            return -(a * Math.pow(2, 10 * (v -= 1)) * Math.sin((v - s) * (2 * Math.PI) / p));
        }
    },
    Out {
        public double calc(double v) {
            double s, a = 0.1, p = 0.4;
            if (v == 0) return 0;
            if (v == 1) return 1;
            if (a < 1) {
                a = 1;
                s = p / 4;
            } else s = p * Math.asin(1 / a) / (2 * Math.PI);
            return (a * Math.pow(2, -10 * v) * Math.sin((v - s) * (2 * Math.PI) / p) + 1);
        }
    },
    InOut {
        public double calc(double v) {
            double s, a = 0.1, p = 0.4;
            if (v == 0) return 0;
            if (v == 1) return 1;
            if (a < 1) {
                a = 1;
                s = p / 4;
            } else s = p * Math.asin(1 / a) / (2 * Math.PI);
            if ((v *= 2) < 1) return -0.5 * (a * Math.pow(2, 10 * (v -= 1)) * Math.sin((v - s) * (2 * Math.PI) / p));
            return a * Math.pow(2, -10 * (v -= 1)) * Math.sin((v - s) * (2 * Math.PI) / p) * 0.5 + 1;
        }
    }
}
