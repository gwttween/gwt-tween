package com.gwttween.client.easing;

public enum EasingQuadratic implements Easing {

    In {
        public double calc(double v) {
            return v * v;
        }
    },
    Out {
        public double calc(double v) {
            if ((v *= 2) < 1) return 0.5 * v * v;
            return -0.5 * (--v * (v - 2) - 1);
        }
    },
    InOut {
        public double calc(double v) {
            return v * (2 - v);
        }
    }
}
