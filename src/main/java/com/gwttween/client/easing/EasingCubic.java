package com.gwttween.client.easing;

public enum EasingCubic implements Easing {

    In {
        public double calc(double v) {
            return v * v * v;
        }
    },
    Out {
        public double calc(double v) {
            return --v * v * v + 1;
        }
    },
    InOut {
        public double calc(double v) {
            if ((v *= 2) < 1) return 0.5 * v * v * v;
            return 0.5 * ((v -= 2) * v * v + 2);
        }
    }
}
