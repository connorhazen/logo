package slogo.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import slogo.structs.CommandStruct;
import slogo.view.animations.TurtleAnimation;


/**
 * This class serves as the creator for the various turtle objects located in the passed command struct.
 * It uses a boolean property combined with a listener to determine when the turtle map has changed
 * or is modified.
 *
 * It creates Turtle Drawer objects for each instance.
 */
public class ElementDrawer {
  private CommandStruct commandStruct;
  private Pane canvas;

  private List<Turtle> drawnTurtles;
  private List<TurtleDrawer> turtleDrawers;
  private Group elements;
  private String currentImage;
  private LinkedList<Pair<TurtleDrawer,TurtleAnimation>> currentTrans;
  private boolean running;


  public ElementDrawer(CommandStruct commandStruct){
    this.commandStruct = commandStruct;
    SimpleBooleanProperty mapChanged = commandStruct.getChangedMap();
    drawnTurtles = new ArrayList<>();
    turtleDrawers = new ArrayList<>();
    elements = new Group();
    currentImage = "";
    running = false;
    currentTrans = new LinkedList<>();

    mapChanged.addListener(e -> {
      if(canvas!=null){
        makeTurtles();
        commandStruct.resetChangedMap();
      }
    });


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
    running = false;
    currentTrans.clear();
    elements.getChildren().clear();
    commandStruct.reset();
    drawnTurtles.clear();
    turtleDrawers.clear();
  }

  public void setCanvas(Pane canvas){
    this.canvas = canvas;
    canvas.getChildren().add(elements);
  }

  public void makeTurtles(){
    Map<Integer, Turtle> turtles = commandStruct.getMyTurtleMap();
    for (Turtle t : turtles.values()){
      if(!drawnTurtles.contains(t)){
        drawnTurtles.add(t);
        TurtleDrawer toAdd = new TurtleDrawer(t, canvas, elements);
        turtleDrawers.add(toAdd);
        setImage(toAdd);
        toAdd.setAnimationListener((e,ee,eee) -> getAnimations(toAdd));
      }
    }
  }

  public void run(DoubleProperty speed, double maxSpeed){
    if(!running){
      running = true;
      playAnimations(speed, maxSpeed);
    }

  }





  private void getAnimations(TurtleDrawer toAdd) {
    currentTrans.addAll(toAdd.getAnimations());
  }

  private void playAnimations(DoubleProperty speed, double maxSpeed) {
    if(currentTrans.isEmpty()){
      running = false;
      return;
    }

    Pair<TurtleDrawer, TurtleAnimation> toPlay = currentTrans.poll();
    toPlay.getKey().play(toPlay.getValue(), speed, maxSpeed, () -> playAnimations(speed, maxSpeed));
  }
}
