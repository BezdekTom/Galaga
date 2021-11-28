package galaga;

import javafx.scene.image.Image;

public final class Constants {
    private  Constants(){}

    public static  final Image[] ENEMY_SHIPS;
    public static  final int[] NUMBER_OF_LIVES;
    public static  final Image HUMANS_SHIP;
    public static  final Image MY_MISSILE;
    public static  final Image EXPLOSION;

    static{
        HUMANS_SHIP = new Image(Constants.class.getResourceAsStream("galaga_ship.png"));
        EXPLOSION = new Image(Constants.class.getResourceAsStream("explosion.gif"));

        ENEMY_SHIPS = new Image[2];
        ENEMY_SHIPS[0] = new Image(Constants.class.getResourceAsStream("galaga_enemy_ship.png"));
        ENEMY_SHIPS[1] = new Image(Constants.class.getResourceAsStream("galaga_enemy_ship_2.png"));

        NUMBER_OF_LIVES = new int[2];
        NUMBER_OF_LIVES[0] = 1;
        NUMBER_OF_LIVES[1] = 2;


        MY_MISSILE = new Image(Constants.class.getResourceAsStream("my_missile.jpg"));

    }
}
