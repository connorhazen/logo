package slogo.view;


import java.awt.Canvas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.util.Pair;

public class TurtleDrawer {

  private static final double TURTLE_SIZE = 75;
  private Group elements;
  private SimpleObjectProperty currentTurtleGif;
  private Node turtleNode;
  private LinkedList<Animation> currentTrans;
  private final Duration ANIMATION_DURATION = Duration.seconds(1);
  private SimpleDoubleProperty turtleXLoc;
  private SimpleDoubleProperty turtleYLoc;
  private SimpleDoubleProperty centerX;
  private SimpleDoubleProperty centerY;
  private double lastX;
  private double lastY;
  private Pane canvas;
  private Turtle turtle;


  public TurtleDrawer() {
    currentTurtleGif = new SimpleObjectProperty();
    elements = new Group();
    currentTrans = new LinkedList<>();

  }

  public void addTurtleToCanvas(Pane canvas, Turtle turtle) {
    this.canvas = canvas;
    this.turtle = turtle;

    centerY = new SimpleDoubleProperty();
    centerX = new SimpleDoubleProperty();

    centerX.bind(Bindings.divide(canvas.widthProperty(), 2));
    centerY.bind(Bindings.divide(canvas.heightProperty(), 2));

    if(!canvas.getChildren().contains(elements)){
      canvas.getChildren().add(elements);
    }
    turtleNode = makeTurtle();
    makeAnimation();
    elements.getChildren().add(turtleNode);

  }

  private void makeAnimation(){


    //turtle.getVisibleProperty().addListener(e -> {trans.getChildren().add(visibleAnimation(turtle));});
    turtle.getCordsProperty().addListener(e -> currentTrans.add(moveAnimation()));
    turtle.getAngleProperty().addListener(e -> currentTrans.add(rotateAnimation()));

  }

  private Animation rotateAnimation() {
    RotateTransition ret = new RotateTransition();
    ret.setToAngle(turtle.getAngle() + 90);
    ret.setNode(turtleNode);
    return ret;
  }

  private Animation moveAnimation() {
    Transition ret = createMoveAnimation(ANIMATION_DURATION, turtleNode, turtle.getPenStatus(), turtle.getX()-lastX, turtle.getY()-lastY, lastX, lastY);
    lastX = turtle.getX();
    lastY = turtle.getY();
    return ret;
  }

  private Animation visibleAnimation() {
    return null;
  }
  /*
  private WrapableLine drawLine(double startX, double startY, double endX, double endY){
    WrapableLine ret = new WrapableLine(startX, startY, endX, endY, canvas);
  }*/


  private Node makeTurtle() {

    ImageView newTurt = new ImageView();

    newTurt.imageProperty().bind(currentTurtleGif);

    newTurt.setFitWidth(TURTLE_SIZE);
    newTurt.setFitHeight(TURTLE_SIZE);
    newTurt.setRotate(turtle.getAngle() + 90);


    lastX = turtle.getX();
    lastY = turtle.getY();

    turtleXLoc = new SimpleDoubleProperty(lastX);
    turtleYLoc = new SimpleDoubleProperty(lastY);

    NumberBinding xLoc =
        Bindings.add(turtleXLoc, Bindings.subtract(centerX, newTurt.getFitWidth()/2));
    NumberBinding yLoc =
        Bindings.add(turtleYLoc, Bindings.subtract(centerY, newTurt.getFitHeight()/2));

    newTurt.layoutXProperty().bind(xLoc);
    newTurt.layoutYProperty().bind(yLoc);

    return newTurt;

  }


  public void reset() {
    elements.getChildren().clear();
  }


  public void changeImage(String file) throws FileNotFoundException {
    String path = "src/resources/turtleImages/" + file + ".gif";
    setImage(path);
  }

  private void setImage(String path) throws FileNotFoundException {
    FileInputStream inputStream = new FileInputStream(path);
    currentTurtleGif.set(new Image(inputStream));
  }

  public void animate(DoubleProperty speed, double maxSpeed) {
    if (!currentTrans.isEmpty()){
      Animation toPlay = currentTrans.poll();
      if(speed.getValue()==maxSpeed){
        toPlay.jumpTo(toPlay.getTotalDuration());
        toPlay.play();
      }
      else{
        toPlay.setRate(speed.getValue());
        toPlay.play();
      }
      toPlay.setOnFinished(e -> animate(speed, maxSpeed));
    }
  }

  //Will not be used in final version
  public void animate(){
    if (!currentTrans.isEmpty()){
      Animation toPlay = currentTrans.poll();
      toPlay.play();
      toPlay.setOnFinished(e -> animate());
    }

  }

  private Transition createMoveAnimation(Duration duration, Node node, boolean penStatus, double xCord, double yCord, double startX, double startY) {
    Group lines = new Group();
    elements.getChildren().add(lines);


    Transition a  = new Transition() {
      {
        setCycleDuration(ANIMATION_DURATION);
      }
      @Override
      protected void interpolate(double frac) {
        turtleXLoc.set(startX + xCord*frac);
        turtleYLoc.set(startY+ yCord*frac);
      }
    };


    a.currentTimeProperty().addListener( new ChangeListener<Duration>() {

      Pair<Double, Double> oldLocation = null;

      /**
       * Draw a line from the old location to the new location
       */
      @Override
      public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

        // skip starting at 0/0
        if( oldValue == Duration.ZERO)
          return;

        // get current location
        double x = node.getBoundsInParent().getCenterX();
        double y = node.getBoundsInParent().getCenterY();

        // initialize the location
        if( oldLocation == null) {
          oldLocation = new Pair<>(x, y);
          return;
        }

        if(penStatus){
          Line l = new Line(oldLocation.getKey(),oldLocation.getValue(),x,y);
          lines.getChildren().add(l);
        }
        oldLocation = new Pair<>(x,y);
      }
    });


    return a;
  }
}
