package presentation;

import javax.swing.*;
import java.awt.*;

public class PiecePanel extends JPanel {
    private Color pieceColor;
    public PiecePanel(Color color) {
        this.pieceColor = color;
        setOpaque(false);
    }

    public void setColor(Color color) {
        this.pieceColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Ajusta el tamaño del círculo como un porcentaje del tamaño del JPanel
        double circleSizePercentage = 0.8;  // Puedes ajustar este valor según tu preferencia
        int circleSize = (int) (Math.min(width, height) * circleSizePercentage);

        // Calcula la posición central del círculo tanto vertical como horizontalmente
        int xOffset = (width - circleSize) / 2;
        int yOffset = (height - circleSize) / 2;

        // Dibuja un círculo en lugar de un rombo
        g2d.setColor(pieceColor);
        g2d.fillOval(xOffset, yOffset, circleSize, circleSize);
    }
}
