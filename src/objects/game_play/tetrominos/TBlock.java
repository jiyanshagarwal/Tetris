package objects.game_play.tetrominos;

import game.Game;
import objects.ObjectID;
import objects.game_play.GameBoard;

/**
 *
 * @author Jiyansh
 */
public final class TBlock extends Tetromino {
    public static final int START_ROW = 0;
    public static final int START_COLUMN = 4;

    public TBlock(GameBoard gameBoard, Game game) {
        super(START_ROW, START_COLUMN, game.ASSETS.T_BLOCK, ObjectID.T_BLOCK, gameBoard);
        makeTetromino();
    }

    @Override
    protected void makeTetromino() {
        cells.add(new int[]{0, 1});
        cells.add(new int[]{1, 0});
        cells.add(new int[]{1, 1});
        cells.add(new int[]{1, 2});
        boundingBoxSize = 3;
    }
}
