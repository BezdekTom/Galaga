package galaga;

import javafx.geometry.Point2D;

public interface ShipOnPlaceListener {
    void startWaveAttac(EnemyShipOnPlace aEnemyShipOnPlace, double aHeight, int aType, Point2D aPosition);
}