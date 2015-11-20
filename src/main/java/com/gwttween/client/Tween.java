package com.gwttween.client;

import com.gwttween.client.easing.Easing;
import com.gwttween.client.easing.EasingLinear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tween {
    /**
     * copy form {@link sun.misc.DoubleConsts#MIN_VALUE}
     */
    public static final double MAX_VALUE = 1.7976931348623157E308D;
    private Map<String, Double> valuesStart = new HashMap<String, Double>();
    private Map<String, Double> valuesEnd = new HashMap<String, Double>();
    private Map<String, Double> valuesStartRepeat = new HashMap<String, Double>();
    private long duration = 1000; //default 1 second
    private double repeat = 0;
    private boolean yoyo = false;
    private boolean isPlaying = false;
    private boolean reversed = false;
    private long delayTime = 0;
    private long startTime = 0;
    private Easing easingFunction = EasingLinear.None;
    private Interpolation interpolationFunction = Interpolation.Linear;
    private List<Tween> chainedTween = new ArrayList<Tween>();
    private TweenCallBack onStartCallback = null;
    private boolean onStartCallbackFired = false;
    private TweenOnUpdateCallBack onUpdateCallback = null;
    private TweenCallBack onCompleteCallback = null;
    private TweenCallBack onStopCallback = null;
    private TweenManager tweenManager;
    private Map<String, Double> calculations;

    public Tween(TweenManager tweenManager, String _object) {
        this.tweenManager = tweenManager;
        this.calculations = parseProperties(_object);
        valuesStart = parseProperties(_object);
    }

    /**
     * Set properties for destination and duration of animation
     *
     * @param properties example {@code "{ x: 100 , y: 100 }" }
     * @param duration   in milliseconds (1000 is 1 second)
     * @return this Tween
     */
    public Tween to(String properties, long duration) {
        if (duration != 0) {
            this.duration = duration;
        }
        valuesEnd = parseProperties(properties);
        return this;
    }

    /**
     * Simple start without arguments
     */
    public Tween start() {
        return start(0);
    }

    /**
     * Preparation of all variables for running
     *
     * @param time if 0 than time will be taken from TweenManager
     * @return this
     */
    public Tween start(long time) {
        tweenManager.add(this);
        isPlaying = true;
        onStartCallbackFired = false;

        startTime = time != 0 ? time : tweenManager.getTime();
        startTime += delayTime;
        calculations = new HashMap<String, Double>();

        for (String property : valuesEnd.keySet()) {
            calculations.put(property, valuesStart.get(property));
            valuesStartRepeat.put(property, valuesStart.get(property));
        }
        return this;
    }

    /**
     * Stop running and call callback onStop, also stop chained Tween
     *
     * @return this
     */
    public Tween stop() {

        if (!isPlaying) {
            return this;
        }

        tweenManager.remove(this);
        isPlaying = false;

        if (onStopCallback != null) {
            onStopCallback.call(calculations);
        }

        this.stopChainedTween();
        return this;

    }

    /**
     * Stop all chained tween
     */
    public void stopChainedTween() {
        for (Tween aChainedTween : chainedTween) {
            aChainedTween.stop();
        }
    }

    /**
     * Set delay before start of animation
     *
     * @param delay in millisecond (1000 is 1 second)
     * @return this
     */
    public Tween delay(long delay) {

        delayTime = delay;
        return this;

    }

    /**
     * Set repeat number
     *
     * @param number repeat number
     * @return this Tween
     */
    public Tween repeat(long number) {
        this.repeat = number;
        return this;
    }

    public Tween yoyo(boolean yoyo) {
        this.yoyo = yoyo;
        return this;
    }


    /**
     * Set easing
     *
     * @param easing {@link Easing}
     * @return this Tween
     */
    public Tween easing(Easing easing) {
        easingFunction = easing;
        return this;
    }

    public Tween interpolation(Interpolation interpolation) {
        interpolationFunction = interpolation;
        return this;
    }

    public Tween chain(Tween tween) {
        chainedTween.add(tween);
        return this;
    }

    /**
     * It will be called before start
     *
     * @param callback {@link TweenCallBack}
     * @return this Tween
     */
    public Tween onStart(TweenCallBack callback) {
        onStartCallback = callback;
        return this;
    }

    /**
     * It will be called every time on update and will provide updated values
     *
     * @param callback implementation of {@link TweenOnUpdateCallBack}
     * @return this
     */
    public Tween onUpdate(TweenOnUpdateCallBack callback) {
        onUpdateCallback = callback;
        return this;
    }

    /**
     * It will be called when tween will be complited
     *
     * @param callback implementation of {@link TweenCallBack}
     * @return this Tween
     */
    public Tween onComplete(TweenCallBack callback) {
        onCompleteCallback = callback;
        return this;
    }

    public Tween onStop(TweenCallBack callback) {
        onStopCallback = callback;
        return this;
    }

    /**
     * Example: {@code '{ rotation: 360, y: 300 }'} or {@code rotation: 360, y: 300}
     *
     * @param properties string
     * @return this Tween
     */
    private Map<String, Double> parseProperties(String properties) {
        Map<String, Double> result = new HashMap<String, Double>();
        properties = properties.replace("{", "");
        properties = properties.replace("}", "");
        String[] pr = properties.split(",");
        for (String keyValue : pr) {
            String[] split = keyValue.split(":");
            if (split.length > 0) {
                Double value = getValue(split[1].trim());
                if (value != null) {
                    result.put(split[0].trim(), value);
                }
            }
        }
        return result;
    }

    private Double getValue(String s) {
        Double object;
        try {
            object = Double.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
        return object;
    }

    /**
     * @param time current time of animation
     * @return false when compleet
     */
    public boolean update(double time) {
        if (time < startTime) {
            return true;
        }

        if (onStartCallbackFired) {
            if (onStartCallback != null) {
                onStartCallback.call(calculations);
            }
            onStartCallbackFired = true;
        }

        double elapsed = (time - startTime) / duration;
        elapsed = elapsed > 1 ? 1 : elapsed;
        double value = easingFunction.calc(elapsed);

        for (String property : valuesEnd.keySet()) {

            Double start = valuesStart.get(property);
            Double end = valuesEnd.get(property);

           if (end != null && start != null) {
                double st = start;
                double ed = end;
                Double result = (st + (ed - st) * value);
                calculations.put(property, result);
            }
        }
        if (onUpdateCallback != null) {
            onUpdateCallback.call(calculations, value);
        }
        if (elapsed == 1) {
            if (repeat > 0) {
                if (isFinite(repeat)) {
                    repeat--;
                }
                // reassign starting values, restart by making startTime = now
                for (String property : valuesStartRepeat.keySet()) {
                    if (yoyo) {
                        Double tmp = valuesStartRepeat.get(property);
                        valuesStartRepeat.put(property, valuesEnd.get(property));
                        valuesEnd.put(property, tmp);
                    }
                    valuesStart.put(property, valuesStartRepeat.get(property));
                }
                if (yoyo) {
                    reversed = !reversed;
                }
                startTime = (int) time + delayTime;
                return true;
            } else {
                if (onCompleteCallback != null) {
                    onCompleteCallback.call(calculations);
                }
                for (Tween aChainedTween : chainedTween) {
                    aChainedTween.start((int) time);
                }
                return false;
            }
        }
        return true;
    }


    /**
     * copy from {@link Double#isFinite(double)}
     */
    private boolean isFinite(double repeat) {
        return Math.abs(repeat) <= MAX_VALUE;
    }


}

