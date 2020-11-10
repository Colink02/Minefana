package com.github.games647.minefana;

import org.bukkit.Bukkit;

/**
 * This task should run every second
 */
public class TicksPerSecondTask implements Runnable {

    private static final int INTERVAL_CHECK = 1;

    private float lastTicks = 20.0F;

    /**
     * Get the ticks count of the last check. 20 Ticks should pass per second
     *
     * @return the ticks count of the last check
     */
    public float getLastTicks() {
        return lastTicks;
    }

    //the last time we updated the ticks
    private long lastCheck;

    @Override
    public void run() {

        //nanoTime is more accurate
        long currentTime = System.nanoTime();
        //update the last check
        lastCheck = currentTime;

        //how many ticks passed since the last check * 1000 to convert to seconds
        float tps = TPS.getTPS();
        if (tps >= 0.0F && tps <= 20.0F) {
            //Prevent all invalid values
            lastTicks = tps;
        }
    }
}
