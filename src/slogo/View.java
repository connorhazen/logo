package slogo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.windows.BackgroundColor;
import slogo.windows.HelpWindow;
import slogo.windows.ImageSelection;
import slogo.windows.PenColorWindow;
import slogo.windows.SelectLanguage;

public class View implements ViewInterface {

  private final int WIDTH = 900;
  private final int HEIGHT = 600;


  private final ControllerInterface controller;
  private final Scene scene;
  private Stage mainStage;
  private Pane canvas;
  private Turtle currentTurtle;

  public View(ControllerInterface cont, Stage primaryStage, Turtle turtle){
    currentTurtle = turtle;
    this.mainStage = primaryStage;
    controller = cont;
    makeScreen(primaryStage);
    BorderPane pane = createBorderPane();
    scene = new Scene(pane, WIDTH, HEIGHT);
    mainStage.setScene(scene);
    mainStage.show();
    makeTurtle(turtle);
  }


  private BorderPane createBorderPane(){
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(createTopHBox());
    borderPane.setRight(createRightVBox());
    borderPane.setBottom(createBottomHBox());
    borderPane.setCenter(createMiddleCanvas());
    return borderPane;
  }

  private Pane createMiddleCanvas(){
    canvas = new Pane();
    canvas.setVisible(true);
    return canvas;
  }
  private VBox createRightVBox(){
    VBox right = new VBox();
    right.setSpacing(5);
    right.setPrefWidth(150);
    TextArea ta1 = new TextArea();
    TextArea ta2 = new TextArea();
    right.getChildren().add(ta1);
    right.getChildren().add(ta2);
    return right;
  }

  private HBox createTopHBox(){
    HBox hTop = new HBox();
    hTop.setSpacing(5);
    hTop.getChildren().add(makeButton("Help", e -> helpWindow()));
    hTop.getChildren().add(makeButton("Set Pen Color", e -> setPenColorWindow()));
    hTop.getChildren().add(makeButton("Set Background Color", e -> setBackGroundColorWindow()));
    hTop.getChildren().add(makeButton("Set Image", e -> setImageWindow()));
    hTop.getChildren().add(setLanguageWindow());
    return hTop;
  }

  private void launchWindow(Application application){
    try{
      application.start(new Stage());
    } catch (Exception e){
      //TODO: Implement proper error handling
      e.printStackTrace();
    }
  }
  private ChoiceBox setLanguageWindow() {
    return SelectLanguage.languageDropDown(controller);
  }

  private void setImageWindow() {
    ImageSelection iw = new ImageSelection(this);
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
    bottom.setPrefHeight(100);
    bottom.setSpacing(5);
    TextArea ta = new TextArea();
    ta.setOnKeyPressed(e -> submitText(e, ta.getText(), ta));
    Button run = makeButton("Run", e -> {controller.executeCommand(ta.getText()); ta.clear();});
    run.setPrefHeight(bottom.getPrefHeight());
    bottom.getChildren().addAll(ta, run);
    return bottom;
  }

  private void submitText(KeyEvent keyEvent, String commandText, TextArea ta) {
    if(keyEvent.getCode() == KeyCode.ENTER){
      controller.executeCommand(commandText);
      ta.clear();
    }
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

  private void makeTurtle(Turtle turtle){
    TurtleDrawer.drawTurtle(canvas, turtle);
  }

  private void reset(Turtle turtle){
    turtle.reset();
    TurtleDrawer.clearCanvas(canvas, turtle);
  }



  @Override
  public void printError(Exception exception) {

    System.out.println(exception.getMessage());

  }

}
