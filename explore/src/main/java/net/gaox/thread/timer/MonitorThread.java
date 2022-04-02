package net.gaox.thread.timer;

import java.util.TimerTask;

/**
 * <p> 线程监视器 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:30
 */
public
class MonitorThread extends TimerTask {
    private LittleThread mt;

    public MonitorThread(LittleThread mt) {
        super();
        this.mt = mt;
    }

    @Override
    public void run() {
        System.out.println("ThreadID:" + " MonitorThread running!");
        mt.notifyLock();
    }

}
