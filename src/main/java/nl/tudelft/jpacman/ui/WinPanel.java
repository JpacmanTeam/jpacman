package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.pageUtil.ButtonOnPage;

import javax.swing.*;
import java.awt.*;

/**
 * A panel defined by a lost page of game includes buttons.
 */
public class WinPanel extends JPanel {

    /**
     * static source of background
     */
    private final String BACKGROUND_SOURCE = "src/main/resources/component/winBackground.png";

    /**
     * static source of retry button
     */
    private final String RETRY_BUTTON_SOURCE = "src/main/resources/component/retryButton.png";
    /**
     * static source of next button
     */
    private final String NEXT_BUTTON_SOURCE = "src/main/resources/component/nextButton.png";

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
    private final int pageHeight = 420;

    /**
     * background image object
     * */
    private final Image background;

    /**
     * retry button object
     * */
    private final ButtonOnPage retryButton;
    /**
     * next button object
     * */
    private final ButtonOnPage nextButton;
    /**
     * home button object
     * */
    private final ButtonOnPage homeButton;

    /**
     * Create lose panel and add the buttons
     * */
    public WinPanel(){
        setLayout(null);

        //define background image by source
        background = new ImageIcon(BACKGROUND_SOURCE).getImage();

        //define retry button by image source and size
        retryButton = new ButtonOnPage(new ImageIcon(RETRY_BUTTON_SOURCE),new Rectangle(130,200,100,40));
        //define next button by image source and size
        nextButton = new ButtonOnPage(new ImageIcon(NEXT_BUTTON_SOURCE),new Rectangle(130,250,100,40));
        //define home button by image source and size
        homeButton = new ButtonOnPage(new ImageIcon(HOME_BUTTON_SOURCE),new Rectangle(130,300,100,40));

        add(retryButton);
        add(nextButton);
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

    public ButtonOnPage getRetryButton() {
        return retryButton;
    }

    public ButtonOnPage getNextButton() {
        return nextButton;
    }
}
