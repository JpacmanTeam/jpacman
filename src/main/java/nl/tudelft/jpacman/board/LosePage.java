package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.ui.LosePanel;
import javax.swing.*;

public class LosePage extends JFrame {

    public LosePage(){
        // Create a new BoardPanel object
        LosePanel losePanel = new LosePanel();

        // Add the BoardPanel to the JFrame
        add(losePanel);

        // Set the size, title, and visibility of the JFrame
        setSize(losePanel.getPreferredSize());
        setTitle("JPacman");
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
