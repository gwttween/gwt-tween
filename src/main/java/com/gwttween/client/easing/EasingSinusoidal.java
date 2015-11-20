package com.gwttween.client.easing;

public enum EasingSinusoidal implements Easing {

    In {
        public double calc(double v) {
            return 1 - Math.cos(v * Math.PI / 2);
        }
    },
    Out {
        public double calc(double v) {
            return Math.sin(v * Math.PI / 2);
        }
    },
    InOut {
        public double calc(double v) {
            return 0.5 * (1 - Math.cos(Math.PI * v));
        }
    }
}
