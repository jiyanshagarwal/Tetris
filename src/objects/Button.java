package objects;

import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jiyansh
 */
public class Button extends GameObject {

    public static final int DEFAULT_BORDER_WIDTH = 2;
    public static final Color DEFAULT_BORDER_COLOR = Color.DARK_GRAY;
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static final Color DEFAULT_HOVER_COLOR = DEFAULT_BACKGROUND_COLOR.brighter();

    private final Text text;
    private BufferedImage image;
    private Color backgroundColor;
    private Color borderColor;
    private Color hoverColor;
    private int borderWidth;

    private Game game;

    private boolean hover = false;
    private boolean clicked = false;
    private boolean clickable = true;

    public Button(int x, int y, int width, int height, String text, Game game) {
        super(x, y, width, height, ObjectID.BUTTON);

        this.text = new Text(text, game);
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
        hoverColor = DEFAULT_HOVER_COLOR;
        borderWidth = DEFAULT_BORDER_WIDTH;

        int textX = x + (width - this.text.getWidth()) / 2;
        int textY = y + ((height - this.text.getHeight()) / 2) - 1;         //Remove the -1 at the end if default font IS NOT used.
        this.text.setX(textX);
        this.text.setY(textY);
        this.game = game;
    }

    @Override
    public void leftMousePressed() {
        if (mouseInBounds() && clickable) {
            clicked = true;
            hover = false;
        }
    }

    @Override
    public void tick() {
        text.tick();

        if (mouseInBounds() && clickable) {
            if (!game.MOUSE_INPUT.leftIsPressed()) {
                clicked = false;
                hover = true;
            }
        } else {
            hover = false;
            clicked = false;
        }
    }

    @Override
    public void render(Graphics g) {
        Color textColor = text.getColor();

        //Border
        g.setColor(borderColor);
        g.fillRect(x, y, width, height);

        //Background
        if (clicked && clickable) {
            g.setColor(backgroundColor.darker());
        } else if (hover && clickable) {
            g.setColor(hoverColor);

            Color negativeText = new Color(255 - text.getColor().getRed(),
                    255 - text.getColor().getGreen(), 255 - text.getColor().getBlue());
            text.setColor(negativeText);
        } else {
            g.setColor(backgroundColor);
        }
        g.fillRect(x + borderWidth, y + borderWidth, width - 2 * borderWidth, height - 2 * borderWidth);

        if (image != null) {
            g.drawImage(image, x + Math.abs(width - image.getWidth()) / 2, y + Math.abs(height - image.getHeight()) / 2, null);
        }

        //Text
        text.render(g);
        text.setColor(textColor);

    }

    public boolean mouseInBounds() {
        return this.getHitBox().contains(game.MOUSE_INPUT.getMouseX(), game.MOUSE_INPUT.getMouseY());
    }

    //---------------------------------------------[Getters and Setters]----------------------------------------------//
    @Override
    public void setX(int x) {
        super.setX(x);
        int textX = x + (width - this.text.getWidth()) / 2;
        this.text.setX(textX);
    }
    
    @Override
    public void setY(int y){
        super.setY(y);
        int textY = y + ((height - this.text.getHeight()) / 2) - 1;         //Remove the -1 at the end if default font IS NOT used.
        this.text.setY(textY);
    }
    
    public boolean isClicked() {
        return clicked;
    }

    public String getText() {
        return text.getText();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getTextColor() {
        return text.getColor();
    }

    public Font getFont() {
        return text.getFont();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public Color getHoverColor() {
        return hoverColor;
    }
    
    public void setText(String text) {
        this.text.setText(text);
        int textX = x + (width - this.text.getWidth()) / 2;
        this.text.setX(textX);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setTextColor(Color textColor) {
        this.text.setColor(textColor);
    }

    public void setFont(Font font) {
        this.text.setFont(font);
    }

    public void setBorderWidth(int borderWidth) {
        text.setX(text.getX() - borderWidth);
        
        this.borderWidth = borderWidth;
        
        text.setX(text.getX() + borderWidth);
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }
    
    public void isClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
