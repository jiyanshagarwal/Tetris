package game.states;

import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import objects.Button;
import objects.Text;

/**
 *
 * @author Jiyansh
 */
public class ControlsScreenState extends GameState {

    Game game;
    Button backButton;
    
    public ControlsScreenState(Game game) {
        super(StateID.CONTROLS_SCREEN);
        this.game = game;
    }

    @Override
    public void startState() {
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 35);
        
        Text title = new Text("Controls", game);
        title.setFont(titleFont);
        title.setColor(Color.CYAN);
        title.setX((Game.FRAME_WIDTH - title.getWidth()) / 2);
        title.setY(30);        
        addGameObject(title);
        
        //Back button
        backButton = new Button(10, 10, 40, 40, "", game);
        backButton.setBackgroundColor(Color.BLACK);
        backButton.setBorderColor(Color.BLACK);
        backButton.setHoverColor(Color.WHITE);
        backButton.setImage(game.ASSETS.BACK_ICON);
        addGameObject(backButton);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (backButton.isClicked()) {
            game.changeState(StateID.START_SCREEN);
        }
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        
        //Arrow image
        if (game.ASSETS.CONTROLS != null) {
            int imageX = Math.abs(Game.FRAME_WIDTH - game.ASSETS.CONTROLS.getWidth()) / 2;
            
            g.drawImage(game.ASSETS.CONTROLS, imageX, 100, null);
        }
    }
}
