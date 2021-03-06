package slogo.view.wrapableObjects;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import slogo.view.Turtle;

/**
 * Similar to the wrapable line, this class acts as the translator between given x,y location
 * and actual display location.
 */
public class WrapableTurtleImage extends ImageView{

  private static final double TURTLE_SIZE = 80;
  private Turtle turtle;
  private SimpleDoubleProperty turtleXRelative;
  private SimpleDoubleProperty turtleYRelative;
  private SimpleDoubleProperty visualXOffset;
  private SimpleDoubleProperty visualYOffset;
  private Pane canvas;

  /**
   * @param turtle
   * @param canvas
   * @param centerX
   * @param centerY
   * @param currentTurtleGif
   */
  public WrapableTurtleImage(Turtle turtle, Pane canvas,  SimpleDoubleProperty centerX, SimpleDoubleProperty centerY, SimpleObjectProperty<Image> currentTurtleGif){
    super();
    this.canvas = canvas;
    turtleYRelative = new SimpleDoubleProperty(turtle.getY());
    turtleXRelative = new SimpleDoubleProperty(turtle.getX());
    visualXOffset = new SimpleDoubleProperty(0);
    visualYOffset = new SimpleDoubleProperty(0);
    this.turtle = turtle;
    makeInitialTurtle(currentTurtleGif);
    makeLocBinding(centerX, centerY);
  }

  public void setTurtleXRelative(double val){
    turtleXRelative.set(val);
  }
  public void setTurtleYRelative(double val){
    turtleYRelative.set(val);
  }


  /**
   * These two getters are used to get the actual turtle coordinates based off center of screen.
   * However, it is not the visual location, which wraps, but rather the raw coordinate.
   * @return
   */
  public SimpleDoubleProperty getXLocLines(){
    return turtleXRelative;
  }
  public SimpleDoubleProperty getYLocLines(){
    return turtleYRelative;
  }

  /**
   * These two getters are used to get turtles last location.
   * @return
   */
  public double getLastX(){
    return turtle.getLastX();
  }
  public double getLastY(){
    return  turtle.getLastY();
  }




  private void makeLocBinding(SimpleDoubleProperty centerX, SimpleDoubleProperty centerY) {
    NumberBinding xLoc = Bindings.add(turtleXRelative, centerX);
    NumberBinding yLoc = Bindings.add(turtleYRelative, centerY);


    turtleXRelative.addListener(e -> calcOffsetX(xLoc.doubleValue(), canvas.getWidth()));
    turtleYRelative.addListener(e -> calcOffsetY(yLoc.doubleValue(), canvas.getHeight()));

    centerX.addListener(e-> {
      visualXOffset.setValue(0);
      calcOffsetX(xLoc.doubleValue(), canvas.getWidth());
    });

    centerY.addListener(e-> {
      visualYOffset.setValue(0);
      calcOffsetY(yLoc.doubleValue(), canvas.getHeight());
    });

    this.layoutXProperty().bind(Bindings.add(Bindings.subtract(visualXOffset, this.getFitWidth()/2), xLoc));
    this.layoutYProperty().bind(Bindings.add(Bindings.subtract(visualYOffset, this.getFitHeight()/2), yLoc));
  }

  private void calcOffsetX(Double currentLoc, Double screenVal) {
    if(visualXOffset.doubleValue() + currentLoc  > screenVal){
      visualXOffset.set(visualXOffset.doubleValue() - screenVal);
      calcOffsetX(currentLoc, screenVal);
    }
    if(visualXOffset.doubleValue() + currentLoc <= 0){
      visualXOffset.set(visualXOffset.doubleValue() + screenVal);
      calcOffsetX(currentLoc, screenVal);
    }
  }

  private void calcOffsetY(Double currentLoc, Double screenVal) {
    if(visualYOffset.doubleValue() + currentLoc > screenVal){
      visualYOffset.set(visualYOffset.doubleValue() -  screenVal);
      calcOffsetY(currentLoc, screenVal);
    }
    if(visualYOffset.doubleValue() + currentLoc  <= 0){
      visualYOffset.set(visualYOffset.doubleValue() +  screenVal);
      calcOffsetY(currentLoc, screenVal);
    }
  }

  private void makeInitialTurtle(
      SimpleObjectProperty<Image> currentTurtleGif){
    this.imageProperty().bind(currentTurtleGif);
    this.setFitWidth(TURTLE_SIZE);
    this.setFitHeight(TURTLE_SIZE);
    this.setRotate(turtle.getAngle() + 90);
  }

}
