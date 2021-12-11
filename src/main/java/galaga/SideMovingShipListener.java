package galaga;

import javafx.geometry.Point2D;

public interface SideMovingShipListener {
    void ChangeShip(Point2D position, int direction, int type, int numberOfHits, double height);
}
