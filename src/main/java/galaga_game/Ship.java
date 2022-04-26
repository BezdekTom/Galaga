package galaga_game;

import lombok.Setter;

abstract class Ship extends  DrawableSimulableEntity {
	@Setter
    protected ShipListener shipListener = new ShipListenerEmpty();

    public abstract void fire();
}
