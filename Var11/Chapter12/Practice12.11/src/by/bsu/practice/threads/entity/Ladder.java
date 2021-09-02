package by.bsu.practice.threads.entity;

import java.util.concurrent.atomic.AtomicBoolean;

// трап (аналогично терминалу)
public class Ladder {
    private AtomicBoolean isBusy = new AtomicBoolean();

    public Ladder() {
        this.isBusy.set(false);
    }

    public boolean getIsBusy() {
        return isBusy.get();
    }

    public void setIsBusy(boolean isBusy) {
        this.isBusy.set(isBusy);
    }
}
