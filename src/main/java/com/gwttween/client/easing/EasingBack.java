package com.gwttween.client.easing;

public enum EasingBack implements Easing {

    In {
        public double calc(double v) {
            double s = 1.70158;
            return v * v * ((s + 1) * v - s);
        }
    },
    Out {
        public double calc(double v) {
            double s = 1.70158;
            return --v * v * ((s + 1) * v + s) + 1;
        }
    },
    InOut {
        public double calc(double v) {
            double s = 1.70158 * 1.525;
            if ((v *= 2) < 1) return 0.5 * (v * v * ((s + 1) * v - s));
            return 0.5 * ((v -= 2) * v * ((s + 1) * v + s) + 2);
        }
    }
}
