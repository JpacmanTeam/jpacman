package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.ui.StartPanel;

import javax.swing.*;

public class Start extends JFrame {

    public Start() {
        // Create a new BoardPanel object
        StartPanel StartPanel = new StartPanel();

        // Add the BoardPanel to the JFrame
        add(StartPanel);

        // Set the size, title, and visibility of the JFrame
        setSize(StartPanel.getPreferredSize());
        setTitle("JPacman");
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
