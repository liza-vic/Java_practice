package by.bsu.practice.threads.entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// класс самолет с фиксированным кол-вом мест и дальностью
public class Plane {
    public final int id;
    public final int MAX_PASSENGERS;
    public final RangeZone zone;

    private AtomicInteger passengersNow = new AtomicInteger();
    private AtomicBoolean isBusy = new AtomicBoolean();
    private AtomicReference<Destination> town = new AtomicReference<Destination>();

    public Plane(int id, int MAX_PASSENGERS, RangeZone zone) {
        this.id = id;
        this.MAX_PASSENGERS = MAX_PASSENGERS;
        this.zone = zone;
    }

    public AtomicInteger getPassengersNow() {
        return passengersNow;
    }

    public void setPassengersNow(int passengersNow) {
        this.passengersNow.set(passengersNow);
    }

    public void addPassenger(){
        this.passengersNow.addAndGet(1);
    }

    public boolean isFull(){
        return passengersNow.get() == MAX_PASSENGERS;
    }

    public boolean getIsBusy() {
        return isBusy.get();
    }

    public void setIsBusy(boolean isBusy) {
        this.isBusy.set(isBusy);
    }

    public Destination getTown() {
        return town.get();
    }

    public void setTown(Destination town) {
        this.town.set(town);
    }
}
