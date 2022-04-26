package galaga_game;

import javafx.geometry.Point2D;

public interface ArriveShipListener {
    void ChangeShip(Point2D position, int direction, int previousDirection, int type, int numberOfHits, int numberFromSide,double height);
}
