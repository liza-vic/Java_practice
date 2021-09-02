package by.bsu.practice.threads.service;

import by.bsu.practice.threads.entity.Airport;
import by.bsu.practice.threads.entity.Destination;
import by.bsu.practice.threads.entity.Passenger;
import by.bsu.practice.threads.entity.Plane;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class AirportWork {
    private Semaphore semaphoreTerminal;
    private Semaphore semaphoreLadder;
    private ReentrantLock lock = new ReentrantLock();
    private Airport airport;

    public AirportWork(Semaphore semaphoreTerminal, Semaphore semaphoreLadder, Airport airport) {
        this.semaphoreTerminal = semaphoreTerminal;
        this.semaphoreLadder = semaphoreLadder;
        this.airport = airport;
    }

    public  void findTerminal(Passenger passenger) {

        //семафор для того чтобы кол-во людей было одновременно не больше кол-ва терминалов
        try{
            semaphoreTerminal.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        lock.lock();

        int i = 0;
        boolean isFind = true;
        //ищем свободный терминал
        while(isFind){

            if(!airport.getTerminals()[i].getIsBusy()){
                airport.getTerminals()[i].setIsBusy(true);
                passenger.setTerminalNum(i);
                isFind = false;
            }
            i++;
        }
        lock.unlock();

        System.out.println("Passenger " + Thread.currentThread().getName() +" came to terminal " + passenger.getTerminalNum());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        //находим самолет для пассажира по его городу
        findPlane(passenger);
        //освобождаем терминал
        airport.getTerminals()[passenger.getTerminalNum()].setIsBusy(false);
        System.out.println("Passenger " + Thread.currentThread().getName() + passenger.getDESTINATION() +" moved from terminal " + passenger.getTerminalNum());
        semaphoreTerminal.release();
    }

    //ищем свободный трап аналогично терминалу
    public void findLadder(Passenger passenger){
        try{
            semaphoreLadder.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        lock.lock();
        int i = 0;
        boolean isFind = true;
        //ищем свободный трап
        while(isFind){

            if(!airport.getLadders()[i].getIsBusy()){
                airport.getLadders()[i].setIsBusy(true);
                passenger.setLadderNum(i);
                isFind = false;
            }
            i++;
        }

        lock.unlock();
        System.out.println("Passenger " + Thread.currentThread().getName() +" came to ladder " + passenger.getLadderNum());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        //освобождаем трап
        airport.getLadders()[passenger.getLadderNum()].setIsBusy(false);
        System.out.println("Passenger " + Thread.currentThread().getName() + passenger.getDESTINATION() +" moved from ladder " + passenger.getLadderNum());

        semaphoreLadder.release();
        fullPlanesCheck();
    }

    //утправляем полные самолеты
    private void fullPlanesCheck(){
        for(Plane plane : airport.getPlanes()){
            if(plane.isFull()){
                plane.setTown(null);
                plane.setPassengersNow(0);
                plane.setIsBusy(false);
            }
        }
    }

    private void findPlane(Passenger passenger){
        lock.lock();
        boolean isFind = false;
        //перебираем все самолеты
        for(Plane plane : airport.getPlanes()){

            //если у самолета та же дальность что нужна пассажиру рассматриваем самолет дальше
            if(passenger.getDESTINATION().getZone().equals(plane.zone)){

                //если в самолете нет людей резервируем самолет для города пассажира
                if(!plane.getIsBusy()){
                    plane.setIsBusy(true);
                    plane.setTown(passenger.getDESTINATION());
                    plane.addPassenger();
                    passenger.setPlaneNum(plane.id);
                    System.out.println("Passenger " + Thread.currentThread().getName() + passenger.getDESTINATION() + " got place on plane " + plane.id);
                    isFind = true;
                    break;
                }
                // если самолет еще не полон и город тот что нужен пассажиру, сажаем его туда
                else if(plane.getTown().equals(passenger.getDESTINATION()) && !plane.isFull()){
                    plane.addPassenger();
                    passenger.setPlaneNum(plane.id);
                    System.out.println("Passenger " + Thread.currentThread().getName() + passenger.getDESTINATION() + " got place on plane " + plane.id);
                    isFind = true;
                    break;
                }
            }
        }

        //если среди самолетов не нашли нужного или свободного, ждем какое-то время и пробуем еще раз
        if(!isFind){
                try {
                    Thread.sleep(500);
                    findPlane(passenger);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
        }
        lock.unlock();

    }


}
