package slogo.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;
import slogo.ExceptionHelper;
import slogo.view.animations.MoveAnimation;
import slogo.view.animations.RotateAnimation;
import slogo.view.animations.TurtleAnimation;
import slogo.view.animations.VisibleAnimation;
import slogo.view.wrapableObjects.WrapableLine;
import slogo.view.wrapableObjects.WrapableTurtleImage;

/**
 * The turtle drawer class is responsible for listening to turtle movements or changes, and
 * creating the associated animations. The Element drawer then pulls the animations into its onw queue
 * inorder to be played at end of command execution.
 */
public class TurtleDrawer {

  private Group elements;
  private WrapableTurtleImage turtleNode;
  private LinkedList<TurtleAnimation> currentTrans;
  private final Duration ANIMATION_DURATION = Duration.seconds(.5);
  private SimpleDoubleProperty centerX;
  private SimpleDoubleProperty centerY;
  private SimpleObjectProperty<Image> currentTurtleGif;
  private Pane canvas;
  public Turtle turtle;
  private String imgName;
  private String prevImg;
  private SimpleBooleanProperty newAnimations;

  public TurtleDrawer(Turtle turtle, Pane canvas, Group elements) {
    this.canvas = canvas;
    this.turtle = turtle;
    this.elements = elements;

    prevImg = "";
    imgName = "";
    currentTurtleGif = new SimpleObjectProperty<>();
    currentTrans = new LinkedList<>();
    newAnimations = new SimpleBooleanProperty(false);
    addTurtleToCanvas();


  }




  public void addTurtleToCanvas() {
    makeCenterBindings();
    turtleNode = new WrapableTurtleImage(turtle, canvas, centerX, centerY, currentTurtleGif);
    setMouseClick();
    elements.getChildren().add(turtleNode);
    makeAnimationBindings();
  }

  public  void play(TurtleAnimation toPlay, DoubleProperty speed, double maxSpeed, Runnable a) {
    Group lines = new Group();
    elements.getChildren().add(lines);
    SimpleDoubleProperty turtleX = turtleNode.getXLocLines();
    SimpleDoubleProperty turtleY = turtleNode.getYLocLines();

    double startLocX = turtleX.doubleValue();
    double startLocY = turtleY.doubleValue();

    if (speed.doubleValue() == maxSpeed) {
      normalExecute(toPlay, lines, startLocX, startLocY);
      a.run();
    } else {
      animateExecute(toPlay, lines, turtleX, turtleY, startLocX, startLocY, speed, a);
    }
  }

  public void changeImage(String file) {
    String path = "data/turtleImages/" + file + ".gif";
    setImage(path);
  }

  public void setAnimationListener(ChangeListener<Boolean> func) {
    newAnimations.addListener(func);
  }

  public List<Pair<TurtleDrawer, TurtleAnimation>> getAnimations() {
    ArrayList<Pair<TurtleDrawer, TurtleAnimation>> ret = new ArrayList<>();
    if(!newAnimations.getValue()){
      return ret;
    }
    for(TurtleAnimation animation : currentTrans){
      ret.add(new Pair<>(this, animation));
    }
    currentTrans.clear();
    newAnimations.set(false);
    return ret;
  }





  private void animateExecute(TurtleAnimation toPlay, Group lines, SimpleDoubleProperty turtleX,
      SimpleDoubleProperty turtleY, double startLocX, double startLocY, DoubleProperty speed, Runnable a) {
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
      a.run();
    });
    ana.play();
  }

  private void normalExecute(TurtleAnimation toPlay, Group lines, double startLocX,
      double startLocY) {
    toPlay.execute();
    lines.getChildren().add(makeLines(startLocX, startLocY));
  }

  private Node makeLines(double startLocX, double startLocY) {
    return new WrapableLine(startLocX, startLocY, turtleNode.getXLocLines().doubleValue(),
        turtleNode.getYLocLines().doubleValue(), canvas, centerX, centerY, turtle.getPen());
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
    turtle.getVisibleProperty().addListener(e -> {
      currentTrans.add(visibleAnimation());
      newAnimations.set(true);
    });
    turtle.getCordsProperty().addListener(e -> {
      currentTrans.add(moveAnimation());
      newAnimations.set(true);
    });
    turtle.getAngleProperty().addListener(e -> {
      currentTrans.add(rotateAnimation());
      newAnimations.set(true);
    });
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
