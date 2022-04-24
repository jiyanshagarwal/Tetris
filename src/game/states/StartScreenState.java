package game.states;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import objects.Button;
import objects.DialogBox;

/**
 *
 * @author Jiyansh
 */
public class StartScreenState extends GameState {

    private Game game;
    private Button playButton;
    private Button controlsButton;
    private Button creditsButton;
    private DialogBox creditsDialog;

    public StartScreenState(Game game) {
        super(StateID.START_SCREEN);
        this.game = game;
    }

    @Override
    public void startState() {
        //Making the buttons
        int buttonWidth = 120;
        int buttonHeight = 40;

        int buttonX = (Game.FRAME_WIDTH - buttonWidth) / 2;
        int buttonY = 300;

        playButton = new Button(buttonX, buttonY, buttonWidth, buttonHeight, "Play", game);
        controlsButton = new Button(buttonX, buttonY + buttonHeight + 20, buttonWidth, buttonHeight, "Controls", game);
        creditsButton = new Button(buttonX, buttonY + (2 * buttonHeight) + 40, buttonWidth, buttonHeight, "Credits", game);

        playButton.setBackgroundColor(new Color(5, 5, 5));
        playButton.setTextColor(Color.WHITE);
        playButton.setHoverColor(Color.WHITE);

        controlsButton.setBackgroundColor(new Color(5, 5, 5));
        controlsButton.setTextColor(Color.WHITE);
        controlsButton.setHoverColor(Color.WHITE);

        creditsButton.setBackgroundColor(new Color(5, 5, 5));
        creditsButton.setTextColor(Color.WHITE);
        creditsButton.setHoverColor(Color.WHITE);

        addGameObject(playButton);
        addGameObject(controlsButton);
        addGameObject(creditsButton);

        creditsDialog = new DialogBox(DialogBox.INFORMATIONAL, "Credits", "     Programming and Graphics:     Jiyansh Agarwal", game);
        addGameObject(creditsDialog);
    }

    @Override
    public void tick() {
        super.tick();

        if (playButton.isClicked() && !creditsDialog.isActive()) {
            game.changeState(StateID.PLAY_SCREEN);
        } else if (controlsButton.isClicked() && !creditsDialog.isActive()) {
            game.changeState(StateID.CONTROLS_SCREEN);
        } else if (creditsButton.isClicked()) {
            //game.changeState(StateID.CREDITS_SCREEN);
            creditsDialog.showDialog();
        }
    }

    @Override
    public void render(Graphics g) {
        if (game.ASSETS.GAME_ICON_WITH_TEXT_LARGE != null) {
            int imageX = Math.abs(Game.FRAME_WIDTH - game.ASSETS.GAME_ICON_WITH_TEXT_LARGE.getWidth()) / 2;

            g.drawImage(game.ASSETS.GAME_ICON_WITH_TEXT_LARGE, imageX, 70, null);
        }

        super.render(g);
    }
}
