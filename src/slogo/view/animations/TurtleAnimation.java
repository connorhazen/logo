package slogo.view.animations;


import javafx.animation.Animation;
import javafx.util.Duration;

/**
 * This interface is responsible for creating the various turtle transitions.
 *
 * The two methods run the animation, with one being instantaneous to allow for quicker command execution
 * depending on user preference.
 */
public interface TurtleAnimation {

  Animation getAnimation(Duration duration);

  void execute();

}
