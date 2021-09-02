package by.bsu.practice.threads.entity;

import java.util.concurrent.atomic.AtomicBoolean;

//терминал аэропорта с полем, показывающим занят ли он
public class Terminal {

    private AtomicBoolean isBusy = new AtomicBoolean();

    public Terminal() {
        this.isBusy.set(false);
    }

    public boolean getIsBusy() {
        return isBusy.get();
    }

    public void setIsBusy(boolean isBusy) {
        this.isBusy.set(isBusy);
    }
}
