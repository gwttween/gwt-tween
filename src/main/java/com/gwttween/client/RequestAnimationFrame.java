package com.gwttween.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.core.client.Duration;

public class RequestAnimationFrame {

    private final AnimationScheduler scheduler;
    private boolean isRunning = false;
    private boolean isStarted = false;
    private boolean isStopped = false;
    private AnimationScheduler.AnimationHandle requestHandle;
    private int runId = -1;
    private double startTime = -1;
    private double stopTime = 0;
    private double stoppingDelta = 0;
    private RequestAnimationHandler requestAnimationHandler;

    private final AnimationScheduler.AnimationCallback callback = new AnimationScheduler.AnimationCallback() {
        public void execute(double timestamp) {
            // Schedule the next animation frame.
            if (refresh(timestamp))
                requestHandle = scheduler.requestAnimationFrame(callback);
            else
                requestHandle = null;
        }
    };

    /**
     * Construct a new {@link RequestAnimationFrame}.
     */
    public RequestAnimationFrame() {
        this(AnimationScheduler.get());
    }

    /**
     * Construct a new {@link AnimationScheduler} using the specified scheduler
     * to scheduler request frames.
     *
     * @param scheduler an {@link AnimationScheduler} instance
     */
    protected RequestAnimationFrame(AnimationScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public RequestAnimationFrame setRequestAnimationHandler(RequestAnimationHandler requestAnimationHandler) {
        this.requestAnimationHandler = requestAnimationHandler;
        return this;
    }

    /**
     * Stops this animation. If the animation is running or is
     * scheduled to run, {@link #onStop()} will be called.
     */
    public void stop() {
        // Ignore if the animation is not currently running.
        if (!isRunning)
            return;

        // Reset the state.
        isStopped = isStarted; // Used by onCancel.
        isRunning = false;
        stopTime = Duration.currentTimeMillis();

        // Cancel the animation request.
        if (requestHandle != null) {
            requestHandle.cancel();
            requestHandle = null;
        }

        onStop();
    }

    /**
     * Run this animation. If the animation is already running, it will be
     * canceled first.
     * <p>
     * If the element is not <code>null</code>, the {@link #onRefresh(double)}
     * method might be called only if the element may be visible (generally left
     * at the appreciation of the browser). Otherwise, it will be called
     * unconditionally.
     */
    public void run() {
        stop();
        isRunning = true;
        ++runId;
        if (!isStarted)
            startTime = Duration.currentTimeMillis();
        callback.execute(Duration.currentTimeMillis());
    }

    /**
     * Called immediately after the animation is stopped.
     */
    protected void onStop() {
        if (requestAnimationHandler != null) {
            requestAnimationHandler.onStop();
        }
    }

    /**
     * Called immediately before the animation starts.
     */
    protected void onStart() {
        if (requestAnimationHandler != null) {
            requestAnimationHandler.onStart();
        }
    }

    /**
     * Called when the animation should be updated.
     *
     * @param duration The duration of the {@link RequestAnimationFrame} in milliseconds.
     */
    protected void onRefresh(double duration) {
        if (requestAnimationHandler != null) {
            requestAnimationHandler.onRefresh(duration);
        }
    }

    /**
     * Check if the specified run ID is still being run.
     *
     * @param curRunId the current run ID to check
     * @return true if running, false if canceled or restarted
     */
    private boolean isRunning(int curRunId) {
        return isRunning && (runId == curRunId);
    }

    /**
     * Refresh the {@link RequestAnimationFrame}.
     *
     * @param curTime the current time
     * @return true if the animation should run again, false if it is complete
     */
    private boolean refresh(double curTime) {
        /*
         * Save the run id. If the runId is incremented during this execution
		 * block, we know that this run has been canceled.
		 */
        final int curRunId = runId;

        if (isStopped) {
            stoppingDelta += curTime - stopTime;
            isStopped = false;
        }

        if (!isStarted) {
            isStarted = true;
            onStart();
            return isRunning(curRunId);
        } else {
            onRefresh(curTime - startTime - stoppingDelta);
            return isRunning(curRunId);
        }
    }

}
