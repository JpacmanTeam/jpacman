package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.pageUtil.ButtonOnPage;

import javax.swing.*;
import java.awt.*;

/**
 * A panel defined by a home page of game includes buttons.
 */
public class StartPanel extends JPanel {

    /**
     * static source of background
     */
    private final String BACKGROUND_SOURCE = "src/main/resources/component/homeBackground.png";

    /**
     * static source of start button
     */
    private final String START_BUTTON_SOURCE = "src/main/resources/component/startButton.png";

    /**
     * width of page
     */
    private final int pageWidth = 368;

    /**
     * height of page
     */
    private final int pageHeight = 420;

    /**
     * background image object
     * */
    private final Image background;

    /**
     * start button object
     * */
    private final ButtonOnPage startButton;

    /**
     * Create start panel and add the buttons
     * */
    public StartPanel() {
        setLayout(null);

        //define background image by source
        background = new ImageIcon(BACKGROUND_SOURCE).getImage();

        //define start button by image source and size
        startButton = new ButtonOnPage(new ImageIcon(START_BUTTON_SOURCE),new Rectangle(126,200,120,40));

        add(startButton);
    }

    public ButtonOnPage getStartButton() {
        return startButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(pageWidth, pageHeight);
    }
}
