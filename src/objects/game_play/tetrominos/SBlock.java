package objects.game_play.tetrominos;

import game.Game;
import objects.ObjectID;
import objects.game_play.GameBoard;

/**
 *
 * @author Jiyansh
 */
public final class SBlock extends Tetromino {

    public static final int START_ROW = 0;
    public static final int START_COLUMN = 4;

    private boolean rotationState;

    public SBlock(GameBoard gameBoard, Game game) {
        super(START_ROW, START_COLUMN, game.ASSETS.S_BLOCK, ObjectID.S_BLOCK, gameBoard);
        rotationState = false;
        makeTetromino();
    }

    @Override
    public void rotateRight() {
        if (!rotationState) {
            super.rotateRight();
            rotationState = true;
        } else {
            super.rotateLeft();
            rotationState = false;
        }
    }

    @Override
    public void rotateLeft() {
        //Calls the above method since both do the same thing.
        rotateRight();
    }

    @Override
    protected void makeTetromino() {
        cells.add(new int[]{0, 1});
        cells.add(new int[]{0, 2});
        cells.add(new int[]{1, 0});
        cells.add(new int[]{1, 1});
        boundingBoxSize = 3;
    }
}
