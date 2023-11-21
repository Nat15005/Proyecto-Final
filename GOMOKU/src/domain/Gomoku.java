package domain;

public class Gomoku {
    private int[][] board;
    private int currentPlayer;

    public Gomoku() {
        // Inicializar el tablero con celdas vacías (0)
        board = new int[15][15];
        initializeBoard();

        // Inicializar el jugador actual (1 o 2)
        currentPlayer = 1;
    }

    private void initializeBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public boolean makeMove(int row, int col) {
        // Verificar si la casilla está vacía
        if (board[row][col] == 0) {
            // Colocar la ficha del jugador actual
            board[row][col] = currentPlayer;
            // Cambiar al siguiente jugador
            switchPlayer();
            return true;  // Movimiento válido
        } else {
            return false;  // Casilla ocupada, movimiento inválido
        }
    }

}