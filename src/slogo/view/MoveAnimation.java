package slogo.view;


import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.util.Duration;

public class MoveAnimation implements TurtleAnimation {
  private Turtle turtle;
  private WrapableTurtleImage viewTurtle;
  private double lastX;
  private double lastY;
  private double endX;
  private double endY;
  public MoveAnimation(Turtle turtle, WrapableTurtleImage viewTurtle){
    this.viewTurtle = viewTurtle;
    this.turtle = turtle;
    lastX = viewTurtle.getLastX();
    lastY = viewTurtle.getLastY();
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
    Transition a  = new Transition() {
      {
        setCycleDuration(duration);
      }
      @Override
      protected void interpolate(double frac) {
        viewTurtle.setTurtleXRelative(startX + xCord*frac);
        viewTurtle.setTurtleYRelative(startY+ yCord*frac);
      }
    };
    return a;
  }

}
