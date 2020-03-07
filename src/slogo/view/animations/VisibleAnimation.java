package slogo.view.animations;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import slogo.view.Turtle;
import slogo.view.wrapableObjects.WrapableTurtleImage;

public class VisibleAnimation implements TurtleAnimation {

  private WrapableTurtleImage viewTurtle;
  private boolean visible;

  public VisibleAnimation(Turtle turtle, WrapableTurtleImage viewTurtle){
    this.viewTurtle = viewTurtle;
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
