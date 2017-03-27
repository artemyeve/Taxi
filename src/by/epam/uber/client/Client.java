package by.epam.uber.client;

import by.epam.uber.location.Location;
import by.epam.uber.generator.IdGenerator;


public class Client extends Thread {
    private long clientId;
    private Location location;
    private Location targetLocation;
    
    public Client() {
        clientId = IdGenerator.generateClientId();
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
    
    public void setTargetLocation(Location targetLocation)
    {
        this.targetLocation = targetLocation;
    }
    
    public long getClientId() {

        return clientId;
    }
    
}
