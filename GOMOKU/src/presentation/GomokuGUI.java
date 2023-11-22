package presentation;

import domain.Gomoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.io.File;

public class GomokuGUI extends JFrame {

    private Gomoku gomoku;
    private JPanel[][] boardPanels;
    private int currentPlayerTurn = 1;

    private Color[] playersColors = {
            Color.BLACK,
            Color.WHITE
    };

    public GomokuGUI() {
        setTitle("Gomoku POOs");
        boardPanels = new JPanel[15][15];
        gomoku = new Gomoku(); // Puedes ajustar la inicialización según tus necesidades
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        int width = 750;
        int height = 800;
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false); // Evitar que la ventana sea redimensionable


        prepareElementsMenu();
        prepareElementsBoard();
    }

    private void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        prepareActionsMenu();
        prepareActionsBoard();  // Agrega esta línea para preparar las acciones del tablero
    }

    private void confirmExit() {
        int option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void prepareElementsMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Options");
        menuBar.add(fileMenu);

        String[] menuItems = {"Nuevo", "Abrir", "Guardar", "Exit"};

        for (int i = 0; i < menuItems.length; i++) {
            JMenuItem menuItem = new JMenuItem(menuItems[i]);
            fileMenu.add(menuItem);
            if (i == menuItems.length - 2 || i == 0) {
                fileMenu.addSeparator();
            }
        }
    }

    private void prepareActionsMenu() {
        JMenuBar menuBar = getJMenuBar();
        if (menuBar != null) {
            JMenu fileMenu = menuBar.getMenu(0);

            int menuItemIndex = 0;
            for (Component component : fileMenu.getMenuComponents()) {
                if (component instanceof JMenuItem) {
                    JMenuItem menuItem = (JMenuItem) component;
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String itemName = menuItem.getText();
                            switch (itemName) {
                                case "Nuevo":
                                    JOptionPane.showMessageDialog(null, "Función Nuevo en construcción");
                                    break;
                                case "Abrir":
                                    openFile();
                                    break;
                                case "Guardar":
                                    JOptionPane.showMessageDialog(null, "Función Guardar en construcción");
                                    break;
                                case "Exit":
                                    confirmExit();
                                    break;
                            }
                        }
                    });
                    menuItemIndex++;
                }
            }
        }
    }

    private void prepareElementsBoard() {
        String imagePath = "./images/img.jpg";
        ImagePanel outerOuterPanel = new ImagePanel(imagePath);

        outerOuterPanel.setLayout(new BorderLayout());
        outerOuterPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new BorderLayout());

        JPanel boardContainerPanel = new JPanel();
        boardContainerPanel.setOpaque(false);
        boardContainerPanel.setLayout(new GridLayout(15, 15));

        Border blackBorder = BorderFactory.createLineBorder(new Color(52, 30, 18, 255),2);
        boardContainerPanel.setBorder(blackBorder);

        Border emptyBorder = BorderFactory.createEmptyBorder(24, 21, 24, 21);
        boardContainerPanel.setBorder(BorderFactory.createCompoundBorder(blackBorder, emptyBorder));

        // Agregar números en una línea debajo de cada columna
//        JPanel columnNumbersPanel = new JPanel();
//        columnNumbersPanel.setOpaque(false);
//        columnNumbersPanel.setLayout(new GridLayout(15, 1)); // Ajustar el espacio horizontal
////
//        // Agregar números en una línea a la izquierda de cada fila
//        JPanel rowNumbersPanel = new JPanel();
//        rowNumbersPanel.setOpaque(false);
//        rowNumbersPanel.setLayout(new GridLayout(15, 1));

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.setPreferredSize(new Dimension(40, 40));
                panel.setBorder(BorderFactory.createLineBorder(new Color(52, 30, 18, 255)));
                boardPanels[i][j] = panel;
                boardContainerPanel.add(panel);
            }
//            JLabel columnLabel = new JLabel(String.format("%d", i + 1), SwingConstants.RIGHT);
//            columnLabel.setForeground(new Color(52, 30, 18, 255));
//            columnNumbersPanel.add(columnLabel);
////
//            JLabel rowLabel = new JLabel(String.format("%d    ", i + 1), SwingConstants.LEFT);
//            rowLabel.setForeground(new Color(52, 30, 18, 255));
//            rowNumbersPanel.add(rowLabel);
        }

        outerPanel.add(boardContainerPanel, BorderLayout.CENTER);

//        outerPanel.add(columnNumbersPanel, BorderLayout.NORTH);
//        outerPanel.add(rowNumbersPanel, BorderLayout.WEST);

        outerOuterPanel.add(outerPanel, BorderLayout.CENTER);
        add(outerOuterPanel);
    }

    private void prepareActionsBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                boardPanels[i][j].addMouseListener(new PieceClickListener(this, i, j));
            }
        }
    }


    public void handlePieceClick(int row, int col) {
        Color currentPlayerColor = playersColors[currentPlayerTurn - 1];
        gomoku.makeMove(row, col); // Asegúrate de que makeMove acepte solo row y col como argumentos
        updateBoardView(); // Actualiza la vista después de cada movimiento

        // Verifica si hay un ganador
        int winner = gomoku.checkWinner();
        if (winner != 0) {
            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
            // Puedes realizar acciones adicionales después de que alguien gane
        } else {
            // Cambia al siguiente jugador
            currentPlayerTurn = (currentPlayerTurn % 2) + 1;
        }
    }


    private void updateBoardView() {
        int[][] boardState = gomoku.getBoardState();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (boardState[i][j] != 0) {
                    PiecePanel piecePanel = new PiecePanel(playersColors[boardState[i][j] - 1]);
                    boardPanels[i][j].removeAll();
                    boardPanels[i][j].add(piecePanel);
                }
            }
        }
        repaint();
    }




    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Abriendo archivo: " + selectedFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        GomokuGUI window = new GomokuGUI();
        window.setVisible(true);
    }
}
