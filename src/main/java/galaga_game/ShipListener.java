package galaga_game;

import javafx.geometry.Point2D;

public interface ShipListener {
    void fire(Point2D position, int type);
}
