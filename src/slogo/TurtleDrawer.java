package slogo;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import javax.swing.JPanel;

public class TurtleDrawer {

  private Pane canvas;
  private Turtle turtle;
  private Group elements;
  private String defaultTurtle = "turtle";

  public TurtleDrawer(Pane canvas, Turtle turtle){
    elements = new Group();
    this.turtle = turtle;
    this.canvas = canvas;

    canvas.getChildren().add(elements);

  }



  public void addTurtleToCanvas(String image){
    makeTurtleBind(canvas, turtle, image);

    makeLineBind(canvas, turtle);
  }

  public void addTurtleToCanvas(){
    makeTurtleBind(canvas, turtle, defaultTurtle);

    makeLineBind(canvas, turtle);
  }

  private void makeLineBind(Pane canvas, Turtle turtle) {
    turtle.getHistory().addListener((ListChangeListener<Object>) c -> makeLine(canvas, turtle) );
  }

  private void makeLine(Pane canvas, Turtle turtle) {
    if(turtle.getPenStatus() && turtle.getHistory().size()>0){
      double offsetX = canvas.getWidth()/2;
      double offsetY = canvas.getHeight()/2;

      Pair<Double, Double> lastLoc = (Pair<Double, Double>) turtle.getHistory().get(turtle.getHistory().size()-1);
      Line l = new Line(offsetX+ lastLoc.getKey(), offsetY+lastLoc.getValue(), offsetX+ turtle.getX(), offsetY+turtle.getY());
      elements.getChildren().add(l);
    }
  }

  private void makeTurtleBind(Pane canvas, Turtle turtle, String file) {
    try{
      FileInputStream inputStream = new FileInputStream("src/resources/turtleImages/" + file + ".gif");

      defaultTurtle = file;

      Image turtleGif = new Image(inputStream);
      ImageView newTurt = new ImageView(turtleGif);
      newTurt.setFitWidth(turtle.getSize());
      newTurt.setFitHeight(turtle.getSize());


      NumberBinding angle = Bindings.add(turtle.getAngleProperty(), 90);
      newTurt.rotateProperty().bind(angle);

      NumberBinding xLoc =
          Bindings.add(Bindings.divide(canvas.widthProperty(),2), Bindings.subtract(turtle.getXProperty(),Bindings.divide(newTurt.fitWidthProperty(),2)));
      newTurt.xProperty().bind(xLoc);

      NumberBinding yLoc =
          Bindings.add(Bindings.divide(canvas.heightProperty(),2), Bindings.subtract(turtle.getYProperty(),Bindings.divide(newTurt.fitHeightProperty(),2)));
      newTurt.yProperty().bind(yLoc);

      elements.getChildren().add(newTurt);
    }
    catch (Exception e){
      if(file.equals(defaultTurtle)){
        System.out.println("default turtle missing");
        e.printStackTrace();
      }
      else{
        System.out.println("Turtle GIF not found : using default");
        makeTurtleBind(canvas, turtle, defaultTurtle);
      }
    }
  }


  public void clearCanvas() {
    elements.getChildren().clear();
    addTurtleToCanvas();
  }


}
