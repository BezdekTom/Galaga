package galaga_game;

import javafx.geometry.Point2D;

interface SideMovingShipListener {
    void ChangeShip(Point2D position, int direction, int type, int numberOfHits, double height);
}
