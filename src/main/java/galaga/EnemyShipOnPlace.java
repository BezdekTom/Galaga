package galaga;

import javafx.geometry.Point2D;

public class EnemyShipOnPlace extends EnemyShip{
    private final Point2D centerPosition;
    private int direction;

    public EnemyShipOnPlace(double aHeight, int aType, Point2D aPosition, int aDirection, SimulableListener aSimulableListener,ShipListener shipListener, EnemyShipListener aEnemyShipListener){
        super(aHeight, aType, aPosition, aSimulableListener, shipListener,aEnemyShipListener);
        centerPosition = aPosition;
        direction = aDirection;
    }

    @Override
    public void simulate(double timeStep){
        wasAlive = alive;

        if(alive){
            if(direction == 1){
                if(position.getX() + Constants.SPEED_KOEF*timeStep*Constants.OSCILATING_SPEED >= centerPosition.getX() + Constants.OSCILATION_DISTANCE){
                    position = new Point2D(centerPosition.getX()+Constants.OSCILATION_DISTANCE, position.getY());
                    direction*=-1;
                }
                else{
                    position = new Point2D(position.getX() + timeStep*Constants.OSCILATING_SPEED*Constants.SPEED_KOEF, position.getY());
                }
            }
            else {
                if (position.getX() - timeStep * Constants.SPEED_KOEF*Constants.OSCILATING_SPEED <= centerPosition.getX() - Constants.OSCILATION_DISTANCE) {
                    position = new Point2D(centerPosition.getX() - Constants.OSCILATION_DISTANCE, position.getY());
                    direction *= -1;
                } else {
                    position = new Point2D(position.getX() - timeStep * Constants.OSCILATING_SPEED*Constants.SPEED_KOEF, position.getY());
                }
            }
        }
        else if(explosionTimer > 0)
        {
            explosionTimer -= timeStep;
        }
        else
        {
            simulableListener.destruct(this);

        }


    }

    @Override
    public  void fire(){
        if(alive) {
            shipListener.fire(position.add(width * 0.5, 0), type);
        }
    }
}
