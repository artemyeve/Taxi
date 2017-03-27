
package by.epam.uber.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import by.epam.uber.client.Client;
import by.epam.uber.exception.TaxiException;
import by.epam.uber.taxi.Taxi;
import by.epam.uber.taxi.TaxiThread;
import by.epam.uber.timedistance.BestValue;
import by.epam.uber.timedistance.Calculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Manager {

    private static  Logger logger = LogManager.getLogger();
    private static Manager instance = null;
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static Lock lock = new ReentrantLock();
    private Condition taxiStateChangeCondition = lock.newCondition();
    private Calculator calculator = new Calculator();
    private Set<Taxi> taxis = new HashSet<>();

    private Manager() {
    }

    public static Manager getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new Manager();
                    instanceCreated.set(true);
                }
            } catch (ExceptionInInitializerError e) {
                logger.log(Level.FATAL,"Could not create Manager instance");
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    
    public int getTaxisSize() {
        return taxis.size();
    }
    
    public void addTaxi(Taxi taxi) {
        try {
            lock.lock();
            taxis.add(taxi);
            logger.log(Level.INFO,"Added taxi: " +taxi);
        } finally {
            lock.unlock();
        }
    }
    
    public Taxi chooseTaxiFor(Client client) throws TaxiException {
        lock.lock();
        try {

            BestValue bestVal = calculator.calcBestValue(taxis, client);
            
            Taxi taxi = bestVal.getTaxi();

            
            new TaxiThread(taxi, client).start();
            

            try {
                taxiStateChangeCondition.await();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR,e.getMessage());
            }
            
            return taxi;
        } catch(TaxiException e) {
            throw new TaxiException(e);
        } finally {
            lock.unlock();
        }
        
    }
    
    public void freeCondition() {
        lock.lock();
        try {
            taxiStateChangeCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}
