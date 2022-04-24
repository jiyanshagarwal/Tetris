package objects.game_play.tetrominos;

import game.Game;
import objects.ObjectID;
import objects.game_play.GameBoard;

/**
 *
 * @author Jiyansh
 */
public final class LBlock extends Tetromino {
    public static final int START_ROW = 0;
    public static final int START_COLUMN = 4;

    public LBlock(GameBoard gameBoard, Game game) {
        super(START_ROW, START_COLUMN, game.ASSETS.L_BLOCK, ObjectID.L_BLOCK, gameBoard);
        makeTetromino();
    }

    @Override
    protected void makeTetromino() {
        cells.add(new int[]{0, 2});
        cells.add(new int[]{1, 0});
        cells.add(new int[]{1, 1});
        cells.add(new int[]{1, 2});
        boundingBoxSize = 3;
    }
}
