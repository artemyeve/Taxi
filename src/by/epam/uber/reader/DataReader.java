package by.epam.uber.reader;

/**
 * Created by Acer on 16.03.2017.
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by Acer on 19.02.2017.
 */
public class DataReader {
    private static Logger logger = LogManager.getLogger(DataReader.class);

    public static List<String> readFile(String path)  {

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));

        } catch (IOException ex) {
            logger.log(Level.FATAL,"Input error",ex);
            throw new RuntimeException(ex);
        }

        return lines;
    }


}
