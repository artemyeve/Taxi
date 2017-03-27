package by.epam.uber.taxi.state;

/**
 * Created by Acer on 12.03.2017.
 */
public class ArrivingTaxiState implements TaxiState {
    public ArrivingTaxiState() {
    }
    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public String toString() {
        return "Arriving";
    }
}
