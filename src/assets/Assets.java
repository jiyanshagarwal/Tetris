package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import objects.ObjectID;

/**
 *
 * @author Jiyansh
 */
public class Assets {

    public final BufferedImage LINE_BLOCK;
    public final BufferedImage SQUARE_BLOCK;
    public final BufferedImage T_BLOCK;
    public final BufferedImage S_BLOCK;
    public final BufferedImage Z_BLOCK;
    public final BufferedImage J_BLOCK;
    public final BufferedImage L_BLOCK;
    public final BufferedImage GAME_ICON_WITH_TEXT;
    public final BufferedImage GAME_ICON_WITH_TEXT_LARGE;
    public final BufferedImage CONTROLS;
    public final BufferedImage BACK_ICON;

    public Assets() throws IOException {
        LINE_BLOCK = ImageIO.read(Assets.class.getResource("/res/Line Block.png"));
        SQUARE_BLOCK = ImageIO.read(Assets.class.getResource("/res/Square Block.png"));
        T_BLOCK = ImageIO.read(Assets.class.getResource("/res/T Block.png"));
        S_BLOCK = ImageIO.read(Assets.class.getResource("/res/S Block.png"));
        Z_BLOCK = ImageIO.read(Assets.class.getResource("/res/Z Block.png"));
        J_BLOCK = ImageIO.read(Assets.class.getResource("/res/J Block.png"));
        L_BLOCK = ImageIO.read(Assets.class.getResource("/res/L Block.png"));
        GAME_ICON_WITH_TEXT = ImageIO.read(Assets.class.getResource("/res/Tetris Game Icon With Text.png"));
        GAME_ICON_WITH_TEXT_LARGE = ImageIO.read(Assets.class.getResource("/res/Tetris Game Icon With Text Large.png"));
        CONTROLS = ImageIO.read(Assets.class.getResource("/res/Tetris Controls.png"));
        BACK_ICON = ImageIO.read(Assets.class.getResource("/res/Back Icon.png"));
    }

    public BufferedImage getTetrominoImg(ObjectID id) {
        switch (id) {
            case LINE_BLOCK:
                return LINE_BLOCK;
            case SQUARE_BLOCK:
                return SQUARE_BLOCK;
            case T_BLOCK:
                return T_BLOCK;
            case S_BLOCK:
                return S_BLOCK;
            case Z_BLOCK:
                return Z_BLOCK;
            case J_BLOCK:
                return J_BLOCK;
            case L_BLOCK:
                return L_BLOCK;
            default:
                throw new IllegalArgumentException("Assets.getTetrominoImg only excepts tetromino object ids.");
        }
    }
}
