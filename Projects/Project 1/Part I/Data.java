import java.util.*;
import java.io.*;

public class Data {
    public static void readData(String filePath, List<Integer> benefits, List<Integer> weights) {
        //Perform exception handling using try catch block while reading from the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            //Skip first seven lines in order to immediately get to the data required for processing
            for (int i = 0; i < 7; i++) {
                reader.readLine();
            }

            //Read from the file until the end
            while ((line = reader.readLine()) != null) {
                if (line.equals("EOF"))
                    break;

                //Split the id, benefit and weight of each row seperated by a single space
                String[] data = line.split(" ");

                //Store read data into variables
                int benefit = Integer.parseInt(data[1]);
                int weight = Integer.parseInt(data[2]);

                //Assign modified variables to particular collections
                benefits.add(benefit);
                weights.add(weight);
            }
            reader.close();
            //Handle IO (input output) exception
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}