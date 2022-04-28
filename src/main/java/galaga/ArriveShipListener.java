package galaga;

import javafx.geometry.Point2D;

interface ArriveShipListener {
    void ChangeShip(Point2D position, int direction, int previousDirection, int type, int numberOfHits, int numberFromSide,double height);
}
