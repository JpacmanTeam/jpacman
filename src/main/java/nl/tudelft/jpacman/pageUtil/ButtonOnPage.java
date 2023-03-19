package nl.tudelft.jpacman.pageUtil;

import javax.swing.*;
import java.awt.*;

public class ButtonOnPage extends JButton {
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
