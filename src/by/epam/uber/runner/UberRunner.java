package by.epam.uber.runner;

import by.epam.uber.generator.ClientActivityGenerator;
import by.epam.uber.generator.TaxiGenerator;

public class UberRunner {
    
    public static void main(String[] args) {

        TaxiGenerator.generateTaxis(2);

        ClientActivityGenerator.generateClientActivity(4);
        

    }
}
