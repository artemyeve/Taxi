package by.epam.uber.parser;

/**
 * Created by Acer on 16.03.2017.
 */


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Acer on 03.03.2017.
 */
public class DataParser {
    private static Logger logger = LogManager.getLogger(DataParser.class);
    private static final Pattern PATTERN = Pattern.compile("(\\d)+||(\\d+(//.)\\d+)||((-)(\\d)+)|((-)(\\d+(//.)\\d+))");
    private static final int NUM_COORD = 4;

    public static List<String> getCoordinates(List<String> fileData) {
        List<String> coordinates = new ArrayList<>();
        Matcher matcher;
        String[] strings;

        for (String line : fileData) {
            int count = 0;
            strings = line.trim().split("\\s+");
            if (strings.length == NUM_COORD) {
                for (String singleNumber : strings) {
                    matcher = PATTERN.matcher(singleNumber);
                    if (matcher.find()) {
                        count++;
                    }
                }
                if (count == NUM_COORD) {
                    coordinates.add(line);
                }
            }
        }
        logger.log(Level.INFO, "Correct coordinates");
        return coordinates;
    }

}
