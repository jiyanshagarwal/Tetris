package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Jiyansh
 */
public class MouseInputManager extends MouseAdapter {

    private int mouseX, mouseY;
    private boolean leftPressed = false;
    private Game game;

    public MouseInputManager(Game game) {
        this.game = game;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            game.getState().leftMousePressed();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean leftIsPressed() {
        return leftPressed;
    }
}
