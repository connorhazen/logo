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


  /**
   * This method is responsible for returning an Animation for the associated action.
   * @param duration
   * @return Correct Animation for turtle
   */
  Animation getAnimation(Duration duration);


  /**
   * This method is used to create an instantaneous "animation" for complex loops.
   * If we animated each step, the code would take quite long.
   * TODO: make execute return a runnable expression to be called so it could be added to queue
   */
  void execute();

}
