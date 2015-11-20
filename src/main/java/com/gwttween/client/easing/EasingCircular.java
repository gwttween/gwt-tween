package com.gwttween.client.easing;

public enum EasingCircular implements Easing {

    In {
        public double calc(double v) {
            return 1 - Math.sqrt(1 - v * v);
        }
    },
    Out {
        public double calc(double v) {
            return Math.sqrt(1 - (--v * v));
        }
    },
    InOut {
        public double calc(double v) {
            if ((v *= 2) < 1) return -0.5 * (Math.sqrt(1 - v * v) - 1);
            return 0.5 * (Math.sqrt(1 - (v -= 2) * v) + 1);
        }
    }
}
