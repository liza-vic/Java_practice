package by.bsu.practice.threads.entity;

import by.bsu.practice.threads.service.AirportWork;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

//класс-поток пассажир, который функционирует в аэропорте
public class Passenger implements Callable<Integer> {
    private final Destination DESTINATION;
    private final AirportWork airportWork;
    private AtomicInteger terminalNum = new AtomicInteger();
    private AtomicInteger ladderNum = new AtomicInteger();
    private AtomicInteger planeNum = new AtomicInteger();

    public Passenger(Destination DESTINATION, AirportWork airportWork) {
        this.DESTINATION = DESTINATION;
        this.airportWork = airportWork;
    }


    @Override
    public Integer call() throws InterruptedException {
        airportWork.findTerminal(this);
        airportWork.findLadder(this);
        return null;
    }

    public Destination getDESTINATION() {
        return DESTINATION;
    }


    public int getTerminalNum() {
        return terminalNum.get();
    }

    public void setTerminalNum(int terminalNum) {
        this.terminalNum.set(terminalNum);
    }

    public int getLadderNum() {
        return ladderNum.get();
    }

    public void setLadderNum(int ladderNum) {
        this.ladderNum.set(ladderNum);
    }

    public int getPlaneNum() {
        return planeNum.get();
    }

    public void setPlaneNum(int planeNum) {
        this.planeNum.set(planeNum);
    }
}
