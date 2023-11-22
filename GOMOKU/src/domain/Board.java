package domain;

public class Board {
    private int[][] boardState;

    public Board() {
        boardState = new int[15][15];
        initializeBoard();
    }

    private void initializeBoard() {
        // Inicializar el estado del tablero
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                boardState[i][j] = 0;
            }
        }
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public void makeMove(int row, int col, int playerNumber) {
        // Verificar si la casilla está vacía antes de realizar el movimiento
        if (boardState[row][col] == 0) {
            boardState[row][col] = playerNumber;
            printBoard();  // Imprimir el estado del tablero después de realizar el movimiento
        }
        // Puedes lanzar una excepción o manejar de alguna otra manera si la casilla ya está ocupada
    }

    public int checkWinner() {
        // Verificar si hay un ganador en filas, columnas o diagonales
        // Devolver el número del jugador ganador (1 o 2) o 0 si no hay ganador

        // Implementa la lógica para verificar las filas
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                if (boardState[i][j] != 0 &&
                        boardState[i][j] == boardState[i][j + 1] &&
                        boardState[i][j] == boardState[i][j + 2] &&
                        boardState[i][j] == boardState[i][j + 3] &&
                        boardState[i][j] == boardState[i][j + 4]) {
                    return boardState[i][j];
                }
            }
        }

        // Implementa la lógica para verificar las columnas
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 15; j++) {
                if (boardState[i][j] != 0 &&
                        boardState[i][j] == boardState[i + 1][j] &&
                        boardState[i][j] == boardState[i + 2][j] &&
                        boardState[i][j] == boardState[i + 3][j] &&
                        boardState[i][j] == boardState[i + 4][j]) {
                    return boardState[i][j];
                }
            }
        }

        // Implementa la lógica para verificar las diagonales
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (boardState[i][j] != 0 &&
                        (boardState[i][j] == boardState[i + 1][j + 1] &&
                                boardState[i][j] == boardState[i + 2][j + 2] &&
                                boardState[i][j] == boardState[i + 3][j + 3] &&
                                boardState[i][j] == boardState[i + 4][j + 4])) {
                    return boardState[i][j];
                }

                if (boardState[i + 4][j] != 0 &&
                        (boardState[i + 4][j] == boardState[i + 3][j + 1] &&
                                boardState[i + 4][j] == boardState[i + 2][j + 2] &&
                                boardState[i + 4][j] == boardState[i + 1][j + 3] &&
                                boardState[i + 4][j] == boardState[i][j + 4])) {
                    return boardState[i + 4][j];
                }
            }
        }

        // No hay ganador
        return 0;
    }

    public int convertCoordinatesToIndex(int row, int col) {
        // Convertir las coordenadas de fila y columna a un índice único
        return row * 15 + col;
    }

    public void printBoard() {
        System.out.println("Matriz de estado de juego");
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(boardState[i][j] + " ");
            }
            System.out.println();
        }
    }
}
