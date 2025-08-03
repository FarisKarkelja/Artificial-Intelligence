public class SudokuHelper {
    //Declaration of an array together with a constructor
    private final char[][] sudokuBoard;

    public SudokuHelper(char[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    //Manage the value validity by applying row, column, box rule
    public boolean checkIfValuePossible(char value, int row, int column) {
        for (int i = 0; i < 9; i++) {
            if (sudokuBoard[row][i] == value || sudokuBoard[i][column] == value)
                return false;
        }

        int boxRow = row - (row % 3);
        int boxColumn = column - (column % 3);
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxColumn; j < boxColumn + 3; j++) {
                if (sudokuBoard[i][j] == value)
                    return false;
            }
        }
        return true;
    }

    public boolean checkIfValueAllowed(char value, int row, int column) {
        return checkIfValuePossible(value, row, column);
    }
}