package slogo.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import slogo.ExceptionHelper;
import slogo.view.animations.MoveAnimation;
import slogo.view.animations.RotateAnimation;
import slogo.view.animations.TurtleAnimation;
import slogo.view.animations.VisibleAnimation;
import slogo.view.wrapableObjects.WrapableLine;
import slogo.view.wrapableObjects.WrapableTurtleImage;

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
  public Turtle turtle;
  private String imgName;
  private String prevImg;

  public TurtleDrawer(Turtle turtle, Pane canvas, Group elements) {
    this.canvas = canvas;
    this.turtle = turtle;
    this.elements = elements;
    running = false;
    prevImg = "";
    imgName = "";
    currentTurtleGif = new SimpleObjectProperty<>();
    currentTrans = new LinkedList<>();
    addTurtleToCanvas();

  }

  public void addTurtleToCanvas() {
    makeCenterBindings();
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
      if (!activeState) {
        this.changeImage("inactive_turtle");
      } else {
        this.setImage(prevImg);
      }
    });
  }

  public void reset() {
    elements.getChildren().clear();
  }

  public void run(DoubleProperty speed, double maxSpeed) {
    if (running) {
      return;
    }
    running = true;
    play(speed, maxSpeed);
  }

  public void changeImage(String file) {
    String path = "data/turtleImages/" + file + ".gif";
    setImage(path);
  }

  private void play(DoubleProperty speed, double maxSpeed) {
    Group lines = new Group();
    elements.getChildren().add(lines);
    SimpleDoubleProperty turtleX = turtleNode.getXLocLines();
    SimpleDoubleProperty turtleY = turtleNode.getYLocLines();

    double startLocX = turtleX.doubleValue();
    double startLocY = turtleY.doubleValue();

    if (!currentTrans.isEmpty()) {
      TurtleAnimation toPlay = currentTrans.poll();
      if (speed.doubleValue() == maxSpeed) {
        normalExecute(toPlay, lines, startLocX, startLocY);
        play(speed, maxSpeed);
      } else {
        animateExecute(toPlay, lines, turtleX, turtleY, startLocX, startLocY, speed, maxSpeed);
      }
    }
  }

  private void animateExecute(TurtleAnimation toPlay, Group lines, SimpleDoubleProperty turtleX, SimpleDoubleProperty turtleY, double startLocX, double startLocY, DoubleProperty speed,
      double maxSpeed) {
    ChangeListener<Number> lis = (e, ee, eee) -> {
      lines.getChildren().clear();
      lines.getChildren().add(makeLines(startLocX, startLocY));
    };
    turtleX.addListener(lis);
    turtleY.addListener(lis);
    Animation ana = toPlay.getAnimation(ANIMATION_DURATION);
    ana.setRate(speed.doubleValue());
    ana.setOnFinished(e -> {
      turtleX.removeListener(lis);
      turtleY.removeListener(lis);
      lines.getChildren().add(makeLines(startLocX, startLocY));
      checkDoneAnimating();
      play(speed, maxSpeed);
    });
    ana.play();
  }

  private void normalExecute(TurtleAnimation toPlay, Group lines, double startLocX, double startLocY) {
    toPlay.execute();
    System.out.println("executed");
    lines.getChildren().add(makeLines(startLocX, startLocY));
    checkDoneAnimating();
  }


  private Node makeLines(double startLocX, double startLocY) {
    return new WrapableLine(startLocX, startLocY, turtleNode.getXLocLines().doubleValue(),
        turtleNode.getYLocLines().doubleValue(), canvas, centerX, centerY, turtle.getPen());
  }

  private void checkDoneAnimating() {
    if (currentTrans.isEmpty()) {
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
    try {
      FileInputStream inputStream = new FileInputStream(path);
      currentTurtleGif.set(new Image(inputStream));
    } catch (FileNotFoundException fnfe) {
      new ExceptionHelper().fileNotFound(fnfe);

    }
  }

  private void makeAnimationBindings() {
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

}
