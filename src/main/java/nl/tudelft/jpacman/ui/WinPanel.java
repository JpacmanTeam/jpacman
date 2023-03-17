package nl.tudelft.jpacman.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinPanel extends JPanel {
    private int boardWidth = 380;
    private int boardHeight = 420;
    private Image backgroundImage;

     public WinPanel() {

         setLayout(null);
         backgroundImage = new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\win.png").getImage();
         JButton nextButton = new JButton(new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\next.png"));
         nextButton.setBounds(130,200,100, 40);
         nextButton.addActionListener(new ActionListener() {
             //Override
             public void actionPerformed(ActionEvent e) {

             }
         });
         add(nextButton);

         JButton ReplayButton = new JButton(new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\replay.png"));
         ReplayButton.setBounds(130,250,100, 40);
         ReplayButton.addActionListener(new ActionListener() {
             //Override
             public void actionPerformed(ActionEvent e) {

             }
         });
         add(ReplayButton);

         JButton Homebutton = new JButton(new ImageIcon("src\\main\\java\\nl\\tudelft\\jpacman\\sprite\\home.png"));
         Homebutton.setBounds(130,300,100, 40);
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
