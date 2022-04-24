package game.states;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import objects.Button;
import objects.DialogBox;
import objects.game_play.GameBoard;

/**
 *
 * @author Jiyansh
 */
public class PlayState extends GameState {

    private boolean paused;
    private Game game;

    private GameBoard board;
    private Button pauseButton;
    private Button exitButton;
    private DialogBox exitDialog;
    private DialogBox playAgainDialog;

    public PlayState(Game game) {
        super(StateID.PLAY_SCREEN);
        this.game = game;
        paused = false;
    }

    @Override
    public void startState() {
        int boardX = (Game.FRAME_WIDTH - GameBoard.TOTAL_WIDTH) / 2;
        int boardY = (Game.FRAME_HEIGHT - GameBoard.TOTAL_HEIGHT) / 2;
        board = new GameBoard(boardX, boardY, game);
        addGameObject(board);

        //Pause and Exit Buttons
        int buttonX = (boardX + GameBoard.PLAY_FIELD_WIDTH + GameBoard.SPACING) + (Math.abs(GameBoard.SIDEBAR_WIDTH - 100) / 2);
        pauseButton = new Button(buttonX, 340, 100, 30, "Pause", game);
        pauseButton.setBackgroundColor(Color.BLACK);
        pauseButton.setTextColor(Color.WHITE);
        pauseButton.setHoverColor(Color.WHITE);
                
        exitButton = new Button(buttonX, 390, 100, 30, "Exit", game);
        exitButton.setBackgroundColor(Color.BLACK);
        exitButton.setTextColor(Color.WHITE);
        exitButton.setHoverColor(Color.WHITE);

        addGameObject(pauseButton);
        addGameObject(exitButton);

        //Exit confirmation Dialog Box
        exitDialog = new DialogBox(DialogBox.CONFIRMATION, "Confirm", "Are you sure you want to exit?", game);
        addGameObject(exitDialog);

        //Play Again confirmation Dialog Box
        playAgainDialog = new DialogBox(DialogBox.CONFIRMATION, "", "", game);
        addGameObject(playAgainDialog);
    }

    public void pauseState() {
        paused = true;
    }

    public void unPauseState() {
        paused = false;
    }

    @Override
    public void pReleased() {
        paused = !paused;

        if (paused) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }

    @Override
    public void r_Released() {
        playAgainDialog.setTitle("Restart?");
        playAgainDialog.setText("Are you sure you want to restart?");
        playAgain();
    }

    @Override
    public void escapeReleased() {
        exit();
    }

    private void playAgain() {
        pauseState();
        playAgainDialog.showDialog();
    }

    private void exit() {
        pauseState();
        exitDialog.showDialog();
    }

    @Override
    public void tick() {
        if (exitDialog.isConfirmed() == DialogBox.CONFIRMED) {
            game.changeState(StateID.START_SCREEN);
        } else if (exitDialog.isConfirmed() == DialogBox.CANCELED) {
            exitDialog.hideDialog();
            unPauseState();
        }

        if (playAgainDialog.isConfirmed() == DialogBox.CONFIRMED) {
            game.changeState(StateID.PLAY_SCREEN);
        } else if (playAgainDialog.isConfirmed() == DialogBox.CANCELED && board.isGameOver()) {
            game.changeState(StateID.START_SCREEN);
        } else if (playAgainDialog.isConfirmed() == DialogBox.CANCELED) {
            playAgainDialog.hideDialog();
            unPauseState();
        }

        if (board.isGameOver()) {
            playAgainDialog.setTitle("Game Over!");
            playAgainDialog.setText("Want to play again?");
            playAgain();
        }

        if (pauseButton.isClicked()) {
            paused = !paused;

            if (paused) {
                pauseButton.setText("Resume");
            } else {
                pauseButton.setText("Pause");
            }

            pauseButton.isClickable(false);
            pauseButton.tick();                 //VERY IMPORTANT!! Otherwise it won't update to unclicked!!
            pauseButton.isClickable(true);
        }

        if (exitButton.isClicked()) {
            exit();

            exitButton.isClickable(false);
            exitButton.tick();                 //VERY IMPORTANT!! Otherwise it won't update to unclicked!!
            exitButton.isClickable(true);
        }

        if (!paused) {
            /* Don't use enhanced for loop because objects can't be removed while looping! 
             * It will throw a java.util.concurrentmodificationexception            
             */
            for (int i = 0; i < GAME_OBJECTS.size(); i++) {
                GAME_OBJECTS.get(i).tick();
            }
        } else {
            if (exitDialog.isActive()) {
                exitDialog.tick();
            } else if (playAgainDialog.isActive()) {
                playAgainDialog.tick();
            } else {
                pauseButton.tick();
                exitButton.tick();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        if (game.ASSETS.GAME_ICON_WITH_TEXT != null && board != null) {
            int imageX = (board.getX() + GameBoard.PLAY_FIELD_WIDTH + GameBoard.SPACING)
                    + (Math.abs(GameBoard.SIDEBAR_WIDTH - game.ASSETS.GAME_ICON_WITH_TEXT.getWidth()) / 2);

            g.drawImage(game.ASSETS.GAME_ICON_WITH_TEXT, imageX, 450, null);
        }
    }

    @Override
    public void upPressed() {
        board.upPressed();
    }

    @Override
    public void downPressed() {
        board.downPressed();
    }

    @Override
    public void leftPressed() {
        board.leftPressed();
    }

    @Override
    public void rightPressed() {
        board.rightPressed();
    }

    @Override
    public void leftRotatePressed() {
        board.leftRotatePressed();
    }

    @Override
    public void upReleased() {
        board.upReleased();
    }

    @Override
    public void downReleased() {
        board.downReleased();
    }

    @Override
    public void leftReleased() {
        board.leftReleased();
    }

    @Override
    public void rightReleased() {
        board.rightReleased();
    }

    @Override
    public void leftRotateReleased() {
        board.leftRotateReleased();
    }
}
