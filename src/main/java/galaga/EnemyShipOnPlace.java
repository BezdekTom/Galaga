package galaga;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class EnemyShipOnPlace extends EnemyShip{
    private final Point2D centerPosition;
    private ShipOnPlaceListener shipOnPlaceListener;
    private int direction;
    private boolean moving = false;
    private boolean visible = true;

    public EnemyShipOnPlace(double aHeight, int aType, Point2D aPosition, int aDirection, SimulableListener aSimulableListener,ShipListener shipListener, EnemyShipListener aEnemyShipListener){
        super(aHeight, aType, aPosition, aSimulableListener, shipListener,aEnemyShipListener);
        centerPosition = aPosition;
        direction = aDirection;
    }

    @Override
    public void simulate(double timeStep){
        wasAlive = alive;

        if(alive  || !visible){
            if(moving) {
                if (direction == 1) {
                    if (position.getX() + Constants.SPEED_KOEF * timeStep * Constants.OSCILATING_SPEED >= centerPosition.getX() + Constants.OSCILATION_DISTANCE) {
                        position = new Point2D(centerPosition.getX() + Constants.OSCILATION_DISTANCE, position.getY());
                        direction *= -1;
                    } else {
                        position = new Point2D(position.getX() + timeStep * Constants.OSCILATING_SPEED * Constants.SPEED_KOEF, position.getY());
                    }
                } else {
                    if (position.getX() - timeStep * Constants.SPEED_KOEF * Constants.OSCILATING_SPEED <= centerPosition.getX() - Constants.OSCILATION_DISTANCE) {
                        position = new Point2D(centerPosition.getX() - Constants.OSCILATION_DISTANCE, position.getY());
                        direction *= -1;
                    } else {
                        position = new Point2D(position.getX() - timeStep * Constants.OSCILATING_SPEED * Constants.SPEED_KOEF, position.getY());
                    }
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
        if(alive && visible) {
            shipListener.fire(position.add(width * 0.5, 0), type);
        }
    }

    public void startMoving(){
        moving = true;
    }

    public void merge(boolean aAlive, boolean aWasAlive){
        visible = true;
        alive = aAlive;
        wasAlive =aWasAlive;
    }

    public void waveAttac(){
        if(visible && alive){
            visible = false;
            alive = false;
            wasAlive = false;
            shipOnPlaceListener.startWaveAttac(this,height, type, position);
        }
    }

    public int getType(){
        return type;
    }

    public void setShipOnPlaceListener(ShipOnPlaceListener aShipOnPlaceListener){
        shipOnPlaceListener = aShipOnPlaceListener;
    }

    @Override
    public  void draw(GraphicsContext gc){
        if(visible){
            gc.save();
            drawInternal(gc);
            gc.restore();
        }
    }

    public boolean isVisible() {
        return visible;
    }
}
