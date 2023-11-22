package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

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


    /**
     * {@inheritDoc}
     * <p>
     * Overrides the {@code paintComponent} method to draw a circular piece with the specified color.
     * </p>
     *
     * @param g The {@code Graphics} context within which to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Ajusta el tamaño del círculo como un porcentaje del tamaño del JPanel
        double circleSizePercentage = 1.0;  // Puedes ajustar este valor según tu preferencia
        int circleSize = (int) (Math.min(width, height) * circleSizePercentage);

        // Calcula la posición central del círculo tanto vertical como horizontalmente
        int xOffset = (width - circleSize) / 2;
        int yOffset = (height - circleSize) / 2;

        Path2D path = new Path2D.Double();
        path.moveTo(xOffset + circleSize / 2, yOffset);
        path.lineTo(xOffset + circleSize, yOffset + circleSize / 2);
        path.lineTo(xOffset + circleSize / 2, yOffset + circleSize);
        path.lineTo(xOffset, yOffset + circleSize / 2);
        path.closePath();

        g2d.setColor(pieceColor);
        g2d.fill(path);
    }
}
