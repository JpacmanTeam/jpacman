package nl.tudelft.jpacman.board;


import nl.tudelft.jpacman.ui.WinPanel;

import javax.swing.*;

public class WinPage extends JFrame{


        public WinPage() {
            // Create a new BoardPanel object
            WinPanel winPanel = new WinPanel();

            // Add the BoardPanel to the JFrame
            add(winPanel);

            // Set the size, title, and visibility of the JFrame
            setSize(winPanel.getPreferredSize());
            setTitle("JPacman");
            setVisible(true);
            setLayout(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

}
