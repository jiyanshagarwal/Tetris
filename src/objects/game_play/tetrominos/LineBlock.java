package objects.game_play.tetrominos;

import game.Game;
import objects.ObjectID;
import objects.game_play.GameBoard;

/**
 *
 * @author Jiyansh
 */
public final class LineBlock extends Tetromino {

    public static final int START_ROW = -1;
    public static final int START_COLUMN = 3;

    private boolean rotationState;

    public LineBlock(GameBoard gameBoard, Game game) {
        super(START_ROW, START_COLUMN, game.ASSETS.LINE_BLOCK, ObjectID.LINE_BLOCK, gameBoard);
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
        cells.add(new int[]{1, 0});
        cells.add(new int[]{1, 1});
        cells.add(new int[]{1, 2});
        cells.add(new int[]{1, 3});
        boundingBoxSize = 4;
    }
}
