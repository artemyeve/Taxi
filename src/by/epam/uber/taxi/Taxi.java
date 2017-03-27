package by.epam.uber.taxi;

import by.epam.uber.generator.IdGenerator;
import by.epam.uber.location.Location;
import by.epam.uber.taxi.state.AvailableTaxiState;
import by.epam.uber.taxi.state.TaxiState;
import by.epam.uber.timedistance.TimeDistance;


public class Taxi {

    private long taxiId;
    private TaxiState taxiState;
    private Location location;
    private Location targetLocation;
    private Location finalTargetLocation;
    private TimeDistance totalTimeDistance;
    
    public Taxi() {
        taxiId = IdGenerator.generateTaxiId();
        taxiState = new AvailableTaxiState();
        totalTimeDistance = new TimeDistance(0, 0);
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public Location getTargetLocation() {
        return targetLocation;
    }
    
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }
    
    public TaxiState getTaxiState() {
        return taxiState;
    }
    
    public void setTaxiState(TaxiState state) {
        this.taxiState = state;
    }
    
    public long getTaxiId() {
        return taxiId;
    }

    public Location getFinalTargetLocation() {
        return finalTargetLocation;
    }

    public void setFinalTargetLocation(Location finalTargetLocation) {
        this.finalTargetLocation = finalTargetLocation;
    }

    public TimeDistance getTotalTimeDistance() {
        return totalTimeDistance;
    }
    
    public void setTotalTimeDistance(TimeDistance totalTimeDistance) {
        this.totalTimeDistance = totalTimeDistance;
    }
    

    @Override
    public String toString() {
        return String.format("Taxi: id %s, location %s", taxiId, location);
    }

}
