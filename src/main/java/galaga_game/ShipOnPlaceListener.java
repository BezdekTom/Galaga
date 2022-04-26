package galaga_game;

import javafx.geometry.Point2D;

interface ShipOnPlaceListener {
    void startWaveAttac(EnemyShipOnPlace aEnemyShipOnPlace, double aHeight, int aType, Point2D aPosition, int aNumberOfHits);
}