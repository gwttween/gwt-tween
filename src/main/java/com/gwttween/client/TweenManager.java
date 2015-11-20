package com.gwttween.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main part which will update all Tweeness
 */
public class TweenManager {
    long navigationStart = 0;
    List<Tween> tweenList = new ArrayList<Tween>();

    public long getTime() {
        long offset = navigationStart != 0 ? navigationStart : new Date().getTime();
        return new Date().getTime() - offset;
    }

    public List<Tween> getAll() {

        return tweenList;

    }

    public void removeAll() {

        tweenList = new ArrayList<Tween>();

    }

    public void add(Tween tween) {

        tweenList.add(tween);

    }

    public void remove(Tween tween) {

        if (tween != null)
            tweenList.remove(tween);

    }

    public boolean update(double time) {
        if(navigationStart == 0){
            navigationStart = new Date().getTime();
        }

        if (tweenList.size() == 0) return false;

        int i = 0;

        time = time != 0 ? time : getTime();

        while (i < tweenList.size()) {

            if (tweenList.get(i).update(time)) {

                i++;

            } else {

                tweenList.remove(i);

            }
        }

        return true;

    }
}
