package slogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
  private static final String STYLESHEET = "slogo/default.css";
  private static final String PROPERTIES = "src/slogo/button_properties.txt";
  private final ExceptionHelper errorHelper;

  public View(ControllerInterface cont, Stage primaryStage, Turtle turtle){
    errorHelper = new ExceptionHelper();
    currentTurtle = turtle;
    this.mainStage = primaryStage;
    controller = cont;
    makeScreen(primaryStage);
    scene = new Scene(createBorderPane(), WIDTH, HEIGHT);
    scene.getStylesheets().add(STYLESHEET);
    mainStage.setScene(scene);
    mainStage.show();
    makeTurtle(turtle);
  }

  /**
   * Methods for creating elements in the UI
   */

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
    Rectangle edges = new Rectangle();
    canvas.getChildren().add(edges);

    edges.widthProperty().bind(canvas.widthProperty());
    edges.heightProperty().bind(canvas.heightProperty());
    edges.setFill(Color.WHITE);

    return canvas;
  }

  private VBox createRightVBox(){
    VBox right = new VBox();
    right.getStyleClass().add("vbox");
    TextArea ta1 = new TextArea();
    TextArea ta2 = new TextArea();
    right.getChildren().add(ta1);
    right.getChildren().add(ta2);
    return right;
  }

  private HBox createTopHBox(){
    List<EventHandler<ActionEvent>> eae = getButtonActions();
    List<String> buttonMap = getButtonProperties();
    HBox hTop = new HBox();
    hTop.getStyleClass().add("hbox");
    try{
      int index = 0;
      for(String k : buttonMap){
        Class[] paraActionEvent = new Class[1];
        paraActionEvent[0] = EventHandler.class;
        Class[] paraString = new Class[1];
        paraString[0] = String.class;

        Object obj = Button.class.getDeclaredConstructor().newInstance();

        Method setText = Labeled.class.getDeclaredMethod("setText", paraString);
        setText.invoke(obj, k);

        Method setOnAction = ButtonBase.class.getDeclaredMethod("setOnAction", EventHandler.class);
        setOnAction.invoke(obj, (EventHandler<ActionEvent>) eae.get(index));
        hTop.getChildren().add((Button) obj);
        index++;
      }
      hTop.getChildren().add(setLanguageWindow());
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException cnfe){
      errorHelper.reflectionError(cnfe);
    }
    return hTop;
  }

  private HBox createBottomHBox(){
    //TODO: Use reflection to add run button
    HBox bottom = new HBox();
    bottom.getStyleClass().add("hbox-bot");
    TextArea ta = new TextArea();
    ta.setOnKeyPressed(e -> submitText(e, ta.getText(), ta));
    Button run = makeButton("Run", e -> {controller.executeCommand(ta.getText()); ta.clear();});
    run.setPrefHeight(bottom.getPrefHeight());
    bottom.getChildren().addAll(ta, run);
    return bottom;
  }

  /**
   * Helper methods for creating UI elements
   */

  private List<EventHandler<ActionEvent>> getButtonActions(){
    return new ArrayList<>() {{
      add(e -> helpWindow());
      add(e -> setPenColorWindow());
      add(e -> setBackGroundColorWindow());
      add(e -> setImageWindow());
    }};
  }

  private ArrayList<String> getButtonProperties() {
    ArrayList<String> buttonMap = new ArrayList<>();
    Scanner s;
    try{
      s = new Scanner(new File(PROPERTIES));
      s.useDelimiter(" ");
      while(s.hasNext()){
        buttonMap.add(s.next());
      }
    } catch(FileNotFoundException fnfe){
      errorHelper.fileNotFound(fnfe);
    }
    return buttonMap;
  }

  private void submitText(KeyEvent keyEvent, String commandText, TextArea ta) {
    if(keyEvent.getCode() == KeyCode.ENTER){
      controller.executeCommand(commandText);
      ta.clear();
    }
  }

  /**
   * Methods for launching settings windows
   */

  private void launchWindow(Application application){
    try {
      application.start(new Stage());
    } catch (Exception e) {
      errorHelper.fileNotFound(e);
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
