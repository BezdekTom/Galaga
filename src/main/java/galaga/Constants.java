package galaga;

import javafx.scene.image.Image;

public final class Constants {
    private  Constants(){}

    public static  final Image[] ENEMY_SHIPS;
    public static  final  Image HUMANS_SHIP;
    public static  final  Image MY_MISSILE;

    static{
        HUMANS_SHIP = new Image(Constants.class.getResourceAsStream("galaga_ship.png"));
        ENEMY_SHIPS = new Image[1];
        ENEMY_SHIPS[0] = new Image(Constants.class.getResourceAsStream("galaga_enemy_ship.png"));
        MY_MISSILE = new Image(Constants.class.getResourceAsStream("my_missile.jpg"));


        System.out.println("Inicializovano");
    }
}
