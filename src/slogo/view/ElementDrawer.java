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

  /**
   * This changes all active turtle images.
   * @param file
   */
  public void setImageForAll(String file){
    currentImage  = file;
    for (TurtleDrawer d : turtleDrawers){
      setImage(d);
    }
  }

  /**
   * This changes the turtle image for a specific turtle.
   * @param td
   */
  public void setImage(TurtleDrawer td){
    td.changeImage(currentImage);
  }

  /**
   * This is a general method to reset all elements, it clears the turtles and returns to origin.
   */
  public void reset(){
    running = false;
    currentTrans.clear();
    elements.getChildren().clear();
    commandStruct.reset();
    drawnTurtles.clear();
    turtleDrawers.clear();
  }

  /**
   * This method is used to set the pane in which we add turtles.
   *
   * It is not done in constructor since the pane may have not been initialized.
   * @param canvas
   */
  public void setCanvas(Pane canvas){
    this.canvas = canvas;
    canvas.getChildren().add(elements);
  }

  /**
   * This runs through all the created turtles and instantiates an associated turtle drawer.
   */
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

  /**
   * This method runs the queue of animations created by the slogo code.
   * If the speed == max speed, then we remove the animations all together and just execute
   * instantaneously.
   * @param speed
   * @param maxSpeed
   */
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
