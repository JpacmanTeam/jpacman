package nl.tudelft.jpacman.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LosePanel extends JPanel {
    private int boardWidth = 380;
    private int boardHeight = 420;
    private Image backgroundImage;

    public LosePanel(){
        setLayout(null);
        backgroundImage = new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\lose.png").getImage();

        JButton ReplayButton = new JButton(new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\replay.png"));
        ReplayButton.setBounds(130,200,100, 40);
        ReplayButton.addActionListener(new ActionListener() {
            //Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(ReplayButton);

        JButton Homebutton = new JButton(new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\home.png"));
        Homebutton.setBounds(130,250,100, 40);
        Homebutton.addActionListener(new ActionListener() {
            //Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(Homebutton);
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

