import java.io.*;
import java.util.*;

public class Data {
    public static List<City> loadData(String filePath) {
        //Declaration of a collection of cities
        List<City> cities = new ArrayList<>();

        //Perform exception handling using try catch block while reading from the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            //Skip first six lines in order to immediately get to the data required for processing
            for (int i = 0; i < 6; i++) {
                reader.readLine();
            }

            //Read from the file until the end
            while ((line = reader.readLine()) != null) {
                if (line.equals("EOF"))
                    break;

                //Split the id, x and y of each row seperated by a single space
                String[] data = line.split(" ");
                //Store read data into variables
                int id = Integer.parseInt(data[0]);
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                cities.add(new City(id, x, y));
            }
            reader.close();
            //Handle IO (input output) exception
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file." + e.getMessage());
        }
        return cities;
    }
}