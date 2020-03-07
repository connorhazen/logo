package slogo.view.animations;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import slogo.view.Turtle;
import slogo.view.wrapableObjects.WrapableTurtleImage;

public class RotateAnimation implements TurtleAnimation {
  private Turtle turtle;
  private WrapableTurtleImage viewTurtle;
  private double angle;

  public RotateAnimation(Turtle turtle, WrapableTurtleImage viewTurtle){
    this.viewTurtle = viewTurtle;
    this.turtle = turtle;
    angle = turtle.getAngle() + 90;
  }

  @Override
  public Animation getAnimation(Duration duration) {
    RotateTransition ret = new RotateTransition();
    ret.setToAngle(angle);
    ret.setNode(viewTurtle);
    return ret;
  }

  @Override
  public void execute() {
    viewTurtle.setRotate(angle);

  }
}
