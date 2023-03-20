package nl.tudelft.jpacman.pageUtil;

import javax.swing.*;
import java.awt.*;

/**
 * The button using in any panel
 * */
public class ButtonOnPage extends JButton {
    /**
     * Create button by pass image and bounds
     * */
    public ButtonOnPage(ImageIcon image, Rectangle rect){
        setIcon(image);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setBounds(rect);
    }
}
