package by.epam.uber.generator;

import by.epam.uber.client.Client;
import by.epam.uber.exception.TaxiException;
import by.epam.uber.manager.Manager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Acer on 16.03.2017.
 */
public class ClientActivityGenerator {
    private static Logger logger = LogManager.getLogger(ClientActivityGenerator.class);

    public static void generateClientActivity(int number) {
        for (int i = 0; i < number; i++) {
            Client client = new Client();
            client.setLocation(LocationGenerator.generateLocation());
            client.setTargetLocation(LocationGenerator.generateLocation());
            Manager manager = Manager.getInstance();
            try {
                manager.chooseTaxiFor(client);

            } catch (TaxiException e) {
                logger.log(Level.ERROR,"Cannot choose taxi. No taxis added");
            }

        }
    }
}
