package slogo.view;


import javafx.animation.Animation;
import javafx.util.Duration;

public interface TurtleAnimation {
  Animation getAnimation(Duration duration);
  void execute();

}
