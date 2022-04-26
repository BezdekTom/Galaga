package galaga_game;

import javafx.geometry.Point2D;

public class MoveSideEnemyShip extends EnemyShip{
    private final int direction;
    private final int previousDirection;
    private double widthBorder;
    private final int speed = 5;
    private SideMovingShipListener sideMovingShipListener;

    public MoveSideEnemyShip(double aHeight, int aType, Point2D aPosition, int numberFromSide, int aDirection, int aPreviousDirection, double gameWidth, SimulableListener aSimulableListener,ShipListener aShipListener, EnemyShipListener aEnemyShipListener, SideMovingShipListener aSideMovingShipListener)
    {
        super(aHeight, aType, aPosition, aSimulableListener, aShipListener, aEnemyShipListener);
        previousDirection = aPreviousDirection;
        direction = aDirection;
        sideMovingShipListener = aSideMovingShipListener;

        double spaceWidth = (gameWidth - Constants.SHIPS_IN_ROW*width - 10 - 2*Constants.MARGIN)*0.2;
        if(direction == 1){
            widthBorder = gameWidth - Constants.MARGIN - 5 - width- (double) numberFromSide * (spaceWidth + width) ;
        }
        else{
            widthBorder = Constants.MARGIN + 5 + (double) numberFromSide * (spaceWidth + width);
        }

    }

    public MoveSideEnemyShip(int aType, Point2D aPosition, int numberFromSide, int aDirection, int previousDirection, double gameWidth, SimulableListener aSimulableListener,ShipListener aShipListener, EnemyShipListener aEnemyShipListener, SideMovingShipListener aSideMovingShipListener)
    {
        this(Constants.DEFAULT_SHIP_HEIGHT, aType, aPosition, numberFromSide, aDirection, previousDirection, gameWidth, aSimulableListener, aShipListener, aEnemyShipListener, aSideMovingShipListener);
    }

    @Override
    public void simulate(double timeStep){
        wasAlive = alive;
        if(alive) {
            if(direction == 1){
                if(position.add(direction*speed*Constants.SPEED_KOEF * timeStep* 20, 0).getX() <= widthBorder){
                    position = position.add(direction*speed*Constants.SPEED_KOEF * timeStep* 20, 0);
                }
                else{
                    position = new Point2D(widthBorder, position.getY());
                    sideMovingShipListener.ChangeShip(position, previousDirection, type, numberOfHits, height);
                    simulableListener.destruct(this);
                }
            }
            else{
                if(position.add(direction *Constants.SPEED_KOEF* speed* timeStep* 20, 0).getX() >= widthBorder){
                    position = position.add(direction *Constants.SPEED_KOEF* speed* timeStep* 20, 0);
                }
                else{
                    position = new Point2D(widthBorder, position.getY());
                    sideMovingShipListener.ChangeShip(position, previousDirection, type, numberOfHits, height);
                    simulableListener.destruct(this);
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
        if(alive){
            shipListener.fire(position.add(width*0.5,0),type);
        }
    }
}
