package slogo;


import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View implements ViewInterface {

  private final ControllerInterface controller;
  private final Scene scene;
  private final Circle tCircle;

  public View(ControllerInterface cont, Stage primaryStage){
    controller = cont;
    makeScreen(primaryStage);
    Group root = new Group();
    scene = new Scene(root, 500, 500);
    primaryStage.setScene(scene);
    primaryStage.show();


    tCircle = makeTurtle();
    root.getChildren().add(tCircle);
    root.getChildren().add(makeRun());

    //startAnimation();

  }

//  private void startAnimation() {
//    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
//    Timeline animation = new Timeline();
//    animation.setCycleCount(Timeline.INDEFINITE);
//    animation.getKeyFrames().add(frame);
//    animation.play();
//  }
  private void makeScreen(Stage primaryStage){

  }
  private Circle makeTurtle(){
    Circle ret = new Circle(250,250,10);
    return ret;
  }

  private Button makeRun(){
    Button b = new Button();

    b.setOnAction(e -> {controller.updateModel("");});
    return b;

  }

  @Override
  public void updateView(Turtle turtle) {
    tCircle.setCenterX(turtle.getX());
    tCircle.setCenterY(turtle.getY());
  }



  @Override
  public void printError(Exception exception) {

  }

}
