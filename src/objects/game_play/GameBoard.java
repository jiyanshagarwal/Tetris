package objects.game_play;

import game.Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import objects.GameObject;
import objects.ObjectID;
import objects.Text;
import objects.game_play.tetrominos.*;

/**
 *
 * @author Jiyansh
 */
public final class GameBoard extends GameObject {

    //-------------------------------------------[Gameboard Dimensions]-------------------------------------------//
    public static final int GRID_SQUARE_WIDTH = 30;                     //Measured in pixels
    public static final int GRID_SQUARE_BORDER_WIDTH = 3;
    public static final int GRID_WIDTH = 10;                            //Measured in squares of width GRID_SQUARE_WIDTH
    public static final int GRID_HEIGHT = 20;
    public static final int PLAY_FIELD_WIDTH = (GRID_SQUARE_WIDTH * GRID_WIDTH) - ((GRID_WIDTH - 1) * GRID_SQUARE_BORDER_WIDTH);
    public static final int PLAY_FIELD_HEIGHT = (GRID_SQUARE_WIDTH * GRID_HEIGHT) - ((GRID_HEIGHT - 1) * GRID_SQUARE_BORDER_WIDTH);

    public static final int SPACING = 20;
    public static final int SIDEBAR_WIDTH = (GRID_SQUARE_WIDTH * 4) + (2 * SPACING);

    public static final int TOTAL_WIDTH = PLAY_FIELD_WIDTH + SIDEBAR_WIDTH + SPACING;
    public static final int TOTAL_HEIGHT = PLAY_FIELD_HEIGHT;
    //-----------------------------------------------------------------------------------------------------------//

    private final ObjectID[][] FIELD = new ObjectID[GRID_HEIGHT][GRID_WIDTH];
    private final int[] NUM_FILLED_SQUARES = new int[GRID_HEIGHT];            //Counts how many squares are filled in each line.

    private final Game game;
    private final ArrayList<Tetromino> BAG;
    private Tetromino currentPiece;
    private int speed = Game.FPS;
    private boolean gameOver;

    private int linesCleared;
    private int currentLevel;
    private int score;
    private Text linesClearedText;
    private Text levelText;
    private Text scoreText;

    //Allows keys to be held for continuous movement.
    private boolean rightKeyHold;
    private boolean leftKeyHold;
    private boolean rightRotateKeyHold;
    private boolean leftRotateKeyHold;
    private boolean downKeyHold;

    //Random Generator
    public GameBoard(int x, int y, Game game) {
        super(x, y, TOTAL_WIDTH, TOTAL_HEIGHT, ObjectID.GAME_BOARD);

        BAG = new ArrayList<>();
        this.game = game;
        gameOver = false;

        linesCleared = 0;
        currentLevel = 1;
        score = 0;

        linesClearedText = new Text(0, 280, Integer.toString(linesCleared), game);
        levelText = new Text(0, 230, Integer.toString(currentLevel), game);
        scoreText = new Text(0, 180, Integer.toString(score), game);

        Font statsFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);

        linesClearedText.setFont(statsFont);
        levelText.setFont(statsFont);
        scoreText.setFont(statsFont);

        linesClearedText.setColor(Color.WHITE);
        levelText.setColor(Color.WHITE);
        scoreText.setColor(Color.WHITE);

        makeBag();
        currentPiece = BAG.remove(0);
        game.getState().addGameObject(currentPiece);
    }

    /**
     * Makes a new bag with all 7 tetrominos and randomizes it.
     */
    public void makeBag() {
        BAG.clear();
        BAG.add(new LineBlock(this, game));
        BAG.add(new SquareBlock(this, game));
        BAG.add(new SBlock(this, game));
        BAG.add(new ZBlock(this, game));
        BAG.add(new TBlock(this, game));
        BAG.add(new JBlock(this, game));
        BAG.add(new LBlock(this, game));

        Collections.shuffle(BAG);
    }

    public boolean shiftLeftCurrentPiece() {
        ArrayList<int[]> piece = currentPiece.getTetromino();

        for (int[] cell : piece) {
            cell[1] -= 1;
            int newColumn = cell[1] + currentPiece.getColumn();

            if (newColumn < 0 || FIELD[cell[0] + currentPiece.getRow()][newColumn] != null) {
                return false;
            }
        }

        currentPiece.shiftLeft();
        return true;
    }

    public boolean shiftRightCurrentPiece() {
        ArrayList<int[]> piece = currentPiece.getTetromino();

        for (int[] cell : piece) {
            cell[1] += 1;
            int newColumn = cell[1] + currentPiece.getColumn();

            if (newColumn >= GameBoard.GRID_WIDTH || FIELD[cell[0] + currentPiece.getRow()][newColumn] != null) {
                return false;
            }
        }

        currentPiece.shiftRight();
        return true;
    }

    public boolean shiftDownCurrentPiece() {
        ArrayList<int[]> piece = currentPiece.getTetromino();

        for (int[] cell : piece) {
            cell[0] += 1;
            int newRow = cell[0] + currentPiece.getRow();

            if (newRow >= GameBoard.GRID_HEIGHT || FIELD[newRow][cell[1] + currentPiece.getColumn()] != null) {
                return false;
            }
        }

        currentPiece.shiftDown();
        return true;
    }

    public boolean rotateLeftCurrentPiece() {
        ArrayList<int[]> piece = currentPiece.getTetromino();

        for (int[] cell : piece) {
            //Rotation Algorithm (see Tetromino class rotation method for explanation)
            int oldColumn = cell[1];
            cell[1] = cell[0];
            cell[0] = currentPiece.getBoundingBoxSize() - oldColumn - 1;

            int newRow = cell[0] + currentPiece.getRow();
            int newColumn = cell[1] + currentPiece.getColumn();

            if (newRow < 0 || newRow >= GameBoard.GRID_HEIGHT
                    || newColumn < 0 || newColumn >= GameBoard.GRID_WIDTH
                    || FIELD[newRow][newColumn] != null) {
                return false;
            }
        }

        currentPiece.rotateLeft();
        return true;
    }

    public boolean rotateRightCurrentPiece() {
        ArrayList<int[]> piece = currentPiece.getTetromino();

        for (int[] cell : piece) {
            //Rotation Algorithm (see Tetromino class rotation method for explanation)
            int oldRow = cell[0];
            cell[0] = cell[1];
            cell[1] = currentPiece.getBoundingBoxSize() - oldRow - 1;

            int newRow = cell[0] + currentPiece.getRow();
            int newColumn = cell[1] + currentPiece.getColumn();

            if (newRow < 0 || newRow >= GameBoard.GRID_HEIGHT
                    || newColumn < 0 || newColumn >= GameBoard.GRID_WIDTH
                    || FIELD[newRow][newColumn] != null) {
                return false;
            }
        }

        currentPiece.rotateRight();
        return true;
    }

    /**
     * Fills the grid squares where the current piece is and locks its position. Creates new currentPiece.
     */
    public void lockCurrentPiece() {
        if (!gameOver) {
            for (int[] cell : currentPiece.getTetromino()) {
                fillGridSquare(cell[0] + currentPiece.getRow(), cell[1] + currentPiece.getColumn(), currentPiece.getID());
            }

            game.getState().removeGameObject(currentPiece);

            currentPiece = BAG.remove(0);
            game.getState().addGameObject(currentPiece);
            downKeyHold = false;

            //If certain grid area at the top is already filled, it will block the next tetromino and thus game is over.
            if (FIELD[1][4] != null || FIELD[1][5] != null || FIELD[1][6] != null
                    || (FIELD[0][3] != null && currentPiece.getID() == ObjectID.LINE_BLOCK)) {

                gameOver = true;
            } else {
                if (BAG.isEmpty()) {
                    makeBag();
                }
            }
        }
    }

    /**
     * Sets the field to be filled at the given row and column.
     *
     * @param row
     * @param column
     * @param id
     */
    public void fillGridSquare(int row, int column, ObjectID id) {
        if (FIELD[row][column] == null) {
            FIELD[row][column] = id;
            NUM_FILLED_SQUARES[row] += 1;
        }
    }

    /**
     * Returns a copy of the line at the given row.
     *
     * @param row
     * @return
     */
    public ObjectID[] getLine(int row) {
        return Arrays.copyOf(FIELD[row], FIELD[row].length);
    }

    /**
     * Checks if a given line is completely filled (i.e. if all the values are true).
     *
     * @param row
     * @return
     */
    public boolean lineIsFilled(int row) {
        return NUM_FILLED_SQUARES[row] == GRID_WIDTH;
    }

    public void deleteLine(int row) {
        FIELD[row] = new ObjectID[GRID_WIDTH];
        NUM_FILLED_SQUARES[row] = 0;
    }

    /**
     * Shifts all lines above given row down. Replaces top line with blank line. The given row is overwritten.
     *
     * @param row
     */
    public void shiftLinesDown(int row) {
        for (int i = row; i > 0; i--) {
            FIELD[i] = FIELD[i - 1];
            NUM_FILLED_SQUARES[i] = NUM_FILLED_SQUARES[i - 1];

            if (NUM_FILLED_SQUARES[i - 1] == 0) {
                //Must do this since each line must be a different array.
                FIELD[i] = new ObjectID[GRID_WIDTH];
                break;
            }
        }
    }

    public void updateScore(int lines) {
        switch (lines) {
            case 1:
                score += 100 * currentLevel;
                break;
            case 2:
                score += 300 * currentLevel;
                break;
            case 3:
                score += 500 * currentLevel;
                break;
            case 4:
                score += 800 * currentLevel;
                break;
        }
    }

    @Override
    public void tick() {
        if (!gameOver) {
            int currentLinesCleared = 0;

            //--------------------[Detect Filled Line]--------------------//
            for (int i = FIELD.length - 1; i >= 0; i--) {
                if (lineIsFilled(i)) {
                    deleteLine(i);
                    shiftLinesDown(i);
                    i++;
                    currentLinesCleared++;
                }
            }

            linesCleared += currentLinesCleared;
            updateScore(currentLinesCleared);

            currentLevel = (linesCleared / 10) + 1;

            speed = (int) (Game.FPS * Math.pow(0.8 - ((currentLevel - 1) * 0.007), currentLevel - 1));

            //--------------------[Moving Tetromino Down]--------------------//
            if (game.getTickCount() % speed == 0) {
                if (!shiftDownCurrentPiece()) {
                    lockCurrentPiece();
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //Placed Tetrominos: Drawn first so that the play field border is drawn over them.
        for (int i = 0; i < FIELD.length; i++) {
            for (int j = 0; j < FIELD[i].length; j++) {
                if (FIELD[i][j] != null) {
                    g.drawImage(game.ASSETS.getTetrominoImg(FIELD[i][j]),
                            this.getX() + (GameBoard.GRID_SQUARE_WIDTH * j) - (j * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                            this.getY() + (GameBoard.GRID_SQUARE_WIDTH * i) - (i * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                            null);
                }
            }
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(5));

        //Main play field for tetris blocks
        g2.drawRect(x, y, PLAY_FIELD_WIDTH, PLAY_FIELD_HEIGHT);

        //Next block viewer        
        int viewerHeight = (GRID_SQUARE_WIDTH * 2) + (SPACING * 2);
        g2.drawRect(x + PLAY_FIELD_WIDTH + SPACING, y, SIDEBAR_WIDTH, viewerHeight);

        //Stats area
        g2.drawRect(x + PLAY_FIELD_WIDTH + SPACING, y + viewerHeight + SPACING, SIDEBAR_WIDTH, height - viewerHeight - SPACING);

        //Next Tetromino
        Tetromino nextTet = BAG.get(0);
        int xPos = (x + PLAY_FIELD_WIDTH + SPACING) + 5 + (Math.abs(SIDEBAR_WIDTH - nextTet.getBoundingBoxSize() * GRID_SQUARE_WIDTH) / 2);
        int yPos = y + (Math.abs(viewerHeight - 2 * GRID_SQUARE_WIDTH) / 2);

        for (int[] cell : nextTet.getTetromino()) {
            g.drawImage(game.ASSETS.getTetrominoImg(nextTet.getID()),
                    xPos + (GameBoard.GRID_SQUARE_WIDTH * cell[1]) - (cell[1] * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                    yPos + (GameBoard.GRID_SQUARE_WIDTH * cell[0]) - (cell[0] * GameBoard.GRID_SQUARE_BORDER_WIDTH),
                    null);
        }

        //Game Stats
        scoreText.setText("Score: " + score);
        levelText.setText("Level: " + currentLevel);
        linesClearedText.setText("Lines: " + linesCleared);

        scoreText.setX((x + PLAY_FIELD_WIDTH + SPACING) + (Math.abs(SIDEBAR_WIDTH - scoreText.getWidth()) / 2));
        levelText.setX((x + PLAY_FIELD_WIDTH + SPACING) + (Math.abs(SIDEBAR_WIDTH - levelText.getWidth()) / 2));
        linesClearedText.setX((x + PLAY_FIELD_WIDTH + SPACING) + (Math.abs(SIDEBAR_WIDTH - linesClearedText.getWidth()) / 2));

        scoreText.render(g);
        levelText.render(g);
        linesClearedText.render(g);
    }

    @Override
    public void upPressed() {
        if (rightRotateKeyHold) {
            rotateRightCurrentPiece();
        }
        rightRotateKeyHold = true;
    }

    @Override
    public void leftRotatePressed() {
        if (leftRotateKeyHold) {
            rotateLeftCurrentPiece();
        }
        leftRotateKeyHold = true;
    }

    @Override
    public void downPressed() {
        if (downKeyHold) {
            if (!shiftDownCurrentPiece()) {
                lockCurrentPiece();
                score--;
            }
        }

        score++;
        downKeyHold = true;
    }

    @Override
    public void leftPressed() {
        if (leftKeyHold) {
            shiftLeftCurrentPiece();
        }

        leftKeyHold = true;
    }

    @Override
    public void rightPressed() {
        if (rightKeyHold) {
            shiftRightCurrentPiece();
        }

        rightKeyHold = true;
    }

    @Override
    public void upReleased() {
        rightRotateKeyHold = false;
        rotateRightCurrentPiece();
    }

    @Override
    public void leftRotateReleased() {
        leftRotateKeyHold = false;
        rotateLeftCurrentPiece();
    }

    @Override
    public void downReleased() {
        if (!downKeyHold) {
            score++;
        }
        downKeyHold = false;

        if (!shiftDownCurrentPiece()) {
            lockCurrentPiece();
        }
    }

    @Override
    public void leftReleased() {
        leftKeyHold = false;
        shiftLeftCurrentPiece();
    }

    @Override
    public void rightReleased() {
        rightKeyHold = false;
        shiftRightCurrentPiece();
    }

    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
