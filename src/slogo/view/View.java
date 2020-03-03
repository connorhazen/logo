package slogo.view;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.ExceptionHelper;
import slogo.view.ViewFactory.ElementFactory;
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

  public View(ControllerInterface cont, Stage primaryStage, Turtle turtle){
    drawer = new TurtleDrawer();
    boxHistory = new CommandHistoryView();
    canvas = new Pane();
    errorHelper = new ExceptionHelper();
    currentTurtle = turtle;
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
    ElementFactory factory = ElementFactory.startFactory(controller, this, currentTurtle);
    borderPane.setTop(factory.getNode("SettingsBar").getElement());
    borderPane.setRight(factory.getNode("RightView", errorBox, historyBox).getElement());
    borderPane.setBottom(factory.getNode("BottomView", inputBox).getElement());
    borderPane.setCenter(factory.getNode("CanvasView", canvas).getElement());
    borderPane.setLeft(factory.getNode("CommandView").getElement());
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
    BackgroundColor bc = new BackgroundColor(controller);
    launchWindow(bc);
  }

  @SuppressWarnings("Used in reflection")
  private void setPenColorWindow() {
    PenColorWindow pcw = new PenColorWindow(controller);
    launchWindow(pcw);
  }

  @SuppressWarnings("Used in reflection")
  private void helpWindow() {
    HelpWindow hw = new HelpWindow(controller);
    launchWindow(hw);
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
    //drawer.animate(animationSpeed.valueProperty(), animationSpeed.getMax())
    drawer.animate();
    printHistory(history);
  }

  private void changeInputBox(String replace){
    inputBox.clear();
    inputBox.appendText(replace);
  }

}
