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
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
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


  private Group elements;
  private WrappableTurtleImage turtleNode;
  private LinkedList<Animation> currentTrans;
  private final Duration ANIMATION_DURATION = Duration.seconds(1);
  private SimpleDoubleProperty centerX;
  private SimpleDoubleProperty centerY;
  private SimpleObjectProperty<Image> currentTurtleGif;


  private Pane canvas;
  private Turtle turtle;


  public TurtleDrawer() {
    currentTurtleGif = new SimpleObjectProperty<>();
    elements = new Group();
    currentTrans = new LinkedList<>();

  }

  public void addTurtleToCanvas(Pane canvas, Turtle turtle) {
    this.canvas = canvas;
    this.turtle = turtle;
    makeCenterBindings();
    if(!canvas.getChildren().contains(elements)){
      canvas.getChildren().add(elements);
    }

    turtleNode = new WrappableTurtleImage(turtle, canvas, centerX, centerY, currentTurtleGif);
    elements.getChildren().add(turtleNode);
    makeAnimationBindings();

    canvas.widthProperty().addListener(e->{
      System.out.println(turtleNode.getBoundsInParent().getCenterX());
    });
  }

  public void reset() {
    elements.getChildren().clear();
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
      toPlay.setRate(.1);
      toPlay.play();
      toPlay.setOnFinished(e -> animate());
    }

  }

  public void changeImage(String file) throws FileNotFoundException {
    String path = "data/turtleImages/" + file + ".gif";
    setImage(path);
  }

  private void makeCenterBindings() {
    centerY = new SimpleDoubleProperty();
    centerX = new SimpleDoubleProperty();
    centerX.bind(Bindings.divide(canvas.widthProperty(), 2));
    centerY.bind(Bindings.divide(canvas.heightProperty(), 2));
  }

  private void setImage(String path) throws FileNotFoundException {
    FileInputStream inputStream = new FileInputStream(path);
    currentTurtleGif.set(new Image(inputStream));
  }

  private void makeAnimationBindings(){
    turtle.getVisibleProperty().addListener(e -> currentTrans.add(visibleAnimation(
        (SimpleBooleanProperty) e)));
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
    return turtleNode.moveAnimation(ANIMATION_DURATION);
  }

  private Animation visibleAnimation(SimpleBooleanProperty visible) {
    FadeTransition fade = new FadeTransition();
    if (visible.getValue()){
      fade.setToValue(1);
    }
    else{
      fade.setToValue(0);
    }
    fade.setNode(turtleNode);
    return fade;
  }

  /*
  private WrapableLine drawLine(double startX, double startY, double endX, double endY){
    WrapableLine ret = new WrapableLine(startX, startY, endX, endY, canvas);
  }*/

}
