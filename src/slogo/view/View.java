package slogo.view;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
import slogo.ControllerInterface;
import slogo.ExceptionHelper;
import slogo.windows.BackgroundColor;
import slogo.windows.HelpWindow;
import slogo.windows.ImageSelection;
import slogo.windows.PenColorWindow;
import slogo.windows.SelectLanguage;

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

    errorHelper = new ExceptionHelper();
    currentTurtle = turtle;
    this.mainStage = primaryStage;
    controller = cont;
    makeScreen(primaryStage);

    scene = new Scene(createBorderPane() , WIDTH, HEIGHT);
    scene.getStylesheets().add(STYLESHEET);

    mainStage.setScene(scene);
    mainStage.show();
    makeKeyListens();
    makeTurtle();

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
    borderPane.setTop(hboxfactory());
    borderPane.setRight(createRightVBox());
    borderPane.setBottom(createBottomHBox());
    borderPane.setCenter(createMiddleCanvas());
    borderPane.setLeft(createLeftVbox());
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
    String colon = ":";
    List<String> labels = new ArrayList<>(ResourceBundle.getBundle("slogo.view.labels").keySet());
    VBox right = new VBox();
    right.getStyleClass().add("vbox");
    Label error = new Label(labels.get(0) + colon);
    errorBox = new TextArea();
    errorBox.setWrapText(true);

    Label his = new Label(labels.get(1) + colon);
    historyBox = new TextArea();


    right.getChildren().addAll(his, historyBox, error, errorBox);

    return right;
  }

  private VBox createLeftVbox(){
    VBox left = new VBox();
    left.getStyleClass().add("vbox");
    Label vars = new Label("Saved Variables:");
    TextArea varBox = new TextArea();

    Label coms = new Label("Saved Commands:");
    TextArea ta2 = new TextArea();

    left.getChildren().addAll(vars, varBox, coms, ta2);
    return left;
  }


  private HBox createBottomHBox(){
    //TODO: Use reflection to add run button
    HBox bottom = new HBox();
    bottom.getStyleClass().add("hbox-bot");
    inputBox = new TextArea();
    //ta.setOnKeyPressed(e -> submitText(e, ta.getText(), ta));
    Button run = makeButton("Run", e -> {runButtonEvent();});
    Button reset = makeButton("Reset", e -> {reset(currentTurtle);});
    run.getStyleClass().add("run-bot");
    reset.getStyleClass().add("run-bot");
    bottom.getChildren().addAll(inputBox, run, reset);
    return bottom;
  }

  private void runButtonEvent() {
    controller.executeCommand(inputBox.getText());
    boxHistory.add(inputBox.getText());
    inputBox.clear();
  }

  private HBox hboxfactory(){
    SettingsBar sb = new SettingsBar(controller, this);
    return (HBox) sb.getElement();
  }
  @Deprecated
  private HBox makeTopHBox(){
    HBox myBox = new HBox();
    Properties props = new Properties();
    HashMap<String, String> myButtonMap = new HashMap<>();
    try {
      props.load(View.class.getResourceAsStream("buttons.properties"));
      for(String key : props.stringPropertyNames()){
        myButtonMap.put(key, props.getProperty(key));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Class<?> thisView = View.class;
    Object obj = this;
    for(String key : myButtonMap.keySet()){
      for(Method m : thisView.getDeclaredMethods()){
        if(myButtonMap.get(key).equals(m.getName())){
          Button b = new Button(key);
          b.setOnAction(e -> {
            try {
              m.invoke(obj, null);
            } catch (IllegalAccessException | InvocationTargetException ex) {
              errorHelper.reflectionError(ex);
            }
          });
          myBox.getChildren().add(b);
        }
      }
    }
    myBox.getChildren().addAll(setLanguageWindow(), setImageWindow());
    return myBox;
  }

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

  private ChoiceBox setImageWindow() {
   return ImageSelection.imageDropDown(this);
  }

  @SuppressWarnings("Used in reflection")
  private void setBackGroundColorWindow() {
    BackgroundColor bc = new BackgroundColor(controller);
    launchWindow(bc);
  }

  @SuppressWarnings("Used in reflection")
  private void setPenColorWindow() {
    //PenColorWindow pcw = new PenColorWindow(controller);
    //launchWindow(pcw);
  }

  @SuppressWarnings("Used in reflection")
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

  private void makeTurtle(){
    drawer.addTurtleToCanvas(canvas, currentTurtle);
  }

  private void reset(Turtle turtle){
    turtle.reset();
    drawer.reset();
    errorBox.clear();
    historyBox.clear();
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

  private void changeInputBox(String replace){
    inputBox.clear();
    inputBox.appendText(replace);
  }

}
