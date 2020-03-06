package slogo.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import slogo.ExceptionHelper;

public class TurtleDrawer {

  private Group elements;
  private WrapableTurtleImage turtleNode;
  private LinkedList<TurtleAnimation> currentTrans;
  private final Duration ANIMATION_DURATION = Duration.seconds(.5);
  private SimpleDoubleProperty centerX;
  private SimpleDoubleProperty centerY;
  private SimpleObjectProperty<Image> currentTurtleGif;
  private boolean running;
  private Pane canvas;
  private Turtle turtle;
  private String imgName;
  private String prevImg;
  private boolean animatePen = false;

  public TurtleDrawer() {
    running = false;
    prevImg = "";
    imgName = "";
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
    turtleNode = new WrapableTurtleImage(turtle, canvas, centerX, centerY, currentTurtleGif);
    setMouseClick();
    elements.getChildren().add(turtleNode);
    makeAnimationBindings();
  }

  private void setMouseClick() {
    turtleNode.setOnMouseClicked(e -> {
      System.out.println(currentTurtleGif.getValue().toString());
      boolean activeState = turtle.switchActive();
      System.out.println(activeState);
      if(!activeState) this.changeImage("inactive_turtle");
      else this.setImage(prevImg);
    });
  }

  public void reset() {
    elements.getChildren().clear();
  }

  public void animate(DoubleProperty speed, double maxSpeed) {
    if(running){
      return;
    }
    running = true;
    play(speed, maxSpeed);
  }


  public void changeImage(String file) {
    String path = "data/turtleImages/" + file + ".gif";
    setImage(path);
  }

  private void play(DoubleProperty speed, double maxSpeed){
    Group lines = new Group();
    elements.getChildren().add(lines);
    SimpleDoubleProperty turtleX = turtleNode.getXLocLines();
    SimpleDoubleProperty turtleY = turtleNode.getYLocLines();

    double startLocX = turtleX.doubleValue();
    double startLocY = turtleY.doubleValue();

    ChangeListener<Number> lis = null;

    if(animatePen){
      lis = (e,ee,eee) -> {
        lines.getChildren().clear();
        lines.getChildren().add(new WrapableLine(startLocX, startLocY, turtleX.doubleValue(), turtleY.doubleValue(), canvas, centerX, centerY));
      };
      turtleX.addListener(lis);
      turtleY.addListener(lis);
    }

    if(!currentTrans.isEmpty()){
      TurtleAnimation toPlay = currentTrans.poll();
      if(speed.doubleValue() == maxSpeed){
        toPlay.execute();
        System.out.println("executed");
        lines.getChildren().add(new WrapableLine(startLocX, startLocY, turtleX.doubleValue(), turtleY.doubleValue(), canvas, centerX, centerY));
        checkDoneAnimating();
        play(speed,maxSpeed);
      }
      else{
        Animation ana = toPlay.getAnimation(ANIMATION_DURATION);
        ana.setRate(speed.doubleValue());
        ana.setOnFinished(e -> {
          lines.getChildren().add(new WrapableLine(startLocX, startLocY, turtleX.doubleValue(), turtleY.doubleValue(), canvas, centerX, centerY));
          checkDoneAnimating();
          play(speed, maxSpeed);
        });
        ana.play();
      }


    }
  }

  private void setRate(Animation toPlay, DoubleProperty speed, double maxSpeed) {
    if(speed.getValue() == maxSpeed){
      toPlay.jumpTo(ANIMATION_DURATION);
    }
    else{
      toPlay.setRate(speed.getValue());
    }
  }

  private void checkDoneAnimating() {
    if(currentTrans.isEmpty()){
      running = false;
    }
  }

  private void makeCenterBindings() {
    centerY = new SimpleDoubleProperty();
    centerX = new SimpleDoubleProperty();
    centerX.bind(Bindings.divide(canvas.widthProperty(), 2));
    centerY.bind(Bindings.divide(canvas.heightProperty(), 2));
  }

  private void setImage(String path) {
    prevImg = imgName;
    imgName = path;
    try{
    FileInputStream inputStream = new FileInputStream(path);
    currentTurtleGif.set(new Image(inputStream));
    } catch (FileNotFoundException fnfe){
    new ExceptionHelper().fileNotFound(fnfe);

    }
  }

  private void makeAnimationBindings(){
    turtle.getVisibleProperty().addListener(e -> currentTrans.add(visibleAnimation()));
    turtle.getCordsProperty().addListener(e -> currentTrans.add(moveAnimation()));
    turtle.getAngleProperty().addListener(e -> currentTrans.add(rotateAnimation()));
  }

  private TurtleAnimation rotateAnimation() {
    return new RotateAnimation(turtle, turtleNode);
  }

  private TurtleAnimation moveAnimation() {
    return new MoveAnimation(turtle, turtleNode);
  }

  private TurtleAnimation visibleAnimation() {
    return new VisibleAnimation(turtle, turtleNode);

  }

  /*
  private WrapableLine drawLine(double startX, double startY, double endX, double endY){
    WrapableLine ret = new WrapableLine(startX, startY, endX, endY, canvas);
  }*/

}