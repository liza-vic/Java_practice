package by.bsu.practice.threads;

import by.bsu.practice.threads.entity.Airport;
import by.bsu.practice.threads.entity.Destination;
import by.bsu.practice.threads.entity.Passenger;
import by.bsu.practice.threads.service.AirportWork;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    private static final Airport AIRPORT = Airport.getInstance();
    private static final Semaphore SEMAPHORE_FIRST = new Semaphore(Airport.TERMINALS, true);
    private static final Semaphore SEMAPHORE_SECOND = new Semaphore(Airport.LADDERS, true);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static AirportWork airportWorking = new AirportWork(SEMAPHORE_FIRST,SEMAPHORE_SECOND,AIRPORT);
    public static void main(String[] args) {

        List<Callable<Integer>> callable = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            callable.add(new Passenger(Destination.values()[(int)(Math.random() * Destination.values().length - 1)], airportWorking));
        }

        try {
            executorService.invokeAll(callable);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
