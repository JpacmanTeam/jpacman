package nl.tudelft.jpacman.ui;



import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Start;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;


public class StartPanel extends JPanel {

    private int boardWidth = 380;
    private int boardHeight = 420;
    private Image backgroundImage;
    private PacManUI pacManUI;


    public StartPanel() {
        setLayout(null);

        backgroundImage = new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\back.png").getImage();
        JButton startButton = new JButton(new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\stbut.png"));
        startButton.setBounds(130,200,100, 40);
        startButton.addActionListener(new ActionListener() {

                //Override
                public void actionPerformed(ActionEvent e) {
/*
                    // Create a new Launcher object
                    Launcher launcher = new Launcher();

                    // Create a new game instance
                    launcher.makeGame();

                    // Create a new PacManUI object
                    PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
                    PacManUI pacManUI = builder.build(launcher.getGame());
                    // Remove the home page components

                    // Add any other components to be removed

                    // Resize the window to fit the Pacman game
                    setSize(pacManUI.getSize());

                    // Repaint the window
                    revalidate();
                    repaint();

                    // Display the Pac-Man game UI
                    pacManUI.start();

*/
                }
        });

        add(startButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardWidth, boardHeight);
    }
}
