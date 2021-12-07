package galaga;

public abstract class Ship extends  DrawableSimulableEntity {
    protected ShipListener shipListener = new ShipListenerEmpty();

    public void setShipListener(ShipListener aShipListener){
        shipListener = aShipListener;
    }
    public abstract void fire();
}
