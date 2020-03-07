package slogo.view;

import java.awt.Canvas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;
import slogo.ExceptionHelper;
import slogo.structs.CommandStruct;
import slogo.view.animations.MoveAnimation;
import slogo.view.animations.RotateAnimation;
import slogo.view.animations.TurtleAnimation;
import slogo.view.animations.VisibleAnimation;
import slogo.view.wrapableObjects.WrapableLine;
import slogo.view.wrapableObjects.WrapableTurtleImage;

public class ElementDrawer {
  private CommandStruct commandStruct;
  private Pane canvas;
  private Map<Integer, Turtle> turtles;
  private List<Turtle> drawnTurtles;
  private List<TurtleDrawer> turtleDrawers;
  private Group elements;
  private String currentImage;
  private LinkedList<Pair<TurtleDrawer,TurtleAnimation>> currentTrans;
  private boolean running;


  public ElementDrawer(CommandStruct commandStruct){
    this.commandStruct = commandStruct;
    turtles =  commandStruct.getTurtleMapProperty();
    drawnTurtles = new ArrayList<>();
    turtleDrawers = new ArrayList<>();
    elements = new Group();
    currentImage = "";
    running = false;
    currentTrans = new LinkedList<>();

  }

  public void setCanvas(Pane canvas){
    this.canvas = canvas;
    canvas.getChildren().add(elements);
  }

  public void makeTurtles(){
    turtles = commandStruct.getTurtleMapProperty();
    System.out.println(turtles.size());
    for (Turtle t : turtles.values()){
      if(!drawnTurtles.contains(t)){
        drawnTurtles.add(t);
        TurtleDrawer toAdd = new TurtleDrawer(t, canvas, elements);
        turtleDrawers.add(toAdd);
        setImage(toAdd);
        toAdd.setAnimationListener((e,ee,eee) -> getAnimations(toAdd));
      }
    }
    System.out.println(turtleDrawers.size());
  }

  private void getAnimations(TurtleDrawer toAdd) {
    currentTrans.addAll(toAdd.getAnimations());
  }

  public void run(SimpleDoubleProperty speed, double maxSpeed){
    if(!running){
      running = true;
      playAnimations(speed, maxSpeed);
    }

  }

  private void playAnimations(SimpleDoubleProperty speed, double maxSpeed) {
    if(currentTrans.isEmpty()){
      running = false;
      return;
    }

    Pair<TurtleDrawer, TurtleAnimation> toPlay = currentTrans.poll();
    toPlay.getKey().play(toPlay.getValue(), speed, maxSpeed);
    playAnimations(speed, maxSpeed);
  }

  public void setImageForAll(String file){
    currentImage  = file;
    for (TurtleDrawer d : turtleDrawers){
      setImage(d);
    }
  }
  public void setImage(TurtleDrawer td){
    td.changeImage(currentImage);
  }
  public void reset(){
    elements.getChildren().clear();
    drawnTurtles.clear();
    turtleDrawers.clear();
  }
}
