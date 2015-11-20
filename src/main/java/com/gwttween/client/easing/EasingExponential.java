package com.gwttween.client.easing;

public enum EasingExponential implements Easing {

    In {
        public double calc(double v) {
            return v == 0 ? 0 : Math.pow(1024, v - 1);
        }
    },
    Out {
        public double calc(double v) {
            return v == 1 ? 1 : 1 - Math.pow(2, -10 * v);
        }
    },
    InOut {
        public double calc(double v) {
            if (v == 0) return 0;
            if (v == 1) return 1;
            if ((v *= 2) < 1) return 0.5 * Math.pow(1024, v - 1);
            return 0.5 * (-Math.pow(2, -10 * (v - 1)) + 2);
        }
    }
}
