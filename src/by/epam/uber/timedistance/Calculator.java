package by.epam.uber.timedistance;

import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.uber.client.Client;
import by.epam.uber.exception.TaxiException;
import by.epam.uber.location.Location;
import by.epam.uber.taxi.Taxi;


public class Calculator {

    private static Logger logger = LogManager.getLogger();
    private static double SPEED = 1000.0;
    private static double EARTH_RADIUS = 6371e3;
    
        public BestValue calcBestValue(Set<Taxi> taxis, Client client) throws TaxiException {
        logger.log(Level.DEBUG,"TAXIS EMPTY: " + taxis.isEmpty());
        if (taxis.isEmpty()) {
            throw new TaxiException();
        }
        logger.log(Level.DEBUG,"In calcBestValue");
        TimeDistance bestTimeDist = null;
        TimeDistance timeDist = null;
        Taxi bestTaxi = null;
        
        for (Taxi taxi : taxis) { 
            if (taxi.getTaxiState().isAvailable()
                && (bestTimeDist == null 
                    | (timeDist = calcTimeDistance(
                            taxi.getLocation(), client.getLocation()))
                        .compareTo(bestTimeDist) < 0)) {
                
                bestTimeDist = timeDist; 
                bestTaxi = taxi;
            }
        }

            if (bestTaxi == null) {

                for (Taxi taxi : taxis) {
                    timeDist = addTimeDistances(
                            taxi.getTotalTimeDistance(),
                            calcTimeDistance(taxi.getFinalTargetLocation(), client.getLocation()));

                    if (bestTimeDist == null || timeDist.compareTo(bestTimeDist) < 0) {
                        bestTimeDist = timeDist;
                        bestTaxi = taxi;
                    }
                }

            }

            bestTaxi.setTotalTimeDistance(
                    addTimeDistances(bestTimeDist,
                            calcTimeDistance(client.getLocation(),
                                    client.getTargetLocation())));

            bestTaxi.setFinalTargetLocation(client.getTargetLocation());

            return new BestValue(bestTaxi, bestTimeDist);
        }



    public TimeDistance calcTimeDistance(Location loc1, Location loc2) {
        
        double distance = calcDistance(
                loc1.getLatitude(), loc1.getLongitude(),
                loc2.getLatitude(), loc2.getLongitude());
        
        logger.log(Level.DEBUG,"In CalcTimeDistance: distance - " + distance);
        return new TimeDistance((int) distance, SPEED);
    }

    public TimeDistance addTimeDistances(TimeDistance td1, TimeDistance td2) {
        int meters = td1.getMeters() + td2.getMeters();
        int minutes = td1.getMinutes() + td2.getMinutes();

        return new TimeDistance(meters, minutes);
    }

    private double calcDistance(double lat1, double lon1, double lat2, double lon2) {
         double lat1Radian = Math.toRadians(lat1);
         double lat2Radian = Math.toRadians(lat2);
         double latDiff = Math.toRadians(lat2 - lat1);
         double longDiff = Math.toRadians(lon2 - lon1);
         
         double a = Math.pow(Math.sin(latDiff/2), 2) +
                    Math.cos(lat1Radian) * Math.cos(lat2Radian) *
                    Math.pow(Math.sin(longDiff/2), 2);
         
         double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
         
         double distance = EARTH_RADIUS * c;
         
         return distance;
         
    }
}
