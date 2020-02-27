package slogo;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ListChangeListener;
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
    try{
      FileInputStream inputStream = new FileInputStream("src/slogo/turtle.gif");
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

      canvas.getChildren().add(newTurt);
    }
    catch (Exception e){
      System.out.println("Turtle GIF not found");
      e.printStackTrace();
    }


  }


  public static void clearCanvas(Pane canvas, Turtle turtle) {
    canvas.getChildren().clear();
    addTurtleToCanvas(canvas, turtle);
  }


}
