package presentation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PieceClickListener extends MouseAdapter {
    private GomokuGUI gomokuGUI;
    private int row;
    private int col;

    public PieceClickListener(GomokuGUI gomokuGUI, int row, int col) {
        this.gomokuGUI = gomokuGUI;
        this.row = row;
        this.col = col;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gomokuGUI.handlePieceClick(row, col);
    }
}
