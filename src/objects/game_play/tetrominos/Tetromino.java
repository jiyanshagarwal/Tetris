package objects.game_play.tetrominos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import objects.GameObject;
import objects.ObjectID;
import objects.game_play.GameBoard;

/**
 *
 * @author Jiyansh
 */
public abstract class Tetromino extends GameObject {

    protected int row, column;                        //Coordinates of the entire tetrimino on the GameBoard.
    protected int boundingBoxSize;
    protected ArrayList<int[]> cells;
    private final BufferedImage cellPic;

    public Tetromino(int row, int column, BufferedImage cellPic, ObjectID id, GameBoard gameBoard) {
        super(gameBoard.getX() + (GameBoard.GRID_SQUARE_WIDTH * column) - (column * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                gameBoard.getY() + (GameBoard.GRID_SQUARE_WIDTH * row) - (row * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                id);

        this.row = row;
        this.column = column;
        this.cellPic = cellPic;

        cells = new ArrayList<>();
    }

    /**
     * Fills the tetromino array to make the correct tetromino based on Object ID.
     */
    protected abstract void makeTetromino();

    public void shiftLeft() {
        column--;
        this.setX(this.getX() - (GameBoard.GRID_SQUARE_WIDTH - GameBoard.GRID_SQUARE_BORDER_WIDTH));
    }

    public void shiftRight() {
        column++;
        this.setX(this.getX() + (GameBoard.GRID_SQUARE_WIDTH - GameBoard.GRID_SQUARE_BORDER_WIDTH));
    }

    public void shiftDown() {
        row++;
        this.setY(this.getY() + (GameBoard.GRID_SQUARE_WIDTH - GameBoard.GRID_SQUARE_BORDER_WIDTH));
    }

    /**
     * Switching (transposing) the rows and columns and then flipping the columns rotates 90 degrees to the right.
     */
    public void rotateRight() {
        for (int[] cell : cells) {
            int oldRow = cell[0];
            cell[0] = cell[1];

            /*Gets the size of the bounding box based on the max of width and height. Transposes the old row to the 
            column and then flips columns by subtracting. The -1 is important since the row and oolumn count start at 0.
             */
            cell[1] = boundingBoxSize - oldRow - 1;
        }
    }

    /**
     * Switching (transposing) the rows and columns and then flipping the rows rotates 90 degrees to the left.
     */
    public void rotateLeft() {
        for (int[] cell : cells) {
            int oldColumn = cell[1];
            cell[1] = cell[0];

            /*Gets the size of the bounding box based on the max of width and height. Transposes the old row to the 
            column and then flips columns by subtracting. The -1 is important since the row and oolumn count start at 0.
             */
            cell[0] = boundingBoxSize - oldColumn - 1;
        }
    }

    public void removeCellAtRow(int row) {
        for(int i = 0; i < cells.size(); i++) {
            if(cells.get(i)[0] == row) {
                cells.remove(i);
                i--;
            }
        }
    }
    
    /**
     * Shifts all cells above a given row down by one row.
     * @param row 
     */
    public void shiftCellsDown(int row) {
        for(int[] cell : cells) {
            if(cell[0] < row) {
                cell[0]++;
            }
        }
    }
    
    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {
        for (int[] cell : cells) {
            g.drawImage(cellPic,
                    this.getX() + (GameBoard.GRID_SQUARE_WIDTH * cell[1]) - (cell[1] * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                    this.getY() + (GameBoard.GRID_SQUARE_WIDTH * cell[0]) - (cell[0] * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                    null);
        }
    }

    public ArrayList<int[]> getTetromino() {
        ArrayList<int[]> tet = new ArrayList<>();

        for (int[] cell : cells) {
            tet.add(Arrays.copyOf(cell, cell.length));
        }

        return tet;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    
    public int getBoundingBoxSize() {
        return boundingBoxSize;
    }
    
    public boolean isEmpty() {
        return cells.isEmpty();
    }
}