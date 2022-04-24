package game.states;

import java.awt.Graphics;
import java.util.ArrayList;
import objects.GameObject;

/**
 *
 * @author Jiyansh
 */
public abstract class GameState {

    protected final ArrayList<GameObject> GAME_OBJECTS;
    protected StateID id;

    public GameState(StateID id) {
        GAME_OBJECTS = new ArrayList<>();
        this.id = id;
    }

    public abstract void startState();

    public void endState() {
        removeAllGameObjects();
    }

    public void addGameObject(GameObject obj) {
        GAME_OBJECTS.add(obj);
    }

    public void addGameObject(int index, GameObject obj) {
        GAME_OBJECTS.add(index, obj);
    }

    public void removeGameObject(GameObject obj) {
        GAME_OBJECTS.remove(obj);
    }

    public void removeAllGameObjects() {
        GAME_OBJECTS.clear();
    }

    public void tick() {
        /* Don't use enhanced for loop because then objects can't be removed on the fly! 
         * It will throw a java.util.concurrentmodificationexception        
         */
        for (int i = 0; i < GAME_OBJECTS.size(); i++) {
            GAME_OBJECTS.get(i).tick();
        }
    }
    
    public void render(Graphics g) {
        /* Do not use an enhanced for loop because then objects can't be removed on the fly! 
         * It will throw a java.util.concurrentmodificationexception        
        */
        for (int i = 0; i < GAME_OBJECTS.size(); i++) {
            GAME_OBJECTS.get(i).render(g);
        }
    }

    public void leftMousePressed() {
        for (int i = 0; i < GAME_OBJECTS.size(); i++) {
            GAME_OBJECTS.get(i).leftMousePressed();
        }
    }
    
    public void leftMouseReleased() {
        for (int i = 0; i < GAME_OBJECTS.size(); i++) {
            GAME_OBJECTS.get(i).leftMouseReleased();
        }
    }

    public void upPressed() {
    }

    public void downPressed() {
    }

    public void leftPressed() {
    }

    public void rightPressed() {
    }

    public void leftRotatePressed() {
    }
    
    public void upReleased() {
    }

    public void downReleased() {
    }

    public void leftReleased() {
    }

    public void rightReleased() {
    }
    
    public void leftRotateReleased() {
    }
    
    public void pReleased() {
    }
    
    public void r_Released() {
    }
    
    public void escapeReleased() {
    }
}
