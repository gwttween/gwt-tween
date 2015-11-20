package com.gwttween.client.easing;

public enum EasingBounce implements Easing {

    In {
        public double calc(double v) {
            return 1 - EasingBounce.Out.calc(1 - v);
        }
    },
    Out {
        public double calc(double v) {

            if (v < (1 / 2.75)) {

                return 7.5625 * v * v;

            } else if (v < (2 / 2.75)) {

                return 7.5625 * (v -= (1.5 / 2.75)) * v + 0.75;

            } else if (v < (2.5 / 2.75)) {

                return 7.5625 * (v -= (2.25 / 2.75)) * v + 0.9375;

            } else {

                return 7.5625 * (v -= (2.625 / 2.75)) * v + 0.984375;

            }
        }
    },
    InOut {
        public double calc(double v) {
            if (v < 0.5) return EasingBounce.In.calc(v * 2) * 0.5;
            return EasingBounce.Out.calc(v * 2 - 1) * 0.5 + 0.5;
        }
    }
}
