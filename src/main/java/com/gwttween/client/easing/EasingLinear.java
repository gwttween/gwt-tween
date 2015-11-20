package com.gwttween.client.easing;

public enum EasingLinear implements Easing {

    None {
        public double calc(double v) {
            return v;
        }
    }

}
