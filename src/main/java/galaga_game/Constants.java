package galaga_game;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

public final class Constants {
    private  Constants(){}

    public static final Image BACKGROUND;

    public static final Image[] ENEMY_SHIPS;
    public static final int[] NUMBER_OF_LIVES;
    public static final int[] POINTS;
    public static final Image[] ENEMY_MISSILES;

    public static final Image HUMANS_SHIP;
    public static final Image HUMANS_MISSILE;
    public static final Image EXPLOSION;

    public static final Image[] WAVES;

    public static final double  MISSILE_SPEED = 200;
    public static final double  DEFAULT_SHIP_HEIGHT = 30;
    public static final int SHIPS_IN_ROW = 6;
    public static final double MARGIN = 20;
    public static final double OSCILATING_SPEED = 20;
    public static final double OSCILATION_DISTANCE = 10;
    public static double SPEED_KOEF = 1;
    public static double WAVE_WIDTH = 70;

    static{
        WAVES = new Image[4];
        WAVES[0] = new Image(Constants.class.getResourceAsStream("images/waves_0.png"));
        WAVES[1] = new Image(Constants.class.getResourceAsStream("images/waves_1.png"));
        WAVES[2] = new Image(Constants.class.getResourceAsStream("images/waves_2.png"));
        WAVES[3] = new Image(Constants.class.getResourceAsStream("images/waves_3.png"));

        BACKGROUND = new Image(Constants.class.getResourceAsStream("images/space.jpg"));

        HUMANS_SHIP = new Image(Constants.class.getResourceAsStream("images/galaga_ship.png"));
        HUMANS_MISSILE = new Image(Constants.class.getResourceAsStream("images/my_missile.jpg"));

        EXPLOSION = new Image(Constants.class.getResourceAsStream("images/explosion.gif"));

        ENEMY_SHIPS = new Image[4];
        ENEMY_SHIPS[0] = new Image(Constants.class.getResourceAsStream("images/galaga_enemy_ship.png"));
        ENEMY_SHIPS[1] = new Image(Constants.class.getResourceAsStream("images/galaga_enemy_ship_2.png"));
        ENEMY_SHIPS[2] = new Image(Constants.class.getResourceAsStream("images/enemy_ship_3.png"));
        ENEMY_SHIPS[3] = new Image(Constants.class.getResourceAsStream("images/enemy_ship_4.png"));

        NUMBER_OF_LIVES = new int[4];
        NUMBER_OF_LIVES[0] = 2;
        NUMBER_OF_LIVES[1] = 2;
        NUMBER_OF_LIVES[2] = 3;
        NUMBER_OF_LIVES[3] = 1;

        POINTS = new int[4];
        POINTS[0] = 25;
        POINTS[1] = 50;
        POINTS[2] = 100;
        POINTS[3] = 30;

        ENEMY_MISSILES = new Image[4];
        ENEMY_MISSILES[0] = new Image(Constants.class.getResourceAsStream("images/enemy_missile.png"));
        ENEMY_MISSILES[1] = new Image(Constants.class.getResourceAsStream("images/enemy_missile_2.png"));
        ENEMY_MISSILES[2] = new Image(Constants.class.getResourceAsStream("images/enemy_missile.png"));
        ENEMY_MISSILES[3] = new Image(Constants.class.getResourceAsStream("images/enemy_missile_4.gif"));
    }
}
