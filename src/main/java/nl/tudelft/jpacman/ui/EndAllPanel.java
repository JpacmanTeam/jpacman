package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.pageUtil.ButtonOnPage;

import javax.swing.*;
import java.awt.*;

/**
 * A panel defined by a lost page of game includes buttons.
 * @author Saksit Sirisakda
 */
public class EndAllPanel extends JPanel {

    /**
     * static source of background
     */
    private final String BACKGROUND_SOURCE = "src/main/resources/component/EndAllBackground.png";

    /**
     * static source of home button
     */
    private final String HOME_BUTTON_SOURCE = "src/main/resources/component/homeButton.png";

    /**
     * width of page
     */
    private final int pageWidth = 368;

    /**
     * height of page
     */
    private final int pageHeight = 400;

    /**
     * background image object
     * */
    private final Image background;

    /**
     * home button object
     * */
    private final ButtonOnPage homeButton;

    /**
     * Create lose panel and add the buttons
     * */
    public EndAllPanel(){
        setLayout(null);

        //define background image by source
        background = new ImageIcon(BACKGROUND_SOURCE).getImage();

        //define home button by image source and size
        homeButton = new ButtonOnPage(new ImageIcon(HOME_BUTTON_SOURCE),new Rectangle(130,275,120,40));

        add(homeButton);
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

    public ButtonOnPage getHomeButton() {
        return homeButton;
    }

}

