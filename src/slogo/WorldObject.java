package slogo;

public class WorldObject implements WorldObjectInterface{
    private double myX;
    private double myY;
    private double myAngle;
    private double myWidth;
    private double myHeight;
    private static final double threeSixty = 360;


    public WorldObject(double xCoor, double yCoor, double orrientation, double width, double height){
        myX = xCoor;
        myY = yCoor;
        myWidth = width;
        myHeight = height;
        myAngle = orrientation % threeSixty;
    }

    public double getWidth(){
        return myWidth;
    }

    public double getHeight(){
        return myHeight;
    }

    @Override
    public double getX() {
        return myX;
    }

    @Override
    public double getY() {
        return myY;
    }

    @Override
    public double getAngle(){
        return myAngle;
    }

}
