package by.epam.uber.timedistance;

import by.epam.uber.taxi.Taxi;

public class BestValue {

    private Taxi taxi;
    private TimeDistance timeDistance;
    
    public BestValue(Taxi t, TimeDistance td) {
        taxi = t;
        timeDistance = td;
    }
    
    public Taxi getTaxi() {
        return taxi;
    }
    
    public TimeDistance getTimeDistance() {
        return timeDistance;
    }
    
    

}
