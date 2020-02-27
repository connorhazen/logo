package slogo;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class TurtleDrawer {

  private static final double TURTLE_SIZE = 75;
  private Group elements;
  private SimpleObjectProperty currentTurtleGif;

  public TurtleDrawer() {
    currentTurtleGif = new SimpleObjectProperty();
    elements = new Group();

  }

  public void addTurtleToCanvas(Pane canvas, Turtle turtle) {
    if(!canvas.getChildren().contains(elements)){
      canvas.getChildren().add(elements);
    }
    makeTurtleBind(canvas, turtle);
    makeLineBind(canvas, turtle);
  }

  private void makeLineBind(Pane canvas, Turtle turtle) {
    turtle.getHistory().addListener((ListChangeListener<Object>) c -> makeLine(canvas, turtle));
  }

  private void makeLine(Pane canvas, Turtle turtle) {
    if (turtle.getPenStatus() && turtle.getHistory().size() > 0) {
      double offsetX = canvas.getWidth() / 2;
      double offsetY = canvas.getHeight() / 2;

      Pair<Double, Double> lastLoc = (Pair<Double, Double>) turtle.getHistory()
          .get(turtle.getHistory().size() - 1);
      Line l = new Line(offsetX + lastLoc.getKey(), offsetY + lastLoc.getValue(),
          offsetX + turtle.getX(), offsetY + turtle.getY());
      elements.getChildren().add(l);
    }
  }

  private void makeTurtleBind(Pane canvas, Turtle turtle) {

    ImageView newTurt = new ImageView();

    newTurt.imageProperty().bind(currentTurtleGif);

    newTurt.setFitWidth(TURTLE_SIZE);
    newTurt.setFitHeight(TURTLE_SIZE);

    newTurt.visibleProperty().bind(turtle.getVisibleProperty());

    NumberBinding angle = Bindings.add(turtle.getAngleProperty(), 90);
    newTurt.rotateProperty().bind(angle);

    NumberBinding xLoc =
        Bindings.add(Bindings.divide(canvas.widthProperty(), 2), Bindings
            .subtract(turtle.getXProperty(), Bindings.divide(newTurt.fitWidthProperty(), 2)));
    newTurt.xProperty().bind(xLoc);

    NumberBinding yLoc =
        Bindings.add(Bindings.divide(canvas.heightProperty(), 2), Bindings
            .subtract(turtle.getYProperty(), Bindings.divide(newTurt.fitHeightProperty(), 2)));
    newTurt.yProperty().bind(yLoc);

    elements.getChildren().add(newTurt);

  }


  public void reset() {
    elements.getChildren().clear();
  }


  public void changeImage(String file) throws FileNotFoundException {
    String path = "src/resources/turtleImages/" + file + ".gif";
    setImage(path);
  }

  private void setImage(String path) throws FileNotFoundException {
    FileInputStream inputStream = new FileInputStream(path);
    currentTurtleGif.set(new Image(inputStream));
  }
}
