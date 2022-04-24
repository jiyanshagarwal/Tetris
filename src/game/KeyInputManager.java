package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Custom key manager. NOTE: It sends same signal for w, x, m, and up arrow keys. Also same mapping for WASD and Arrow
 * Keys
 *
 * @author Jiyansh
 */
public class KeyInputManager extends KeyAdapter {

    private Game game;

    public KeyInputManager(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                game.getState().upPressed();
                break;
            case KeyEvent.VK_DOWN:
                game.getState().downPressed();
                break;
            case KeyEvent.VK_LEFT:
                game.getState().leftPressed();
                break;
            case KeyEvent.VK_RIGHT:
                game.getState().rightPressed();
                break;
            case KeyEvent.VK_W:
                game.getState().upPressed();
                break;
            case KeyEvent.VK_S:
                game.getState().downPressed();
                break;
            case KeyEvent.VK_A:
                game.getState().leftPressed();
                break;
            case KeyEvent.VK_D:
                game.getState().rightPressed();
                break;
            case KeyEvent.VK_X:
                game.getState().upPressed();
                break;
            case KeyEvent.VK_M:
                game.getState().upPressed();
                break;
            case KeyEvent.VK_Z:
                game.getState().leftRotatePressed();
                break;
            case KeyEvent.VK_N:
                game.getState().leftRotatePressed();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                game.getState().upReleased();
                break;
            case KeyEvent.VK_DOWN:
                game.getState().downReleased();
                break;
            case KeyEvent.VK_LEFT:
                game.getState().leftReleased();
                break;
            case KeyEvent.VK_RIGHT:
                game.getState().rightReleased();
                break;
            case KeyEvent.VK_W:
                game.getState().upReleased();
                break;
            case KeyEvent.VK_S:
                game.getState().downReleased();
                break;
            case KeyEvent.VK_A:
                game.getState().leftReleased();
                break;
            case KeyEvent.VK_D:
                game.getState().rightReleased();
                break;
            case KeyEvent.VK_X:
                game.getState().upReleased();
                break;
            case KeyEvent.VK_M:
                game.getState().upReleased();
                break;
            case KeyEvent.VK_Z:
                game.getState().leftRotateReleased();
                break;
            case KeyEvent.VK_N:
                game.getState().leftRotateReleased();
                break;
            case KeyEvent.VK_P:
                game.getState().pReleased();
                break;
            case KeyEvent.VK_R:
                game.getState().r_Released();
                break;
            case KeyEvent.VK_ESCAPE:
                game.getState().escapeReleased();
                break;
        }
    }
}
