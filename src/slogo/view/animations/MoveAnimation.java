package slogo.view.animations;


import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.util.Duration;
import slogo.view.Coordinates;
import slogo.view.Turtle;
import slogo.view.wrapableObjects.WrapableTurtleImage;

public class MoveAnimation implements TurtleAnimation {

  private WrapableTurtleImage viewTurtle;
  private double lastX;
  private double lastY;
  private double endX;
  private double endY;
  public MoveAnimation(Turtle turtle, WrapableTurtleImage viewTurtle, Coordinates last){
    this.viewTurtle = viewTurtle;
    lastX = last.getX();
    lastY = last.getY();
    endX = turtle.getX();
    endY = turtle.getY();


  }
  @Override
  public Animation getAnimation(Duration duration){
    return createMoveAnimation(duration, lastX, lastY, endX-lastX, endY-lastY);
  }

  @Override
  public void execute(){
    viewTurtle.setTurtleXRelative(endX);
    viewTurtle.setTurtleYRelative(endY);
  }

  private Transition createMoveAnimation(Duration duration, double startX, double startY, double xCord, double yCord) {
    return new Transition() {
      {
        setCycleDuration(duration);
      }
      @Override
      protected void interpolate(double frac) {
        viewTurtle.setTurtleXRelative(startX + xCord*frac);
        viewTurtle.setTurtleYRelative(startY+ yCord*frac);
      }
    };
  }

}
