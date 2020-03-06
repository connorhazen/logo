package slogo.view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

public class VisibleAnimation implements TurtleAnimation {
  private Turtle turtle;
  private WrapableTurtleImage viewTurtle;
  private boolean visible;

  public VisibleAnimation(Turtle turtle, WrapableTurtleImage viewTurtle){
    this.viewTurtle = viewTurtle;
    this.turtle = turtle;
    visible = turtle.getVisibilityStatus();

  }

  @Override
  public Animation getAnimation(Duration duration) {
    FadeTransition fade = new FadeTransition();
    if (visible){
      fade.setToValue(1);
    }
    else{
      fade.setToValue(0);
    }
    fade.setNode(viewTurtle);
    return fade;
  }

  @Override
  public void execute() {
    viewTurtle.setVisible(visible);

  }
}
