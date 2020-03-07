package slogo.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.ExceptionHelper;
import slogo.view.ViewFactory.BorderPaneElement;
import slogo.view.ViewFactory.ElementFactory;
import slogo.view.ViewFactory.ElementMove;
import slogo.windows.BackgroundColor;
import slogo.windows.HelpWindow;
import slogo.windows.PenColorWindow;

public class View implements ViewInterface {

  private final int WIDTH = 1200;
  private final int HEIGHT = 600;

  private final ControllerInterface controller;
  private final Scene scene;
  private Stage mainStage;
  private Pane canvas;
  private Turtle currentTurtle;
  private static final String STYLESHEET = "slogo/view/default.css";
  private static final String PROPERTIES = "src/slogo/view/button_properties.txt";
  private final ExceptionHelper errorHelper;
  private final TurtleDrawer drawer;
  private TextArea errorBox;
  private TextArea historyBox;
  private TextArea inputBox;
  private CommandHistoryView boxHistory;
  private ArrayList<String> clickedCommands;
  private Color myColor;

  public View(ControllerInterface cont, Stage primaryStage, Turtle turtle){
    clickedCommands = new ArrayList<>();
    drawer = new TurtleDrawer();
    currentTurtle = turtle;
    canvas = new Pane();
    boxHistory = new CommandHistoryView();
    errorHelper = new ExceptionHelper();
    this.mainStage = primaryStage;
    controller = cont;
    instantiateGUIElements();
    scene = new Scene(createBorderPane() , WIDTH, HEIGHT);
    scene.getStylesheets().add(STYLESHEET);

    mainStage.setScene(scene);
    mainStage.show();
    makeKeyListens();

    makeTurtle();

  }

  private void instantiateGUIElements(){
    errorBox = new TextArea();
    historyBox = new TextArea();
    inputBox = new TextArea();
  }

  private void makeKeyListens(){
    inputBox.setOnKeyPressed(event ->  {
        switch (event.getCode()) {
          case UP:    changeInputBox(boxHistory.getPast()); break;
          case DOWN:  changeInputBox(boxHistory.getNext()); break;
        }
    });
  }

  private BorderPane createBorderPane(){
    BorderPane borderPane = new BorderPane();
    ElementFactory factory = ElementFactory.startFactory(controller, this, currentTurtle, clickedCommands);

    BorderPaneElement top = factory.getNode("SettingsBar", BorderPaneLocation.TOP);
    BorderPaneElement right = factory.getNode("RightView", BorderPaneLocation.RIGHT, errorBox, historyBox);
    BorderPaneElement bottom = factory.getNode("BottomView", BorderPaneLocation.BOTTOM, inputBox);
    BorderPaneElement left = factory.getNode("CommandView", BorderPaneLocation.LEFT);
    BorderPaneElement center = factory.getNode("CanvasView", BorderPaneLocation.CENTER, canvas);

    borderPane.setTop(top.getElement());
    borderPane.setRight(right.getElement());
    borderPane.setBottom(bottom.getElement());
    borderPane.setLeft(left.getElement());
    borderPane.setCenter(center.getElement());

    return borderPane;
  }

  private void launchWindow(Application application){
    try {
      application.start(new Stage());
    } catch (Exception e) {
      errorHelper.fileNotFound(e);
    }
  }

  @SuppressWarnings("Used in reflection")
  private void runButtonEvent() {
    controller.executeCommand(inputBox.getText());
    boxHistory.add(inputBox.getText());
    inputBox.clear();
  }

  @SuppressWarnings("Used in reflection")
  private void setBackGroundColorWindow() {
    BackgroundColor bc = new BackgroundColor(this);
    launchWindow(bc);
  }

  public void setBackGroundColor(Color color){
    if(color != null) {
      myColor = color;
      System.out.println(myColor.getBlue());
      Rectangle rct = (Rectangle)canvas.getChildren().get(0);
      rct.setFill(myColor);
    }
    else new ExceptionHelper().getErrorMessage(new NullPointerException("Color is null"));
  }

  @SuppressWarnings("Used in reflection")
  private void setPenColorWindow() {
    PenColorWindow pcw = new PenColorWindow(controller);
    launchWindow(pcw);
  }

  @SuppressWarnings("Used in reflection")
  private void helpWindow() {
    //TODO: Stylize help window so it doesn't look terrible
    launchWindow(new HelpWindow());
  }

  @SuppressWarnings("Used in reflection")
  private void reset(Turtle turtle){
    turtle.reset();
    drawer.reset();
    errorBox.clear();
    historyBox.clear();
    drawer.addTurtleToCanvas(canvas, currentTurtle);
  }

  private void makeTurtle(){
    drawer.addTurtleToCanvas(canvas, currentTurtle);
  }

  @Override
  public void printErrorFromException(Exception exception) {
    printError(exception.getMessage());
  }

  private void printError(String message){
    errorBox.appendText(message + "\n");
  }

  @Override
  public void setImage(String file){
    try {
      drawer.changeImage(file);
    }
    catch (Exception e){
      printError("TurtleFileNotFound");
    }

  }

  @Override
  public void printHistory(List<String> history) {
    for(String s : history){
      historyBox.appendText(s+"\n");
    }

  }

  @Override
  public void updateView(List<String> history) {
    SimpleDoubleProperty tempVal = new SimpleDoubleProperty(4);

    drawer.animate(tempVal, 4);
    printHistory(history);
    setBackGroundColor(currentTurtle.getBackgroundColor());
  }

  private void changeInputBox(String replace){
    inputBox.clear();
    inputBox.appendText(replace);
  }

}
