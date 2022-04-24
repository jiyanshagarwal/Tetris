package objects;

import game.Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Jiyansh
 */
public final class DialogBox extends GameObject {

    public static final int INFORMATIONAL = 0;
    public static final int CONFIRMATION = 1;

    public static final int CONFIRMED = 1;
    public static final int CANCELED = 0;
    public static final int NULL = -1;

    private static final int WIDTH = 250;
    private static final int TEXT_HEIGHT = 10;
    private static final int TITLE_HEIGHT = 20;
    private static final int BUTTON_HEIGHT = 30;
    private static final int SPACING = 20;

    private int dialogType;
    private String title;
    private String text;
    private Button okButton;
    private Button cancelButton;

    private boolean active;
    private int confirmed;      //Used with confirmation box. If true, ok was clicked. Else cancel was clicked.

    public DialogBox(int dialogType, String title, String text, Game game) {
        super(0, 0, ObjectID.DIALOG_BOX);

        this.setWidth(WIDTH);

        /* The max length of text per line is 35 characters. Thus height is determined by the number of lines needed
         * for text plus title and button heights.
         */
        if (text.length() == 0) {
            this.setHeight((3 * SPACING + BUTTON_HEIGHT + TITLE_HEIGHT));
        } else {
            this.setHeight(((text.length() / 35) + 1) * TEXT_HEIGHT + (5 * (text.length() / 35))
                    + (4 * SPACING + BUTTON_HEIGHT + TITLE_HEIGHT));
        }

        this.setX((Game.FRAME_WIDTH - this.getWidth()) / 2);
        this.setY((Game.FRAME_HEIGHT - this.getHeight()) / 2);

        if (dialogType != INFORMATIONAL && dialogType != CONFIRMATION) {
            throw new IllegalArgumentException("Invlid dialog box type.");
        }

        this.dialogType = dialogType;
        this.title = title;
        this.text = text;
        this.active = false;
        confirmed = NULL;

        if (this.dialogType == INFORMATIONAL) {
            okButton = new Button(x + (this.getWidth() - 50) / 2, y + this.getHeight() - BUTTON_HEIGHT - SPACING,
                    50, BUTTON_HEIGHT, "Ok", game);
            okButton.setBackgroundColor(Color.BLACK);
            okButton.setTextColor(Color.WHITE);
            okButton.setHoverColor(Color.WHITE);
            okButton.isClickable(false);

        } else if (this.dialogType == CONFIRMATION) {
            okButton = new Button(x + (this.getWidth() - 100 - 50 - SPACING) / 2, y + this.getHeight() - BUTTON_HEIGHT - SPACING,
                    50, BUTTON_HEIGHT, "Ok", game);
            cancelButton = new Button(okButton.getX() + okButton.getWidth() + SPACING, y + this.getHeight() - BUTTON_HEIGHT - SPACING,
                    100, BUTTON_HEIGHT, "Cancel", game);

            okButton.setBackgroundColor(Color.GREEN);
            okButton.setTextColor(Color.WHITE);
            okButton.setHoverColor(Color.WHITE);
            okButton.isClickable(false);

            cancelButton.setBackgroundColor(Color.BLACK);
            cancelButton.setTextColor(Color.WHITE);
            cancelButton.setHoverColor(Color.WHITE);
            cancelButton.isClickable(false);
        }
    }

    public void showDialog() {
        active = true;
        okButton.isClickable(true);

        if (dialogType == CONFIRMATION) {
            cancelButton.isClickable(true);
        }
    }

    public void hideDialog() {
        active = false;
        confirmed = NULL;
        okButton.isClickable(false);
        okButton.tick();                        //Important or won't update to unclicked.

        if (dialogType == CONFIRMATION) {
            cancelButton.isClickable(false);
            cancelButton.tick();                //Important or won't update to unclicked.
        }
    }

    @Override
    public void tick() {
        if (active) {
            if (dialogType == INFORMATIONAL) {
                if (okButton.isClicked()) {
                    hideDialog();
                }
                okButton.tick();
            }

            if (dialogType == CONFIRMATION) {
                if (okButton.isClicked()) {
                    confirmed = CONFIRMED;
                }
                if (cancelButton.isClicked()) {
                    confirmed = CANCELED;
                }

                okButton.tick();
                cancelButton.tick();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(x, y, width, height);

            g.setColor(Color.WHITE);

            Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, Text.calculateFontSize(TITLE_HEIGHT));
            g.setFont(titleFont);
            g.drawString(title, x + ((this.getWidth() - Text.getTextWidth(g, titleFont, title)) / 2), y + SPACING + TITLE_HEIGHT);

            Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, Text.calculateFontSize(TEXT_HEIGHT));
            g.setFont(textFont);
            int textY = y + (2 * SPACING) + TITLE_HEIGHT;

            if (text.length() <= 35) {
                g.drawString(text, x + ((this.getWidth() - Text.getTextWidth(g, textFont, text)) / 2), textY + TEXT_HEIGHT);

            } else {
                for (int i = 0; i < text.length() / 35; i++) {
                    String textSegment = text.substring(35 * i, 35 * (i + 1));

                    g.drawString(textSegment, x + ((this.getWidth() - Text.getTextWidth(g, textFont, textSegment)) / 2),
                            textY + ((i + 1) * TEXT_HEIGHT) + 5 * i);
                }

                String textSegment = text.substring(text.length() - (text.length() % 35));

                g.drawString(textSegment, x + ((this.getWidth() - Text.getTextWidth(g, textFont, textSegment)) / 2),
                        textY + (((text.length() / 35) + 1) * TEXT_HEIGHT) + (text.length() / 35) * 5);
            }

            okButton.render(g);

            if (dialogType == CONFIRMATION) {
                cancelButton.render(g);
            }
        }
    }

    @Override
    public void leftMousePressed() {
        okButton.leftMousePressed();

        if (dialogType == CONFIRMATION) {
            cancelButton.leftMousePressed();
        }
    }

    public int isConfirmed() {
        if (this.dialogType == CONFIRMATION) {
            return confirmed;
        }
        return NULL;
    }

    public boolean isActive() {
        return active;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;

        /* The max length of text per line is 35 characters. Thus height is determined by the number of lines needed
         * for text plus title and button heights.
         */
        if (text.length() == 0) {
            this.setHeight((3 * SPACING + BUTTON_HEIGHT + TITLE_HEIGHT));
        } else if (text.length() < 35) {
            this.setHeight(TEXT_HEIGHT + (4 * SPACING + BUTTON_HEIGHT + TITLE_HEIGHT));
        } else {
            this.setHeight(((text.length() % 35) + 1) * TEXT_HEIGHT + (5 * (text.length() % 35))
                    + (4 * SPACING + BUTTON_HEIGHT + TITLE_HEIGHT));
        }

        this.setX((Game.FRAME_WIDTH - this.getWidth()) / 2);

        okButton.setY(y + this.getHeight() - BUTTON_HEIGHT - SPACING);

        if (this.dialogType == CONFIRMATION) {
            cancelButton.setY(y + this.getHeight() - BUTTON_HEIGHT - SPACING);
        }
    }
}
