package by.epam.uber.generator;

import by.epam.uber.taxi.Taxi;
import by.epam.uber.manager.Manager;

public class TaxiGenerator {

    public static void generateTaxis(int number) {
        for (int i = 0; i < number; i++) {
            Taxi taxi = new Taxi();
            taxi.setLocation(LocationGenerator.generateLocation());
            Manager.getInstance().addTaxi(taxi);
        }
    }
}
