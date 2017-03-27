package by.epam.uber.taxi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.epam.uber.client.Client;
import by.epam.uber.location.Location;
import by.epam.uber.taxi.state.ArrivingTaxiState;
import by.epam.uber.taxi.state.AvailableTaxiState;
import by.epam.uber.timedistance.Calculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.uber.manager.Manager;
import by.epam.uber.taxi.state.ReadyTaxiState;


public class TaxiThread extends Thread {

    private static Logger logger = LogManager.getLogger();
    private static Manager manager = Manager.getInstance();
    private static final Lock lock = new ReentrantLock(true);
    private Taxi taxi;
    private Client client;
    
    public TaxiThread(Taxi taxi, Client client) {
        this.taxi = taxi;
        this.client = client;
    }
    
    
    public void run() {

        lock.lock();

        if (!taxi.getTaxiState().isAvailable()) {

            manager.freeCondition();
            

            while (!taxi.getTaxiState().isAvailable()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR,e.getMessage());
                }
            }

            taxi.setTaxiState(new ArrivingTaxiState());
            lock.unlock();
        } else {
            taxi.setTargetLocation(client.getTargetLocation());
            taxi.setTaxiState(new ArrivingTaxiState());

            manager.freeCondition();
            lock.unlock();
            logger.log(Level.DEBUG,"TAXI THREAD RELEASED LOCK");
        }

        move(taxi.getLocation(), client.getLocation());
        logger.log(Level.INFO,String.format("Taxi %d picking client %d up\n",
                          taxi.getTaxiId(), client.getClientId()));
        taxi.setTaxiState(new ReadyTaxiState());

        move(taxi.getLocation(), client.getTargetLocation());
        logger.log(Level.INFO,String.format("\nTaxi %d brought client %d to target location\n",
                          taxi.getTaxiId(), client.getClientId()));         
        taxi.setTaxiState(new AvailableTaxiState());
    }

    private void move(Location from, Location to) {
        int timeWillTake = new Calculator().calcTimeDistance(from, to).getMinutes();
        logger.log(Level.INFO,"TIMEWILLTAKE " + timeWillTake);
        Location deltaLocation = deltaLocation(from, to, timeWillTake != 0 ? timeWillTake : 1);
        double deltaLong = deltaLocation.getLongitude();
        double deltaLat = deltaLocation.getLatitude();

        while(!(from).equals(to)) {

            from.setLongitude(from.getLongitude() + deltaLong);
            from.setLatitude(from.getLatitude() + deltaLat);
            
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR,e.getMessage());
            }
        }
    }

    private Location deltaLocation(Location from, Location to, int timeWillTake) {
        double deltaLong = (to.getLongitude() - from.getLongitude())/timeWillTake;
        double deltaLat = (to.getLatitude() - from.getLatitude())/timeWillTake;
        return new Location(deltaLat, deltaLong);
    }
    
}
