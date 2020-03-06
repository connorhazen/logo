package slogo.view;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class WrapableTurtleImage extends ImageView{

  private static final double TURTLE_SIZE = 80;
  private double lastX;
  private double lastY;
  private Turtle turtle;
  private SimpleDoubleProperty turtleXRelative;
  private SimpleDoubleProperty turtleYRelative;
  private SimpleDoubleProperty visualXOffset;
  private SimpleDoubleProperty visualYOffset;
  private SimpleDoubleProperty centerX;
  private SimpleDoubleProperty centerY;
  private Pane canvas;

  public WrapableTurtleImage(Turtle turtle, Pane canvas,  SimpleDoubleProperty centerX, SimpleDoubleProperty centerY, SimpleObjectProperty<Image> currentTurtleGif){
    super();
    this.canvas = canvas;
    this.centerX = centerX;
    this.centerY = centerY;
    turtleYRelative = new SimpleDoubleProperty(turtle.getY());
    turtleXRelative = new SimpleDoubleProperty(turtle.getX());
    visualXOffset = new SimpleDoubleProperty(0);
    visualYOffset = new SimpleDoubleProperty(0);
    this.turtle = turtle;
    lastX = turtle.getX();
    lastY = turtle.getY();

    makeInitialTurtle(currentTurtleGif);
    makeLocBinding(centerX, centerY);
  }

  public Animation moveAnimation(Duration duration) {
    Transition ret = createMoveAnimation(duration, turtle.getPenStatus(), turtle.getX()-lastX, turtle.getY()-lastY, lastX, lastY);
    lastX = turtle.getX();
    lastY = turtle.getY();
    return ret;
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
    if(visualXOffset.doubleValue() + currentLoc  + this.getFitWidth()/2 > screenVal){
      visualXOffset.set(visualXOffset.doubleValue() - screenVal+this.getFitWidth());
      calcOffsetX(currentLoc, screenVal);
    }
    if(visualXOffset.doubleValue() + currentLoc - this.getFitWidth()/2 <= 0){
      visualXOffset.set(visualXOffset.doubleValue() + screenVal-this.getFitWidth());
      calcOffsetX(currentLoc, screenVal);
    }
  }

  private void calcOffsetY(Double currentLoc, Double screenVal) {
    if(visualYOffset.doubleValue() + currentLoc + this.getFitHeight()/2 > screenVal){
      visualYOffset.set(visualYOffset.doubleValue() -  screenVal+this.getFitHeight());
      calcOffsetY(currentLoc, screenVal);
    }
    if(visualYOffset.doubleValue() + currentLoc - this.getFitHeight()/2 <= 0){
      visualYOffset.set(visualYOffset.doubleValue() +  screenVal-this.getFitHeight());
      calcOffsetY(currentLoc, screenVal);
    };
  }

  private void makeInitialTurtle(
      SimpleObjectProperty<Image> currentTurtleGif){
    this.imageProperty().bind(currentTurtleGif);
    this.setFitWidth(TURTLE_SIZE);
    this.setFitHeight(TURTLE_SIZE);
    this.setRotate(turtle.getAngle() + 90);
  }

  private Transition createMoveAnimation(Duration duration, boolean penStatus, double xCord, double yCord, double startX, double startY) {
    Transition a  = new Transition() {
      {
        setCycleDuration(duration);
      }
      @Override
      protected void interpolate(double frac) {
        turtleXRelative.set(startX + xCord*frac);
        turtleYRelative.set(startY+ yCord*frac);
      }
    };
    return a;
  }

  public NumberBinding getXLocLines(){
    return Bindings.add(turtleXRelative, centerX);
  }
  public NumberBinding getYLocLines(){
    return Bindings.add(turtleYRelative, centerY);
  }

}
