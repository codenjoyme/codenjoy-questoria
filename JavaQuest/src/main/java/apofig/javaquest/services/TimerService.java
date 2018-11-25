package apofig.javaquest.services;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: oleksandr.baglai
 * Date: 10/1/12
 * Time: 7:12 AM
 */
public class TimerService implements Runnable {

    private ScheduledThreadPoolExecutor executor;
    private ScheduledFuture<?> future;

    @Autowired
    private PlayerService playerService;

    private volatile boolean paused;
    private int period;

    public void init() {
        executor = new ScheduledThreadPoolExecutor(1);
        future = executor.scheduleAtFixedRate(this, period, period, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if (paused) {
            return;
        }

        try {
            playerService.tick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}