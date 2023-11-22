package domain;

public class Gomoku {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;

    public Gomoku() {
        board = new Board();
        players = new Player[]{new HumanPlayer(), new ComputerPlayer()};
        currentPlayerIndex = 0;
    }

    public int checkWinner() {
        return board.checkWinner();
    }

    public int[][] getBoardState() {
        return board.getBoardState();
    }

    public void makeMove(int row, int col) {
        int currentPlayerNumber = currentPlayerIndex + 1;
        board.makeMove(row, col, currentPlayerNumber);

        // Verificar si hay un ganador después de cada movimiento
        int winner = board.checkWinner();
        if (winner != 0) {
            System.out.println("Player " + winner + " wins!");
            // Puedes realizar acciones adicionales después de que alguien gane
        } else {
            // Cambiar al siguiente jugador
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        }
    }

    // Otros métodos para manejar la lógica del juego
}
