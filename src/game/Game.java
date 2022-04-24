package game;

import assets.Assets;
import game.states.ControlsScreenState;
import game.states.CreditsScreenState;
import game.states.GameState;
import game.states.PlayState;
import game.states.StartScreenState;
import game.states.StateID;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jiyansh
 */
public class Game extends JPanel {

    public final JFrame FRAME;

    public static final String TITLE = "Tetris";
    public static final int FRAME_WIDTH = 500;
    public static final int FRAME_HEIGHT = 600;
    public static final int FPS = 120;

    public final KeyInputManager KEY_INPUT;
    public final MouseInputManager MOUSE_INPUT;
    public final Assets ASSETS;

    private BufferedImage iconImage;
    private GameState state;
    private int tickCounter = 0;

    public Game() throws IOException {
        FRAME = new JFrame(TITLE);
        KEY_INPUT = new KeyInputManager(this);
        MOUSE_INPUT = new MouseInputManager(this);

        try {
            iconImage = ImageIO.read(Game.class.getResource("/res/Tetris Game Icon.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        ASSETS = new Assets();

        initialize();
        changeState(StateID.START_SCREEN);          //Make sure to do this AFTER initialize().
    }

    /**
     * Sets up the frame and JPanel.
     */
    private void initialize() {
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setResizable(false);
        FRAME.setIconImage(iconImage);
        FRAME.addKeyListener(KEY_INPUT);
        FRAME.addMouseListener(MOUSE_INPUT);
        FRAME.addMouseMotionListener(MOUSE_INPUT);

        /*Sets up the JPanel that everything will be drawn to. This is added to the JFrame and its Graphics g object is passed to every
        render method to draw with. */
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setBackground(Color.black);

        /* Need to add to both the frame and the JPanel in case one or the other isn't focused on. 
         * Not doing this will lead to incorrect behavior.        
         */
        this.addMouseListener(MOUSE_INPUT);
        this.addMouseMotionListener(MOUSE_INPUT);

        FRAME.add(this);
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);      //Must be called after FRAME.pack() to work.
        FRAME.setVisible(true);
    }

    public final void changeState(StateID newState) {
        if (state != null) {
            state.endState();
        }

        switch (newState) {
            case START_SCREEN:
                state = new StartScreenState(this);
                break;
            case PLAY_SCREEN:
                state = new PlayState(this);
                break;
            case CONTROLS_SCREEN:
                state = new ControlsScreenState(this);
                break;
            case CREDITS_SCREEN:
                state = new CreditsScreenState();
                break;
        }

        state.startState();
    }

    public void tick() {
        state.tick();
        tickCounter++;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        state.render(g);
    }

    public void startGame() {
        double timePerTick = 1_000_000_000 / FPS;   //Basically how long to wait (in nanoseconds) between each update of game object positions.

        long lastTime = System.nanoTime();
        long now;
        long delta;

        while (true) {
            now = System.nanoTime();
            delta = now - lastTime;

            if (delta >= timePerTick) {             //Wait until specified amount of timePerTick has passed until allowing an update. 
                tick();                
                repaint();                          //repaint() calls the paintComponent method.
                lastTime = now;
            }
        }
    }

    public GameState getState() {
        return state;
    }

    public int getTickCount() {
        return tickCounter;
    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.startGame();
    }
}
