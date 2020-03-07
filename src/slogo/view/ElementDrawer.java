package slogo.view;

import java.awt.Canvas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
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
  private SimpleMapProperty<Integer, Turtle> turtles;
  private List<Turtle> drawnTurtles;
  private List<TurtleDrawer> turtleDrawers;
  private Group elements;
  private String currentImage;
  public ElementDrawer(CommandStruct commandStruct){
    this.commandStruct = commandStruct;
    turtles =  commandStruct.getTurtleMapProperty();
    drawnTurtles = new ArrayList<>();
    turtleDrawers = new ArrayList<>();
    elements = new Group();
    currentImage = "";
  }

  public void setCanvas(Pane canvas){
    this.canvas = canvas;
    canvas.getChildren().add(elements);

  }

  public void makeTurtles(){
    for (Turtle t : turtles.values()){
      if(!drawnTurtles.contains(t)){
        TurtleDrawer toAdd = new TurtleDrawer(t, canvas, elements);
        turtleDrawers.add(toAdd);
        setImage(toAdd);
      }
    }
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
  public void reset()
}
