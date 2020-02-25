package slogo;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javax.swing.JPanel;

public class TurtleDrawer {

  public static void drawTurtle(Pane canvas, Turtle turtle){

    Circle newTurt = new Circle();
    newTurt.setRadius(turtle.getSize());

    NumberBinding xLoc =
        Bindings.add(Bindings.divide(canvas.widthProperty(),2), turtle.getX());

    newTurt.centerXProperty().bind(xLoc);

    NumberBinding yLoc =
        Bindings.add(Bindings.divide(canvas.heightProperty(),2), turtle.getY());

    newTurt.centerYProperty().bind(yLoc);


    canvas.getChildren().add(newTurt);
  }


}
