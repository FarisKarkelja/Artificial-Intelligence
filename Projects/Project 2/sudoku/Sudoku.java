public class Sudoku {
    //Declaration of an array and array helper together with a constructor
    private final char[][] sudokuBoard;
    private final SudokuHelper sudokuHelper;

    public Sudoku(char[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.sudokuHelper = new SudokuHelper(sudokuBoard);
    }

    public boolean solveSudokuPuzzle() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                //Look for empty cells
                if (sudokuBoard[row][column] == '0') {
                    //Try to enter values [1,9] while checking their validity
                    for (char value = '1'; value <= '9'; value++) {
                        if (sudokuHelper.checkIfValueAllowed(value, row, column)) {
                            sudokuBoard[row][column] = value;
                            //Recursively call the method resulting in either solved board or a necessary backtrack
                            if (solveSudokuPuzzle())
                                return true;
                            sudokuBoard[row][column] = '0';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    //Represent the sudoku board in a desired format
    public void representSudokuPuzzle() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0)
                System.out.println("+ - - - + - - - + - - - +");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    System.out.print("| ");
                System.out.print(sudokuBoard[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+ - - - + - - - + - - - +\n");
    }
}