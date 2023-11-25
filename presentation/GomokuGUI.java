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

    private int[] boardPanelSize = {
            15
    };

    public GomokuGUI() {
        setTitle("Gomoku POOs");
        boardPanels = new JPanel[boardPanelSize[0]][boardPanelSize[0]];
        gomoku = new Gomoku(boardPanelSize[0]);
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        int width = 750;
        int height = 800;
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
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
        prepareActionsBoard();
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
private JPanel boardContainerPanel;
    private ImagePanel outerOuterPanel;
    private JPanel outerPanel;
    private void prepareElementsBoard() {
        String imagePath = "../images/img.jpg";
        outerOuterPanel = new ImagePanel(imagePath); // panel
        outerOuterPanel.setLayout(new BorderLayout());
        outerOuterPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        outerPanel = new JPanel(); // panel
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new BorderLayout());

        boardContainerPanel = new JPanel(); // panel
        fillBoardWithTiles();

        outerPanel.add(boardContainerPanel, BorderLayout.CENTER);
        outerOuterPanel.add(outerPanel, BorderLayout.CENTER);
        add(outerOuterPanel);
    }

    private void fillBoardWithTiles() {
        boardContainerPanel.setLayout(new GridLayout(boardPanelSize[0],boardPanelSize[0])); // No hay espacio entre las casillas
        boardContainerPanel.setOpaque(false);
        boardContainerPanel.setLayout(new GridLayout(15, 15));
        Border blackBorder = BorderFactory.createLineBorder(new Color(52, 30, 18, 255),2);
        boardContainerPanel.setBorder(blackBorder);
        Border emptyBorder = BorderFactory.createEmptyBorder(24, 21, 24, 21);
        boardContainerPanel.setBorder(BorderFactory.createCompoundBorder(blackBorder, emptyBorder));

        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.setPreferredSize(new Dimension(40, 40));
                panel.setBorder(BorderFactory.createLineBorder(new Color(52, 30, 18, 180)));

                panel.setLayout(new OverlayLayout(panel));
                boardPanels[i][j] = panel;
                boardContainerPanel.add(panel);
            }
        }
    }

    private void prepareActionsBoard() {
        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
                boardPanels[i][j].addMouseListener(new PieceClickListener(this, i, j));
            }
        }
    }

    public void handlePieceClick(int row, int col) {
        Color currentPlayerColor = playersColors[currentPlayerTurn - 1];
        gomoku.makeMove(row, col);
        updateBoardView();

        int winner = gomoku.checkWinner();
        if (winner != 0) {
            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
        } else {
            currentPlayerTurn = (currentPlayerTurn % 2) + 1;
            PiecePanel piecePanel = new PiecePanel(currentPlayerColor);
            boardPanels[row][col].removeAll();
            boardPanels[row][col].add(piecePanel);
            boardPanels[row][col].revalidate();
            boardPanels[row][col].repaint();
        }
    }


//    public void refresh() {
//        int[][] boardState = gomoku.getBoardState();
//        for (int i = 0; i < boardPanelSize[0]; i++) {
//            for (int j = 0; j < boardPanelSize[0]; j++) {
//                PiecePanel piecePanel = new PiecePanel(playersColors[boardState[i][j] - 1]);
//                boardPanels[i][j].removeAll();
//                boardPanels[i][j].add(piecePanel);
//                boardPanels[i][j].revalidate();
//                boardPanels[i][j].repaint();
//            }
//        }
//    }




    private void updateBoardView() {
        int[][] boardState = gomoku.getBoardState();
        for (int i = 0; i < boardPanelSize[0]; i++) {
            for (int j = 0; j < boardPanelSize[0]; j++) {
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
