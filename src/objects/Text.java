package objects;

import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

/**
 *
 * @author Jiyansh
 */
public class Text extends GameObject {

    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);

    private String text;
    private Color color;
    private Font font;
    private Game game;

    public Text(String text, Game game) {
        super(0, 0, getTextWidth(game.getGraphics(), DEFAULT_FONT, text), getTextHeight(game.getGraphics(), DEFAULT_FONT), ObjectID.TEXT);
        this.text = text;
        this.font = DEFAULT_FONT;
        this.color = DEFAULT_COLOR;
        this.game = game;
    }
    
    public Text(int x, int y, String text, Game game) {
        super(x, y, getTextWidth(game.getGraphics(), DEFAULT_FONT, text), getTextHeight(game.getGraphics(), DEFAULT_FONT), ObjectID.TEXT);
        this.text = text;
        this.font = DEFAULT_FONT;
        this.color = DEFAULT_COLOR;
        this.game = game;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        
        //Ascent is added because strings are drawn from the lower left corner, not upper left.
        g.drawString(text, x, y + g.getFontMetrics().getAscent());
    }

    public static int getTextHeight(Graphics g, Font font) {
        return g.getFontMetrics(font).getHeight();
    }

    public static int getTextWidth(Graphics g, Font font, String text) {
        return g.getFontMetrics(font).stringWidth(text);
    }

    public static int getTextSpacing(Graphics g, Font font) {
        return g.getFontMetrics(font).getLeading();
    }
    
    public static int calculateFontSize(int heightInPixels) {
        return (int) (heightInPixels * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0);
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public void setText(String text) {
        this.text = text;
        setWidth(getTextWidth(game.getGraphics(), font, text));
        setHeight(getTextHeight(game.getGraphics(), font));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
        setWidth(getTextWidth(game.getGraphics(), font, text));
        setHeight(getTextHeight(game.getGraphics(), font));
    }
}
