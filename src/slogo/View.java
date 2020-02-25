package slogo;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
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

  public View(ControllerInterface cont, Stage primaryStage, Turtle turtle){
    currentTurtle = turtle;
    this.mainStage = primaryStage;
    controller = cont;
    makeScreen(primaryStage);
    BorderPane pane = createBorderPane();
    scene = new Scene(pane, WIDTH, HEIGHT);
    scene.getStylesheets().add(STYLESHEET);
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
    Rectangle edges = new Rectangle();
    canvas.getChildren().add(edges);

    edges.widthProperty().bind(canvas.widthProperty());
    edges.heightProperty().bind(canvas.heightProperty());
    edges.setFill(Color.WHITE);

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

  private List<EventHandler<ActionEvent>> getButtonActions(){
    return new ArrayList<EventHandler<ActionEvent>>(){{
      add(e -> helpWindow());
      add(e -> setPenColorWindow());
      add(e -> setBackGroundColorWindow());
      add(e -> setImageWindow());
    }};
  }
  private HBox createTopHBox(){
    List<EventHandler<ActionEvent>> eae = getButtonActions();
    List<String> buttonMap = getButtonProperties();
    HBox hTop = new HBox();
    hTop.setSpacing(5);
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

    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException cnfe){
      //TODO: Proper error handling
      System.out.println("Error" + cnfe.toString());
    }
    hTop.getChildren().add(setLanguageWindow());
    return hTop;
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
      //TODO: Implement proper error handling
      System.out.println("File not found");
    }
    return buttonMap;
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
    //TODO make reset button
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
    TurtleDrawer.addTurtleToCanvas(canvas, turtle);
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
