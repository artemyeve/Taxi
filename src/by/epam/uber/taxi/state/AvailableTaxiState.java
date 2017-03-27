package by.epam.uber.taxi.state;


public class AvailableTaxiState implements TaxiState {

    public AvailableTaxiState() {
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override 
    public String toString() {
        return "Available";
    }

}
