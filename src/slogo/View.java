package slogo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import slogo.windows.BackgroundColor;
import slogo.windows.HelpWindow;
import slogo.windows.ImageSelection;
import slogo.windows.PenColorWindow;
import slogo.windows.SelectLanguage;

public class View implements ViewInterface {

  private final ControllerInterface controller;
  private final Scene scene;
  private final Circle tCircle;
  private Stage mainStage;

  public View(ControllerInterface cont, Stage primaryStage){
    this.mainStage = primaryStage;
    controller = cont;
    makeScreen(primaryStage);
    Group root = new Group();
    scene = new Scene(createBorderPane(), 500, 500);
    mainStage.setScene(scene);
    mainStage.show();


    tCircle = makeTurtle();
    root.getChildren().add(tCircle);
    root.getChildren().add(makeRun());

    //startAnimation();

  }

  private BorderPane createBorderPane(){
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(createTopHBox());
    borderPane.setRight(createRightVBox());
    borderPane.setBottom(createBottomHBox());
    borderPane.setStyle("-fx-padding: 10;");
    return borderPane;
  }

  private VBox createRightVBox(){
    VBox right = new VBox();
    TextArea ta1 = new TextArea();
    ta1.setStyle("-fx-max-width: 150;");
    TextArea ta2 = new TextArea();
    ta2.setStyle("-fx-max-width: 150;");
    right.getChildren().add(ta1);
    right.getChildren().add(ta2);
    return right;
  }

  private HBox createTopHBox(){
    HBox htop = new HBox();
    htop.setSpacing(5);
    htop.getChildren().add(makeButton("Help", e -> helpWindow()));
    htop.getChildren().add(makeButton("Set Pen Color", e -> setPenColorWindow()));
    htop.getChildren().add(makeButton("Set Background Color", e -> setBackGroundColorWindow()));
    htop.getChildren().add(makeButton("Set Image", e -> setImageWindow()));
    htop.getChildren().add(makeButton("Language", e -> setLanguageWindow()));
    return htop;
  }

  private void launchWindow(Application application){
    try{
      application.start(mainStage);
    } catch (Exception e){
      //TODO: Implement proper error handling
      e.printStackTrace();
    }
  }
  private void setLanguageWindow() {
    SelectLanguage sl = new SelectLanguage(controller);
    launchWindow(sl);
  }

  private void setImageWindow() {
    ImageSelection iw = new ImageSelection(controller);
    launchWindow(iw);
  }

  private void setBackGroundColorWindow() {
    BackgroundColor bc = new BackgroundColor(controller);
    launchWindow(bc);
  }

  private void setPenColorWindow() {
    PenColorWindow pcw = new PenColorWindow(controller);
    launchWindow(pcw);
  }

  private void helpWindow() {
    HelpWindow hw = new HelpWindow(controller);
    launchWindow(hw);
  }

  private HBox createBottomHBox(){
    HBox bottom = new HBox();
    TextArea ta = new TextArea();
    ta.setStyle("-fx-max-height: 100;");
    bottom.getChildren().add(ta);
    return bottom;
  }

  private Button makeButton(String title, EventHandler<ActionEvent> ae){
    Button btn = new Button(title);
    btn.setOnAction(ae);
    btn.setStyle("-fx-padding: 5px");
    return btn;
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
