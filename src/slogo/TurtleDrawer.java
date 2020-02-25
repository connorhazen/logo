package slogo;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import javax.swing.JPanel;

public class TurtleDrawer {



  public static void addTurtleToCanvas(Pane canvas, Turtle turtle){

    makeTurtleBind(canvas, turtle);

    makeLineBind(canvas, turtle);
  }

  private static void makeLineBind(Pane canvas, Turtle turtle) {
    turtle.getHistory().addListener((ListChangeListener<Object>) c -> makeLine(canvas, turtle) );
  }

  private static void makeLine(Pane canvas, Turtle turtle) {
    if(turtle.getPenStatus() && turtle.getHistory().size()>0){
      double offsetX = canvas.getWidth()/2;
      double offsetY = canvas.getHeight()/2;

      Pair<Double, Double> lastLoc = (Pair<Double, Double>) turtle.getHistory().get(turtle.getHistory().size()-1);
      Line l = new Line(offsetX+ lastLoc.getKey(), offsetY+lastLoc.getValue(), offsetX+ turtle.getX(), offsetY+turtle.getY());
      canvas.getChildren().add(l);
    }
  }

  private static void makeTurtleBind(Pane canvas, Turtle turtle) {
    Circle newTurt = new Circle();
    newTurt.setRadius(turtle.getSize());

    NumberBinding xLoc =
        Bindings.add(Bindings.divide(canvas.widthProperty(),2), turtle.getXProperty());
    newTurt.centerXProperty().bind(xLoc);

    NumberBinding yLoc =
        Bindings.add(Bindings.divide(canvas.heightProperty(),2), turtle.getYProperty());
    newTurt.centerYProperty().bind(yLoc);

    canvas.getChildren().add(newTurt);
  }


  public static void clearCanvas(Pane canvas, Turtle turtle) {
    canvas.getChildren().clear();
    addTurtleToCanvas(canvas, turtle);
  }


}
