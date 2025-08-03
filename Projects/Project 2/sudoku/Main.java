import java.text.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String file = "C:\\Users\\Korisnik\\Desktop\\assignment 2\\sudoku.txt";
        List<char[][]> collectionOfSudoku = Data.readSudokuData(file);

        int counter = 1;
        long startTime = System.nanoTime();

        for (char[][] sudokuPuzzle : collectionOfSudoku) {
            Sudoku sudoku = new Sudoku(sudokuPuzzle);
            System.out.println("   <--- Sudoku " + counter + " --->");
            sudoku.representSudokuPuzzle();

            System.out.println("Attempting to solve Sudoku " + counter + "...\n");

            if (sudoku.solveSudokuPuzzle()) {
                System.out.println("    <--- Solved! --->");
                sudoku.representSudokuPuzzle();
            } else {
                System.out.println("Provided sudoku puzzle cannot be solved.");
            }

            if (counter < collectionOfSudoku.size()) {
                System.out.println("Proceeding to Sudoku " + (counter + 1) + "...\n");
            }
            counter++;
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000.0;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Execution time: " + decimalFormat.format(duration) + " milliseconds.");
    }
}