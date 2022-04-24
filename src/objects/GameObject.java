package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Jiyansh
 */
public abstract class GameObject {

    public static final int DEFAULT_WIDTH = 0;
    public static final int DEFAULT_HEIGHT = 0;
    public static final int DEFAULT_VELOCITY = 0;

    protected int x, y;
    protected int width, height;
    protected int velocityX, velocityY;
    protected ObjectID id;

    public GameObject(int x, int y, int width, int height, ObjectID id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        velocityX = DEFAULT_VELOCITY;
        velocityY = DEFAULT_VELOCITY;
        this.id = id;
    }

    public GameObject(int x, int y, ObjectID id) {
        this.x = x;
        this.y = y;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        velocityX = DEFAULT_VELOCITY;
        velocityY = DEFAULT_VELOCITY;
        this.id = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public Rectangle getHitBox() {
        return new Rectangle(x, y, width, height);
    }

    public int clampValue(int num, int min, int max) {
        if (num < max) {
            if (num >= min) {
                return num;
            }
            return min;
        }
        return max;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public ObjectID getID() {
        return id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVelocityX(int velocity) {
        this.velocityX = velocity;
    }

    public void setVelocityY(int velocity) {
        this.velocityY = velocity;
    }

    public void setObjectID(ObjectID id) {
        this.id = id;
    }

    public void leftMousePressed() {
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

    public void leftMouseReleased() {
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
}
