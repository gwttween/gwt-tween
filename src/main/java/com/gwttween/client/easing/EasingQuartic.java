package com.gwttween.client.easing;

public enum EasingQuartic implements Easing {

    In {
        public double calc(double v) {
            return v * v * v * v;
        }
    },
    Out {
        public double calc(double v) {
            return 1 - (--v * v * v * v);
        }
    },
    InOut {
        public double calc(double v) {
            if ((v *= 2) < 1) return 0.5 * v * v * v * v;
            return -0.5 * ((v -= 2) * v * v * v - 2);
        }
    }
}
