import java.util.*;
import java.io.*;

public class Data {
    public static List<char[][]> readSudokuData(String filePath) {
        //Declaration of a collection of sudoku puzzles
        List<char[][]> collectionOfSudoku = new ArrayList<>();

        //Perform exception handling using try catch block while reading from the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            //Skip first four lines in order to immediately get to the data required for processing
            for (int i = 0; i < 4; i++)
                reader.readLine();

            //Declaration of initial placeholders
            int row = 0;
            char[][] sudokuBoard = new char[9][9];

            String line;

            //Read from the file until the end
            while ((line = reader.readLine()) != null) {
                if (line.equals("EOF"))
                    break;

                //Manage 9 sudoku boards
                if (line.startsWith("SUDOKU")) {
                    if (row == 9)
                        collectionOfSudoku.add(sudokuBoard);
                    sudokuBoard = new char[9][9];
                    row = 0;
                } else if (line.matches("[0-9]{9}")) {
                    sudokuBoard[row++] = line.toCharArray();
                }
            }

            //Add the 10th sudoku board manually
            if (row == 9)
                collectionOfSudoku.add(sudokuBoard);

            reader.close();
            //Handle IO (input output) exception
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return collectionOfSudoku;
    }
}